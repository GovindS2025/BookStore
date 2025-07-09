package com.org.bookstore_backend.DTO;

import com.org.bookstore_backend.entity.Author;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class BookSaveDTO {

    private Long id;
    private String title;
    private AuthorDTO author;
    private String isbn;
    private int publicationYear;
    private double price;
    private PublisherDTO publisher;
    private boolean borrowed;

}