
package ie.tcd.cdm.incident_service.services;

import ie.tcd.cdm.incident_service.apis.communication.CommunicationService;
import ie.tcd.cdm.incident_service.apis.communication.model.BroadcastNotificationFromUserDTO;
import ie.tcd.cdm.incident_service.apis.geo.GeoService;
import ie.tcd.cdm.incident_service.apis.geo.model.UpdateWeightDTO;
import ie.tcd.cdm.incident_service.dto.CreateReportDTO;
import ie.tcd.cdm.incident_service.dto.UpdateReportDTO;
import ie.tcd.cdm.incident_service.jpa.ReportRepository;
import ie.tcd.cdm.incident_service.mapper.ReportMapper;
import ie.tcd.cdm.incident_service.model.Incident;
import ie.tcd.cdm.incident_service.model.Report;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ReportService {
    private final ReportRepository reportRepository;
    private final ReportMapper mapper;
    private final GeoService geoService;
    private final CommunicationService communicationService;

    public void updateReport(long id, UpdateReportDTO reportDTO) {
        Report report = this.getReportById(id);
        mapper.updateReportFromDTO(reportDTO, report);
        reportRepository.save(report);
    }

    public void deleteReport(long id) {
        reportRepository.deleteById(id);
    }

    public void createReport(CreateReportDTO reportDTO) throws IOException, InterruptedException {
        log.info("A nice report is created!");
        reportRepository.save(Report.builder()
                .name(reportDTO.name())
                .location(reportDTO.location())
                .emergencyType(reportDTO.emergencyType())
                        .timeReceived(n)
                .emergencySeverity(reportDTO.emergencySeverity())
                .generalDescription(reportDTO.generalDescription())
                .build());

        String str  = String.format("A dangerous event has happened on %s. Take care.", reportDTO.location());

        BroadcastNotificationFromUserDTO broadcastNotificationFromUserDTO = new BroadcastNotificationFromUserDTO(
                str,
                "!!!WARNING!!!",
                0,
                null
        );
        communicationService.broadcastNotification(broadcastNotificationFromUserDTO);
    }

    public Report getReportById(long id) {
        return reportRepository.getReferenceById(id);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Scheduled(fixedRate = 20000)
    public void checkValidReports() throws IOException, InterruptedException {
        log.info("Valid reports are being checked!");
        List<Report>allReports = getAllReports();
        LocalDateTime currentTime = LocalDateTime.now();
        for(Report report: allReports) {
            LocalDateTime createdAt = report.getTimeReceived().toLocalDateTime();
            LocalDateTime currentTimeLess2Minutes = currentTime.minusMinutes(1);

            Timestamp twoMinutesAgoTimestamp = Timestamp.valueOf(currentTimeLess2Minutes);
            Timestamp createdAtTimeStamp = Timestamp.valueOf(createdAt);


            int compareCreatedAt = twoMinutesAgoTimestamp.compareTo(createdAtTimeStamp);

            // if there are any invalid ones call deleteIncident(id) needing the id from step above
            if(compareCreatedAt > 0) {
                String incidentName = report.getLocation();
                deleteReport(report.getId());
                UpdateWeightDTO updateWeightDTO = new UpdateWeightDTO(incidentName);
                geoService.deleteIncident(updateWeightDTO);
            }
        }
    }
}