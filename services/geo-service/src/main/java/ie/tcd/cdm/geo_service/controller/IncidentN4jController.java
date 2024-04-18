package ie.tcd.cdm.geo_service.controller;

import ie.tcd.cdm.geo_service.dto.UpdateWeightDTO;
import ie.tcd.cdm.geo_service.model.CdmPoint;
import ie.tcd.cdm.geo_service.services.GeoCodingService;
import ie.tcd.cdm.geo_service.services.GeoService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.neo4j.driver.Values;
import org.neo4j.driver.types.Point;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@AllArgsConstructor
@Builder
@RestController
@RequestMapping("/weight")
public class IncidentN4jController {
    private final GeoService geoService;
    private final GeoCodingService geoCodingService;

    @PostMapping("/createIncident")
    public void createIncident(@RequestBody UpdateWeightDTO updateWeightDTO) throws IOException {
        geoService.addWeights(updateWeightDTO.streetName());
    }
    @DeleteMapping("/deleteIncident")
    public void deleteIncident(@RequestBody UpdateWeightDTO updateWeightDTO) throws IOException {
        geoService.removeWeights(updateWeightDTO.streetName());
    }

    @GetMapping("/updatePlots")
    public List<Point[]> updatePlots() throws IOException {
        Set<String> incidentStreet = geoService.getIncidentStreet();
        return geoService.createListIncidentsPoints(incidentStreet);
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
