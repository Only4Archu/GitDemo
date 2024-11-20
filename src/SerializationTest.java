import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.Location;
import pojo.SetLocation;

public class SerializationTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		
		SetLocation sl = new SetLocation();
		sl.setAccuracy(50);
		sl.setAddress("29, side layout, cohen 09");
		sl.setLanguage("French-IN");
		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		sl.setLocation(l);
		sl.setName("Frontline house");
		sl.setPhone_number("(+91) 983 893 3937");
		List<String> t = new  ArrayList<String>(); 
		t.add("shoe park");
		t.add("shop");
		sl.setTypes(t);
		sl.setWebsite("http://google.com");
		Response response1 = given()
						  .log().all()
						  .queryParam("key", "qaclick123")
				          .header("Content-Type","application/json")
		                  .body(sl)
		                  .when()
		                  .post("maps/api/place/add/json")
		                  .then()
		                  .log().all()
		                  .assertThat().statusCode(200)
		                  .extract().response();
		System.out.println(response1.asString());

	}

}
