package com.org.bookstore_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookSaveDTO {

    private String title;
    private int author_id;
    private  int publisher_id;

    public String getIsbn() {
        return null; // Placeholder for ISBN, as it is not provided in the original code
    }

    public double getPrice() {
        return 0.0; // Placeholder for price, as it is not provided in the original code
    }
}