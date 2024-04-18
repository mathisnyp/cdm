package ie.tcd.cdm.dublin_open_data_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DublinOpenDataService {

    @Autowired
    private final WebClient webClient;
    private final String dublinBusAPI = "https://api.nationaltransport.ie/gtfsr/v2/Vehicles?format=json";
    //    PUBLICLY AVAILABLE API KEY
    private final String webAPIKey = "d482091fa0ff469998282278c07ed278";

    public DublinOpenDataService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(dublinBusAPI).build();
    }

    public /*List<Bus>*/ String getAllBuses() {
        String response = webClient.get()
                .uri(dublinBusAPI)
                .header("x-api-key", webAPIKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println(response);
        return response;
    }
}