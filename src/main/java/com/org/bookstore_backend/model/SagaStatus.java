package com.org.bookstore_backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "saga_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SagaStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderId;

    @Column(nullable = false)
    private String sagaType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SagaState state;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    @Column
    private LocalDateTime completedAt;

    @Column
    private LocalDateTime failedAt;

    @Column(columnDefinition = "TEXT")
    private String failureReason;

    @Column
    private Integer retryCount;

    @Column(columnDefinition = "TEXT")
    private String compensationData;

    @Column(columnDefinition = "JSONB")
    @JdbcTypeCode(java.sql.Types.OTHER)
    private Map<String, Object> metadata;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (startedAt == null) {
            startedAt = LocalDateTime.now();
        }
        if (retryCount == null) {
            retryCount = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum SagaState {
        STARTED,
        INVENTORY_RESERVED,
        PAYMENT_PROCESSED,
        SHIPMENT_CREATED,
        COMPLETED,
        FAILED,
        COMPENSATING,
        COMPENSATED
    }
}
