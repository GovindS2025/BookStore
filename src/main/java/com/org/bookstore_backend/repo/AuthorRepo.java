package com.org.bookstore_backend.repo;

import com.org.bookstore_backend.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Long> {
    // Add custom query methods if needed
}
