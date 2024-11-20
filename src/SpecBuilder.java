import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Add Place
		RequestSpecification req = new RequestSpecBuilder()
				                       .setBaseUri("https://rahulshettyacademy.com")
				                       .addQueryParam("key", "qaclick123")
				                       .setContentType(ContentType.JSON)
				                       .build();
		RequestSpecification req1 = given().log().all()
				.spec(req);
		
		ResponseSpecification res = new ResponseSpecBuilder()
				                        .expectStatusCode(200)
				                        .expectContentType(ContentType.JSON)
				                        .build();
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
	
		String response = req1
				.body(Payload.AddPlace())
				.when().post("maps/api/place/add/json")
				.then().log().all().spec(res).extract().response().asString();
		
		/*String response = req1
		.body(Payload.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().body("scope", equalTo("APP"))
		.header("Server","Apache/2.4.52 (Ubuntu)").extract().response().asString();*/
		
		//Add place -> Update place with new address, Get place to validate if new address is present in response
		
		System.out.println(response);
		System.out.println("post request completed");
		
		JsonPath js = new  JsonPath(response);
		String placeId = js.getString("place_id");
		
		System.out.println(placeId);
		
		String newAddress = "Summer Walk, Africa";
		
		req1
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().body("msg", equalTo("Address successfully updated"));
		
		System.out.println("put request completed");
		
		//Get place
		
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", ""+placeId+"")
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).body("address", equalTo(newAddress)).extract().response().asString();
		
		
		JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress,newAddress);
		
	}

}

