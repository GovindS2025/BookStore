package com.org.bookstore_backend.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface Author extends MongoRepository<Author, String> {
}
