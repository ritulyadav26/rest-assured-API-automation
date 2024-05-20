package userManagement;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;

public class BuilderPatternImplementation {

    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;

    @Test
    public void testRestAssuredNormalApproach() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given()
                .contentType(ContentType.JSON)
                .queryParam("userId", "1")
                .header("Authorization", "Bearer my-token")
                .when()
                .get("/posts")
                .then()
                .assertThat()
                .statusCode(200);

    }

    @Test
    public void testRestAssuredBuilderPattern() {
        requestSpec = getRequestSpecificationBuilder("1", "application/json");
        responseSpec = getResponseSpecificationBuilder(200, "application/json");
        //RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given()
                .spec(requestSpec)
                .when()
                .get("/posts")
                .then()
                .spec(responseSpec);

    }

    private RequestSpecification getRequestSpecificationBuilder(String queryParam, String contentType) {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com")
                .setContentType(contentType)
                .addQueryParam("userId", queryParam)
                .build();
        return requestSpec;
    }

    private ResponseSpecification getResponseSpecificationBuilder(int statusCode, String contentType) {
         responseSpec = new ResponseSpecBuilder()
                 .expectStatusCode(statusCode)
                 .expectContentType(contentType)
                 .build();
         return responseSpec;
    }
}
