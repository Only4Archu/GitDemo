import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class OathTeat {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		
		String createAccessToken = given()
				.log().all()
				.formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
				.formParam("grant_type","client_credentials")
				.formParam("scope","trust")
				.when()
				.post("oauthapi/oauth2/resourceOwner/token")
				.then()
				.log().all()
				.assertThat().statusCode(200)
				.extract().response().asString();
		
		System.out.println(createAccessToken);
		
		JsonPath js = new  JsonPath(createAccessToken);
		String accessToken = js.getString("access_token");
		
		System.out.println(accessToken);
		
		/*given()
		.log().all()
		.queryParam("access_token", accessToken)
		.when()
		.get("oauthapi/getCourseDetails")
		.then()
		.log().all()
		.assertThat().body("url", equalTo("rahulshettycademy.com"));*/
		
		//Deserialization using pojo class
		
		GetCourse gc = given()
				.log().all()
				.queryParam("access_token", accessToken)
				.when()
				.get("oauthapi/getCourseDetails")
				.as(GetCourse.class);
		
		System.out.println(gc.getInstructor());
		
		System.out.println(gc.getLinkedIn());
		
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		List<Api> apiCourses = gc.getCourses().getApi();
		for (int i=0;i<apiCourses.size();i++)
		{
			if(apiCourses.get(i).getCourseTitle().equals("SoapUI Webservices testing")) {
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		ArrayList<String> a = new  ArrayList<String>(); 
		int sum = 0;
		List<WebAutomation> webAutomationCourses = gc.getCourses().getWebAutomation();
		for (int j=0;j<webAutomationCourses.size();j++)
		{
			System.out.println(webAutomationCourses.get(j).getCourseTitle());
			a.add(webAutomationCourses.get(j).getCourseTitle());
			sum = sum + Integer.parseInt( webAutomationCourses.get(j).getPrice());
		}
		System.out.println(sum);
		
		String[] s = {"Selenium Webdriver Java","Cypress","Protractor"}; 
		List<String> e=Arrays.asList(s);
		Assert.assertTrue(e.equals(a));
	
	}

}
