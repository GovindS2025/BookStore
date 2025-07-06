package com.org.bookstore_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BorrowUpdateDTO {
        private int bookid;
        private String title;
        private int author_id;
        private  int publisher_id;

    public Integer getId() {
        return getId();
    }

    public String getBook_id() {
        return getBook_id();
    }

    public Long getUser_id() {
        return getUser_id();
    }

    public LocalDate getBorrowDate() {
        return LocalDate.now();
    }

    public LocalDate getReturnDate() {
        return LocalDate.now().plusDays(14); // Assuming a 14-day borrowing period
    }
}

