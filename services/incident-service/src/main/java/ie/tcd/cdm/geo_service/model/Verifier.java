package ie.tcd.cdm.geo_service.model;

import ie.tcd.cdm.geo_service.model.Report;
import lombok.Getter;
public class Verifier {
    
    Report report;

    public Verifier(Report report) {
        this.report = report;
    }

    public Boolean verify(Report report) {
        return true;
    } 
}