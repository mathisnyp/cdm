package ie.tcd.cdm.incident_service.dto;

import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record CreateEventDTO(
        @NotNull
        String eventName,
        @NotNull
        String eventType,
        @NotNull
        String venue,
        @NotNull
        Integer attendees,
        @NotNull
        String generalDescription,
        @NotNull
        Timestamp eventTime
){}
