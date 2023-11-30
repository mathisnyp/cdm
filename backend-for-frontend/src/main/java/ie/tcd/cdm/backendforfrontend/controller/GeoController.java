package ie.tcd.cdm.backendforfrontend.controller;

import ie.tcd.cdm.backendforfrontend.services.GeoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geo")
public class GeoController {
    private GeoService geoService;

    //TODO create endpoint that
    public GeoController(GeoService geoService) {
        this.geoService = geoService;
    }
    @PostMapping("/hello")
    public void sendHello() {
        geoService.sendHello();
    }
}
