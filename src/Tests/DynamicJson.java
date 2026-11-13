package Tests;

import files.Payloads;
import files.ReusableMethods;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DynamicJson {

    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle) {

        RestAssured.baseURI="http://216.10.245.166";
        String response= given().log().all()
                .header("Content-Type","application/json")
                .body(Payloads.addBook(isbn,aisle))
                .when().post("/Library/Addbook.php")
                .then().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = ReusableMethods.rawToJson(response);
        String id= js.get("ID");
        System.out.println(id);

    }

    @DataProvider(name = "BooksData")
    public Object[][] getData() {
        // Multidimensional object array containing multiple datasets {isbn, aisle}
        return new Object[][] {
                {"ojfwty", "9363"},
                {"cwetee", "4253"},
                {"okmfet", "533"}
        };
    }
}
