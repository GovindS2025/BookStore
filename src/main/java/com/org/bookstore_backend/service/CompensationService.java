package com.org.bookstore_backend.service;

public interface CompensationService {

    /**
     * Release reserved inventory when order fails
     */
    boolean releaseInventory(String orderId, String bookIds);

    /**
     * Process refund for failed payment
     */
    boolean processRefund(String orderId, String paymentId, String reason);

    /**
     * Cancel shipment when order fails
     */
    boolean cancelShipment(String orderId, String trackingNumber);

    /**
     * Rollback user account changes
     */
    boolean rollbackUserChanges(String orderId, String userId);

    /**
     * Rollback cart changes
     */
    boolean rollbackCartChanges(String orderId, String userId);

    /**
     * Compensate all steps for a failed order
     */
    boolean compensateOrder(String orderId, String failureReason);
}
