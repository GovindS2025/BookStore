package com.org.bookstore_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "Publishers")
/*@Document(collection = "publishers")*/
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Publisher {

        @Id
        private String id;
        private String name;

        // One publisher has many books

        private List<Book> books = new ArrayList<>();

    public <E> List<E> getBookIds() {
        List<E> bookIds = new ArrayList<>();
        for (Book book : books) {
            bookIds.add((E) book.getId());
        }
        return bookIds;
    }
}


