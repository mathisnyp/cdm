package ie.tcd.cdm.backendforfrontend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ie.tcd.cdm.auth.api.DefaultApi;

@Service
public class AuthService {
    @Value("${service_endpoints.auth}")
    private String baseurl;

    public final DefaultApi apiClient;

    public AuthService() {
        DefaultApi defaultApi = new DefaultApi();
        defaultApi.setCustomBaseUrl(baseurl);
        apiClient = defaultApi;
    }


}
