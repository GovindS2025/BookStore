package com.org.bookstore_backend.repo;

import com.org.bookstore_backend.model.SagaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SagaStatusRepo extends JpaRepository<SagaStatus, Long> {

    Optional<SagaStatus> findByOrderId(String orderId);

    List<SagaStatus> findByState(SagaStatus.SagaState state);

    List<SagaStatus> findBySagaType(String sagaType);

    @Query("SELECT s FROM SagaStatus s WHERE s.state IN ('FAILED', 'COMPENSATING') AND s.retryCount < :maxRetries")
    List<SagaStatus> findFailedSagasForRetry(@Param("maxRetries") Integer maxRetries);

    @Query("SELECT s FROM SagaStatus s WHERE s.startedAt < :cutoffTime AND s.state NOT IN ('COMPLETED', 'COMPENSATED')")
    List<SagaStatus> findStaleSagas(@Param("cutoffTime") LocalDateTime cutoffTime);

    @Query("SELECT COUNT(s) FROM SagaStatus s WHERE s.state = :state")
    Long countByState(@Param("state") SagaStatus.SagaState state);

    @Query("SELECT COUNT(s) FROM SagaStatus s WHERE s.startedAt >= :startDate AND s.startedAt < :endDate")
    Long countSagasInTimeRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
