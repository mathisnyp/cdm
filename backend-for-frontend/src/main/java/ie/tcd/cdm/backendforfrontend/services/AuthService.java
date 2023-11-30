package ie.tcd.cdm.backendforfrontend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ie.tcd.cdm.auth.api.DefaultApi;

@Service
public class AuthService extends DefaultApi{
    public AuthService(@Value("${service_endpoints.auth}") String baseurl) {
        super();
        this.setCustomBaseUrl(baseurl);
    }


}
