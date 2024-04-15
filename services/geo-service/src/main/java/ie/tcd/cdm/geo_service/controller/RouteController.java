package ie.tcd.cdm.geo_service.controller;

import ie.tcd.cdm.geo_service.dto.DangerRequestDTO;
import ie.tcd.cdm.geo_service.dto.EmergencyRouteRequestDTO;
import ie.tcd.cdm.geo_service.dto.RouteRequestDTO;
import ie.tcd.cdm.geo_service.model.CdmPoint;
import ie.tcd.cdm.geo_service.services.GeoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/route")
public class RouteController {
    private final GeoService geoService;

    public RouteController(GeoService geoService){
        this.geoService = geoService;
    }

    @PostMapping("/user")
    public List<CdmPoint> getRoute(@RequestBody RouteRequestDTO routeRequestDTO) throws IOException {

        return geoService.getRoute(routeRequestDTO.location(), routeRequestDTO.destination());
    }

    @PostMapping("/emergency")
    public List<CdmPoint> getEmergencyRoute(@RequestBody EmergencyRouteRequestDTO emergencyRouteRequestDTO) {
        return geoService.getEmergencyRoute(emergencyRouteRequestDTO.location(), emergencyRouteRequestDTO.destination());
    }

    @PostMapping("/danger")
    public List<CdmPoint> getDangerRoute(@RequestBody DangerRequestDTO dangerRequestDTO) throws IOException {
        return geoService.getDangerRoute(dangerRequestDTO.location());
    }
}
