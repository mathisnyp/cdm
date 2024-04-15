package ie.tcd.cdm.geo_service.dto;

import jakarta.validation.constraints.NotNull;

public record DangerRequestDTO(
        @NotNull
        PointDTO location
) {
}
