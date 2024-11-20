import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class TotalSum {

	@Test
	public void ToFindTotalSum() {
		JsonPath js = new  JsonPath(Payload.CourseFee());
		int dashboardPurchaseAmount = js.getInt("dashboard.purchaseAmount");
		int courseSize= js.getInt("courses.size()");
		int totalSum=0;
		for(int i=0;i<courseSize;i++) {
			totalSum = totalSum+(js.getInt("courses["+i+"].price"))*(js.getInt("courses["+i+"].copies"));
		}	
		Assert.assertEquals(totalSum, dashboardPurchaseAmount);
	}
}