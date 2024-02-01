package ie.tcd.cdm.communication_service.create_incident;

import ie.tcd.cdm.incident_service.model.Report;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.sql.Timestamp;

class CreateReportTest {

    @Test
    void ReportModelCreation() {
        Assertions.assertDoesNotThrow(() -> {
            //var timestamp = new Timestamp(System.currentTimeMillis());
            Report report = new Report("John", "O Connell St", "Fire", "Major", "Fire in Cleary's Shopping Centre");

            Assertions.assertEquals("John", Report.getName());
            Assertions.assertEquals("O Connell St", Report.getLocation());
            Assertions.assertEquals("Fire", Report.getEmergencyType());
            Assertions.assertEquals("Major", Report.getEmergencySeverity());
            Assertions.assertEquals("Fire in Cleary's Shopping Centre", Report.getDescription());
            Assertions.assertEquals(timestamp, Report.getTimeReceived());
        });
    }
}
