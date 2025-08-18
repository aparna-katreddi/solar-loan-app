package com.solar.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String browser = System.getProperty("browser", "chrome").toLowerCase(); // fallback to chrome
            String reportPath = String.format("test-output/extent-report-%s.html", browser);
            createInstance(reportPath);
        }
        return extent;
    }

    private static ExtentReports createInstance(String filePath) {
        ExtentSparkReporter reporter = new ExtentSparkReporter(filePath);
        reporter.config().setReportName("Solar Loan Test Report");
        reporter.config().setDocumentTitle("Test Execution Report");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User Role", "QA Automation Engineer");
        extent.setSystemInfo("Browser", System.getProperty("browser", "chrome"));
        return extent;
    }
}

