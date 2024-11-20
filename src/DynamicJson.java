import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	//@Test
	public void addBook()
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response = given().log().all().header("Content-Type","application/json")
		.body(Payload.AddBook("ewr","321"))
		.when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).body("Msg", equalTo("successfully added"))
		.extract().response().asString();
		JsonPath js = ReusableMethods.rawToJson(response);
		String id =js.getString("ID");
		System.out.println(id);
		
	}
	
	    @Test
		public void addBook2() throws IOException
		{
			RestAssured.baseURI="http://216.10.245.166";
			String response = given().log().all().header("Content-Type","application/json")
			.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\Asus\\Desktop\\addbook1.json"))))
			.when().post("Library/Addbook.php")
			.then().log().all().assertThat().statusCode(200).body("Msg", equalTo("successfully added"))
			.extract().response().asString();
			JsonPath js = ReusableMethods.rawToJson(response);
			String id =js.getString("ID");
			System.out.println(id);
			
		}
		
	//@Test(dataProvider="BooksData")
	public void addBook1(String isbn, String aisle)
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response = given().log().all().header("Content-Type","application/json")
		.body(Payload.AddBook(isbn,aisle))
		.when().post("Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).body("Msg", equalTo("successfully added"))
		.extract().response().asString();
		JsonPath js = ReusableMethods.rawToJson(response);
		String id =js.getString("ID");
		System.out.println("Add book test case executed "+id);
		
	}
	
	//@Test(dataProvider="BooksData")
	public void deleteBook1(String isbn, String aisle)
	{
		RestAssured.baseURI="http://216.10.245.166";
		given().log().all().header("Content-Type","application/json")
		.body(Payload.DeleteBook(isbn,aisle))
		.when().post("Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("book is successfully deleted"));
		
	}
	
	@DataProvider(name = "BooksData")
	public Object[][] getData()
	{
		//array = collection of elements
		//multidimensional array = collection of arrays
		return new Object[][] {{"nwereds","14343"},{"nrtgfgd","9676"},{"noiuhjd","8695"}};
	}
	
}
