package com.org.bookstore_backend.services;

import com.org.bookstore_backend.entity.Order;
import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    void deleteOrder(Long id);
}

