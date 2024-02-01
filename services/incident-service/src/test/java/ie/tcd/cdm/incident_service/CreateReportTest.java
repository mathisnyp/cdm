package ie.tcd.cdm.communication_service.create_incident;

import ie.tcd.cdm.geo_service.model.Report;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.sql.Timestamp;

class CreateReportTest {

    @Test
    void ReportModelCreation() {
        Assertions.assertDoesNotThrow(() -> {
            //var timestamp = new Timestamp(System.currentTimeMillis());
            Report report = new Report("John", "O Connell St", "Fire", "Major", "Fire in Cleary's Shopping Centre", "noon");

            Assertions.assertEquals("John", report.getName());
            Assertions.assertEquals("O Connell St", report.getLocation());
            Assertions.assertEquals("Fire", report.getEmergencyType());
            Assertions.assertEquals("Major", report.getEmergencySeverity());
            Assertions.assertEquals("Fire in Cleary's Shopping Centre", report.getGeneralDescription());
            Assertions.assertEquals("noon", report.getTimeReceived());
        });
    }
}
