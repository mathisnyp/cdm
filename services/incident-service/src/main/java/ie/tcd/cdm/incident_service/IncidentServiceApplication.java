package ie.tcd.cdm.incident_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IncidentServiceApplication {
        public static void main(String[] args) {
            SpringApplication.run(IncidentServiceApplication.class, args);
        }

}

