package ie.tcd.cdm.geo_service.services;

import ie.tcd.cdm.geo_service.dto.PointDTO;
import ie.tcd.cdm.geo_service.jpa.*;
import ie.tcd.cdm.geo_service.model.CdmPoint;
import lombok.AllArgsConstructor;
import org.neo4j.driver.Values;
import org.neo4j.driver.internal.value.PointValue;
import org.neo4j.driver.types.Point;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class GeoService {
    private final RouteRepository routeRepository;
    private final GeoCodingService geoCodingService;
    private final EmergencyRouteRepository emergencyRouteRepository;
    private final WeightRepository weightRepository;
    private final DangerRepository dangerRepository;
    private final BlockingRepository blockingRepository;
    public List<CdmPoint> getRoute(PointDTO location, String destination) throws IOException {

        org.neo4j.driver.types.Point origin = Values.point(4326, location.latitude(), location.longitude()).asPoint();
        var destinationCoordinates = geoCodingService.searchForPoint(destination);
        org.neo4j.driver.types.Point destinationPoint = Values.point(4326, destinationCoordinates.getLon(), destinationCoordinates.getLat()).asPoint();
        ArrayList<CdmPoint> result = new ArrayList<>();
        result.add(new CdmPoint(location.latitude(), location.longitude()));
        int threshold = 10;
        var route = routeRepository.findRoute(new PointValue(origin), new PointValue(destinationPoint), threshold);
        while(route.size() <= 2 && threshold <= 100){
            threshold += 10;
            System.out.println(threshold);
            route = routeRepository.findRoute(new PointValue(origin), new PointValue(destinationPoint), threshold);
        }
        result.addAll(route);
        result.add(destinationCoordinates);
        return result;

    }

    public List<CdmPoint> getEmergencyRoute(PointDTO location, PointDTO destination) {

        org.neo4j.driver.types.Point origin = Values.point(4326, location.latitude(), location.longitude()).asPoint();
        org.neo4j.driver.types.Point destinationPoint = Values.point(4326, destination.latitude(), destination.longitude()).asPoint();
        ArrayList<CdmPoint> result = new ArrayList<>();
        result.add(new CdmPoint(location.latitude(), location.longitude()));
        int threshold = 10;
        var route = emergencyRouteRepository.findRoute(new PointValue(origin), new PointValue(destinationPoint), threshold);
        while(route.size() <= 2){
            threshold += 10;
            route = emergencyRouteRepository.findRoute(new PointValue(origin), new PointValue(destinationPoint), threshold);
        }
        result.addAll(route);
        result.add(new CdmPoint(destination.latitude(), destination.longitude()));
        return result;

    }

    public String getDangerStreet(PointDTO location){
        org.neo4j.driver.types.Point origin = Values.point(4326, location.latitude(), location.longitude()).asPoint();
        int threshold = 200;
        String street = dangerRepository.getStreetOut(new PointValue(origin), threshold);
        System.out.println(street);
        return street;
    }

    public List<CdmPoint> getDangerRoute(PointDTO location) throws IOException {
        org.neo4j.driver.types.Point origin = Values.point(4326, location.latitude(), location.longitude()).asPoint();
        int threshold = 50;
        List<Boolean> isLocationIncident = dangerRepository.getIsLocationIncident(new PointValue(origin), threshold);

        System.out.println(isLocationIncident);
        if(isLocationIncident.contains(true)){
            String destination = getDangerStreet(location);
            System.out.println(destination);
            return getRoute(location, destination);
        }
        return null;
    }

    public void addWeights(String addressStreet){
        weightRepository.addWeight(addressStreet);
    }

    public void removeWeights(String addressStreet){
        weightRepository.removeWeight(addressStreet);
    }

    public Set<Point> getBlockingArea(String street){
        List<Point> intersections = blockingRepository.getAllStreetsNearIncident(street);
        return new HashSet<>(intersections);
    }

    public Point[] getPlotPoints(String street) throws IOException {
        var incidentCoordinates = geoCodingService.searchForPoint(street);
        Point incidentPoint = Values.point(4326, incidentCoordinates.getLon(), incidentCoordinates.getLat()).asPoint();
        Set<Point> blockingArea = getBlockingArea(street);
        Point[] result = new Point[blockingArea.size() + 1];

        result[0] = incidentPoint;

        int i = 1;
        for (Point point: blockingArea){
            result[i++] = point;
        }

        return result;
    }

    public List<Point[]> createListIncidentsPoints(Set<String> incidentStreet) throws IOException {
        List<Point[]> listPointArrays = new ArrayList<>();
        for (String street: incidentStreet){
            Point[] points = getPlotPoints(street);
            listPointArrays.add(points);
        }
        return listPointArrays;
    }

    public Set<String> getIncidentStreet(){
        List<String> listIncidents = weightRepository.getIncidentStreets();
        return new HashSet<>(listIncidents);
    }
}

