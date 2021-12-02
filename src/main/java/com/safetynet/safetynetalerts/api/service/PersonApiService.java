package com.safetynet.safetynetalerts.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.api.repository.PersonApiRepository;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class PersonApiService {

	@Autowired
	private PersonApiRepository repository;

	/**
	 * Retrieves persons data from PersonApiRepository
	 * 
	 * @return List of Person retrieved from data
	 * @throws IOException
	 */
	public List<Person> getPersons() throws IOException {
		return repository.getPersonsDatas();
	}

	/**
	 * Saves a new person in persons list
	 * 
	 * @param personsList : List of Person
	 * @param newPerson : Person to add to the persons list
	 * @return persons list with new person saved
	 * @throws IOException
	 */
	public List<Person> savePerson(List<Person> personsList, Person newPerson){
		personsList.add(newPerson);
		return personsList;

	}

	/**
	 * updates person info in persons list
	 * 
	 * @param personsList : List of Person
	 * @param personToUpdate : Person to update in the persons list  
	 * @return persons list with updated person
	 * @throws IOException
	 */
	public List<Person> updatePerson(List<Person> personsList, Person personToUpdate){
		List<Person> updatedPerson = new ArrayList<>();
		for (Person person : personsList) {
			if (person.getLastName().equals(personToUpdate.getLastName())
					&& person.getFirstName().equals(personToUpdate.getFirstName())) {

				updatedPerson.add(person);
			}
		}
		personsList.add(personToUpdate);
		personsList.removeAll(updatedPerson);
		return personsList;
	}

	/**
	 * deletes a person in a persons list
	 * 
	 * @param personsList : List of Person
	 * @param personToDelete : Person to delete from the persons list
	 * @return persons list without the deleted person
	 * @throws IOException
	 */
	public List<Person> deletePerson(List<Person> personsList, Person personToDelete){
		List<Person> toRemove = new ArrayList<>();
		for (Person person : personsList) {
			if (person.getFirstName().equals(personToDelete.getFirstName()) &&
					person.getLastName().equals(personToDelete.getLastName())) {
				toRemove.add(person);
			}
		}
		personsList.removeAll(toRemove);
		return personsList;
	}
}