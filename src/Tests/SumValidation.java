package Tests;

import files.Payloads;
import io.restassured.path.json.JsonPath;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidation {

    @Test
    public void sumOfCourses() {
        JsonPath js = new JsonPath(Payloads.coursePrice());
        int count = js.getInt("courses.size()");
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");

        int calculatedTotal = 0;

        for (int i = 0; i < count; i++) {
            int price = js.getInt("courses[" + i + "].price");
            int copies = js.getInt("courses[" + i + "].copies");
            calculatedTotal += (price * copies);
        }

        System.out.println("Calculated Total: " + calculatedTotal);
        System.out.println("Dashboard Purchase Amount: " + purchaseAmount);

        // 6. Verify if Sum of all Course prices matches with Purchase Amount
        // (50*6) + (40*4) + (45*10) = 300 + 160 + 450 = 910
        Assert.assertEquals(calculatedTotal, purchaseAmount, "Calculated sum does not match purchase amount!");
    }
}
