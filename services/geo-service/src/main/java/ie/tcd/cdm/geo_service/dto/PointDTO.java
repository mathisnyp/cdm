package ie.tcd.cdm.geo_service.dto;

import jakarta.validation.constraints.NotNull;

public record PointDTO(
        @NotNull
        double latitude,
        @NotNull
        double longitude
) {

}
