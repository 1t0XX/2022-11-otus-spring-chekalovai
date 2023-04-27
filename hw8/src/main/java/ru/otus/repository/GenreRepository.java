package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.model.Genre;


public interface GenreRepository extends MongoRepository<Genre, String>, GenreRepositoryCustom {
}
