/*
 * Copyright (c) 2025 Lahiru Kasun
 * Licensed under the MIT License – see the LICENSE file for details.
 */

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public abstract class TestBase {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite: Setting up resources...");
        // Example setup: Initialize database connection, start a server, etc.
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite: Cleaning up resources...");
        // Example cleanup: Close database connection, stop a server, etc.
    }
}


