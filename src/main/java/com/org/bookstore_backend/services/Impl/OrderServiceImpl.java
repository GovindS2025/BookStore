package com.org.bookstore_backend.services.Impl;

import com.org.bookstore_backend.entity.Order;
import com.org.bookstore_backend.repo.OrderRepo;
import com.org.bookstore_backend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Override
    public Order saveOrder(Order order) {
        return orderRepo.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepo.findById(id);
        return order.orElse(null);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepo.deleteById(id);
    }
}

