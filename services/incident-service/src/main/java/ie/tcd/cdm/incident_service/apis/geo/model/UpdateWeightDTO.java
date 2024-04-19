package ie.tcd.cdm.incident_service.apis.geo.model;

import jakarta.validation.constraints.NotNull;

public record UpdateWeightDTO(
        @NotNull
        String streetName
) {
}
