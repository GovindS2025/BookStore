package com.org.bookstore_backend.repo;

import com.org.bookstore_backend.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepo extends JpaRepository<Borrow, Integer> {
    // This interface will automatically provide CRUD operations for the Borrow entity
    // Additional custom query methods can be defined here if needed
}
