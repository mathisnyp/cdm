package ie.tcd.cdm.incident_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Timestamp;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private long id;
    @Setter
    @Getter
    private String eventName;
    @Setter
    @Getter
    private String eventType;
    @Setter
    @Getter
    private String venue;
    @Setter
    @Getter
    private Integer attendees;
    @Setter
    @Getter
    private String generalDescription;
    @Setter
    @Getter
    private Timestamp eventTime;
}