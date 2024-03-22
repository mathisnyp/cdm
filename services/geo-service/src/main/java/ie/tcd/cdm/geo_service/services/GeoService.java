package ie.tcd.cdm.geo_service.services;

import ie.tcd.cdm.geo_service.dto.PointDTO;
import ie.tcd.cdm.geo_service.jpa.RouteRepository;
import ie.tcd.cdm.geo_service.model.CdmPoint;
import lombok.AllArgsConstructor;
import org.neo4j.driver.Values;
import org.neo4j.driver.internal.value.PointValue;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GeoService {
    private final RouteRepository routeRepository;
    private final GeoCodingService geoCodingService;
    public List<CdmPoint> getRoute(PointDTO location, String destination) throws IOException {

        org.neo4j.driver.types.Point origin = Values.point(4326, location.latitude(), location.longitude()).asPoint();
        var destinationCoordinates = geoCodingService.searchForPoint(destination);
        org.neo4j.driver.types.Point destinationPoint = Values.point(4326, destinationCoordinates.getLon(), destinationCoordinates.getLat()).asPoint();
        ArrayList<CdmPoint> result = new ArrayList<>();
        result.add(new CdmPoint(location.latitude(), location.longitude()));
        var route = routeRepository.findRoute(new PointValue(origin), new PointValue(destinationPoint));
        result.addAll(route);
        result.add(destinationCoordinates);
        return result;

    }
}
