package com.org.bookstore_backend.repo;

import com.org.bookstore_backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.mongodb.repository.MongoRepository;

/*public interface BookRepo extends MongoRepository<Book, String> {
//Book is our entity and String is type of ID.
}*/
public interface BookRepo extends JpaRepository<Book, String> {
//Book is our entity and String is type of ID.
}
