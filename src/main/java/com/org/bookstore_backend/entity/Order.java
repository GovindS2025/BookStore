package com.org.bookstore_backend.entity;
import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime orderDate;

    // Add other fields as needed

    public Order() {}

    public Order(User user, LocalDateTime orderDate) {
        this.user = user;
        this.orderDate = orderDate;
    }
}
