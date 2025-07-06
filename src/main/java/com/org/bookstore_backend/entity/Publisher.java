package com.org.bookstore_backend.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="publisher")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "publisher_id",length = 11)
    private int publisherid;

    @Column(name = "name",length = 45)
    private String name;

    @OneToMany(mappedBy = "publisher")
    private Set<Book> books;

    public Publisher(int publisherid, String name) {
        this.publisherid = publisherid;
        this.name = name;
    }

    public Publisher(String name) {
        this.name = name;
    }

    public Publisher() {
    }

    public int getPublisherid() {
        return publisherid;
    }

    public void setPublisherid(int publisherid) {
        this.publisherid = publisherid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


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