package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class BlogPostTest extends FunctionalTests{
    @Test
    void userWithCONFIRMEDStatusShouldBeAbleToAddNewPost() {
        JSONObject jsonObj = new JSONObject().put("entry", "TestEntry");
        given()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString())
                .expect()
                .log()
                .all()
                .statusCode(HttpStatus.SC_CREATED)
                .when()
                .post("/blog/user/1/post");
    }


}
