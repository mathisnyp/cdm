//import ie.tcd.cdm.dublin_open_data_service.model.Bus;
//import ie.tcd.cdm.dublin_open_data_service.model.Position;
//import ie.tcd.cdm.dublin_open_data_service.model.Trip;
//import ie.tcd.cdm.dublin_open_data_service.model.Vehicle;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.test.web.reactive.server.WebTestClient;
//
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
//class GetDublinBusTest {
//    @LocalServerPort
//    private int port;
//    WebTestClient webTestClient;
//
//    @BeforeEach
//    void setUp() {
//        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port + "/dod/api").build();
//    }
//
//    @Test
//    void ifAValidRequestIsMade_AnAPIRequestIsMade_ServiceReturnsStatus200AndBusData() throws Exception {
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
//
//        Date startTime = sdf.parse("16:45:00");
//        Date startDate = sdf2.parse("20240405");
//
//        String testVehicleID = "1";
//        Timestamp testTimestamp = new Timestamp(1712333384);
//        Position testPosition = new Position("53.3459702", "-6.29262495");
//        Trip testTrip = new Trip("1", startTime, startDate, "SCHEDULED","3946_63136", 0);
//        Vehicle testVehicle = new Vehicle(testTrip, testPosition, testTimestamp, testVehicleID);
//
//        webTestClient.
//                get().uri("/bus")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(Bus.class).isEqualTo(Bus.builder()
//                        .id(1)
//                        .vehicle(testVehicle)
//                        .build());;
//    }
//}