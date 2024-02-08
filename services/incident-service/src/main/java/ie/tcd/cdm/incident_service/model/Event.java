package ie.tcd.cdm.incident_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Timestamp;
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private long id;
    @Setter
    @Getter
    String eventName;
    @Setter
    @Getter
    String eventType;
    @Setter
    @Getter
    String venue;
    @Setter
    @Getter
    Integer attendees;
    @Setter
    @Getter
    private Timestamp eventTime;
}