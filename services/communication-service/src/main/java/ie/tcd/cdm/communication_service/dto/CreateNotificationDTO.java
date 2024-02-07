package ie.tcd.cdm.communication_service.dto;

import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record CreateNotificationDTO(
        @NotNull
        String messageBody,
        @NotNull
        String messageHeader,
        @NotNull
        int messageSender,
        @NotNull
        int receiver,
        @NotNull
        String receiverGroup,
        @NotNull
        Timestamp scheduledTime
){}
