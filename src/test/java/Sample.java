import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.Helper;
import dto.PostDTO;
import utils.CommonUtils;
import utils.StatusCodes;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static validations.CommonValidations.verifyResponseCode;
import static utils.Constants.*;

public class Sample extends TestBase {
    public Helper helper;
    public StatusCodes statusCodes;
    private static final Logger logger = LogManager.getLogger(Sample.class);
    public static SoftAssert softAssert;
    public static Response response;

    @BeforeClass(alwaysRun = true)
    public void serviceSetup() {
        helper = new Helper();
        statusCodes = new StatusCodes();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        softAssert = new SoftAssert();
     //   RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test(enabled = true, priority = 1, description = "create posts")
    public void postRequest() {

        String randomTitle = CommonUtils.generateRandomString();
        String randomBody = CommonUtils.generateRandomString();
        String randomUserId = CommonUtils.generateRandomAlphanumeric(5);

        PostDTO postDTO = PostDTO.builder()
                .title(randomTitle)
                .body(randomBody)
                .userId(randomUserId).build();

        response = helper.createPost(postDTO);

        // Retrieve the status code from the response
        int statusCode = response.getStatusCode();
        logger.info("status code is" + statusCode);

        verifyResponseCode(statusCode, statusCodes.SC_CREATED);

        String returnedTitle = response.jsonPath().getString(TITLE);
        String returnedBody =  response.jsonPath().getString(BODY);
        String returnedUserId = response.jsonPath().getString(USERID);
        String id = response.jsonPath().getString(ID);

        softAssert.assertEquals(returnedTitle,randomTitle);
        softAssert.assertEquals(returnedBody,randomBody);
        softAssert.assertEquals(returnedUserId,randomUserId);
        softAssert.assertEquals(id,"101");

        softAssert.assertAll();


    }

    @Test(enabled = true, priority = 2, description = "get one post")
    public void GetExampleAPI() {

        Response response = helper.getPost();

        // Retrieve the status code from the response
        int statusCode = response.getStatusCode();
        logger.info("status code is" + statusCode);

        verifyResponseCode(statusCode, statusCodes.SC_OK);

        // Retrieve the response body as a String
        String responseBody = response.getBody().asString();

        // Perform assertions on the response body (for example, checking if it contains certain text)
        softAssert.assertEquals(responseBody.contains("userId"), true, "Response body does not contain 'userId'");
        softAssert.assertEquals(responseBody.contains("id"), true, "Response body does not contain 'id'");
        softAssert.assertEquals(responseBody.contains("title"), true, "Response body does not contain 'title'");
        softAssert.assertEquals(responseBody.contains("body"), true, "Response body does not contain 'body'");
    }

    @Test(enabled = true, priority = 2, description = "get one post")
    public void GetExampleAPINegative() {

        Response response = helper.getPost();

        // Retrieve the status code from the response
        int statusCode = response.getStatusCode();
        logger.info("status code is" + statusCode);

        verifyResponseCode(statusCode, statusCodes.SC_NOT_FOUND);

        // Retrieve the response body as a String
        String responseBody = response.getBody().asString();

        // Perform assertions on the response body (for example, checking if it contains certain text)
        softAssert.assertEquals(responseBody.contains("userId"), true, "Response body does not contain 'userId'");
        softAssert.assertEquals(responseBody.contains("id"), true, "Response body does not contain 'id'");
        softAssert.assertEquals(responseBody.contains("title"), true, "Response body does not contain 'title'");
        softAssert.assertEquals(responseBody.contains("body"), true, "Response body does not contain 'body'");
    }


    @AfterMethod(alwaysRun = true)
    public void cleanup() {
        response = null;
    }

}



