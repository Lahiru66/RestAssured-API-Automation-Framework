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
import utils.Data;
import utils.StatusCodes;

import static validations.CommonValidations.verifyResponseCode;
import static utils.Constants.*;

public class PostsTest extends TestBase {
    public Helper helper;
    public StatusCodes statusCodes;
    private static final Logger logger = LogManager.getLogger(PostsTest.class);
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
    }

    @Test(enabled = true, priority = 1, description = "create posts", groups ={"smoke"})
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
        String returnedBody = response.jsonPath().getString(BODY);
        String returnedUserId = response.jsonPath().getString(USERID);
        String id = response.jsonPath().getString(ID);

        softAssert.assertEquals(returnedTitle, randomTitle);
        softAssert.assertEquals(returnedBody, randomBody);
        softAssert.assertEquals(returnedUserId, randomUserId);
        softAssert.assertNotNull(id, "Id is null");

        softAssert.assertAll();

    }

    @Test(enabled = true, priority = 1, description = "create posts", groups ={"smoke"},dataProvider = "postDataProvider", dataProviderClass = Data.class)
    public void DataDrivenPostRequest(String title, String body, String userId) {

        PostDTO postDTO = PostDTO.builder()
                .title(title)
                .body(body)
                .userId(userId).build();

        response = helper.createPost(postDTO);

        // Retrieve the status code from the response
        int statusCode = response.getStatusCode();
        logger.info("status code is" + statusCode);

        verifyResponseCode(statusCode, statusCodes.SC_CREATED);

        String returnedTitle = response.jsonPath().getString(TITLE);
        String returnedBody = response.jsonPath().getString(BODY);
        String returnedUserId = response.jsonPath().getString(USERID);
        String id = response.jsonPath().getString(ID);

        softAssert.assertEquals(returnedTitle, title);
        softAssert.assertEquals(returnedBody, body);
        softAssert.assertEquals(returnedUserId, userId);
        softAssert.assertNotNull(id, "Id is null");

        softAssert.assertAll();

    }


    @Test(enabled = true, priority = 2, description = "Get one post", groups ={"regression"})
    public void getRequest() {

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

        softAssert.assertAll();

    }
    @Test(enabled = true, priority = 3, description = "Update one post", groups ={"regression"})
    public void putRequest() {

        String randomTitle = CommonUtils.generateRandomString();
        String randomBody = CommonUtils.generateRandomString();
        String randomUserId = CommonUtils.generateRandomAlphanumeric(5);

        PostDTO postDTO = PostDTO.builder()
                .title(randomTitle)
                .body(randomBody)
                .userId(randomUserId).build();

        response = helper.updatePost(postDTO);

        // Retrieve the status code from the response
        int statusCode = response.getStatusCode();
        logger.info("status code is" + statusCode);

        verifyResponseCode(statusCode, statusCodes.SC_OK);

        String returnedTitle = response.jsonPath().getString(TITLE);
        String returnedBody = response.jsonPath().getString(BODY);
        String returnedUserId = response.jsonPath().getString(USERID);
        String id = response.jsonPath().getString(ID);

        softAssert.assertEquals(returnedTitle, randomTitle);
        softAssert.assertEquals(returnedBody, randomBody);
        softAssert.assertEquals(returnedUserId, randomUserId);
        softAssert.assertNotNull(id, "Id is null");

        softAssert.assertAll();
    }

    @Test(enabled = true, priority = 4, description = "get one post",groups ={"regression"})
    public void patchRequest() {

        String randomTitle = CommonUtils.generateRandomString();
        String randomBody = CommonUtils.generateRandomString();
        String randomUserId = CommonUtils.generateRandomAlphanumeric(5);

        PostDTO postDTO = PostDTO.builder()
                .title(randomTitle)
                .body(randomBody)
                .userId(randomUserId).build();

        response = helper.createPost(postDTO);

        String returnedTitle = response.jsonPath().getString(TITLE);
        String returnedBody = response.jsonPath().getString(BODY);
        String returnedUserId = response.jsonPath().getString(USERID);
        String id = response.jsonPath().getString(ID);

        String randomTitle2 = CommonUtils.generateRandomString();

        postDTO = PostDTO.builder()
                .title(randomTitle2)
                .body(returnedBody)
                .userId(returnedUserId)
                .build();

        response = helper.partialUpdatePost(postDTO);

        // Retrieve the status code from the response
        int statusCode = response.getStatusCode();
        logger.info("status code is" + statusCode);

        verifyResponseCode(statusCode, statusCodes.SC_OK);

        String returnedTitle2 = response.jsonPath().getString(TITLE);
        String returnedBody2 = response.jsonPath().getString(BODY);
        String returnedUserId2 = response.jsonPath().getString(USERID);
        String id2 = response.jsonPath().getString(ID);

        softAssert.assertEquals(returnedTitle2, randomTitle2);
        softAssert.assertNotNull(returnedBody2, "Body is null");
        softAssert.assertNotNull(returnedUserId2, "UserID is null");
        softAssert.assertNotNull(id2, "Id is null");

        softAssert.assertAll();
    }

    @Test(enabled = true, priority = 5, description = "Remove one post", groups ={"smoke"})
    public void deleteRequest() {

        Response response = helper.deletePost();

        // Retrieve the status code from the response
        int statusCode = response.getStatusCode();
        logger.info("status code is" + statusCode);

        verifyResponseCode(statusCode, statusCodes.SC_OK);

    }
    @AfterMethod(alwaysRun = true)
    public void cleanup() {
        response = null;
    }
}

