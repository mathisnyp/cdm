package ie.tcd.cdm.dods_service.listeners;

import ie.tcd.cdm.dods_service.services.GeoService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    private final GeoService geoService;

    public KafkaListeners(GeoService geoService) {
        this.geoService = geoService;
    }


    @KafkaListener(topics = "testTopic", groupId = "test1")
    void listener(String data) {
        System.out.println(data);
        this.geoService.testConsume(data);
    }
}
