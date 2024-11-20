import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class BugTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://only4uarchu27-1729248517825.atlassian.net/";
		
		String createBugResponse = given()
				.log().all()
				.header("Content-Type","application/json")
				.header("Authorization","Basic b25seTR1YXJjaHUyN0BnbWFpbC5jb206QVRBVFQzeEZmR0Ywb2xDMFY0MlB2d1FwbFZaQk9UcDNXMEtuTGx4U3pwT1N0ZzVJSWVTaWtVS2Z6cEFOa0x1M3hTZHJnNmZGQTF1YlVHQkRLVndxcEdKMmk4WERrYkRuQkRqSUktbU53TlBYRi1JblV2YWxvZHNKSk9vV19BVVZYeF9IS3dybW1Zc0szZVdsNzdaZmNibWZPZjJzRHdxUGY5MVNESG1JQnNzYnVIUzg5ZnBuMHRJPUVCMEU4Rjg4")
				.body("{\r\n"
						+ "    \"fields\": {\r\n"
						+ "       \"project\":\r\n"
						+ "       {\r\n"
						+ "          \"key\": \"SCRUM\"\r\n"
						+ "       },\r\n"
						+ "       \"summary\": \"page is not working\",\r\n"
						+ "       \"issuetype\": {\r\n"
						+ "          \"name\": \"Bug\"\r\n"
						+ "       }\r\n"
						+ "   }\r\n"
						+ "}\r\n"
						+ "")
				.when()
				.post("rest/api/3/issue")
				.then()
				.log().all()
				.assertThat().statusCode(201)
				.extract().response().asString();
		
		System.out.println(createBugResponse);
		
		JsonPath js = new  JsonPath(createBugResponse);
		String id = js.getString("id");
		
		System.out.println(id);
		
		given()
		.log().all()
		.header("X-Atlassian-Token","no-check")
		.header("Authorization","Basic b25seTR1YXJjaHUyN0BnbWFpbC5jb206QVRBVFQzeEZmR0Ywb2xDMFY0MlB2d1FwbFZaQk9UcDNXMEtuTGx4U3pwT1N0ZzVJSWVTaWtVS2Z6cEFOa0x1M3hTZHJnNmZGQTF1YlVHQkRLVndxcEdKMmk4WERrYkRuQkRqSUktbU53TlBYRi1JblV2YWxvZHNKSk9vV19BVVZYeF9IS3dybW1Zc0szZVdsNzdaZmNibWZPZjJzRHdxUGY5MVNESG1JQnNzYnVIUzg5ZnBuMHRJPUVCMEU4Rjg4")
		.multiPart("file",new File("C:/Users/Asus/Downloads/linkbug.png/"))
		.pathParam("key", id)
		.when()
		.post("rest/api/3/issue/{key}/attachments")
		.then()
		.log().all()
		.assertThat().statusCode(200)
		.body("filename[0]", equalTo("linkbug.png"));
		
		
	}

}
