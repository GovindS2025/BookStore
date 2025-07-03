package com.org.bookstore_backend.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
//@Document(collection = "authors")
@Entity
@Table(name = "authors")
public class Author {

    @Id
    private Long id;

    private String name;
    private String biography;
    private int birthYear;

}
