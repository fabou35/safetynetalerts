package com.safetynet.safetynetalerts.api.service;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.api.repository.MedicalRecordApiRepository;
import com.safetynet.safetynetalerts.api.repository.PersonApiRepository;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class PersonApiService {

	@Autowired
	private PersonApiRepository repository;

	@Autowired
	private MedicalRecordApiRepository medicalRecordRepository;

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
	 * @param newPerson   : Person to add to the persons list
	 * @return persons list with new person saved
	 * @throws IOException
	 */
	public List<Person> savePerson(List<Person> personsList, Person newPerson) {
		personsList.add(newPerson);
		return personsList;

	}

	/**
	 * Updates person info in persons list
	 * 
	 * @param personsList    : List of Person
	 * @param personToUpdate : Person to update in the persons list
	 * @return persons list with updated person
	 * @throws IOException
	 */
	public List<Person> updatePerson(List<Person> personsList, Person personToUpdate) {
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
	 * Deletes a person in a persons list
	 * 
	 * @param personsList    : List of Person
	 * @param personToDelete : Person to delete from the persons list
	 * @return persons list without the deleted person
	 * @throws IOException
	 */
	public List<Person> deletePerson(List<Person> personsList, Person personToDelete) {
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

	/**
	 * Retrieves a list of emails for a city
	 * 
	 * @param city : city for which we want to have persons emails (String)
	 * @return a list of emails (List of String)
	 * @throws IOException
	 */
	public List<String> getEmails(String city) throws IOException {
		List<Person> personsList = getPersons();
		List<String> emailsList = new ArrayList<>();
		for (Person person : personsList) {
			if (person.getCity().equals(city)) {
				emailsList.add(person.getEmail());
			}
		}
		return emailsList;

	}

	/**
	 * Retrieves data for a person identified with first and last names
	 * 
	 * @param firstName : person's first name (String)
	 * @param lastName  : person's last name (String)
	 * @return a Map of person's data
	 * @throws IOException
	 */
	public Map<String, String> getPersonInfo(String firstName, String lastName) throws IOException {
		List<MedicalRecord> medicalRecordsList = new ArrayList<>();
		medicalRecordsList = medicalRecordRepository.getMedicalRecordsDatas();
		List<Person> personsList = getPersons();
		Map<String, String> personInfo = new HashMap<>();
		for (Person person : personsList) {
			if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
				personInfo.put("firstName", firstName);
				personInfo.put("lastName", lastName);
				long age = calculateAge(person);
				personInfo.put("age", Long.toString(age));
				personInfo.put("address", person.getAddress());
				personInfo.put("email", person.getEmail());
			}
		}
		for (MedicalRecord medicalRecord : medicalRecordsList) {
			if (medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName)) {
				personInfo.put("medications", medicalRecord.getMedications().toString());
				personInfo.put("allergies", medicalRecord.getAllergies().toString());
			}
		}
		return personInfo;

	}

	/**
	 * Retrieves a list of children from an address if exists and their parents
	 * 
	 * @param address : address we want to retrieve the list
	 * @return a list of children if exist or null if don't
	 * @throws IOException
	 */
	public Map<String, String> getChildrenForAnAddress(String address) throws IOException {

		List<Map<String, String>> childrenList = new ArrayList<>();
		List<Person> personsList = new ArrayList<>();
		personsList = getPersons();
		List<Map<String, String>> personsForAnAddress = new ArrayList<>();
		for (Person person : personsList) {
			if (person.getAddress().equals(address)) {
				if (calculateAge(person) <= 18) {
					Map<String, String> child = new HashMap<>();
					child.put("firstName", person.getFirstName());
					child.put("lastName", person.getLastName());
					child.put("age", Long.toString(calculateAge(person)));
					childrenList.add(child);
				} else {
					Map<String, String> parent = new HashMap<>();
					parent.put("firstName", person.getFirstName());
					parent.put("lastName", person.getLastName());
					personsForAnAddress.add(parent);
				}
			}
		}
		Map<String, String> homeHolder = new HashMap<>();
		if(!childrenList.isEmpty()) {
			homeHolder.put("parents", personsForAnAddress.toString());
			homeHolder.put("children", childrenList.toString());
		}
		return homeHolder;
	}

	/**
	 * Calculates and returns a person's age
	 * 
	 * @param person : the Person for who age is calculated
	 * @return the person's age (Long)
	 * @throws IOException
	 */
	public long calculateAge(Person person) throws IOException {
		List<MedicalRecord> medicalRecords = new ArrayList<>();
		medicalRecords = medicalRecordRepository.getMedicalRecordsDatas();
		long age = 0;
		for (MedicalRecord medicalRecord : medicalRecords) {
			if (medicalRecord.getFirstName().equals(person.getFirstName()) &&
					medicalRecord.getLastName().equals(person.getLastName())) {
				String birthdate = medicalRecord.getBirthdate();
				String[] birthdateSplit = birthdate.split("/");
				LocalDate date = LocalDate.of(Integer.parseInt(birthdateSplit[2]), Integer.parseInt(birthdateSplit[0]),
						Integer.parseInt(birthdateSplit[1]));
				LocalDate now = LocalDate.now();
				age = ChronoUnit.YEARS.between(date, now);
			}
		}
		return age;

	}

}