package ie.tcd.cdm.incident_service.dto;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;

public record CreateIncidentDTO(
        @NotNull
        Long id,
        @NotNull
        //ArrayList<Integer> reportIds,
        Integer reportIds,
        @NotNull
        String incidentName
){}
