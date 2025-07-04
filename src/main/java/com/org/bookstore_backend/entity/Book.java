package com.org.bookstore_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/*@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "books")*/

@Entity
//this will hold data from database and each object of this class will represent a row in the database table.
//when
@Table(name = "books")
//relational database
//@ManyToMany relationship is used to define a many-to-many relationship between two entities.

public class Book {
    // Getters and setters
    @Id
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private int publicationYear;

    public void setId(Long id) { this.id = id; }

    public void setTitle(String title) { this.title = title; }

    public void setAuthor(String author) { this.author = author; }

    public void setPublicationYear(int publicationYear) {this.publicationYear = publicationYear;}

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return 0;
    }

    public String getPublicationDate() {
        return "";
    }

    public String getGenre() {
        return "";
    }

    public void setPrice(double price) {

    }

    public String getTitle() {
        return "";
    }

    public String getAuthor() {
        return "";
    }

    public void setGenre(String genre) {
    }

    public Object getId() {
        return null;
    }

    public void setPublicationDate(String publicationDate) {
    }
}