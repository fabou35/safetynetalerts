package com.safetynet.safetynetalerts.test.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.safetynetalerts.api.service.PersonApiService;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
public class PersonApiServiceTest {

	@Autowired
	private PersonApiService service;

	@Test
	public void getPersonsReturnsNotEmptyList() throws IOException{
		// GIVEN
		List<Person> personsList = new ArrayList<>();
		
		// WHEN
		personsList = service.getPersons();
		
		// THEN
		assertThat(personsList).isNotNull();
		
	}
	
	@Test
	public void savesANewPersonInThePersonsList() {
		// GIVEN
		List<Person> personsList = new ArrayList<>();
		Person personToSave = new Person();
		personToSave.setFirstName("new first name");
		personToSave.setLastName("new last name");
		personToSave.setAddress("new address");
		personToSave.setCity("new city");
		personToSave.setZip("new zip");
		personToSave.setPhone("new phone");
		personToSave.setEmail("new email");

		// WHEN
		personsList = service.savePerson(personsList, personToSave);

		// THEN
		assertThat(personsList).contains(personToSave);
	}

	@Test
	public void updatesAPersonFromThePersonsList() {
		// GIVEN
		List<Person> personsList = new ArrayList<>();
		Person person = new Person();
		person.setFirstName("first name");
		person.setLastName("last name");
		person.setAddress("address");
		person.setCity("city");
		person.setZip("zip");
		person.setPhone("phone");
		person.setEmail("email");
		Person personToUpdate = new Person();
		personToUpdate.setFirstName("first name");
		personToUpdate.setLastName("last name");
		personToUpdate.setAddress("new address");
		personToUpdate.setCity("city");
		personToUpdate.setZip("zip");
		personToUpdate.setPhone("phone");
		personToUpdate.setEmail("email");

		// WHEN
		personsList = service.updatePerson(personsList, personToUpdate);

		// THEN
		assertThat(personsList).contains(personToUpdate).doesNotContain(person);
	}

	@Test
	public void deletesAPersonFromThePersonsList() {
		// GIVEN
		List<Person> personsList = new ArrayList<>();
		Person person = new Person();
		person.setFirstName("first name");
		person.setLastName("last name");
		person.setAddress("address");
		person.setCity("city");
		person.setZip("zip");
		person.setPhone("phone");
		person.setEmail("email");
		Person personToDelete = new Person();
		personToDelete.setFirstName("first name to delete");
		personToDelete.setLastName("last name to delete");
		personToDelete.setAddress("address to delete");
		personToDelete.setCity("city to delete");
		personToDelete.setZip("zip to delete");
		personToDelete.setPhone("phone to delete");
		personToDelete.setEmail("email to delete");

		// WHEN
		personsList = service.deletePerson(personsList, personToDelete);

		// THEN
		assertThat(personsList).doesNotContain(personToDelete);

	}
}