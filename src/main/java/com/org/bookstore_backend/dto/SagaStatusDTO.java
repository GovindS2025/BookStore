package com.org.bookstore_backend.dto;

import com.org.bookstore_backend.model.SagaStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SagaStatusDTO {

    private Long id;
    private String orderId;
    private String sagaType;
    private String state;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private LocalDateTime failedAt;
    private String failureReason;
    private Integer retryCount;
    private String compensationData;
    private Map<String, Object> metadata;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static SagaStatusDTO fromEntity(SagaStatus sagaStatus) {
        return SagaStatusDTO.builder()
                .id(sagaStatus.getId())
                .orderId(sagaStatus.getOrderId())
                .sagaType(sagaStatus.getSagaType())
                .state(sagaStatus.getState().name())
                .startedAt(sagaStatus.getStartedAt())
                .completedAt(sagaStatus.getCompletedAt())
                .failedAt(sagaStatus.getFailedAt())
                .failureReason(sagaStatus.getFailureReason())
                .retryCount(sagaStatus.getRetryCount())
                .compensationData(sagaStatus.getCompensationData())
                .metadata(sagaStatus.getMetadata())
                .createdAt(sagaStatus.getCreatedAt())
                .updatedAt(sagaStatus.getUpdatedAt())
                .build();
    }
}
