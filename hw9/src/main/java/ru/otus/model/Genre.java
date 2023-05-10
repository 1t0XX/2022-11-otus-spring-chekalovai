package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "t_genre")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Genre genre = (Genre) o;

        return new EqualsBuilder().append(id, genre.id).append(name, genre.name).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(name).toHashCode();
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}