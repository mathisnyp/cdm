package ie.tcd.cdm.incident_service.apis.geo;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.tcd.cdm.incident_service.apis.communication.model.BroadcastNotificationFromUserDTO;
import ie.tcd.cdm.incident_service.apis.geo.model.UpdateWeightDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GeoService {
    private final HttpClient httpClient;
    private final HttpRequest.Builder deleteIncidentRequest;
    private final ObjectMapper objectMapper;


    public GeoService(@Value("${endpoints.geoservice}") String geoBaseUrl){
        httpClient = HttpClient.newHttpClient();
        deleteIncidentRequest = HttpRequest.newBuilder().uri(URI.create(geoBaseUrl + "/weight/deleteIncident")).header("Content-Type", "application/json");
        objectMapper = new ObjectMapper();
    }

    public void deleteIncident(UpdateWeightDTO updateWeightDTO) throws IOException, InterruptedException {
        String body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(updateWeightDTO);
        httpClient.send(deleteIncidentRequest.POST(HttpRequest.BodyPublishers.ofString(body)).build(), HttpResponse.BodyHandlers.ofString());
    }
}
