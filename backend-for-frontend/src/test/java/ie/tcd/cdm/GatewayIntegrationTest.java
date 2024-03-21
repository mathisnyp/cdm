//package ie.tcd.cdm;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class GatewayIntegrationTest {
//
//    @LocalServerPort
//    private int port;
//
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    @Test
//    public void testService1Route() {
//        String url = "http://localhost:" + port + "/service1/someEndpoint";
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//        assertEquals(200, response.getStatusCodeValue());
//        // Additional assertions can be added here based on the expected response
//    }
////
////    @Test
////    public void testService2Route() {
////        String url = "http://localhost:" + port + "/service2/anotherEndpoint";
////        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
////        assertEquals(200, response.getStatusCodeValue());
////        // Additional assertions can be added here based on the expected response
////    }
//}
//
//
