package ie.tcd.cdm.dublin_open_data_service.model;

import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    @Id
    @Setter
    @Getter
    private String trip_id;
    @Setter
    @Getter
    private Date start_time;
    @Setter
    @Getter
    private Date start_date;
    @Setter
    @Getter
    private String schedule_relationship;
    @Setter
    @Getter
    private String route_id;
    @Setter
    @Getter
    private int direction_id;

}
