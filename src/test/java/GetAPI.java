import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.assertEquals;

public class GetAPI {

    public static SoftAssert softAssert;

    @BeforeMethod
    public void setUp() {
        softAssert = new SoftAssert();
    }

    @Test
    public void testGetExampleAPI() {
        // Define the base URI where the API is hosted
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request to the specific endpoint (/posts/1 in this example)
        Response response = RestAssured.get("/posts/1");

        // Retrieve the status code from the response
        int statusCode = response.getStatusCode();

        // Assert that the status code is 200 (OK)
        assertEquals(statusCode, 200, "Status code is not 200");

        // Retrieve the response body as a String
        String responseBody = response.getBody().asString();

        // Perform assertions on the response body (for example, checking if it contains certain text)
        softAssert.assertEquals(responseBody.contains("userId"), true, "Response body does not contain 'userId'");
        softAssert.assertEquals(responseBody.contains("id"), true, "Response body does not contain 'id'");
        softAssert.assertEquals(responseBody.contains("title"), true, "Response body does not contain 'title'");
        softAssert.assertEquals(responseBody.contains("body"), true, "Response body does not contain 'body'");
    }

}

