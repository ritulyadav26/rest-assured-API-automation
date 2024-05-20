package userManagement;

import core.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.ExtentReport;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class getErgast extends BaseTest {

    @Test(description = "Validate the statuscode for GET users endpoint", groups = "RegressionSuite")
    public void validateResponseBodyGetPathParam() {
        ExtentReport.extentlog =
                ExtentReport.extentreport
                        .startTest("validateResponseBodyGetPathParam", "Validate 200 status code for GET method");
        String raceSeasonValue = "2017";
        Response resp = given().pathParam("raceSeason", raceSeasonValue)
                .when()
                .get("http://ergast.com/api/f1/{raceSeason}/circuits.json");//RestAssured
        int actualStatusCode = resp.statusCode(); //RestAssured
        assertEquals(actualStatusCode, 200); //TestNG
        System.out.println(resp.body().asString());
    }
}
