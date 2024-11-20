package com.udyata.linentrack.linentrack.serviceimpl.jasperreport;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Service
public class JasperReportService {

    private final DataSource dataSource;

    @Autowired
    public JasperReportService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public byte[] generateReport(String reportName, Map<String, Object> parameters) throws JRException, SQLException, IOException {
        JasperReport jasperReport = loadReport(reportName);
        try (Connection connection = dataSource.getConnection()) {

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            return exportReportToPdf(jasperPrint);
        }
    }

    private JasperReport loadReport(String reportName) throws JRException, IOException {

        String reportPath = "reports/" + reportName + ".jrxml";
        InputStream reportStream = new ClassPathResource(reportPath).getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        JRSaver.saveObject(jasperReport, reportName + ".jasper");

        return jasperReport;
    }

    private byte[] exportReportToPdf(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}