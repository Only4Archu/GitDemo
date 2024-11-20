import static io.restassured.RestAssured.*;
public class GraphQL {
	
	public static void main(String[] args) {
		
		String response =given().log().all().header("Content-Type","application/json")
		.body("{\"query\":\"query($locationId:Int!,$characterId: Int!, $episodeId: Int!)\\n{\\n  location(locationId: $locationId)\\n  {\\n    name\\n    type\\n    dimension\\n    id\\n  }\\n  character(characterId:$characterId)\\n  {\\n    id\\n    name\\n    location\\n    {\\n      name\\n    }\\n  }\\n  episode(episodeId:$episodeId)\\n  {\\n    air_date\\n    episode\\n  }\\n  \\n}\",\"variables\":{\"locationId\":15416,\"characterId\":10413,\"episodeId\":11449}}")
		.when()
		.post("https://rahulshettyacademy.com/gq/graphql")
		.then()
		.extract()
		.response()
		.asString();
		
		System.out.println(response);
	}

}
