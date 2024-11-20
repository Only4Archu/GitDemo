import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pojo.AddProductResponse;
import pojo.OrderDetail;
import pojo.Orders;
import pojo.CreateOrderResponse;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.ViewOrderResponse;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ECommereceAPITest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Login call
		RequestSpecification req = new RequestSpecBuilder()
				                   .setBaseUri("https://rahulshettyacademy.com")
				                   .setContentType(ContentType.JSON)
				                   .build();
		
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUserEmail("Chocolate@gmail.com");
		loginRequest.setUserPassword("Dairymilk27");
		
		RequestSpecification reqLogin = given().relaxedHTTPSValidation().log().all().spec(req).body(loginRequest);
		
		LoginResponse loginResponse = reqLogin.post("/api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);
		
		System.out.println(loginResponse.getToken());
		String loginToken = loginResponse.getToken();
		System.out.println(loginResponse.getUserId());
		String loginUserId = loginResponse.getUserId();
		
		//Add the product
		
		RequestSpecification addProductBaseReq = new RequestSpecBuilder()
                                                 .setBaseUri("https://rahulshettyacademy.com")
                                                 .addHeader("authorization", loginToken)
                                                 .build();
		
		RequestSpecification addProductReq = given().log().all().spec(addProductBaseReq)
		.param("productName", "qwerty")
		.param("productAddedBy", loginUserId)
		.param("productCategory", "fashion")
		.param("productSubCategory", "shirts")
		.param("productPrice", "11500")
		.param("productDescription", "Addias Originals")
		.param("productFor", "women")
		.multiPart("productImage", new File("C://Users//Asus//Desktop//balloon.png"));
		
		
		AddProductResponse addProductResponse = addProductReq.when().post("/api/ecom/product/add-product").then().log().all().extract().response().as(AddProductResponse.class);
		
		System.out.println(addProductResponse.getProductId());
		String productId = addProductResponse.getProductId();
		
		//Create Order
		
		RequestSpecification orderBaseReq = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addHeader("authorization", loginToken)
                .setContentType(ContentType.JSON)
                .build();

		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCountry("India");
		orderDetail.setProductOrderedId(productId);
		
		List<OrderDetail> orderDetailList = new  ArrayList<OrderDetail>(); 
		orderDetailList.add(orderDetail);
		
		Orders order = new Orders();
		order.setOrders(orderDetailList);
		
		RequestSpecification createOrdertReq = given().log().all().spec(orderBaseReq).body(order);
		
		CreateOrderResponse createOrderResponse = createOrdertReq.when().post("/api/ecom/order/create-order").then().log().all().extract().response().as(CreateOrderResponse.class);
		
		System.out.println(createOrderResponse.getOrders().get(0));
		String productOrderId = createOrderResponse.getOrders().get(0);
		
		//View Order Details
		
		RequestSpecification viewOrderReq = given().log().all().spec(orderBaseReq).queryParam("id", productOrderId);
		
		ViewOrderResponse viewOrderResponse = viewOrderReq.when().get("/api/ecom/order/get-orders-details").then().log().all().extract().response().as(ViewOrderResponse.class);
		
		System.out.println(viewOrderResponse.getData().getOrderBy());
		System.out.println(viewOrderResponse.getData().getOrderPrice());
		System.out.println(viewOrderResponse.getData().getCountry());
		System.out.println(viewOrderResponse.getData().get_id());
		System.out.println(viewOrderResponse.getData().getProductName());
		

		//Delete Product
		
		RequestSpecification deleteProductReq = given().log().all().spec(orderBaseReq).pathParam("id", productId);
		
		deleteProductReq.when().delete("/api/ecom/product/delete-product/{id}").then().log().all();
		
	}

}
