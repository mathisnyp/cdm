package ie.tcd.cdm.communication_service.services;

import ie.tcd.cdm.communication_service.dto.BroadcastNotificationFromUserDTO;
import ie.tcd.cdm.communication_service.dto.BroadcastNotificationUserToGroupDTO;
import ie.tcd.cdm.communication_service.dto.SendNotificationUserToUserDTO;
import ie.tcd.cdm.communication_service.error.InvalidTokenError;
import ie.tcd.cdm.communication_service.jpa.UserPushNotificationIdRepository;
import ie.tcd.cdm.communication_service.model.UserIdTokenId;
import io.github.jav.exposerversdk.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;

@Service
@Slf4j
public class NotificationService {
    private final PushClient client;

    private final UserPushNotificationIdRepository userPushNotificationIdRepository;

    public NotificationService(UserPushNotificationIdRepository userPushNotificationIdRepository) throws PushClientException {
        this.userPushNotificationIdRepository = userPushNotificationIdRepository;
        this.client = new PushClient();
    }

    public void sendNotification(SendNotificationUserToUserDTO sendNotificationUserToUserDTO) throws PushClientException {
        long targetUserId = sendNotificationUserToUserDTO.to();
        UserIdTokenId userIdTokenId = this.userPushNotificationIdRepository.getUserIdTokenIdByUserID(targetUserId);
        String token = userIdTokenId.getPushNotificationToken().getToken();
        this.sendPushNotification(token,
                sendNotificationUserToUserDTO.messageHeader(),
                sendNotificationUserToUserDTO.messageBody(),
                new HashMap<>());
    }

    public void broadcastNotification(BroadcastNotificationFromUserDTO broadcastNotificationFromUserDTO) throws PushClientException {
        List<String> allTokens = this.userPushNotificationIdRepository.findAll()
                .stream()
                .map((UserIdTokenId eachUserIdTokenId) -> eachUserIdTokenId.getPushNotificationToken()
                        .getToken())
                .toList();
        this.sendPushNotification(allTokens,
                broadcastNotificationFromUserDTO.messageHeader(),
                broadcastNotificationFromUserDTO.messageBody(),
                new HashMap<>());
    }

    public void broadcastNotificationToGroup(BroadcastNotificationUserToGroupDTO broadcastNotificationUserToGroupDTO) throws PushClientException {
        List<String> allTokens = this.userPushNotificationIdRepository.getUserIdTokenIdByGroupID(broadcastNotificationUserToGroupDTO.group())
                .stream()
                .map((UserIdTokenId eachUserIdTokenId) -> eachUserIdTokenId.getPushNotificationToken()
                        .getToken())
                .toList();
        this.sendPushNotification(allTokens,
                broadcastNotificationUserToGroupDTO.messageHeader(),
                broadcastNotificationUserToGroupDTO.messageBody(),
                new HashMap<>());
    }

    //code copied from https://stackoverflow.com/questions/71298367/send-push-notification-using-java-springboot-server-and-expo-react-native-client
    private void sendPushNotification(String token, String title, String message, Map<String, Object> data) throws PushClientException {
        this.sendPushNotification(listOf(token), title, message, data);
    }

    private void sendPushNotification(List<String> tokenList, String title, String message, Map<String, Object> data) throws PushClientException {

        this.checkTokenValidity(tokenList);
        List<ExpoPushMessage> expoPushMessages = this.createPushMessages(tokenList, title, message, data);
        List<List<ExpoPushMessage>> chunks = client.chunkPushNotifications(expoPushMessages);

        List<CompletableFuture<List<ExpoPushTicket>>> messageRepliesFutures = new ArrayList<>();

        for (List<ExpoPushMessage> chunk : chunks) {
            messageRepliesFutures.add(client.sendPushNotificationsAsync(chunk));
        }

        List<ExpoPushMessageTicketPair<ExpoPushMessage>> zippedMessagesTickets = this.getPushNotificationsReplies(messageRepliesFutures, expoPushMessages);

        this.logMessageReplies(zippedMessagesTickets);
    }

    private void logMessageReplies(List<ExpoPushMessageTicketPair<ExpoPushMessage>> zippedMessagesTickets) {
        List<ExpoPushMessageTicketPair<ExpoPushMessage>> okTicketMessages = client.filterAllSuccessfulMessages(zippedMessagesTickets);
        String okTicketMessagesString = okTicketMessages.stream().map(p -> "Title: " + p.message.getTitle() + ", Id:" + p.ticket.getId()).collect(Collectors.joining(","));
        log.info("Recieved OK ticket for " + okTicketMessages.size() + " messages: " + okTicketMessagesString);

        List<ExpoPushMessageTicketPair<ExpoPushMessage>> errorTicketMessages = client.filterAllMessagesWithError(zippedMessagesTickets);
        String errorTicketMessagesString = errorTicketMessages.stream().map(p -> "Title: " + p.message.getTitle() + ", Error: " + p.ticket.getDetails().getError()).collect(Collectors.joining(","));
        log.error("Recieved ERROR ticket for " + errorTicketMessages.size() + " messages: " + errorTicketMessagesString);
    }

    private List<ExpoPushMessageTicketPair<ExpoPushMessage>> getPushNotificationsReplies(List<CompletableFuture<List<ExpoPushTicket>>> messageRepliesFutures, List<ExpoPushMessage> expoPushMessages) {
        // Wait for each completable future to finish
        List<ExpoPushTicket> allTickets = new ArrayList<>();
        for (CompletableFuture<List<ExpoPushTicket>> messageReplyFuture : messageRepliesFutures) {
            try {
                allTickets.addAll(messageReplyFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        if (allTickets.isEmpty()) {
            return Collections.emptyList();
        } else {
            try {
                List<ExpoPushMessageTicketPair<ExpoPushMessage>> zippedMessagesTickets = client.zipMessagesTickets(expoPushMessages, allTickets);
                return zippedMessagesTickets;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private List<ExpoPushMessage> createPushMessages(List<String> tokenList, String title, String message, Map<String, Object> data) {
        ExpoPushMessage expoPushMessage = new ExpoPushMessage();
        expoPushMessage.getTo().addAll(tokenList);
        expoPushMessage.setTitle(title);
        expoPushMessage.setBody(message);
        expoPushMessage.setData(data);
        List<ExpoPushMessage> expoPushMessages = new ArrayList<>();
        expoPushMessages.add(expoPushMessage);
        return expoPushMessages;
    }

    private void checkTokenValidity(List<String> tokenList) {
        List<String> invalidTokens = this.getInvalidTokens(tokenList);
        System.out.println("Here is the token list");
        System.out.println(tokenList);
        if (!invalidTokens.isEmpty())
            throw new InvalidTokenError(invalidTokens);
    }

    private List<String> getInvalidTokens(List<String> tokens) {
        return tokens.stream()
                .filter((String eachToken) -> !PushClientCustomData.isExponentPushToken(eachToken))
                .toList();
    }
}
