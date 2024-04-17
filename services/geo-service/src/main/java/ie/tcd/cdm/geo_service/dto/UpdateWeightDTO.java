package ie.tcd.cdm.geo_service.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateWeightDTO(
        @NotNull
        String streetName
) {
}
