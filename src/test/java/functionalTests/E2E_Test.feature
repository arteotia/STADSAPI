
Feature: End to End API Test
         DEMO PET STORE: https://petstore.swagger.io/

  
Scenario: the end user can add update and remove a pet
					Given all available pets are displayed
					When add a pet to the store
					Then the pet is added to the store
					When update the newly added pet status to sold
					Then the newly added pet status is updated to sold
					When remove the newly added pet
					Then the newly added pet is removed

 
