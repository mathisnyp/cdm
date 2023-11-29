package ie.tcd.cdm.geo_service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeoService {
    @Value("${spring.neo4j.uri}")
    private static String neo4jUri;

    @Value("${spring.neo4j.authentication.username}")
    private static String username;

    @Value("${spring.neo4j.authentication.password}")
    private static String password;

    public static void TestConsume(String kafkaString) {
        System.out.println("Received " + kafkaString);
        // commented the create node becuase I have it on local decomment to create it
        // Neo4jExample.createNode(neo4jUri, username, password, kafkaString);
        String query = Neo4jExample.readNode(neo4jUri, username, password);
        System.out.println("Queried "+query);
    }
}
