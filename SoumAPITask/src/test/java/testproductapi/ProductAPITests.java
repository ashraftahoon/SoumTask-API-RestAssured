package testproductapi;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;

public class ProductAPITests {


	@Test 
	public void testGetProductsPage2WithSize3() {


		Response res=given()
				.baseUri("https://api.qa.soum.sa")
				.header("lang", "ar")
				.queryParam("page number", 2)
				.queryParam("size", 3)
				.when()
				.get("/api-v2/rest/api/v1/product/");
		res.then().log().ifError()
		.statusCode(200);

	}

	@Test 
	public void testGetAllProducts() {


		Response res=given()
				.baseUri("https://api.qa.soum.sa")
				.header("lang", "ar")
				.when()
				.get("/api-v2/rest/api/v1/product/");
		res.then().log().all()
		.statusCode(200);
		
		// Extract the message from the response using JSON path
        String message = res
                .then()
                .extract()
                .path("message");

        // Assert that the message matches the expected value
        String expectedMessage = "Get Products successfully";
        res.then().assertThat().body("message", equalTo(expectedMessage));

	}



	@Test
	public void testGetProductsPage1WithSize10() {
		Response res=given()
				.baseUri("https://api.qa.soum.sa")
				.header("lang", "en")
				.queryParam("page number", 1)
				.queryParam("size", 10)
				.when()
				.get("/api-v2/rest/api/v1/product/");
		
		res.then().log().ifError()
		.statusCode(200);
	}




	@Test 
	public void testGetProductsInvalidPage() {

		Response res=given()
				.baseUri("https://api.qa.soum.sa")
				.header("lang", "en")
				.queryParam("page number", "invalid")
				.queryParam("size", 5)
				.when()
				.get("/api-v2/rest/api/v1/product/");
		
		res.then().log().ifError()
		.statusCode(200);

	}

	@Test  
	public void testGetProductsInvalidSize() {

		Response res=given()
				.baseUri("https://api.qa.soum.sa")
				.header("lang", "en")
				.queryParam("page number", 1)
				.queryParam("size", "invalid")
				.when()
				.get("/api-v2/rest/api/v1/product/")
				.then().extract().response();
		res.then().statusCode(400);

		String message= res.path("violations[0].message");
		System.out.println(message);
		assertEquals(message, "Fail to get product with pagination");
	}




}
