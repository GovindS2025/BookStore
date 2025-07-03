package com.org.bookstore_backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

/*public interface Author extends MongoRepository<Author, String> {
}*/
public interface AuthorRepo extends JpaRepository<AuthorRepo, String> {
    String getName();

    void setName(String name);
}
