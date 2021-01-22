package stepDefinitions;

import java.util.List;
import java.util.Map;
 
import org.junit.Assert;
 
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;



public class Steps {
	
	private static final String BASE_URL = "https://petstore.swagger.io";
	private static Response response;
	private static String jsonString;
	private static String petId;
	
	
	@Given("all available pets are displayed")
	public void all_available_pets_are_displayed() {
		RestAssured.baseURI = BASE_URL;
		 RequestSpecification request = RestAssured.given();
		 response = request.get("/v2/pet/findByStatus?status=available");
		 
		 jsonString = response.asString();
		 List<Map<String, String>> pets = JsonPath.from(jsonString).get("status");
		 Assert.assertTrue(pets.size() > 0);
		 System.out.println(response);
		 System.out.println("all available pets are displayed");
		 
	}
	
	@When("add a pet to the store")
	public void add_a_pet_to_the_store() {
		RestAssured.baseURI = BASE_URL;
		 RequestSpecification request = RestAssured.given();
		 request.header("Content-Type", "application/json");
		 
		 response = request.body( "{ "
					              + "\"name\": \""+ "DOGGIEQWERTY" +"\","
					              + "\"status\": \"available\"		}")                      
		             		 .post("/v2/pet");
		 
		 jsonString = response.asString();
		 String[] extract = jsonString.split(",");
		 for (int i=0; i<extract.length; i++) 
		 {if(extract[i].contains("id")){
			 String[] id = extract[i].split(":");
			 petId=id[1];}
		 }
		 
		 System.out.println(response);
		 System.out.println(petId);
		 System.out.println("add a pet to the store");
	}
	

@Then("the pet is added to the store")
public void the_pet_is_added_to_the_store() {
	Assert.assertEquals(200, response.getStatusCode());
	System.out.println("the pet is added to the store");
}

@When("update the newly added pet status to sold")
public void update_the_newly_added_pet_s_status_to_sold() {
	RestAssured.baseURI = BASE_URL;
	 RequestSpecification request = RestAssured.given();
	 request.header("Content-Type", "application/json");
	 response = request.body( "{ "
			 + "\"id\": \""+ petId +"\","
             + "\"status\": \"available\"		}")
			 .put("/v2/pet");
	  System.out.println("update the newly added pet status to sold");
}

@Then("the newly added pet status is updated to sold")
public void the_newly_added_pet_status_is_updated_to_sold() {
	Assert.assertEquals(200, response.getStatusCode());
	System.out.println("the newly added pet status is updated to sold");
}

@When("remove the newly added pet")
public void remove_the_newly_added_pet() {
	 RestAssured.baseURI = BASE_URL;
	 RequestSpecification request = RestAssured.given();
	 
	 request.header("Content-Type", "application/json");
	 
	 response = request.delete("/v2/pet/"+petId);
	 System.out.println("remove the newly added pet");
}

@Then("the newly added pet is removed")
public void the_newly_added_pet_is_removed() {
	Assert.assertEquals(200, response.getStatusCode());
}



}
