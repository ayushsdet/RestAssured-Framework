package Tests;


import POJO.AddCoursePojo;
import POJO.Api;
import POJO.WebAutomation;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OAuthTest {

    @Test
    public void getCourseDetails() {

        // Expected titles array to validate against API response
        String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};

        // 1. Generate OAuth 2.0 Access Token
        String tokenResponse = given()
                .formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type", "client_credentials")
                .formParam("scope", "trust")
                .when().log().all()
                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
                .asString();

        JsonPath jsonPath = new JsonPath(tokenResponse);
        String accessToken = jsonPath.getString("access_token");

        // 2. Deserialize response directly into your AddCoursePojo class
        AddCoursePojo gc = given()
                .queryParam("access_token", accessToken)
                .when().log().all()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .as(AddCoursePojo.class);

        // 3. Extract top-level details using POJO getters
        System.out.println("LinkedIn URL: " + gc.getLinkedIn());
        System.out.println("Instructor: " + gc.getInstructor());

        // 4. Dynamic price lookup for a specific course
        List<Api> apiCourses = gc.getCourses().getApi();
        for (int i = 0; i < apiCourses.size(); i++) {
            if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                System.out.println("SoapUI Price: " + apiCourses.get(i).getPrice());
            }
        }

        // 5. Collect all WebAutomation titles and assert with expected list
        ArrayList<String> actualTitles = new ArrayList<>();
        List<WebAutomation> webAutomationCourses = gc.getCourses().getWebAutomation();

        for (int j = 0; j < webAutomationCourses.size(); j++) {
            actualTitles.add(webAutomationCourses.get(j).getCourseTitle());
        }

        List<String> expectedList = Arrays.asList(courseTitles);

        System.out.println("Actual Titles: " + actualTitles);
        System.out.println("Expected Titles: " + expectedList);

        Assert.assertEquals(actualTitles, expectedList);
    }
}
