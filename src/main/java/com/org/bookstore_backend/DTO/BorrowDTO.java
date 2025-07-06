package com.org.bookstore_backend.DTO;

import com.org.bookstore_backend.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BorrowDTO {

    private int id;
    private Book book;
    private User user;
    private LocalDate borrowDate;
    private LocalDate returnDate;


}