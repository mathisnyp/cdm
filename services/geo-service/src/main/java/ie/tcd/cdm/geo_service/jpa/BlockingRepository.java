package ie.tcd.cdm.geo_service.jpa;

import ie.tcd.cdm.geo_service.model.Route;
import org.neo4j.driver.internal.value.PointValue;
import org.neo4j.driver.types.Point;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface BlockingRepository extends Neo4jRepository<Route, Long> {
    @Query("MATCH (from:Address)-[:NEAREST_INTERSECTION]->(origin:Intersection) WHERE distance(from.location, $originLocation) < $threshold " +
            "MATCH (source:Intersection {osmid:origin.osmid})-[:NEAREST_INTERSECTION]-(to:Address) " +
            "WHERE to.address_street = from.address_street " +
            "RETURN to.address_street LIMIT 1")
    String getIncidentStreet(PointValue originLocation, int threshold);

    @Query("MATCH (from:Address {address_street: $incidentStreet})-[:NEAREST_INTERSECTION]-(to:Intersection) " +
            "RETURN to.location")
    List<Point> getAllStreetsNearIncident(String incidentStreet);
}
