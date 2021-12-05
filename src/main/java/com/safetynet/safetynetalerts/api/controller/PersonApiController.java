package com.safetynet.safetynetalerts.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.api.service.PersonApiService;
import com.safetynet.safetynetalerts.model.Person;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PersonApiController {

	@Autowired
	private PersonApiService service;

	/**
	 * Get persons list
	 * 
	 * @return List of Person
	 */
	@GetMapping("/person")
	public List<Person> getPersons() {
		List<Person> personsList = service.getPersons();

		log.info("GET request send");
		log.info("Response for the GET request: " + personsList);
		return personsList;
	}

	/**
	 * Adds a person to a persons list
	 * 
	 * @param newPerson : Person to add to the persons list
	 * @return persons list with new person saved
	 */
	@PostMapping("/person")
	public List<Person> savePerson(@RequestBody Person newPerson) {
		List<Person> personsList = service.getPersons();
		personsList = service.savePerson(personsList, newPerson);
		log.info("POST request send with body: " + newPerson);
		log.info("Response for the POST request: " + personsList);
		return personsList;
	}

	/**
	 * Updates person info in persons list
	 * 
	 * @param personToUpdate : Person to update to in the persons list
	 * @return persons list with updated person
	 */
	@PutMapping("/person")
	public List<Person> updatePerson(@RequestBody Person personToUpdate) {
		List<Person> personsList = service.getPersons();
		personsList = service.updatePerson(personsList, personToUpdate);
		log.info("PUT request send with body: " + personToUpdate);
		log.info("Response for the PUT request:" + personsList);
		return personsList;
	}

	/**
	 * Deletes a person from persons list
	 * 
	 * @param personToDelete : Person to delete from the persons list
	 * @return persons list without the deleted person
	 */
	@DeleteMapping("/person")
	public List<Person> deletePerson(@RequestBody Person personToDelete) {
		List<Person> personsList = service.getPersons();
		personsList = service.deletePerson(personsList, personToDelete);
		log.info("DELETE request send with body: " + personToDelete);
		log.info("Response for the DELETE request:" + personsList);
		return personsList;
	}
	
	/**
	 * Gets a list of emails
	 * 
	 * @param city : city for which we want to have persons emails (String)
	 * @return a list of emails (List of String)
	 */
	@GetMapping("/communityEmail")
	public List<String> getCommunityEmail(@RequestParam ("city") String city) {
		List<String> emailsList = service.getEmails(city);
		log.info("Request send for communityEmail");
		log.info("Response for the communityEmail request:" + emailsList);
		return emailsList;
		
	}
	
	/**
	 * Gets data for a person identified with first and last names
	 * 
	 * @param firstName: person's first name (String)
	 * @param lastName  : person's last name (String)
	 * @return a Map of person's data
	 */
	@GetMapping("/personInfo")
	public Map<String, String> getPersonInfo(@RequestParam ("firstName") String firstName, 
			@RequestParam ("lastName") String lastName){
		Map<String, String> personInfo = new HashMap<>();
		personInfo = service.getPersonInfo(firstName, lastName);
		log.info("Request send for personInfo");
		log.info("Response for the personInfo request:" + personInfo);
		return personInfo;
		
	}
	
	/**
	 * Gets a list of children from an address if exists and their parents
	 * 
	 * @param address : address we want to retrieve the list
	 * @return a list of children if exist or null if don't
	 */
	@GetMapping("/childAlert")
	public Map<String, String> getChildrenForAnAddress(@RequestParam("address") String address){
		Map<String, String> personsForAnAddress = new HashMap<>();
		personsForAnAddress = service.getChildrenForAnAddress(address);
		return personsForAnAddress;
	}
}
