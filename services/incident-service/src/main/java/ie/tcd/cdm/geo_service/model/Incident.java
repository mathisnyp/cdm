package ie.tcd.cdm.geo_service.model;

import java.util.ArrayList;

import ie.tcd.cdm.geo_service.model.Report;
import lombok.Getter;
public class Incident {
    @Getter
    ArrayList<Report> reports;
    
    Integer id;
    
    String IncidentName;

    public Incident(ArrayList<Report> report) {
        this.reports = report;

        //Take list of reports
        //Sort through list by location / incident type
        //store reports that match criteria

    }

}
