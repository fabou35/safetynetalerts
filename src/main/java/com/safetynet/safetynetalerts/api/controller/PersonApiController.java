package com.safetynet.safetynetalerts.api.controller;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	 * @throws IOException
	 */
	@GetMapping("/person")
	public List<Person> getPersons() throws IOException {
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
	 * @throws IOException
	 */
	@PostMapping("/person")
	public List<Person> savePerson(@RequestBody Person newPerson) throws IOException {
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
	 * @throws IOException
	 */
	@PutMapping("/person")
	public List<Person> updatePerson(@RequestBody Person personToUpdate) throws IOException {
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
	 * @throws IOException
	 */
	@DeleteMapping("/person")
	public List<Person> deletePerson(@RequestBody Person personToDelete) throws IOException {
		List<Person> personsList = service.getPersons();
		personsList = service.deletePerson(personsList, personToDelete);
		log.info("DELETE request send with body: " + personToDelete);
		log.info("Response for the DELETE request:" + personsList);
		return personsList;
	}
}
