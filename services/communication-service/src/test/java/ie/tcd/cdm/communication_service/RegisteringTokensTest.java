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
class RegisteringTokensTest {
    @LocalServerPort
    private int port;

    WebTestClient webTestClient;

    @Autowired
    PushNotificationTokenRepository pushNotificationTokenRepository;

    @Autowired
    UserPushNotificationIdRepository userIdTokenIdTestRepository;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port + "/communication-service/").build();
    }

    @Test
    void registerNewDeviceTest() {
        String token = "123";
        RegisterDeviceDTO registerDeviceDTO = RegisterDeviceDTO.builder().token(token).build();

        webTestClient.post().uri("/device/register")
                .body(Mono.just(registerDeviceDTO), RegisterDeviceDTO.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .isEmpty();

        PushNotificationToken result = pushNotificationTokenRepository.findByToken(token);
        Assertions.assertEquals(token, result.getToken(), "Token in database does not match registered token!");
        Assertions.assertTrue(result.getId() > -1, "ID has to be positive long value!");
    }

    @Test
    void linkExistingDeviceToNewUserTest() {
        String token = "422";
        long userId = 1;
        PushNotificationToken newToken = new PushNotificationToken(4, token);
        pushNotificationTokenRepository.save(newToken);
        LinkDeviceDTO linkDeviceDTO = LinkDeviceDTO.builder().token(token).userId(userId).build();

        webTestClient.post().uri("/device/link")
                .body(Mono.just(linkDeviceDTO), LinkDeviceDTO.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .isEmpty();
        UserIdTokenId result = userIdTokenIdTestRepository.getUserIdTokenIdByPushNotificationToken(newToken);
        Assertions.assertEquals(userId, result.getUserID());
        Assertions.assertEquals(newToken, result.getPushNotificationToken());
        Assertions.assertTrue(result.getId() > -1, "ID has to be positive long value!");
    }

    @Test
    void linkNewDeviceToExistingUser() {
        String oldToken = "313";
        long userId = 2;
        long oldPushTokenId = 5;
        PushNotificationToken oldPushToken = new PushNotificationToken(oldPushTokenId, oldToken);
        pushNotificationTokenRepository.save(oldPushToken);
        UserIdTokenId userIdTokenId = new UserIdTokenId(234, userId, 1L, oldPushToken);
        userIdTokenIdTestRepository.save(userIdTokenId);

        String newToken = "212";
        PushNotificationToken newPushNotificationToken = new PushNotificationToken(55, newToken);
        pushNotificationTokenRepository.save(newPushNotificationToken);
        LinkDeviceDTO newDeviceTokenLink = new LinkDeviceDTO(newToken, userId);

        webTestClient.post().uri("/device/link")
                .body(Mono.just(newDeviceTokenLink), LinkDeviceDTO.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .isEmpty();
        Assertions.assertTrue(pushNotificationTokenRepository.findById(oldPushTokenId).isEmpty(), "Expect old push token to be deleted in database!");
        Assertions.assertNull(userIdTokenIdTestRepository.getUserIdTokenIdByPushNotificationToken(oldPushToken), "Expect old token link to be deleted in database!");

        UserIdTokenId result = userIdTokenIdTestRepository.getUserIdTokenIdByPushNotificationToken(newPushNotificationToken);
        Assertions.assertEquals(userId, result.getUserID());
        Assertions.assertEquals(newToken, result.getPushNotificationToken().getToken());
        Assertions.assertTrue(result.getId() > -1, "ID has to be positive long value!");
    }

    @Test
    void changeUserGroupTest() {
        String oldToken = "313kj";
        long userId = 9999;
        long oldPushTokenId = 324234;
        PushNotificationToken oldPushToken = new PushNotificationToken(oldPushTokenId, oldToken);
        pushNotificationTokenRepository.save(oldPushToken);
        UserIdTokenId userIdTokenId = new UserIdTokenId(234, userId, 1L, oldPushToken);
        userIdTokenIdTestRepository.save(userIdTokenId);

        String newToken = "212212121";
        PushNotificationToken newPushNotificationToken = new PushNotificationToken(55, newToken);
        pushNotificationTokenRepository.save(newPushNotificationToken);

        long groupId = 1;
        UpdateUserGroupDTO updateUserGroupDTO = new UpdateUserGroupDTO(userId, groupId);
        webTestClient.post().uri("/device/update/group")
                .body(Mono.just(updateUserGroupDTO), UpdateUserGroupDTO.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .isEmpty();

        UserIdTokenId updatedUserIdTokenId = userIdTokenIdTestRepository.getUserIdTokenIdByUserID(userId);
        Assertions.assertEquals(groupId, updatedUserIdTokenId.getGroupID());
    }
}
