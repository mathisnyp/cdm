package ie.tcd.cdm.geo_service.services;

import org.springframework.beans.factory.annotation.Value;

public class IncidentService {
    @Value("${postgres.uri}")
    private String postgresUri;

    @Value("${spring.postgres.authentication.username}")
    private String username;

    @Value("${spring.postgres.authentication.password}")
    private String password;

    public void testConsume(String kafkaString) {
        System.out.println("Received " + kafkaString);
        // commented the create node becuase I have it on local decomment to create it
        Neo4jExample.createNode(neo4jUri, username, password, kafkaString);
        String query = Neo4jExample.readNode(neo4jUri, username, password);
        System.out.println("Queried " + query);
    }

}