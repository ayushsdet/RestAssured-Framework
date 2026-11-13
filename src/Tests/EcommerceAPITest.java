package Tests;

import POJO.LoginRequest;
import POJO.LoginResponse;
import POJO.Order;
import POJO.OrderDetail;
import io.restassured.builder.RequestSpecBuilder;
import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EcommerceAPITest {

    public static void main (String[] args){
        RequestSpecification req= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType("application/json").build();

        LoginRequest loginRequest= new LoginRequest();
        loginRequest.setUserEmail("Ayush7080@gmail.com");
        loginRequest.setUserPassword("Ayush@123");

        RequestSpecification reqLogin =  given().log().all().spec(req).body(loginRequest);

        LoginResponse loginResponse = reqLogin.when().post("api/ecom/auth/login")
                .then().statusCode(200).extract().response().as(LoginResponse.class);

       System.out.println( loginResponse.getToken());
       System.out.println( loginResponse.getUserId());

        String token = loginResponse.getToken();
        String userId = loginResponse.getUserId();


       // 2. Add Product :

        RequestSpecification addProductBaseReq = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", token)
                .build();

        RequestSpecification reqAddProduct = given().log().all()
                .spec(addProductBaseReq)
                .param("productName", "Bandook")
                .param("productAddedBy", userId)
                .param("productCategory", "sports")
                .param("productSubCategory", "cricket")
                .param("productPrice", "5100")
                .param("productDescription", "KWE Sports")
                .param("productFor", "men")
                .multiPart("productImage", new File("C:\\Users\\AYUSH VERMA\\Pictures\\Whatsapp\\20210702_021429_0000.png"));

        String addProductResponse = reqAddProduct.when()
                .post("/api/ecom/product/add-product")
                .then().log().all()
                .extract().response().asString();

        JsonPath js = new JsonPath(addProductResponse);
        String productId = js.getString("productId");



        // 3. Create Order
        RequestSpecification createOrderBaseReq = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", token)
                .setContentType("application/json")
                .build();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCountry("India");
        orderDetail.setProductOrderedId(productId);

        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        orderDetailList.add(orderDetail);

        Order orders = new Order();
        orders.setOrders(orderDetailList);

        RequestSpecification createOrderReq = given().log().all()
                .spec(createOrderBaseReq)
                .body(orders);

        String responseAddOrder = createOrderReq.when()
                .post("/api/ecom/order/create-order")
                .then().log().all()
                .extract().response()
                .asString();

        System.out.println(responseAddOrder);

        // Extract orderId for View Order / Delete Order steps
        JsonPath orderJs = new JsonPath(responseAddOrder);
        String orderId = orderJs.getString("orders[0]");
        System.out.println("Created Order ID: " + orderId);


        // 4. DELETE PRODUCT API (Cleanup)
        RequestSpecification deleteProdBaseReq = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", token)
                .setContentType("application/json")
                .build();

        String deleteProductResponse = given().log().all()
                .spec(deleteProdBaseReq)
                .pathParam("productId", productId)
                .when()
                .delete("/api/ecom/product/delete-product/{productId}")
                .then().log().all()
                .extract().response().asString();

        JsonPath js1 = new JsonPath(deleteProductResponse);
        Assert.assertEquals(js1.getString("message"), "Product Deleted Successfully");

    }

}
