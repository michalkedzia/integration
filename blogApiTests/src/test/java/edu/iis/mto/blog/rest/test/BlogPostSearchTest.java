package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class BlogPostSearchTest extends FunctionalTests {

    @Test
    void postsByUserWithTheDeletedStatusShouldNotBeSuccessful() {
        given()
                .expect()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .when()
                .get("/blog/post/1");
    }
}
