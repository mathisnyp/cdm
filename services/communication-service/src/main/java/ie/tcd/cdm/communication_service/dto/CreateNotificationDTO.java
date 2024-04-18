package ie.tcd.cdm.communication_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.Map;

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
        Timestamp scheduledTime,
        @JsonProperty
        Map<String, Object> data
){}
