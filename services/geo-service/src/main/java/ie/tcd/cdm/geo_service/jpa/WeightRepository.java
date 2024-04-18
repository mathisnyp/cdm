package ie.tcd.cdm.geo_service.jpa;

import ie.tcd.cdm.geo_service.model.Route;
import org.apache.catalina.LifecycleState;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface WeightRepository extends Neo4jRepository<Route, Long> {

    @Query("MATCH (to:Address {address_street:$addressStreet})-[:NEAREST_INTERSECTION]->(source:Intersection)-[r:ROAD_SEGMENT]->() " +
            "WHERE r.name = $addressStreet SET r.length = r.length + 10000")
    void addWeight(String addressStreet);

    @Query("MATCH (to:Address {address_street:$addressStreet})-[:NEAREST_INTERSECTION]->(source:Intersection)-[r:ROAD_SEGMENT]->() " +
            "WHERE r.name = $addressStreet SET r.length = r.length - 10000 ")
    void removeWeight(String addressStreet);

    @Query("MATCH (to:Address)-[:NEAREST_INTERSECTION]->(source:Intersection)-[r:ROAD_SEGMENT]->() " +
            "WHERE r.length > 10000 AND r.name = to.address_street " +
            "RETURN to.address_street AS address_street")
    List<String> getIncidentStreets();
}
