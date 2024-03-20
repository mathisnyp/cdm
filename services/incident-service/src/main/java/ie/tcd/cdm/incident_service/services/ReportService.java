
package ie.tcd.cdm.incident_service.services;

import ie.tcd.cdm.incident_service.dto.CreateReportDTO;
import ie.tcd.cdm.incident_service.dto.UpdateReportDTO;
import ie.tcd.cdm.incident_service.jpa.ReportRepository;
import ie.tcd.cdm.incident_service.mapper.ReportMapper;
import ie.tcd.cdm.incident_service.model.Report;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final ReportMapper mapper;

    public void updateReport(long id, UpdateReportDTO reportDTO) {
        Report report = this.getReportById(id);
        mapper.updateReportFromDTO(reportDTO, report);
        reportRepository.save(report);
    }

    public void deleteReport(long id) {
        reportRepository.deleteById(id);
    }

    public void createReport(CreateReportDTO reportDTO) {
        reportRepository.save(Report.builder()
                .name(reportDTO.name())
                .location(reportDTO.location())
                .emergencyType(reportDTO.emergencyType())
                .emergencySeverity(reportDTO.emergencySeverity())
                .generalDescription(reportDTO.generalDescription())
                .timeReceived(reportDTO.timeReceived())
                .build());
    }

    public Report getReportById(long id) {
        return reportRepository.getReferenceById(id);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }
}