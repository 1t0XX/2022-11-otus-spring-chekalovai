package ru.otus.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.model.Author;
import ru.otus.model.Book;
import ru.otus.model.CommentBook;
import ru.otus.model.Genre;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentBookRepository;
import ru.otus.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@ChangeLog(order = "init-task")
public class InitMongoChangelog {
    private final List<Author> authors = new ArrayList<>();
    private final List<Genre> genres = new ArrayList<>();
    private final List<Book> books = new ArrayList<>();

    @ChangeSet(order = "000", id = "dropDB", author = "chekalovai", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "chekalovai", runAlways = true)
    public void initAuthors(AuthorRepository repository){
        Stream.of(new Author(null, "Михаил","Булгаков"),
                        new Author(null, "Джек","Лондон"),
                        new Author(null, "Иван","Ефремов"))
                .forEach(a -> authors.add(repository.save(a)));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "chekalovai", runAlways = true)
    public void initGenres(GenreRepository repository){
        Stream.of(new Genre(null, "Повесть"),
                        new Genre(null, "Научная фантастика"),
                        new Genre(null, "Рассказ"))
                .forEach(g -> genres.add(repository.save(g)));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "chekalovai", runAlways = true)
    public void initBooks(BookRepository repository){
        Stream.of(new Book(null, "Лезвие бритвы", authors.get(2),genres.get(1)),
                        new Book(null, "Собачье сердце", authors.get(0),genres.get(0)),
                        new Book(null, "Белый клык", authors.get(1),genres.get(0)),
                        new Book(null, "Морфий", authors.get(0),genres.get(2)),
                        new Book(null, "Роковые яйца", authors.get(0),genres.get(0)))
                .forEach(b -> books.add(repository.save(b)));
    }

    @ChangeSet(order = "004", id = "initBookComments", author = "chekalovai", runAlways = true)
    public void initBookComments(CommentBookRepository repository){
        Stream.of(new CommentBook(null, "Читал", books.get(0)),
                        new CommentBook(null, "Понравилось", books.get(0)),
                        new CommentBook(null, "Читал", books.get(1)),
                        new CommentBook(null, "Прикольно", books.get(1)),
                        new CommentBook(null, "Читал", books.get(2)),
                        new CommentBook(null, "Интересно", books.get(2)),
                        new CommentBook(null, "Читал", books.get(3)),
                        new CommentBook(null, "Хорошо", books.get(3)),
                        new CommentBook(null, "Интересно", books.get(3)),
                        new CommentBook(null, "Гуд", books.get(4)),
                        new CommentBook(null, "Норм", books.get(4)))
                .forEach(repository::save);
    }
}