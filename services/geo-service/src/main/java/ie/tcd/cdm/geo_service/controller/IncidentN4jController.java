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
import java.util.Set;
@AllArgsConstructor
@Builder
@RestController
@RequestMapping("/weight")
public class IncidentN4jController {
    private final GeoService geoService;
    private final GeoCodingService geoCodingService;

    @PostMapping("/createIncident")
    public Point[] createIncident(@RequestBody UpdateWeightDTO updateWeightDTO) throws IOException {
        geoService.addWeights(updateWeightDTO.streetName());
        var incidentCoordinates = geoCodingService.searchForPoint(updateWeightDTO.streetName());
        Point incidentPoint = Values.point(4326, incidentCoordinates.getLon(), incidentCoordinates.getLat()).asPoint();
        Set<Point> blockingArea = geoService.getBlockingArea(updateWeightDTO.streetName());
        Point[] result = new Point[blockingArea.size() + 1];

        result[0] = incidentPoint;

        int i = 1;
        for (Point point: blockingArea){
            result[i++] = point;
        }

        return result;
    }
    @DeleteMapping("/deleteIncident")
    public Point[] deleteIncident(@RequestBody UpdateWeightDTO updateWeightDTO) throws IOException {
        geoService.removeWeights(updateWeightDTO.streetName());
        var incidentCoordinates = geoCodingService.searchForPoint(updateWeightDTO.streetName());
        Point incidentPoint = Values.point(4326, incidentCoordinates.getLon(), incidentCoordinates.getLat()).asPoint();
        Set<Point> blockingArea = geoService.getBlockingArea(updateWeightDTO.streetName());
        Point[] result = new Point[blockingArea.size() + 1];

        result[0] = incidentPoint;

        int i = 1;
        for (Point point: blockingArea){
            result[i++] = point;
        }

        return result;
    }

}
