package com.org.bookstore_backend.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Entity
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id",length = 11)
    private int bookid;

    @Column(name = "book_title",length = 45)
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;


    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;


    @OneToMany(mappedBy = "book")
    private Set<Borrow> borrows;

    public Book(int bookid, String title, Author author, Publisher publisher) {
        this.bookid = bookid;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }



    public Book() {
    }

    public Book(String title, Author byId, Publisher byId1) {
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookid=" + bookid +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", publisher=" + publisher +
                '}';
    }

    public double getPrice() {
        return getPrice();
    }

    public Object getIsbn() {
        return getIsbn();
    }

    public void setIsbn(Object isbn) {
        // This method is intentionally left blank as the original code does not provide an implementation.
        return;
    }
}