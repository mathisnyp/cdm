package ie.tcd.cdm.geo_service.jpa;

import ie.tcd.cdm.geo_service.model.CdmPoint;
import ie.tcd.cdm.geo_service.model.Route;
import org.neo4j.driver.internal.value.PointValue;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

//TODO Route data class is not used anywhere, we should figure out where to put this instead
public interface RouteRepository extends Neo4jRepository<Route, Long> {

    @Query("MATCH (to:Address)-[:NEAREST_INTERSECTION]->(source:Intersection) WHERE distance(to.location, $destinationLocation) < 15 " +
            "MATCH (from:Address {location: $location})-[:NEAREST_INTERSECTION]->(origin:Intersection) " +
            "CALL apoc.algo.dijkstra(origin, source, 'ROAD_SEGMENT', 'length') " +
            "YIELD path, weight " +
            "RETURN [n in nodes(path) | {lat: n.location.latitude, lon: n.location.longitude}] AS route LIMIT 1")
    List<CdmPoint> findRoute(PointValue location, PointValue destinationLocation);
}
