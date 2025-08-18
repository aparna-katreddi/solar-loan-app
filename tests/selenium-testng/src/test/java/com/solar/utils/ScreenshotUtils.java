package com.solar.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScreenshotUtils  {

    public static String captureScreenshot(String methodName) {
        WebDriver driver = DriverManager.getDriver();  //  get driver safely per-thread
        if (driver == null) {
            throw new IllegalStateException("Driver is not available for screenshot!");
        }
        String filePath = "screenshots/" + methodName + ".png";
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.createDirectories(Paths.get("test-output/screenshots/"));
            Files.copy(srcFile.toPath(), Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

}

