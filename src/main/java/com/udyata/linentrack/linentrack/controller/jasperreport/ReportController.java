package com.udyata.linentrack.linentrack.controller.jasperreport;
import com.udyata.linentrack.linentrack.serviceimpl.jasperreport.JasperReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final JasperReportService jasperReportService;

    @Autowired
    public ReportController(JasperReportService jasperReportService) {
        this.jasperReportService = jasperReportService;
    }

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generateReport(
            @RequestParam("reportName") String reportName,
            @RequestParam("roomId") Integer roomId) {

        try {

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("roomId", roomId);


            byte[] reportData = jasperReportService.generateReport(reportName, parameters);

            // Set headers for the response
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + reportName + ".pdf");
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);

            // Return the response with the report
            return new ResponseEntity<>(reportData, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
