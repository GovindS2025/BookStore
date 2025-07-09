package com.org.bookstore_backend.DTO;

import com.org.bookstore_backend.entity.Author;
import com.org.bookstore_backend.entity.Borrow;
import com.org.bookstore_backend.entity.Publisher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {
    private Long id;
    private String title;
    private AuthorDTO author;
    private String isbn;
    private int publicationYear;
    private double price;
    private PublisherDTO publisher;
    private boolean borrowed;
}