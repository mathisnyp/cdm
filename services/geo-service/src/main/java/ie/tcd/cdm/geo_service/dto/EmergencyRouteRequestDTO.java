package ie.tcd.cdm.geo_service.dto;

import jakarta.validation.constraints.NotNull;

public record EmergencyRouteRequestDTO(
        @NotNull
        PointDTO location,
        @NotNull
        PointDTO destination) {
}
