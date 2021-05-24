package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PostLikeTest extends FunctionalTests {

    @Test
    void postsShouldReturnTheCorrectNumberOfLikes() {
        Response response = given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(new JSONObject().put("entry", "TestEntry").toString())
                .expect()
                .statusCode(HttpStatus.SC_CREATED)
                .when()
                .post("/blog/user/1/post");

        JSONObject json = new JSONObject(response.body().asString());
        long id = json.getLong("id");

        given()
                .expect()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .post("/blog/user/2/like/" + id);

        response = given()
                .expect()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .get("/blog/post/" + id);

        json = new JSONObject(response.body().asString());
        int likesCount = json.getInt("likesCount");
        Assertions.assertEquals(1, likesCount);
    }

    @Test
    void userWithCONFIRMEDStatusShouldBeAbleToLikePost() {
        Response response = given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(new JSONObject().put("entry", "TestEntry").toString())
                .expect()
                .statusCode(HttpStatus.SC_CREATED)
                .when()
                .post("/blog/user/1/post");

        JSONObject json = new JSONObject(response.body().asString());
        long id = json.getLong("id");

        given()
                .expect()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .post("/blog/user/2/like/" + id);
    }

    @Test
    void userShouldNotBeAbleToLikeYourOwnPost() {
        Response response = given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(new JSONObject().put("entry", "TestEntry").toString())
                .expect()
                .statusCode(HttpStatus.SC_CREATED)
                .when()
                .post("/blog/user/1/post");

        JSONObject json = new JSONObject(response.body().asString());
        long id = json.getLong("id");

        given()
                .expect()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .post("/blog/user/1/like/" + id);
    }

    @Test
    void addLikeToPostAgainShouldNotChangePostStatus() {
        Response response = given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(new JSONObject().put("entry", "TestEntry").toString())
                .expect()
                .statusCode(HttpStatus.SC_CREATED)
                .when()
                .post("/blog/user/1/post");

        JSONObject json = new JSONObject(response.body().asString());
        long id = json.getLong("id");

        given()
                .expect()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .post("/blog/user/2/like/" + id);

        response = given()
                .expect()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .post("/blog/user/2/like/" + id);
        Assertions.assertEquals(false, Boolean.valueOf(response.getBody().asString()));
    }
}
