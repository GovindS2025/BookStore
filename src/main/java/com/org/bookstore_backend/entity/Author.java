package com.org.bookstore_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "author_id", length = 11)
    private Long authorid;

    @Column(name = "name", length = 45)
    private String name;

    // One-to-many relationship: One author can have many books
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Book> books;

    // Optional constructor for convenience (only name)
    public Author(String name) {
        this.name = name;
    }
}
