/*
 * Copyright (c) 2025 Lahiru Kasun
 * Licensed under the MIT License – see the LICENSE file for details.
 */

package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger("ListenerLogger");

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test Failed: " + result.getName() + " - " + result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("Test Skipped: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("Test Suite Started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test Suite Finished: " + context.getName());
    }
}


