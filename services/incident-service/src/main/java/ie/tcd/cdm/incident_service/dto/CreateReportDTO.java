package ie.tcd.cdm.incident_service.dto;

import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record CreateReportDTO(
        @NotNull
        long id,
        @NotNull
        String name,
        @NotNull
        String location,
        @NotNull
        String emergencyType,
        @NotNull
        String emergencySeverity,
        @NotNull
        String generalDescription,
        @NotNull
        Timestamp timeReceived
){}