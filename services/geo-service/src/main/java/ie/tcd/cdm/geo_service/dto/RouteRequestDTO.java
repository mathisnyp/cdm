package ie.tcd.cdm.geo_service.dto;

import jakarta.validation.constraints.NotNull;

public record RouteRequestDTO(
        @NotNull
        PointDTO location,
        @NotNull
        String destination
) {
}
