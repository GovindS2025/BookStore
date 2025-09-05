package com.org.bookstore_backend.service.impl;

import com.org.bookstore_backend.events.DomainEvent;
import com.org.bookstore_backend.events.EventPublisher;
import com.org.bookstore_backend.service.CompensationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CompensationServiceImpl implements CompensationService {

    private final EventPublisher eventPublisher;

    @Override
    public boolean releaseInventory(String orderId, String bookIds) {
        try {
            log.info("Releasing inventory for order: {}, books: {}", orderId, bookIds);
            
            // Simulate inventory release (replace with actual inventory service call)
            // This would typically involve:
            // 1. Updating book quantities
            // 2. Releasing reserved inventory
            // 3. Updating inventory status
            
            // Publish compensation event
            publishCompensationEvent("inventory.released", orderId, Map.of(
                "action", "INVENTORY_RELEASED",
                "bookIds", bookIds,
                "reason", "Order compensation"
            ));
            
            log.info("Inventory released successfully for order: {}", orderId);
            return true;
            
        } catch (Exception e) {
            log.error("Failed to release inventory for order: {}", orderId, e);
            return false;
        }
    }

    @Override
    public boolean processRefund(String orderId, String paymentId, String reason) {
        try {
            log.info("Processing refund for order: {}, payment: {}, reason: {}", orderId, paymentId, reason);
            
            // Simulate refund processing (replace with actual payment service call)
            // This would typically involve:
            // 1. Calling payment gateway refund API
            // 2. Updating payment status
            // 3. Recording refund transaction
            
            // Publish compensation event
            publishCompensationEvent("refund.processed", orderId, Map.of(
                "action", "REFUND_PROCESSED",
                "paymentId", paymentId,
                "reason", reason,
                "amount", "full" // or specific amount
            ));
            
            log.info("Refund processed successfully for order: {}", orderId);
            return true;
            
        } catch (Exception e) {
            log.error("Failed to process refund for order: {}", orderId, e);
            return false;
        }
    }

    @Override
    public boolean cancelShipment(String orderId, String trackingNumber) {
        try {
            log.info("Canceling shipment for order: {}, tracking: {}", orderId, trackingNumber);
            
            // Simulate shipment cancellation (replace with actual shipment service call)
            // This would typically involve:
            // 1. Calling shipping carrier API
            // 2. Updating shipment status
            // 3. Recording cancellation
            
            // Publish compensation event
            publishCompensationEvent("shipment.canceled", orderId, Map.of(
                "action", "SHIPMENT_CANCELED",
                "trackingNumber", trackingNumber,
                "reason", "Order compensation"
            ));
            
            log.info("Shipment canceled successfully for order: {}", orderId);
            return true;
            
        } catch (Exception e) {
            log.error("Failed to cancel shipment for order: {}", orderId, e);
            return false;
        }
    }

    @Override
    public boolean rollbackUserChanges(String orderId, String userId) {
        try {
            log.info("Rolling back user changes for order: {}, user: {}", orderId, userId);
            
            // Simulate user changes rollback (replace with actual user service call)
            // This would typically involve:
            // 1. Reverting user points/credits
            // 2. Updating user statistics
            // 3. Rolling back any user-related changes
            
            // Publish compensation event
            publishCompensationEvent("user.changes.rolledback", orderId, Map.of(
                "action", "USER_CHANGES_ROLLEDBACK",
                "userId", userId,
                "reason", "Order compensation"
            ));
            
            log.info("User changes rolled back successfully for order: {}", orderId);
            return true;
            
        } catch (Exception e) {
            log.error("Failed to rollback user changes for order: {}", orderId, e);
            return false;
        }
    }

    @Override
    public boolean rollbackCartChanges(String orderId, String userId) {
        try {
            log.info("Rolling back cart changes for order: {}, user: {}", orderId, userId);
            
            // Simulate cart changes rollback (replace with actual cart service call)
            // This would typically involve:
            // 1. Restoring cart items
            // 2. Updating cart totals
            // 3. Rolling back any cart-related changes
            
            // Publish compensation event
            publishCompensationEvent("cart.changes.rolledback", orderId, Map.of(
                "action", "CART_CHANGES_ROLLEDBACK",
                "userId", userId,
                "reason", "Order compensation"
            ));
            
            log.info("Cart changes rolled back successfully for order: {}", orderId);
            return true;
            
        } catch (Exception e) {
            log.error("Failed to rollback cart changes for order: {}", orderId, e);
            return false;
        }
    }

    @Override
    public boolean compensateOrder(String orderId, String failureReason) {
        try {
            log.info("Starting full order compensation for order: {}, reason: {}", orderId, failureReason);
            
            // Execute all compensation steps in reverse order
            boolean success = true;
            
            // 1. Cancel shipment (if exists)
            success &= cancelShipment(orderId, "N/A");
            
            // 2. Process refund (if payment was processed)
            success &= processRefund(orderId, "N/A", failureReason);
            
            // 3. Release inventory
            success &= releaseInventory(orderId, "ALL");
            
            // 4. Rollback user changes
            success &= rollbackUserChanges(orderId, "N/A");
            
            // 5. Rollback cart changes
            success &= rollbackCartChanges(orderId, "N/A");
            
            // Publish overall compensation event
            publishCompensationEvent("order.compensated", orderId, Map.of(
                "action", "ORDER_FULLY_COMPENSATED",
                "reason", failureReason,
                "success", success
            ));
            
            if (success) {
                log.info("Order compensation completed successfully for order: {}", orderId);
            } else {
                log.warn("Order compensation completed with some failures for order: {}", orderId);
            }
            
            return success;
            
        } catch (Exception e) {
            log.error("Failed to compensate order: {}", orderId, e);
            return false;
        }
    }

    private void publishCompensationEvent(String topic, String orderId, Map<String, Object> payload) {
        try {
            DomainEvent event = DomainEvent.builder()
                    .type("COMPENSATION_EVENT")
                    .aggregateType("order")
                    .aggregateId(orderId)
                    .occurredAt(System.currentTimeMillis())
                    .payloadJson(payload.toString())
                    .build();
            
            eventPublisher.publish(topic, event);
            
        } catch (Exception e) {
            log.error("Failed to publish compensation event: {}", e.getMessage(), e);
        }
    }
}
