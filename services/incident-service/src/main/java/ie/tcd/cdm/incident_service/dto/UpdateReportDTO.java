package ie.tcd.cdm.incident_service.dto;


import java.sql.Timestamp;

public record UpdateReportDTO(

        String name,

        String location,
        
        String emergencyType,
        
        int emergencySeverity,
        
        String generalDescription,
        
        Timestamp timeReceived
){}
