package ie.tcd.cdm.incident_service.controller;

import ie.tcd.cdm.incident_service.dto.CreateReportDTO;
import ie.tcd.cdm.incident_service.dto.UpdateReportDTO;
import ie.tcd.cdm.incident_service.model.Report;
import ie.tcd.cdm.incident_service.services.ReportService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{id}")
    public Report getReport(@PathVariable long id) {
        return reportService.getReportById(id);
    }

    @PostMapping
    public void createReport(@RequestBody CreateReportDTO report) throws IOException, InterruptedException {
        reportService.createReport(report);
    }

    @PutMapping("/{id}")
    public void updateReport(@PathVariable long id, @RequestBody UpdateReportDTO report) {
        reportService.updateReport(id, report);
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable long id) {
        reportService.deleteReport(id);
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}