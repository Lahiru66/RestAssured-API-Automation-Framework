/*
 * Copyright (c) 2025 Lahiru Kasun
 * Licensed under the MIT License – see the LICENSE file for details.
 */

package validations;

import static org.testng.Assert.assertEquals;

public class CommonValidations {

    public static void verifyResponseCode(int actual, int expected){
        assertEquals(actual,expected,"assertion failed");
    }

    public static void verifyResponseCodeWithCustomException(int actual, int expected) {
        if (actual != expected) {
            throw new CustomValidationException("Expected response code: " + expected + ", but got: " + actual);
        }
    }
}







