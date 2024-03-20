package ie.tcd.cdm.incident_service.dto;


import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record UpdateEventDTO(
        String eventName,

        String eventType,

        String venue,

        int attendees,

        String generalDescription,

        Timestamp eventTime
){}