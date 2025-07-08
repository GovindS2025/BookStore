package com.org.bookstore_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "book")
public class Book {

    // Getters and setters
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id", length = 11)
    private int bookid;

    @Column(name = "book_title", length = 45)
    private String title;

    @Column(name = "isbn", length = 20)
    private String isbn;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<Borrow> borrows;

    // Constructors
    public Book() {}

    public Book(int bookid, String title, String isbn, double price, Author author, Publisher publisher) {
        this.bookid = bookid;
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.author = author;
        this.publisher = publisher;
    }

    public Book(String title, String isbn, double price, Author author, Publisher publisher) {
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.author = author;
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookid=" + bookid +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", price=" + price +
                ", author=" + (author != null ? author.getName() : null) +
                ", publisher=" + (publisher != null ? publisher.getName() : null) +
                '}';
    }
}
