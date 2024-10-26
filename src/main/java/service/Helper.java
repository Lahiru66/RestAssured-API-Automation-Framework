package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.PostDTO;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Helper {

    private ObjectMapper objectMapper;

    public Helper() {
        objectMapper = new ObjectMapper();
    }

    public Response createPost(PostDTO postDTO) {
        Response response = null;
        try {
            String jsonBody = objectMapper.writeValueAsString(postDTO);
            System.out.println("Request Body: " + jsonBody);

            response = given()
                    .header("Content-type", "application/json")
                    .body(jsonBody)
                    .when()
                    .post("/posts")
                    .then()
                    .extract().response();

            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.asString());

        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Log the exception
        }

        return response;
    }

}


