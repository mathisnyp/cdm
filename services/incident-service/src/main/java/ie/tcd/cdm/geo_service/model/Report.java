package ie.tcd.cdm.geo_service.model;

import lombok.Getter;

public class Report {
    @Getter
    public String name;
    @Getter
    String location;
    @Getter
    String emergencyType;
    @Getter
    String emergencySeverity;
    @Getter
    String generalDescription;
    @Getter
    String timeReceived;

    public Report(String name, String location, String emergencyType, String emergencySeverity,
                   String generalDescription, String timeReceived){
        this.name = name;
        this.location = location;
        this.emergencyType = emergencyType;
        this.emergencySeverity = emergencySeverity;
        this.generalDescription = generalDescription;
        this.timeReceived = timeReceived;
    }

}