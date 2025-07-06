package com.org.bookstore_backend.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

//@Data
//@Document(collection = "authors")
@Entity
@Table(name="author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "author_id",length = 11)
    private Long authorid;

    @Column(name = "name",length = 45)
    private String name;
    //One to Many relationship means in simple words one author can write many books.
    //It is a unidirectional relationship, meaning that the Author entity knows about the Book entity, but not vice versa.
    // The @OneToMany annotation is used to define this relationship.
    // The mappedBy attribute indicates that the Book entity has a field named "author" that maps to this relationship.
    // The Set<Book> books field represents the collection of books written by the author.
    // The @OneToMany annotation is used to define a one-to-many relationship between Author and Book entities.
    // // The mappedBy attribute indicates that the Book entity has a field named "author" that maps to this relationship.
      @OneToMany(mappedBy = "author")
//    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
  //  private List<Book> books;
      private Set<Book> books;
      public Author(Long authorid, String name) {
        this.authorid = authorid;
        this.name = name;
    }
    public Author(String name) {
        this.name = name;
    }
    public Author() {
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorid=" + authorid +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
          return getName();
    }

    public long getAuthorid() {
          return getAuthorid();
    }

    public void setName(String name) {
        setName(name);
    }
}