package ie.tcd.cdm.incident_service;

import ie.tcd.cdm.incident_service.model.*;
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

    @Test
    void IncidentsWithReportsFromDifferentLocation(){
        Assertions.assertDoesNotThrow(() -> {
            Report firstreport = new Report("report", "Dame Street", "Fire",
                    "high", "fire in dame street", "noon");
            Report secondreport = new Report("report", "Grafton Street", "Fire",
                    "high", "fire in grafton street", "noon");
            ArrayList<Report> reports = new ArrayList<>();
            reports.add(firstreport);
            reports.add(secondreport);
            Incident incident = new Incident(reports);

            Assertions.assertNotEquals(incident.getReports().get(0).getLocation(), incident.getReports().get(1).getLocation());
        });
    }
}
