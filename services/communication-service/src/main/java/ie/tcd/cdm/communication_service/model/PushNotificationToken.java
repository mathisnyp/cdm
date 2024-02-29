package ie.tcd.cdm.communication_service.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PushNotificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private long id;

    @Setter
    @Column(unique = true)
    private String token;
}
