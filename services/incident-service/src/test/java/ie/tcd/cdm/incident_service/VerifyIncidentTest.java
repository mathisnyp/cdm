//package ie.tcd.cdm.incident_service;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import ie.tcd.cdm.incident_service.model.Verifier;
//import ie.tcd.cdm.incident_service.model.Report;
//public class VerifyIncidentTest {
//    @Test
//    void CreateVerifier() {
//    Assertions.assertDoesNotThrow(()-> {
//            Report report = new Report("John", "O Connell St", "Fire", "Major", "Fire in Cleary's Shopping Centre", "noon");
//
//            Verifier verify = new Verifier(report);
//
//            Assertions.assertNotNull(verify);
//
//        });
//    }
//    @Test
//    void VerifyIncident() {
//        Assertions.assertDoesNotThrow(()-> {
//            Report report = new Report("John", "O Connell St", "Fire", "Major", "Fire in Cleary's Shopping Centre", "noon");
//
//            Verifier verify = new Verifier(report);
//
//            Assertions.assertTrue(verify.verify(report));
//
//        });
//    }
//}
