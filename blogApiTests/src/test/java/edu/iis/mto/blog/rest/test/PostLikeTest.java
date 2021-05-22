package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PostLikeTest extends FunctionalTests{

    @Disabled
    @Test
    void postsShouldReturnTheCorrectNumberOfLikes() {

    }

    @Test
    void userWithCONFIRMEDStatusShouldBeAbleToLikePost() {
        given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .when()
                .post("/blog/user/2/like/1");
    }

    @Disabled
    @Test
    void userShouldNotBeAbleToLikeYourOwnPost() {

    }

    @Disabled
    @Test
    void addLikeToPostAgainShouldNotChangePostStatus() {

    }
}
