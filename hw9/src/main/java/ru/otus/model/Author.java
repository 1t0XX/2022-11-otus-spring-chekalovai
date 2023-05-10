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
@Table(name = "t_author")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(name = "name")
    private String name;
    @NotEmpty
    @Column(name = "surname")
    private String surName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Author author = (Author) o;

        return new EqualsBuilder().append(id, author.id).append(name, author.name).append(surName, author.surName).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(name).append(surName).toHashCode();
    }

    @Override
    public String toString() {
        return "Author{" + "id=" + id + ", name='" + name + '\'' + ", surName='" + surName + '\'' + '}';
    }
}