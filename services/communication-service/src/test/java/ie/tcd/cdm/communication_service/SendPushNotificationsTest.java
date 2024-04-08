package ie.tcd.cdm.communication_service;

import ie.tcd.cdm.communication_service.dto.*;
import ie.tcd.cdm.communication_service.jpa.PushNotificationTokenRepository;
import ie.tcd.cdm.communication_service.jpa.UserPushNotificationIdRepository;
import ie.tcd.cdm.communication_service.model.PushNotificationToken;
import ie.tcd.cdm.communication_service.model.UserIdTokenId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
class SendPushNotificationsTest {
    @LocalServerPort
    private int port;

    WebTestClient webTestClient;

    @Autowired
    PushNotificationTokenRepository pushNotificationTokenRepository;

    @Autowired
    UserPushNotificationIdRepository userIdTokenIdTestRepository;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port + "/communication-service").build();
        userIdTokenIdTestRepository.deleteAll();
        pushNotificationTokenRepository.deleteAll();
    }

    @Test
    void broadcastPushNotification() {
        BroadcastNotificationFromUserDTO broadcastNotificationFromUser = BroadcastNotificationFromUserDTO.builder().from(1).messageHeader("Hi there").messageHeader("I hope you are well!").build();

        webTestClient.post().uri("/notification/broadcast")
                .body(Mono.just(broadcastNotificationFromUser), BroadcastNotificationFromUserDTO.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .isEmpty();
    }

    @Test
    void broadcastPushNotificationToGroup() {
        BroadcastNotificationUserToGroupDTO broadcastNotificationUserToGroup = BroadcastNotificationUserToGroupDTO.builder().from(1).group(1).messageHeader("Hi there").messageHeader("I hope you are well!").build();

        webTestClient.post().uri("/notification/broadcast/group")
                .body(Mono.just(broadcastNotificationUserToGroup), BroadcastNotificationUserToGroupDTO.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .isEmpty();
    }
}
