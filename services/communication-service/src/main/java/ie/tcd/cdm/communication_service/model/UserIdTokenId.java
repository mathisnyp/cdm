package ie.tcd.cdm.communication_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "user_push_notification_id")
public class UserIdTokenId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private long id;

    @Getter
    @Setter
    @Column(unique = true, name = "user_id")
    private long userID;

    @Getter
    @Setter
    @Column(name = "group_id")
    private long groupID;

    @OneToOne
    @JoinColumn(name = "token_id", referencedColumnName = "id", unique = true)
    private PushNotificationToken pushNotificationToken;
}
