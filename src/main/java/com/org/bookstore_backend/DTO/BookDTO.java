package com.org.bookstore_backend.DTO;

import com.org.bookstore_backend.entity.Author;
import com.org.bookstore_backend.entity.Publisher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {

    private int bookid;
    private String title;
    private Author author;
    private Publisher publisher;
}