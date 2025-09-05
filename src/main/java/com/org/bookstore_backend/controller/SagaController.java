package com.org.bookstore_backend.controller;

import com.org.bookstore_backend.dto.SagaStatusDTO;
import com.org.bookstore_backend.model.SagaStatus;
import com.org.bookstore_backend.service.SagaCoordinatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class SagaController {

    private final SagaCoordinatorService sagaCoordinatorService;

    /**
     * POST /api/v1/orders - Create a new order and start saga workflow
     */
    @PostMapping("/orders")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<SagaStatusDTO> createOrder(@RequestBody Map<String, Object> orderRequest) {
        try {
            log.info("Creating new order with saga workflow: {}", orderRequest);
            
            String orderId = generateOrderId();
            String orderData = orderRequest.toString();
            
            SagaStatusDTO sagaStatus = sagaCoordinatorService.startOrderSaga(orderId, orderData);
            
            log.info("Order saga started successfully: {}", sagaStatus);
            return ResponseEntity.ok(sagaStatus);
            
        } catch (Exception e) {
            log.error("Failed to create order with saga: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * GET /api/v1/sagas/{orderId} - Get saga status by order ID
     */
    @GetMapping("/sagas/{orderId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<SagaStatusDTO> getSagaStatus(@PathVariable String orderId) {
        try {
            log.info("Retrieving saga status for order: {}", orderId);
            
            SagaStatusDTO sagaStatus = sagaCoordinatorService.getSagaStatus(orderId);
            
            return ResponseEntity.ok(sagaStatus);
            
        } catch (RuntimeException e) {
            log.warn("Saga not found for order: {}", orderId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error retrieving saga status for order: {}", orderId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * POST /api/v1/sagas/{orderId}/execute - Execute next saga step
     */
    @PostMapping("/sagas/{orderId}/execute")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SagaStatusDTO> executeNextStep(@PathVariable String orderId) {
        try {
            log.info("Executing next saga step for order: {}", orderId);
            
            SagaStatusDTO sagaStatus = sagaCoordinatorService.executeNextStep(orderId);
            
            return ResponseEntity.ok(sagaStatus);
            
        } catch (Exception e) {
            log.error("Error executing saga step for order: {}", orderId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * POST /api/v1/sagas/{orderId}/compensate - Compensate a saga step
     */
    @PostMapping("/sagas/{orderId}/compensate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SagaStatusDTO> compensateStep(
            @PathVariable String orderId,
            @RequestParam String stepName) {
        try {
            log.info("Compensating saga step {} for order: {}", stepName, orderId);
            
            SagaStatusDTO sagaStatus = sagaCoordinatorService.compensateStep(orderId, stepName);
            
            return ResponseEntity.ok(sagaStatus);
            
        } catch (Exception e) {
            log.error("Error compensating saga step for order: {}", orderId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * GET /api/v1/sagas/state/{state} - Get sagas by state
     */
    @GetMapping("/sagas/state/{state}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SagaStatusDTO>> getSagasByState(@PathVariable String state) {
        try {
            log.info("Retrieving sagas with state: {}", state);
            
            SagaStatus.SagaState sagaState = SagaStatus.SagaState.valueOf(state.toUpperCase());
            List<SagaStatusDTO> sagas = sagaCoordinatorService.getSagasByState(sagaState);
            
            return ResponseEntity.ok(sagas);
            
        } catch (IllegalArgumentException e) {
            log.warn("Invalid saga state: {}", state);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error retrieving sagas by state: {}", state, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * POST /api/v1/sagas/retry - Retry failed sagas
     */
    @PostMapping("/sagas/retry")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SagaStatusDTO>> retryFailedSagas() {
        try {
            log.info("Retrying failed sagas");
            
            List<SagaStatusDTO> retriedSagas = sagaCoordinatorService.retryFailedSagas();
            
            return ResponseEntity.ok(retriedSagas);
            
        } catch (Exception e) {
            log.error("Error retrying failed sagas: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * GET /api/v1/sagas/statistics - Get saga statistics
     */
    @GetMapping("/sagas/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SagaCoordinatorService.SagaStatistics> getSagaStatistics() {
        try {
            log.info("Retrieving saga statistics");
            
            SagaCoordinatorService.SagaStatistics statistics = sagaCoordinatorService.getSagaStatistics();
            
            return ResponseEntity.ok(statistics);
            
        } catch (Exception e) {
            log.error("Error retrieving saga statistics: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * POST /api/v1/sagas/{orderId}/force-complete - Force complete a saga (admin only)
     */
    @PostMapping("/sagas/{orderId}/force-complete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SagaStatusDTO> forceCompleteSaga(@PathVariable String orderId) {
        try {
            log.warn("Force completing saga for order: {}", orderId);
            
            SagaStatusDTO sagaStatus = sagaCoordinatorService.forceCompleteSaga(orderId);
            
            return ResponseEntity.ok(sagaStatus);
            
        } catch (Exception e) {
            log.error("Error force completing saga for order: {}", orderId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * POST /api/v1/sagas/{orderId}/force-fail - Force fail a saga (admin only)
     */
    @PostMapping("/sagas/{orderId}/force-fail")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SagaStatusDTO> forceFailSaga(
            @PathVariable String orderId,
            @RequestParam String reason) {
        try {
            log.warn("Force failing saga for order: {} - {}", orderId, reason);
            
            SagaStatusDTO sagaStatus = sagaCoordinatorService.forceFailSaga(orderId, reason);
            
            return ResponseEntity.ok(sagaStatus);
            
        } catch (Exception e) {
            log.error("Error force failing saga for order: {}", orderId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    private String generateOrderId() {
        return "ORD-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
    }
}
