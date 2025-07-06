package com.org.bookstore_backend.repo;

import com.org.bookstore_backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    // Add custom queries if needed
}

