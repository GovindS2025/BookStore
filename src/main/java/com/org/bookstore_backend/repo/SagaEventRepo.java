package com.org.bookstore_backend.repo;

import com.org.bookstore_backend.model.SagaEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SagaEventRepo extends JpaRepository<SagaEvent, Long> {

    List<SagaEvent> findByOrderIdOrderByCreatedAtAsc(String orderId);

    List<SagaEvent> findByOrderIdAndStepName(String orderId, String stepName);

    Optional<SagaEvent> findByOrderIdAndStepNameAndStatus(String orderId, String stepName, SagaEvent.EventStatus status);

    @Query("SELECT e FROM SagaEvent e WHERE e.orderId = :orderId AND e.status = 'FAILED' ORDER BY e.createdAt DESC")
    List<SagaEvent> findFailedEventsByOrderId(@Param("orderId") String orderId);

    @Query("SELECT e FROM SagaEvent e WHERE e.status = 'PENDING' AND e.retryCount < :maxRetries ORDER BY e.createdAt ASC")
    List<SagaEvent> findPendingEventsForRetry(@Param("maxRetries") Integer maxRetries);

    @Query("SELECT COUNT(e) FROM SagaEvent e WHERE e.orderId = :orderId AND e.status = :status")
    Long countEventsByOrderIdAndStatus(@Param("orderId") String orderId, @Param("status") SagaEvent.EventStatus status);
}
