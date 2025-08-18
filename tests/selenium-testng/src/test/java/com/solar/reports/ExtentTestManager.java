package com.solar.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager {
    private static ExtentReports extent = ExtentReportManager.getInstance();

    private static ExtentTest suiteTest;       // Suite node
    private static Map<String, ExtentTest> testNodeMap = new HashMap<>();   // E2E test nodes
    private static Map<String, ExtentTest> classNodeMap = new HashMap<>();  // Class nodes
    private static Map<Long, ExtentTest> methodTestMap = new HashMap<>();   // Method nodes

    // Suite
    public static synchronized void startSuite(String suiteName) {
        if (suiteTest == null) {
            suiteTest = extent.createTest(suiteName);
        }
    }

    // Test (E2E Tests)
    public static synchronized ExtentTest startTestNode(String testName) {
        return testNodeMap.computeIfAbsent(testName, name -> suiteTest.createNode(name));
    }

    // Class
    public static synchronized ExtentTest startClassNode(String testName, String className) {
        String key = testName + ":" + className;
        return classNodeMap.computeIfAbsent(key,
                name -> startTestNode(testName).createNode(className));
    }

    // Method
    public static synchronized ExtentTest startMethodNode(String testName, String className, String methodName) {
        ExtentTest classNode = startClassNode(testName, className);
        ExtentTest methodNode = classNode.createNode(methodName);
        methodTestMap.put(Thread.currentThread().getId(), methodNode);
        return methodNode;
    }

    public static synchronized ExtentTest getTest() {
        return methodTestMap.get(Thread.currentThread().getId());
    }

    public static synchronized void flush() {
        extent.flush();
    }
}



