package Tests;

import files.Payloads;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class Basics {
    public static void main(String[] args){
        RestAssured.baseURI="https://rahulshettyacademy.com";

// Add Place :

        String response = given()
                .queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body(Payloads.addPlace())
                .when()
                  .post("maps/api/place/add/json")
                .then().log().all()
                  .assertThat().statusCode(200)
                .body("scope",equalTo("APP"))
                .header("Server","Apache/2.4.52 (Ubuntu)")
                .extract().response().asString();

        System.out.println(response);

        JsonPath js= new JsonPath(response);
        String placeId = js.getString("place_id");


// Update Place :

        given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type","application/json")
                .body("{\r\n" +
                        "\"place_id\":\""+placeId+"\",\r\n" +
                        "\"address\":\"70 Summer walk, USA\",\r\n" +
                        "\"key\":\"qaclick123\"\r\n" +
                        "}").
                when()
                .put("maps/api/place/update/json")
                .then().assertThat().statusCode(200)
                .body("msg", equalTo("Address successfully updated"));

// Get Place :

        String newAddress = "70 Summer Walk, Africa";

        given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\r\n" +
                        "\"place_id\":\""+placeId+"\",\r\n" +
                        "\"address\":\""+newAddress+"\",\r\n" +
                        "\"key\":\"qaclick123\"\r\n" +
                        "}").
                when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));

//Get Place :

        String getPlaceResponse = given().log().all()
                .queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();

        JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
        String actualAddress = js1.getString("address");
        System.out.println(actualAddress);
        Assert.assertEquals(actualAddress, newAddress);
    }
}
