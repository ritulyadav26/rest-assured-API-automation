package userManagement;

import core.BaseTest;
import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import junit.framework.Assert;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.ExtentReport;
import utils.JsonReader;
import utils.PropertyReader;
import utils.SoftAssertionUtil;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.assertEquals;
import static utils.JsonReader.getJsonArray;

public class getUsers extends BaseTest {

    String serverAddress = PropertyReader.propertyReader("config.properties", "server");

    //SoftAssertionUtil softAssertion = new SoftAssertionUtil();

    @Test
    public void getUserData() {
        given().
                when().get("https://reqres.in/api/users?page=2").
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void validateGetResponseBody() {
        // Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        //Send a GET request and validate the response body using 'then'
        given()
                .when()
                .get("/todos/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body(not(isEmptyString()))
                .body("title", equalTo("delectus aut autem"))
                .body("userId", equalTo(1));
    }

    @Test
    public void validateResponseHasItems() {
        // Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request and store the response in a variable
        Response response = given()
                .when()
                .get("posts/")
                .then()  //here we are not doing validation with then, we are validating using hamcrest 'assertThat' and also then, extract, response is not mandatory here, without them also our response will get saved in 'response'.
                .extract()
                .response();

        // Use Hamcrest to check that the response body contains specific items
        assertThat(response.jsonPath().getList("title"), hasItems("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "qui est esse"));
    }

    @Test
    public void validateResponseHasSize() {
        // Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request and store the response in a variable
        Response response = given()
                .when()
                .get("/comments")
                .then()
                .extract()
                .response();

        //Use Hamcrest to check that the response body has a specific size
        assertThat(response.jsonPath().getList(""), hasSize(500));
    }

    @Test
    public void validateListContainsInOrder() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given()
                .when()
                .get("/comments?postId=1")
                .then()
                .extract()
                .response();

        // Use Hamcrest to check that the response body contains specific items in a specific order
        List<String> expectedEmails = Arrays.asList("Eliseo@gardner.biz", "Jayne_Kuhic@sydney.com", "Nikita@garfield.biz", "Lew@alysha.tv", "Hayden@althea.biz");
        assertThat(response.jsonPath().getList("email"), contains(expectedEmails.toArray(new String[0])));
    }

    @Test
    public void testGetUsersWithQueryParameters() {
        RestAssured.baseURI = "https://reqres.in/api";
        Response response = given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Assert that the response contains 6 users
        response.then().body("data", hasSize(6));

        // Assert that the first user in the list has the correct values
        response.then().body("data[0].id", equalTo(7));
        response.then().body("data[0].email", is("michael.lawson@reqres.in"));
        response.then().body("data[1].first_name", is("Lindsay"));
        response.then().body("data[0].last_name", is("Lawson"));
        response.then().body("data[0].avatar", is("https://reqres.in/img/faces/7-image.jpg"));
    }

    @Test
    public void validateStatusCodeGetUser() {

        System.out.println("**************");

        Response resp =
                given()
                        .queryParam("page", 2)
                        .when()
                        .get("https://reqres.in/api/users"); //Rest Assured
        int actualStatusCode = resp.statusCode(); //Rest Assured
        assertEquals(actualStatusCode, 200); //Testng
    }

    @Test
    public void testGetUsersWithMultipleQueryParams() {
        Response response =
                given()
                        .queryParam("page", 2)
                        .queryParam("per_page", 3)
                        .queryParam("rtqsdr", 4)
                        .when()
                        .get("https://reqres.in/api/users")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
    }

    @Test
    public void testCreateUserWithFormParam() {
        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "John Doe")
                .formParam("job", "Developer")
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        // Assert that the response contains the correct name and job values
        response.then().body("name", equalTo("John Doe"));
        response.then().body("job", equalTo("Developer"));
    }

    @Test
    public void testGetUserListWithHeader() {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);
        System.out.println("testGetUserListWithHeader Executed Successfully");
    }

    @Test
    public void testWithTwoHeaders() {
        given()
                .header("Authorization", "bearer yvwevefgh345adecrfrrrepfjafvf13bx34ghtgirfnscsvnf1267")
                .header("Content-Type", "application/json")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);
        System.out.println("testWithTwoHeaders Executed Successfully");
    }

    @Test
    public void testTwoHeaderWithMap() {

        //Create a map to hold headers
        Map<String, String> headersM = new HashMap<>();
        headersM.put("Content-Type", "application/json");
        headersM.put("Authorization", "bearer yvwevefgh345adecrfrrrepfjafvf13bx34ghtgirfnscsvnf1267");

        //Send a GET request with headers
        given()
                .headers(headersM)
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);

