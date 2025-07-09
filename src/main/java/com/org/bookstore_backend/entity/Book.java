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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id", length = 11)
    private Long bookid;

    @Column(name = "book_title", length = 45)
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(name = "isbn", length = 20)
    private String isbn;

    @Column(name = "publication_year", length = 4)
    private int publicationYear;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<Borrow> borrows;

    // Constructors
    public Book() {
    }

    public Book(Long bookid, String title, String isbn, Author author, Publisher publisher, int publicationYear, double price, Set<Borrow> borrows) {
        this.bookid = bookid;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
        this.price = price;
        this.borrows = borrows;
    }


    @Override
    public String toString() {
        return "Book{" +
                "bookid=" + bookid +
                ", title='" + title + '\'' +
                ", author=" + (author != null ? author.getName() : null) +
                ", isbn='" + isbn + '\'' +
                ", publicationYear=" + publicationYear +
                ", price=" + price + '\'' +
                ", publisher=" + (author != null ? author.getName() : null) +
                ", borrows=" + (borrows != null ? borrows.size() : 0) +
                '}';
    }


    public void setPublicationYear(Object publicationYear) {
        return ; // This method is not applicable for Book entity
    }
}
