package ie.tcd.cdm.communication_service.dto;

import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record CreateNotificationDTO(
        @NotNull
        String messageBody,
        @NotNull
        String messageHeader,
        @NotNull
        long messageSender,
        @NotNull
        long receiver,
        @NotNull
        String receiverGroup,
        @NotNull
        Timestamp scheduledTime
){}
