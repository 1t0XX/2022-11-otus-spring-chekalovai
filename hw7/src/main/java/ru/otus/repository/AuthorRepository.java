package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {}