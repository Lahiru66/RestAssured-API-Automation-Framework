package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.PostDTO;
import endpoints.Routes;
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
                    .post(Routes.base_URL)
                    .then()
                    .extract().response();

            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.asString());

        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Log the exception
        }

        return response;
    }


    public Response getPost(){
        Response response = null;

        response = given()
               // .pathParams("id", id)
                .header("Content-type", "application/json")
                .when()
                .get("/posts/1")
                .then()
                .extract().response();

        return response;

    }


}


