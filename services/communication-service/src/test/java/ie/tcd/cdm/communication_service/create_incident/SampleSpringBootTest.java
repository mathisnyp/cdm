package ie.tcd.cdm.communication_service.create_incident;

import ie.tcd.cdm.communication_service.model.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
class SampleSpringBootTest {
    @LocalServerPort
    private int port;

    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }


    @Test
    void sampleTest() throws Exception {
        webTestClient.
                get().uri("communication/api/notification/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Notification.class).isEqualTo(Notification.builder()
                        .id(1)
                        .messageBody("Evacuation instructions for Smith Street residents")
                        .messageHeader("Emergency Evacuation")
                        .messageSender(1001)
                        .receiver(2001)
                        .receiverGroup("Smith Street Residents")
                        .scheduledTime(Timestamp.valueOf("2024-02-01 09:00:00"))
                        .build());
    }

}
