package ie.tcd.cdm.incident_service;

import ie.tcd.cdm.geo_service.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
public class IncidentClassTest {
    @Test
    void IncidentCreate() {
        Assertions.assertDoesNotThrow(() -> {
            ArrayList<Report> reports = new ArrayList<Report>();
            Incident incident = new Incident(reports);

            Assertions.assertNotNull(incident);
        });
    }    
}
