package com.org.bookstore_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BorrowSaveDTO {

    private String title;
    private int author_id;
    private  int publisher_id;

    public LocalDate getBorrowDate() {
        return LocalDate.now();
    }

    public String getBook_id() {
        return "book_id";
    }

    public Object getReturnDate() {
        return "returnDate";
    }

    public Long getUser_id() {
        return 1L; // Assuming a default user ID for the sake of example
    }
}