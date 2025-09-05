package com.org.bookstore_backend.service.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.bookstore_backend.dto.SagaStatusDTO;
import com.org.bookstore_backend.events.DomainEvent;
import com.org.bookstore_backend.events.EventPublisher;
import com.org.bookstore_backend.model.SagaEvent;
import com.org.bookstore_backend.model.SagaStatus;
import com.org.bookstore_backend.repo.SagaEventRepo;
import com.org.bookstore_backend.repo.SagaStatusRepo;
import com.org.bookstore_backend.service.CompensationService;
import com.org.bookstore_backend.service.SagaCoordinatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SagaCoordinatorServiceImpl implements SagaCoordinatorService {

    private final SagaStatusRepo sagaStatusRepo;
    private final SagaEventRepo sagaEventRepo;
    private final CompensationService compensationService;
    private final EventPublisher eventPublisher;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final int MAX_RETRIES = 3;
    private static final long SAGA_TIMEOUT_MINUTES = 30;

    @Override
    public SagaStatusDTO startOrderSaga(String orderId, String orderData) {
        log.info("Starting order saga for order ID: {}", orderId);

        // Check if saga already exists
        Optional<SagaStatus> existingSaga = sagaStatusRepo.findByOrderId(orderId);
        if (existingSaga.isPresent()) {
            log.warn("Saga already exists for order ID: {}", orderId);
            return SagaStatusDTO.fromEntity(existingSaga.get());
        }

        // Create new saga status
        SagaStatus sagaStatus = SagaStatus.builder()
                .orderId(orderId)
                .sagaType("ORDER_PROCESSING")
                .state(SagaStatus.SagaState.STARTED)
                .metadata(Map.of("orderData", orderData))
                .build();

        SagaStatus savedSaga = sagaStatusRepo.save(sagaStatus);

        // Create initial saga event
        SagaEvent initialEvent = SagaEvent.builder()
                .orderId(orderId)
                .stepName("INVENTORY_RESERVATION")
                .eventType("SAGA_STARTED")
                .status(SagaEvent.EventStatus.PENDING)
                .payload(orderData)
                .build();

        sagaEventRepo.save(initialEvent);

        // Publish saga started event
        publishSagaEvent("saga.started", orderId, "SAGA_STARTED", orderData);

        log.info("Order saga started successfully for order ID: {}", orderId);
        return SagaStatusDTO.fromEntity(savedSaga);
    }

    @Override
    public SagaStatusDTO executeNextStep(String orderId) {
        log.info("Executing next step for order saga: {}", orderId);

        SagaStatus sagaStatus = sagaStatusRepo.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Saga not found for order: " + orderId));

        if (sagaStatus.getState() == SagaStatus.SagaState.COMPLETED || 
            sagaStatus.getState() == SagaStatus.SagaState.COMPENSATED) {
            log.info("Saga already completed/compensated for order: {}", orderId);
            return SagaStatusDTO.fromEntity(sagaStatus);
        }

        try {
            switch (sagaStatus.getState()) {
                case STARTED:
                    return executeInventoryReservation(orderId, sagaStatus);
                case INVENTORY_RESERVED:
                    return executePaymentProcessing(orderId, sagaStatus);
                case PAYMENT_PROCESSED:
                    return executeShipmentCreation(orderId, sagaStatus);
                case SHIPMENT_CREATED:
                    return completeSaga(orderId, sagaStatus);
                default:
                    log.warn("Unknown saga state: {} for order: {}", sagaStatus.getState(), orderId);
                    return SagaStatusDTO.fromEntity(sagaStatus);
            }
        } catch (Exception e) {
            log.error("Error executing saga step for order: {}", orderId, e);
            return handleSagaFailure(orderId, sagaStatus, e.getMessage());
        }
    }

    private SagaStatusDTO executeInventoryReservation(String orderId, SagaStatus sagaStatus) {
        log.info("Executing inventory reservation for order: {}", orderId);
        
        // Create saga event
        SagaEvent event = createSagaEvent(orderId, "INVENTORY_RESERVATION", "IN_PROGRESS");
        
        try {
            // Simulate inventory reservation (replace with actual inventory service call)
            TimeUnit.MILLISECONDS.sleep(500);
            
            // Update saga state
            sagaStatus.setState(SagaStatus.SagaState.INVENTORY_RESERVED);
            sagaStatus.setRetryCount(0);
            SagaStatus savedSaga = sagaStatusRepo.save(sagaStatus);
            
            // Update event status
            event.setStatus(SagaEvent.EventStatus.COMPLETED);
            event.setExecutedAt(LocalDateTime.now());
            sagaEventRepo.save(event);
            
            // Publish event
            publishSagaEvent("saga.inventory.reserved", orderId, "INVENTORY_RESERVED", "{}");
            
            log.info("Inventory reservation completed for order: {}", orderId);
            return SagaStatusDTO.fromEntity(savedSaga);
            
        } catch (InterruptedException e) {
            log.error("Inventory reservation interrupted for order: {}", orderId, e);
            Thread.currentThread().interrupt(); // Restore interrupted status
            event.setStatus(SagaEvent.EventStatus.FAILED);
            event.setErrorMessage("Inventory reservation was interrupted: " + e.getMessage());
            sagaEventRepo.save(event);
            throw new RuntimeException("Inventory reservation was interrupted", e);
        } catch (Exception e) {
            log.error("Inventory reservation failed for order: {}", orderId, e);
            event.setStatus(SagaEvent.EventStatus.FAILED);
            event.setErrorMessage(e.getMessage());
            sagaEventRepo.save(event);
            throw e;
        }
    }

    private SagaStatusDTO executePaymentProcessing(String orderId, SagaStatus sagaStatus) {
        log.info("Executing payment processing for order: {}", orderId);
        
        SagaEvent event = createSagaEvent(orderId, "PAYMENT_PROCESSING", "IN_PROGRESS");
        
        try {
            // Simulate payment processing (replace with actual payment service call)
            TimeUnit.MILLISECONDS.sleep(800);
            
            sagaStatus.setState(SagaStatus.SagaState.PAYMENT_PROCESSED);
            sagaStatus.setRetryCount(0);
            SagaStatus savedSaga = sagaStatusRepo.save(sagaStatus);
            
            event.setStatus(SagaEvent.EventStatus.COMPLETED);
            event.setExecutedAt(LocalDateTime.now());
            sagaEventRepo.save(event);
            
            publishSagaEvent("saga.payment.processed", orderId, "PAYMENT_PROCESSED", "{}");
            
            log.info("Payment processing completed for order: {}", orderId);
            return SagaStatusDTO.fromEntity(savedSaga);
            
        } catch (InterruptedException e) {
            log.error("Payment processing interrupted for order: {}", orderId, e);
            Thread.currentThread().interrupt(); // Restore interrupted status
            event.setStatus(SagaEvent.EventStatus.FAILED);
            event.setErrorMessage("Payment processing was interrupted: " + e.getMessage());
            sagaEventRepo.save(event);
            throw new RuntimeException("Payment processing was interrupted", e);
        } catch (Exception e) {
            log.error("Payment processing failed for order: {}", orderId, e);
            event.setStatus(SagaEvent.EventStatus.FAILED);
            event.setErrorMessage(e.getMessage());
            sagaEventRepo.save(event);
            throw e;
        }
    }

    private SagaStatusDTO executeShipmentCreation(String orderId, SagaStatus sagaStatus) {
        log.info("Executing shipment creation for order: {}", orderId);
        
        SagaEvent event = createSagaEvent(orderId, "SHIPMENT_CREATION", "IN_PROGRESS");
        
        try {
            // Simulate shipment creation (replace with actual shipment service call)
            TimeUnit.MILLISECONDS.sleep(600);
            
            sagaStatus.setState(SagaStatus.SagaState.SHIPMENT_CREATED);
            sagaStatus.setRetryCount(0);
            SagaStatus savedSaga = sagaStatusRepo.save(sagaStatus);
            
            event.setStatus(SagaEvent.EventStatus.COMPLETED);
            event.setExecutedAt(LocalDateTime.now());
            sagaEventRepo.save(event);
            
            publishSagaEvent("saga.shipment.created", orderId, "SHIPMENT_CREATED", "{}");
            
            log.info("Shipment creation completed for order: {}", orderId);
            return SagaStatusDTO.fromEntity(savedSaga);
            
        } catch (InterruptedException e) {
            log.error("Shipment creation interrupted for order: {}", orderId, e);
            Thread.currentThread().interrupt(); // Restore interrupted status
            event.setStatus(SagaEvent.EventStatus.FAILED);
            event.setErrorMessage("Shipment creation was interrupted: " + e.getMessage());
            sagaEventRepo.save(event);
            throw new RuntimeException("Shipment creation was interrupted", e);
        } catch (Exception e) {
            log.error("Shipment creation failed for order: {}", orderId, e);
            event.setStatus(SagaEvent.EventStatus.FAILED);
            event.setErrorMessage(e.getMessage());
            sagaEventRepo.save(event);
            throw e;
        }
    }

    private SagaStatusDTO completeSaga(String orderId, SagaStatus sagaStatus) {
        log.info("Completing saga for order: {}", orderId);
        
        sagaStatus.setState(SagaStatus.SagaState.COMPLETED);
        sagaStatus.setCompletedAt(LocalDateTime.now());
        SagaStatus savedSaga = sagaStatusRepo.save(sagaStatus);
        
        SagaEvent event = createSagaEvent(orderId, "SAGA_COMPLETION", "COMPLETED");
        event.setExecutedAt(LocalDateTime.now());
        sagaEventRepo.save(event);
        
        publishSagaEvent("saga.completed", orderId, "SAGA_COMPLETED", "{}");
        
        log.info("Saga completed successfully for order: {}", orderId);
        return SagaStatusDTO.fromEntity(savedSaga);
    }

    private SagaStatusDTO handleSagaFailure(String orderId, SagaStatus sagaStatus, String errorMessage) {
        log.error("Handling saga failure for order: {} - {}", orderId, errorMessage);
        
        sagaStatus.setState(SagaStatus.SagaState.FAILED);
        sagaStatus.setFailedAt(LocalDateTime.now());
        sagaStatus.setFailureReason(errorMessage);
        sagaStatus.setRetryCount(sagaStatus.getRetryCount() + 1);
        
        SagaStatus savedSaga = sagaStatusRepo.save(sagaStatus);
        
        // Create failure event
        SagaEvent failureEvent = createSagaEvent(orderId, "SAGA_FAILURE", "FAILED");
        failureEvent.setErrorMessage(errorMessage);
        sagaEventRepo.save(failureEvent);
        
        // Publish failure event
        publishSagaEvent("saga.failed", orderId, "SAGA_FAILED", errorMessage);
        
        // Start compensation if max retries exceeded
        if (sagaStatus.getRetryCount() >= MAX_RETRIES) {
            CompletableFuture.runAsync(() -> startCompensation(orderId, errorMessage));
        }
        
        return SagaStatusDTO.fromEntity(savedSaga);
    }

    private void startCompensation(String orderId, String failureReason) {
        log.info("Starting compensation for failed saga order: {}", orderId);
        
        try {
            sagaStatusRepo.findByOrderId(orderId).ifPresent(sagaStatus -> {
                sagaStatus.setState(SagaStatus.SagaState.COMPENSATING);
                sagaStatusRepo.save(sagaStatus);
            });
            
            // Execute compensation steps
            compensationService.compensateOrder(orderId, failureReason);
            
            // Mark as compensated
            sagaStatusRepo.findByOrderId(orderId).ifPresent(sagaStatus -> {
                sagaStatus.setState(SagaStatus.SagaState.COMPENSATED);
                sagaStatusRepo.save(sagaStatus);
            });
            
            publishSagaEvent("saga.compensated", orderId, "SAGA_COMPENSATED", failureReason);
            log.info("Compensation completed for order: {}", orderId);
            
        } catch (Exception e) {
            log.error("Compensation failed for order: {}", orderId, e);
            publishSagaEvent("saga.compensation.failed", orderId, "COMPENSATION_FAILED", e.getMessage());
        }
    }

    private SagaEvent createSagaEvent(String orderId, String stepName, String eventType) {
        SagaEvent event = SagaEvent.builder()
                .orderId(orderId)
                .stepName(stepName)
                .eventType(eventType)
                .status(SagaEvent.EventStatus.PENDING)
                .build();
        
        return sagaEventRepo.save(event);
    }

    private void publishSagaEvent(String topic, String orderId, String eventType, String payload) {
        try {
            DomainEvent event = DomainEvent.builder()
                    .type(eventType)
                    .aggregateType("saga")
                    .aggregateId(orderId)
                    .occurredAt(System.currentTimeMillis())
                    .payloadJson(payload)
                    .build();
            
            eventPublisher.publish(topic, event);
        } catch (Exception e) {
            log.error("Failed to publish saga event: {}", e.getMessage(), e);
        }
    }

    @Override
    public SagaStatusDTO compensateStep(String orderId, String stepName) {
        log.info("Compensating step {} for order: {}", stepName, orderId);
        
        // Implementation would depend on the specific step being compensated
        // For now, we'll trigger full compensation
        startCompensation(orderId, "Manual compensation requested for step: " + stepName);
        
        return getSagaStatus(orderId);
    }

    @Override
    public SagaStatusDTO getSagaStatus(String orderId) {
        return sagaStatusRepo.findByOrderId(orderId)
                .map(SagaStatusDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Saga not found for order: " + orderId));
    }

    @Override
    public List<SagaStatusDTO> getSagasByState(SagaStatus.SagaState state) {
        return sagaStatusRepo.findByState(state)
                .stream()
                .map(SagaStatusDTO::fromEntity)
                .toList();
    }

    @Override
    public List<SagaStatusDTO> retryFailedSagas() {
        List<SagaStatus> failedSagas = sagaStatusRepo.findFailedSagasForRetry(MAX_RETRIES);
        List<SagaStatusDTO> retriedSagas = new ArrayList<>();
        
        for (SagaStatus saga : failedSagas) {
            try {
                SagaStatusDTO retriedSaga = executeNextStep(saga.getOrderId());
                retriedSagas.add(retriedSaga);
            } catch (Exception e) {
                log.error("Failed to retry saga for order: {}", saga.getOrderId(), e);
            }
        }
        
        return retriedSagas;
    }

    @Override
    public SagaStatistics getSagaStatistics() {
        long totalSagas = sagaStatusRepo.count();
        long completedSagas = sagaStatusRepo.countByState(SagaStatus.SagaState.COMPLETED);
        long failedSagas = sagaStatusRepo.countByState(SagaStatus.SagaState.FAILED);
        long compensatingSagas = sagaStatusRepo.countByState(SagaStatus.SagaState.COMPENSATING);
        
        // Calculate average execution time (simplified)
        long averageExecutionTimeMs = 0; // This would require more complex calculation
        
        return new SagaStatistics(totalSagas, completedSagas, failedSagas, compensatingSagas, averageExecutionTimeMs);
    }

    @Override
    public SagaStatusDTO forceCompleteSaga(String orderId) {
        log.warn("Force completing saga for order: {}", orderId);
        return completeSaga(orderId, sagaStatusRepo.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Saga not found for order: " + orderId)));
    }

    @Override
    public SagaStatusDTO forceFailSaga(String orderId, String reason) {
        log.warn("Force failing saga for order: {} - {}", orderId, reason);
        return handleSagaFailure(orderId, sagaStatusRepo.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Saga not found for order: " + orderId)), reason);
    }

    // Kafka consumer for saga events
    @KafkaListener(
        topics = "saga.commands", 
        groupId = "saga-coordinator",
        autoStartup = "true",
        id = "saga-command-consumer"
    )
    @ConditionalOnProperty(name = "spring.kafka.enabled", havingValue = "true", matchIfMissing = true)
    public void handleSagaCommand(@Payload String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        try {
            log.info("Received saga command from topic {}: {}", topic, message);
            // Process saga commands (start, execute, compensate, etc.)
            // Implementation would depend on the specific command structure
        } catch (Exception e) {
            log.error("Error processing saga command: {}", e.getMessage(), e);
        }
    }

    // Scheduled task to handle stale sagas
    @Scheduled(fixedRate = 300000) // Every 5 minutes
    public void handleStaleSagas() {
        LocalDateTime cutoffTime = LocalDateTime.now().minus(SAGA_TIMEOUT_MINUTES, ChronoUnit.MINUTES);
        List<SagaStatus> staleSagas = sagaStatusRepo.findStaleSagas(cutoffTime);
        
        for (SagaStatus staleSaga : staleSagas) {
            log.warn("Found stale saga for order: {}, state: {}", staleSaga.getOrderId(), staleSaga.getState());
            handleSagaFailure(staleSaga.getOrderId(), staleSaga, "Saga timeout exceeded");
        }
    }

    // Scheduled task to retry failed sagas
    @Scheduled(fixedRate = 60000) // Every minute
    public void retryFailedSagasScheduled() {
        retryFailedSagas();
    }
}
