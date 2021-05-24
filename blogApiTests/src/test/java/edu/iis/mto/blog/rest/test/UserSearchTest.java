package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class UserSearchTest extends FunctionalTests{

    @Test
    void deletedUsersShouldNotBeReturned() {
        given()
                .expect()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .get("/blog/user/4");
    }

    @Test
    void findUsersWithREMOVEDStatusShouldNotBeSuccessful() {
        Response response = given()
                .expect()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .get("/blog/user/find?searchString=Bob");
        Assertions.assertEquals("[]",response.body().asString());
    }
}
