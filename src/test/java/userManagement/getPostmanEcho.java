package userManagement;

import core.StatusCode;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import utils.JsonReader;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class getPostmanEcho {
    @Test
    public void validateResponseBodyGetBasicAuth() {
        Response resp = given()
                .auth()
                .basic("postman", "password")
                .when()
                .get("https://postman-echo.com/basic-auth");

        int actualStatusCode = resp.statusCode(); //RestAssured
        assertEquals(actualStatusCode, 200); //TestNG
        System.out.println(resp.body().asString());
        //accessToken = resp.path("token");
    }

    @Test
    public void validateResponseBodyGetDigestAuth() {
        Response resp = given()
                .auth()
                .digest("postman", "password")
                .when()
                .get("https://postman-echo.com/digest-auth");

        int actualStatusCode = resp.statusCode();
        assertEquals(actualStatusCode, StatusCode.SUCCESS.code);
        System.out.println(resp.body().asString());
    }

    @Test
    public void validateWithTestDataFromJson() throws IOException, ParseException {
        String username = JsonReader.getTestData("username");
        String password = JsonReader.getTestData("password");
        System.out.println("username from json is: " + username + ":" + "***password from json is: " + password);
        Response resp = given()
                .auth()
                .basic(username, password)
                .when()
                .get("https://postman-echo.com/basic-auth");

        int actualStatusCode = resp.statusCode(); //RestAssured
        assertEquals(actualStatusCode, 200); //TestNG
        System.out.println("validateWithTestDataFromJson executed successfully");
    }

}
