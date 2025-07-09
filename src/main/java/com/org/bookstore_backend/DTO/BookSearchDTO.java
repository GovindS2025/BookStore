package com.org.bookstore_backend.DTO;

import com.org.bookstore_backend.entity.Author;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BookSearchDTO {
    private Long id;
    private String title;
    private Author author;
    private String isbn;
    private int publicationYear;
    private double price;
    private String publisher;
    private boolean borrowed;

}