        System.out.println("testTwoHeaderWithMap Executed Successfully");
    }

    @Test
    public void testFetchHeaders() {
        Response resp = given()  //we are storing the response(body,cookies, header) in resp
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .extract().response();

        Headers headersO = resp.getHeaders(); //we have created object "headersO" of class Headers and fetching all the headers from response
        for (Header h : headersO) {
            if (h.getName().contains("Server")) {
                System.out.println(h.getName() + " : " + h.getValue());
                assertEquals(h.getValue(), "cloudflare");
                System.out.println("testFetchHeaders Executed Successfully");
            }
        }
    }

    @Test
    public void testUseCookies() {
        given()
                .cookie("cookieKey1", "cookieValue1")
                .cookie("cookieKey2", "cookieValue2")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("response", equalTo("expectedValue"));

        System.out.println("testUseCookies executed Successfully");


    }

    @Test
    public void testUseCookies1() {
        Cookie cookies = new Cookie.Builder("cookieKey1", "cookieValue1")
                .setComment("using cookie key")
                .build();

        given()
                .cookie(cookies)
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);

        System.out.println("testUseCookies1 executed Successfully");
    }

    @Test
    public void TestFetchCookies() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .extract().response();

        Map<String, String> cookies = response.getCookies();
        Cookies cookies1 = response.getDetailedCookies();
        cookies1.getValue("server");
        assertEquals(cookies1.getValue("server"), "cloudflare");
        assertThat(cookies, hasKey("JSESSIONID"));
        assertThat(cookies, hasValue("ABCDEF123456"));
    }

    @Test(description = "validate 204 for delete user", groups = {"SmokeSuite", "RegressionSuite"})
    public void verifyStatusCodeDelete() {
        ExtentReport.extentlog =
                ExtentReport.extentreport
                        .startTest("verifyStatusCodeDelete", "Validate 204 status code for DELETE method");
        Response resp = given()
                .delete("https://reqres.in/api/users/2");
        assertEquals(resp.getStatusCode(), StatusCode.NO_CONTENT.code);
        System.out.println("verifyStatusCodeDelete executed Successfully");
    }

    @Test(groups = "RegressionSuite")
    public void validateWithDataFromPropertiesFile() {
        ExtentReport.extentlog =
                ExtentReport.extentreport
                        .startTest("validateWithDataFromPropertiesFile", "Validate 200 status code for GET method");
        String serverAddress = PropertyReader.propertyReader("config.properties", "serverAddress");
        System.out.println("Server address is: " + serverAddress);
        Response resp =
                given()
                        .queryParam("page", 2)
                        .when()
                        .get(serverAddress); //Rest Assured
        int actualStatusCode = resp.statusCode(); //Rest Assured
        assertEquals(actualStatusCode, 200); //Testng
        System.out.println("validateWithDataFromPropertiesFile executed successfully" + serverAddress);
    }

    @Test
    public void validateFromProperties_TestData() throws IOException, ParseException {
        String endpoint = JsonReader.getTestData("endpoint");
        String URL = serverAddress + endpoint;
        System.out.println("URL is : " + URL);
        Response resp =
                given()
                        .queryParam("page", 2)
                        .when()
                        .get(URL); //Rest Assured
        int actualStatusCode = resp.statusCode(); //Rest Assured
        assertEquals(actualStatusCode, 200); //Testng
        System.out.println("validateFromProperties_TestData executed successfully " + URL);
    }

    @Test
    public void hardAssertion() {
        System.out.println("hardAssert");
        Assert.assertTrue(false);
        System.out.println("hardAssert");
    }

    @Test
    public void softAssertion() {

        System.out.println("softAssert");
        SoftAssertionUtil.assertTrue(true, "");
        SoftAssertionUtil.assertTrue(true, "");
    }

    @Test
    public void validateWithSoftAssertUtil() {
        RestAssured.baseURI = "https://reqres.in/api";
        Response response = given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Assert that the response contains 6 users
        response.then().body("data", hasSize(6));

        SoftAssertionUtil.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "Status code is not 200");
        SoftAssertionUtil.assertAll();
        System.out.println("validateWithSoftAssertUtil executed successfully");
        // Assert that the first user in the list has the correct values
        response.then().body("data[0].id", equalTo(7));
        response.then().body("data[0].email", is("michael.lawson@reqres.in"));
        response.then().body("data[1].first_name", is("Lindsay"));
        response.then().body("data[0].last_name", is("Lawson"));
        response.then().body("data[0].avatar", is("https://reqres.in/img/faces/7-image.jpg"));
    }

    @DataProvider(name = "testdata")
    public Object[][] testData() {
        return new Object[][] {
                {"1","John"},
                {"2","Jane"},
                {"3","Bob"}
        };
    }

    @Test(dataProvider = "testdata")
    @Parameters({"id","name"})
    public void testEndpoint(String id, String name) {
        given()
                .queryParam("id", id)
                .queryParam("name", name)
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200);
    }

    @Test
    public void Test() throws IOException, ParseException {
        JsonReader.getJsonArrayData("technology", 2);
        JSONArray jsonArray = getJsonArray("contact");
        Iterator<String> iterator = jsonArray.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
