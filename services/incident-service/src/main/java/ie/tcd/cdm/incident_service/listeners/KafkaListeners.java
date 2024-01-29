package ie.tcd.cdm.incident_service.listeners;

import ie.tcd.cdm.incident_service.services.IncidentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    private final IncidentService incidentService;

    public KafkaListeners(IncidentService incidentService) {
        this.incidentService = incidentService;
    }


    @KafkaListener(topics = "testTopic", groupId = "test1")
    void listener(String data) {
        System.out.println(data);
        this.incidentService.testConsume(data);
    }
}
