package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClearServiceImpl implements ClearService{

    private final MongoTemplate mongoTemplate;

    public void clear() {
        mongoTemplate.getDb().drop();
    }
}