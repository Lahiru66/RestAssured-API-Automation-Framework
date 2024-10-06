import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.CommonValidations;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static utils.CommonValidations.verifyResponseCode;

public class Sample extends TestBase {
    private static final Logger logger = LogManager.getLogger(Sample.class);

    public static SoftAssert softAssert;

    public static Response response;

    private static String requestBody = "{\n" +
            "  \"title\": \"abcd\",\n" +
            "  \"body\": \"bar\",\n" +
            "  \"userId\": \"1\" \n}";

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        softAssert = new SoftAssert();
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test(enabled = true, priority = 1, description = "get one post")
    public void testGetExampleAPI() {

        response = given()
                .contentType("application/json")
                .relaxedHTTPSValidation()
                .when()
                .get("/posts/1")
                .then()
                .extract().response();

        // Retrieve the status code from the response
        int statusCode = response.getStatusCode();
        logger.info("status code is" + statusCode);

        verifyResponseCode(statusCode,200);

        // Retrieve the response body as a String
        String responseBody = response.getBody().asString();

        // Perform assertions on the response body (for example, checking if it contains certain text)
        softAssert.assertEquals(responseBody.contains("userId"), true, "Response body does not contain 'userId'");
        softAssert.assertEquals(responseBody.contains("id"), true, "Response body does not contain 'id'");
        softAssert.assertEquals(responseBody.contains("title"), true, "Response body does not contain 'title'");
        softAssert.assertEquals(responseBody.contains("body"), true, "Response body does not contain 'body'");
    }


    @Test(enabled = true, priority = 2, description = "create posts")
    public void postRequest() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .extract().response();

        // Retrieve the status code from the response
        int statusCode = response.getStatusCode();
        logger.info("status code is" + statusCode);

        verifyResponseCode(statusCode,201);

        softAssert.assertEquals("foo", response.jsonPath().getString("title"));
        softAssert.assertEquals("bar", response.jsonPath().getString("body"));
        softAssert.assertEquals("1", response.jsonPath().getString("userId"));
        softAssert.assertEquals("101", response.jsonPath().getString("id"));
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup() {
        response = null;
    }


}


