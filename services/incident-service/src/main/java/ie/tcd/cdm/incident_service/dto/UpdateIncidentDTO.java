package ie.tcd.cdm.incident_service.dto;


import java.util.ArrayList;
public record UpdateIncidentDTO(

        Long id,

        //ArrayList<Integer> reportIds,
        Integer reportIds,

        String incidentName
){}