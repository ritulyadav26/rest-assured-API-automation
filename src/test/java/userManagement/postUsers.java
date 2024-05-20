package userManagement;

import core.StatusCode;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;
import pojo.cityRequest;
import pojo.postRequestBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class postUsers {

    private static FileInputStream fileInputStreamMethod(String requestBodyFileName){
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(new File(System.getProperty("user.dir") + "/resources/TestData/" + requestBodyFileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return fileInputStream;
    }

    @Test
    public void validatePostWithString() {

        //Authenticate and get an authorization token
        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"name\":\"morpheus\",\"job\":\"leader\"}")
                .when()
                .post("https://reqres.in/api/users");
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println("validatePostWithString executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validatePutWithString() {

        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"name\":\"morpheus\",\"job\":\"AutomationLead\"}")
                .when()
                .put("https://reqres.in/api/users/2");
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println("validatePutWithString executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validatePatchWithString() {

        Response response = given()
                .header("Content-Type", "application/json")
                .body("{\"name\":\"ritul\"}")
                .when()
                .put("https://reqres.in/api/users/2");
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println("validatePatchWithString executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validatePostWithJsonFile() throws IOException {

        //Authenticate and get an authorization token
        Response response = given()
                .header("Content-Type", "application/json")
                .body(IOUtils.toString(fileInputStreamMethod("postRequestBody.json")))
                .when()
                .post("https://reqres.in/api/users");
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println("validatePostJsonFile executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validatePatchWithJsonFile() throws IOException {

        //Authenticate and get an authorization token
        Response response = given()
                .header("Content-Type", "application/json")
                .body(IOUtils.toString(fileInputStreamMethod("patchRequestBody.json")))
                .when()
                .patch("https://reqres.in/api/users/2");
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println("validatePatchWithJsonFile executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validatePutWithJsonFile() throws IOException {

        //Authenticate and get an authorization token
        Response response = given()
                .header("Content-Type", "application/json")
                .body(IOUtils.toString(fileInputStreamMethod("putRequestBody.json")))
                .when()
                .patch("https://reqres.in/api/users/2");
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println("validatePutWithJsonFile executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validatePostWithPojo()  {

        postRequestBody postRequest = new postRequestBody();
        postRequest.setJob("leader");
        postRequest.setName("morpheus");

        //Authenticate and get an authorization token
        Response response = given()
                .header("Content-Type", "application/json")
                .body(postRequest)
                .when()
                .post("https://reqres.in/api/users");
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println("validatePostWithPojo executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validatePostWithPojoList() {

        List<String> listLanguage = new ArrayList<>();
        listLanguage.add("Java");
        listLanguage.add("Python");

        postRequestBody postRequest = new postRequestBody();
        postRequest.setJob("leader");
        postRequest.setName("morpheus");
        postRequest.setLanguages(listLanguage);

        //Authenticate and get an authorization token
        Response response = given()
                .header("Content-Type", "application/json")
                .body(postRequest)
                .when()
                .post("https://reqres.in/api/users");
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println("validatePostWithPojoList executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validatePutWithPojo() {

        postRequestBody putRequest = new postRequestBody();
        putRequest.setName("Reha");
        putRequest.setJob("AutomationLead");

        //Authenticate and get an authorization token
        Response response = given()
                .header("Content-Type", "application/json")
                .body(putRequest)
                .when()
                .patch("https://reqres.in/api/users/2");
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println("validatePutWithPojo executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validatePatchWithPojo()  {

        postRequestBody patchRequest = new postRequestBody();
        patchRequest.setName("Meghana");

        //Authenticate and get an authorization token
        Response response = given()
                .header("Content-Type", "application/json")
                .body(patchRequest)
                .when()
                .patch("https://reqres.in/api/users/2");
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println("validatePatchWithPojo executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validatePostWithPojoListObject() {

        List<String> listLanguage = new ArrayList<>();
        listLanguage.add("Java");
        listLanguage.add("Python");

        cityRequest cityRequests1 = new cityRequest();
        cityRequests1.setName("bangalore");
        cityRequests1.setTemperature("30");
        cityRequest cityRequests2 = new cityRequest();
        cityRequests2.setName("Delhi");
        cityRequests2.setTemperature("40");
        List<cityRequest> cityRequests = new ArrayList<>();
        cityRequests.add(cityRequests1);
        cityRequests.add(cityRequests2);

        postRequestBody postRequest = new postRequestBody();
        postRequest.setJob("leader");
        postRequest.setName("morpheus");
        postRequest.setLanguages(listLanguage);
        postRequest.setCityRequestBody(cityRequests);

        //Authenticate and get an authorization token
        Response response = given()
                .header("Content-Type", "application/json")
                .body(postRequest)
                .when()
                .post("https://reqres.in/api/users");
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println("validatePostWithPojoList executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validatePatchWithResponsePojo()  {
        String name = "meghana";
        postRequestBody patchRequest = new postRequestBody();
        patchRequest.setName(name);

        //Authenticate and get an authorization token
        Response response = given()
                .header("Content-Type", "application/json")
                .body(patchRequest)
                .when()
                .patch("https://reqres.in/api/users/2");
        postRequestBody responseBody = response.as(postRequestBody.class);
        System.out.println(responseBody.getName());
        assertEquals(responseBody.getName(), name);
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println("validatePatchWithPojo executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test(description = "Validate the response with POJO")
    public void validatePostResponseWithPojoListObject() {

        String name = "bangalore";
        String temperature = "30";

        List<String> listLanguage = new ArrayList<>();
        listLanguage.add("Java");
        listLanguage.add("Python");

        cityRequest cityRequests1 = new cityRequest();
        cityRequests1.setName(name);
        cityRequests1.setTemperature(temperature);
        cityRequest cityRequests2 = new cityRequest();
        cityRequests2.setName("Delhi");
        cityRequests2.setTemperature("40");
        List<cityRequest> cityRequests = new ArrayList<>();
        cityRequests.add(cityRequests1);
        cityRequests.add(cityRequests2);

        postRequestBody postRequest = new postRequestBody();
        postRequest.setJob("leader");
        postRequest.setName("morpheus");
        postRequest.setLanguages(listLanguage);
        postRequest.setCityRequestBody(cityRequests);

        //Authenticate and get an authorization token
        Response response = given()
                .header("Content-Type", "application/json")
                .body(postRequest)
                .when()
                .post("https://reqres.in/api/users");
        postRequestBody responseBody = response.as(postRequestBody.class);
        System.out.println(responseBody.getCityRequestBody().get(0).getName());
        System.out.println(responseBody.getCityRequestBody().get(0).getTemperature());
        System.out.println(responseBody.getLanguages());
        assertEquals(responseBody.getCityRequestBody().get(0).getName(), name);
        assertEquals(responseBody.getCityRequestBody().get(0).getTemperature(), temperature);
        assertEquals(response.getStatusCode(), StatusCode.CREATED.code);
        System.out.println("validatePostWithPojoList executed successfully");
        System.out.println(response.getBody().asString());
    }
}
