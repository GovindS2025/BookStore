package com.org.bookstore_backend.service;

import com.org.bookstore_backend.dto.SagaStatusDTO;
import com.org.bookstore_backend.model.SagaStatus;

import java.util.List;

public interface SagaCoordinatorService {

    /**
     * Start a new order saga workflow
     */
    SagaStatusDTO startOrderSaga(String orderId, String orderData);

    /**
     * Execute the next step in the saga
     */
    SagaStatusDTO executeNextStep(String orderId);

    /**
     * Compensate a failed saga step
     */
    SagaStatusDTO compensateStep(String orderId, String stepName);

    /**
     * Get saga status by order ID
     */
    SagaStatusDTO getSagaStatus(String orderId);

    /**
     * Get all sagas with a specific state
     */
    List<SagaStatusDTO> getSagasByState(SagaStatus.SagaState state);

    /**
     * Retry failed saga steps
     */
    List<SagaStatusDTO> retryFailedSagas();

    /**
     * Get saga statistics
     */
    SagaStatistics getSagaStatistics();

    /**
     * Force complete a saga (admin only)
     */
    SagaStatusDTO forceCompleteSaga(String orderId);

    /**
     * Force fail a saga (admin only)
     */
    SagaStatusDTO forceFailSaga(String orderId, String reason);

    record SagaStatistics(
        long totalSagas,
        long completedSagas,
        long failedSagas,
        long compensatingSagas,
        long averageExecutionTimeMs
    ) {}
}
