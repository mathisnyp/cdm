package ie.tcd.cdm.incident_service.model;
//import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

//import java.sql.Timestamp;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private long id;
    //@ElementCollection
    //private ArrayList<Integer> reportIds; // IDs of reports associated with this incident
    // TODO: make sure that construction of reportIds list happens correctly
    // TODO: configure arraylist to be entered in the db
    @Getter
    @Setter
    private int reportIds;
    @Setter
    @Getter
    String incidentName;

//    public Incident(ArrayList<Report> report) {
//        this.reports = report;
//
//        //Take list of reports
//        //Sort through list by location / incident type
//        //store reports that match criteria
//
//    }
}
