package ru.otus.writer;

import com.mongodb.bulk.BulkWriteResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.model.EntityWithStringId;

import java.util.List;

public class MongoItemWriterWithExtractedId<T extends EntityWithStringId> extends MongoItemWriter<T> {

    private static final String ID_KEY = "_id";
    private final String collection;

    private boolean delete = false;

    public MongoItemWriterWithExtractedId(String collection) {
        super();
        this.collection = collection;
    }

    @Override
    public void setDelete(boolean delete) {
        this.delete = delete;
    }


    private void saveOrUpdate(List<? extends T> items) {
        BulkOperations bulkOperations = initBulkOperations(items.get(0));
        MongoConverter mongoConverter = this.getTemplate().getConverter();
        FindAndReplaceOptions upsert = new FindAndReplaceOptions().upsert();
        for (Object item : items) {
            Document document = new Document();
            mongoConverter.write(item, document);
            Object objectId = document.get(ID_KEY) != null ? document.get(ID_KEY) : new ObjectId();
            Query query = new Query().addCriteria(Criteria.where(ID_KEY).is(objectId));
            bulkOperations.replaceOne(query, document, upsert);
        }
        BulkWriteResult result = bulkOperations.execute();
        if (!CollectionUtils.isEmpty(result.getInserts())) {
            result.getInserts().forEach(res -> {
                String id = res.getId().asString().getValue();
                items.get(res.getIndex()).setId(id);
            });
        }
        if (!CollectionUtils.isEmpty(result.getUpserts())) {
            result.getUpserts().forEach(res -> {
                String id = res.getId().asObjectId().getValue().toString();
                items.get(res.getIndex()).setId(id);
            });
        }
        System.out.println();
    }


    private BulkOperations initBulkOperations(Object item) {
        BulkOperations bulkOperations;
        if (StringUtils.hasText(this.collection)) {
            bulkOperations = this.getTemplate().bulkOps(BulkOperations.BulkMode.ORDERED, this.collection);
        } else {
            bulkOperations = this.getTemplate().bulkOps(BulkOperations.BulkMode.ORDERED, ClassUtils.getUserClass(item));
        }
        return bulkOperations;
    }

    @Override
    protected void doWrite(List<? extends T> items) {
        if (!CollectionUtils.isEmpty(items)) {
            if (this.delete) {
                delete(items);
            } else {
                saveOrUpdate(items);
            }
        }
    }

    private void delete(List<? extends T> items) {
        BulkOperations bulkOperations = initBulkOperations(items.get(0));
        MongoConverter mongoConverter = this.getTemplate().getConverter();
        for (Object item : items) {
            Document document = new Document();
            mongoConverter.write(item, document);
            Object objectId = document.get(ID_KEY);
            if (objectId != null) {
                Query query = new Query().addCriteria(Criteria.where(ID_KEY).is(objectId));
                bulkOperations.remove(query);
            }
        }
        bulkOperations.execute();
    }
}