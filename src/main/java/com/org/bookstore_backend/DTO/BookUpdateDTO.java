package com.org.bookstore_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookUpdateDTO {

    private String bookid;
    private String title;
    private int author_id;
    private  int publisher_id;
}