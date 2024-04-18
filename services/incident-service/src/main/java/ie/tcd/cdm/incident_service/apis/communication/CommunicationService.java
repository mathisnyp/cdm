package ie.tcd.cdm.incident_service.apis.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ie.tcd.cdm.incident_service.apis.communication.model.BroadcastNotificationFromUserDTO;
import ie.tcd.cdm.incident_service.apis.communication.model.BroadcastNotificationUserToGroupDTO;
import ie.tcd.cdm.incident_service.apis.communication.model.SendNotificationUserToUserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CommunicationService {


    private final HttpClient httpClient;
    private final HttpRequest.Builder broadcastNotificationRequest;
    private final HttpRequest.Builder broadcastNotificationToGroupRequest;
    private final HttpRequest.Builder sendNotificationRequest;
    private final ObjectMapper objectMapper;

    @Value("${endpoints.communication}")
    private String communicationBaseUrl ;



    public CommunicationService(){
        httpClient = HttpClient.newHttpClient();
        broadcastNotificationRequest = HttpRequest.newBuilder().uri(URI.create(communicationBaseUrl + "/notification/broadcast")).header("Content-Type", "application/json");
        broadcastNotificationToGroupRequest = HttpRequest.newBuilder().uri(URI.create(communicationBaseUrl + "/notification/broadcast/group")).header("Content-Type", "application/json");
        sendNotificationRequest = HttpRequest.newBuilder().uri(URI.create(communicationBaseUrl + "/notification/send")).header("Content-Type", "application/json");
        objectMapper = new ObjectMapper();
    }

    public void broadcastNotification(BroadcastNotificationFromUserDTO broadcastNotificationFromUserDTO) throws IOException, InterruptedException {
        String body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(broadcastNotificationFromUserDTO);
        httpClient.send(broadcastNotificationRequest.POST(HttpRequest.BodyPublishers.ofString(body)).build(), HttpResponse.BodyHandlers.ofString());
    }
    public void broadcastNotificationToGroup(BroadcastNotificationUserToGroupDTO broadcastNotificationUserToGroupDTO) throws IOException, InterruptedException {
        String body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(broadcastNotificationUserToGroupDTO);
        httpClient.send(broadcastNotificationToGroupRequest.POST(HttpRequest.BodyPublishers.ofString(body)).build(), HttpResponse.BodyHandlers.ofString());


    }
    public void sendNotification(SendNotificationUserToUserDTO sendNotificationUserToUserDTO) throws IOException, InterruptedException {
        String body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sendNotificationUserToUserDTO);
        httpClient.send(sendNotificationRequest.POST(HttpRequest.BodyPublishers.ofString(body)).build(), HttpResponse.BodyHandlers.ofString());

    }
}
