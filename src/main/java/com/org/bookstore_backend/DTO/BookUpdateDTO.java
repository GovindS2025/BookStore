package com.org.bookstore_backend.DTO;

import com.org.bookstore_backend.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookUpdateDTO {

    private Long id;
    private String title;
    private AuthorDTO author;
    private String isbn;
    private int publicationYear;
    private double price;
    private PublisherDTO publisher;
    private boolean borrowed;
}