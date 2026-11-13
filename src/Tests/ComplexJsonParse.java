package Tests;

import files.Payloads;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

    public static void main(String[] args) {

        JsonPath js = new JsonPath(Payloads.coursePrice());

        // 1. Print No of courses returned by API
        int courseCount = js.getInt("courses.size()");
        System.out.println("1. Total courses count: " + courseCount);

        // 2. Print Purchase Amount
        int totalAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println("2. Purchase Amount: " + totalAmount);

        // 3. Print Title of the first course
        String firstCourseTitle = js.getString("courses[0].title");
        System.out.println("3. First Course Title: " + firstCourseTitle);

        // 4. Print All course titles and their respective Prices
        System.out.println("4. Course Titles & Prices:");
        for (int i = 0; i < courseCount; i++) {
            String courseTitle = js.getString("courses[" + i + "].title");
            int coursePrice = js.getInt("courses[" + i + "].price");
            System.out.println("   " + courseTitle + " : " + coursePrice);
        }

        // 5. Print no of copies sold by RPA Course
        System.out.print("5. No of copies sold by RPA: ");
        for (int i = 0; i < courseCount; i++) {
            String courseTitle = js.getString("courses[" + i + "].title");
            if (courseTitle.equalsIgnoreCase("RPA")) {
                int copies = js.getInt("courses[" + i + "].copies");
                System.out.println(copies);
                break;
            }
        }
    }
}