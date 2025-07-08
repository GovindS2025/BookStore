package com.org.bookstore_backend.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="publisher")
public class Publisher {


    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "publisher_id",length = 11)
    private int publisherid;

    @Setter
    @Getter
    @Column(name = "name",length = 45)
    private String name;

    @OneToMany(mappedBy = "publisher")
    private Set<Book> books;

    public Publisher(int publisherid, String name, Set<Book> books) {
        this.publisherid = publisherid;
        this.name = name;
        this.books = books;
    }

    public Publisher(String name, Set<Book> books) {
        this.name = name;
        this.books = books;
    }

    public Publisher(Set<Book> books) {
        this.books = books;
    }

    public Publisher(String name) {
        this.name = name;
    }

//    public void setPublisherid(int publisherid) {
//        this.publisherid = publisherid;
//    }


    @Override
    public String toString() {
        return "Publisher{" +
                "publisherid=" + publisherid +
                ", name='" + name + '\'' +
                '}';
    }

    public void setId(String id) {
        try {
            this.publisherid = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid publisher ID: " + id, e);
        }
    }

    public <E> List<E> getBookIds() {
        if (books == null) {
            return List.of();
        }
        return (List<E>) books.stream()
                .map(Book::getBookid)
                .toList();
    }

}