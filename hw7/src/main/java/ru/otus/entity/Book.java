package ru.otus.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

import static ru.otus.entity.Book.BOOK_AUTHOR_GENRE_GRAPH;

@Entity
@Table(name = "t_book")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NamedEntityGraph(name = BOOK_AUTHOR_GENRE_GRAPH, attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("genre")})
public class Book {

    public static final String BOOK_AUTHOR_GENRE_GRAPH = "book-author_genre-graph";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id")
    private Author author;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Book book = (Book) o;

        return new EqualsBuilder().append(id, book.id).append(name, book.name).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(name).toHashCode();
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", name='" + name + '}';
    }
}