package validations;

import org.testng.annotations.Test;
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







