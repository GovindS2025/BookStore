package com.org.bookstore_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BorrowDTO {
    private int id;
    private BookDTO book;
    private UserDTO user;
    private LocalDate borrowDate;
    private LocalDate returnDate;
}
