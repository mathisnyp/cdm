package ie.tcd.cdm.communication_service.model;

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

public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private long id;
    @Setter
    @Getter
    private String messageBody;
    @Setter
    @Getter
    private String messageHeader;
    @Setter
    @Getter
    private int messageSender;
    @Setter
    @Getter
    private int receiver;
    @Setter
    @Getter
    private String receiverGroup;
    @Setter
    @Getter
    private Timestamp scheduledTime;
}
