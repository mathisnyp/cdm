package ie.tcd.cdm.backendforfrontend.controller;

import ie.tcd.cdm.backendforfrontend.services.GeoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:8081/", "http://localhost:3000/, http://web-ui:3000/"})
@RequestMapping("/geo")
public class GeoController {
    private GeoService geoService;

    public GeoController(GeoService geoService) {
        this.geoService = geoService;
    }
    @PostMapping("/hello")
    public void sendHello() {
        geoService.sendHello();
    }
}
