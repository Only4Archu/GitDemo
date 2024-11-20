import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath js = new  JsonPath(Payload.CourseFee());
		
		//Print No of courses returned by API
		int courseSize= js.getInt("courses.size()");
		System.out.println(courseSize);
		
		//Print Purchase Amount
		int dashboardPurchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(dashboardPurchaseAmount);
		
		//Print Title of the first course
		System.out.println(js.getString("courses[0].title"));
		
		//Print All course titles and their respective Prices
		for(int i=0;i<courseSize;i++) {
			System.out.println("course name = "+js.getString("courses["+i+"].title"));
			System.out.println("course price = "+js.getString("courses["+i+"].price"));
		}
		
		//Print no of copies sold by RPA Course
		for(int j=0;j<courseSize;j++) {
			if (js.getString("courses["+j+"].title").equals("RPA")){
				System.out.println(js.getString("courses["+j+"].copies"));
				break;
			}
		}
		
		//Verify if Sum of all Course prices matches with Purchase Amount
		int sum =0;
		int totalSum=0;
		for(int i=0;i<courseSize;i++) {
			totalSum = totalSum+(js.getInt("courses["+i+"].price"))*(js.getInt("courses["+i+"].copies"));
		}
		System.out.println(totalSum);
		
		Assert.assertEquals(totalSum, dashboardPurchaseAmount);
	}

}
