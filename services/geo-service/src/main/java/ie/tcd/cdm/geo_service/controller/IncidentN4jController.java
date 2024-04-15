package ie.tcd.cdm.geo_service.controller;

import ie.tcd.cdm.geo_service.dto.PointDTO;
import ie.tcd.cdm.geo_service.dto.UpdateWeightDTO;
import ie.tcd.cdm.geo_service.services.GeoService;
import org.neo4j.driver.types.Point;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Set;

@RestController
@RequestMapping("/weight")
public class IncidentN4jController {
    private final GeoService geoService;

    public IncidentN4jController(GeoService geoService){
        this.geoService = geoService;
    }

    @PostMapping("/createIncident")
    public Set<Point> createIncident(@RequestBody UpdateWeightDTO updateWeightDTO){
        String incidentStreet = geoService.getIncidentArea(updateWeightDTO.location());
        geoService.addWeights(incidentStreet);
        return geoService.getBlockingArea(incidentStreet);
    }
    @DeleteMapping("/deleteIncident")
    public void deleteIncident(@RequestBody UpdateWeightDTO updateWeightDTO){
        String incidentStreet = geoService.getIncidentArea(updateWeightDTO.location());
        geoService.removeWeights(incidentStreet);

    }

}
