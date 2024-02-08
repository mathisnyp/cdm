package ie.tcd.cdm.incident_service.model;

public class Verifier {
    
    Report report;

    public Verifier(Report report) {
        this.report = report;
    }

    public Boolean verify(Report report) {
        return true;
    } 
}