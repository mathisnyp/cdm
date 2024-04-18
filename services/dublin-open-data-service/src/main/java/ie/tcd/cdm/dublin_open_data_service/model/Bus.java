package ie.tcd.cdm.dublin_open_data_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.GenerationType;
import lombok.*;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private long id;
    @Setter
    @Getter
    private Vehicle vehicle;
}
