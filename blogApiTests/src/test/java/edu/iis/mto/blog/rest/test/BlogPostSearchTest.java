package edu.iis.mto.blog.rest.test;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class BlogPostSearchTest extends FunctionalTests {

    @Disabled
    @Test
    void postsByUserWithTheDeletedStatusShouldNotBeSuccessful() {

    }
}
