package ie.tcd.cdm.geo_service.jpa;

import ie.tcd.cdm.geo_service.model.Intersection;
import org.neo4j.driver.internal.value.PointValue;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface IntersectionRepository extends Neo4jRepository<Intersection, Integer> {
    Intersection findOneByOsmid(int osmid);

    @Query("MATCH (to:Address {location: $location})-[:NEAREST_INTERSECTION]->(source:Intersection) RETURN source")
    List<Intersection> findAllByAddressLocation(PointValue location);

    @Query("MATCH (to:Address {address_street: $addressStreet})-[:NEAREST_INTERSECTION]->(source:Intersection) RETURN source")
    List<Intersection> findAllByAddressStreet(String addressStreet);

}
