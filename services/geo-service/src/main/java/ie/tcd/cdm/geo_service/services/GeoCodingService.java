package ie.tcd.cdm.geo_service.services;

import fr.dudie.nominatim.client.JsonNominatimClient;
import fr.dudie.nominatim.client.NominatimClient;
import fr.dudie.nominatim.client.request.NominatimSearchRequest;
import ie.tcd.cdm.geo_service.model.CdmPoint;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class GeoCodingService {
    private final NominatimClient nominatimClient;

    public GeoCodingService() {
        HttpClient httpClient = new DefaultHttpClient();
        this.nominatimClient = new JsonNominatimClient(httpClient, "mathisn@tcd.ie");
    }

    public CdmPoint searchForPoint(String query) throws IOException {
        NominatimSearchRequest nominatimSearchRequest = new NominatimSearchRequest();
        nominatimSearchRequest.setQuery(query + ", Dublin");
        nominatimSearchRequest.setCountryCodes(List.of("IE"));
        var address = nominatimClient.search(nominatimSearchRequest).get(0);
        return new CdmPoint(address.getLongitude(), address.getLatitude());
    }


}
