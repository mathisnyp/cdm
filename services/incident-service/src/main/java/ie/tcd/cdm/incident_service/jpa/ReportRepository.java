package ie.tcd.cdm.incident_service.jpa;

import ie.tcd.cdm.incident_service.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface ReportRepository extends JpaRepository<Report, Long> {

}