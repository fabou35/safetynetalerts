package com.safetynet.safetynetalerts.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.api.repository.FireStationApiRepository;
import com.safetynet.safetynetalerts.api.repository.MedicalRecordApiRepository;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class FireStationApiService {

	@Autowired
	private FireStationApiRepository repository;

	@Autowired
	private PersonApiService personService;

	@Autowired
	private MedicalRecordApiRepository medicalRecordRepository;

	/**
	 * Retrieves fire stations data from FireStationApiRepository
	 * 
	 * @return List of FireStation retrieved from data
	 * @throws IOException
	 */
	public List<FireStation> getFireStations() throws IOException {
		return repository.getFireStationsDatas();
	}

	/**
	 * Groups together the addresses for each fire station
	 * 
	 * @param fireStationsList : List of FireStation
	 * @return a fire station list with addresses grouped in set for each station
	 *         number
	 */
	public List<FireStation> getFireStationsAddressesInSet(List<FireStation> fireStationsList) {
		List<FireStation> newFireStationsList = new ArrayList<>();
		List<FireStation> toRemove = new ArrayList<>();
		for (FireStation station : fireStationsList) {
			FireStation newStation = new FireStation();
			if (newFireStationsList.isEmpty()) {
				newStation.setStationNumber(station.getStationNumber());
				newStation.addAddress(station.getAddresses());

			} else {
				for (FireStation fireStation : newFireStationsList) {
					if (fireStation.getStationNumber().equals(station.getStationNumber())) {
						toRemove.add(fireStation);
						station.addAddress(fireStation.getAddresses());
					} else {
						newStation.setStationNumber(station.getStationNumber());
						newStation.addAddress(station.getAddresses());
					}
				}
			}
			newFireStationsList.add(newStation);
		}
		newFireStationsList.removeAll(toRemove);
		return newFireStationsList;
	}

	/**
	 * Orders fire stations list according to ascending station number
	 * 
	 * @param fireStationsList : List of FireStation
	 * @return an ordered fire station list according to ascending station number
	 */
	public List<FireStation> getOrdonnedFireStationsList(List<FireStation> fireStationsList) {

		Collections.sort(fireStationsList);
		return fireStationsList;
	}

	/**
	 * Adds a fire station to the fire stations list
	 * 
	 * @param fireStationsList : List of FireStation
	 * @param fireStation      : FireStation to save in the list
	 * @return fire stations list with new fire station added
	 */
	public List<FireStation> saveFireStation(List<FireStation> fireStationsList, FireStation fireStation) {
		fireStationsList.add(fireStation);
		return fireStationsList;

	}

	/**
	 * Updates fire station number for an address
	 * 
	 * @param fireStationsList : List of FireStation
	 * @param fireStation      : FireStation to update in the list
	 * @return fire stations list with updated fire station
	 */
	public List<FireStation> updateFireStationNumber(List<FireStation> fireStationList, FireStation fireStation) {
		List<FireStation> newFireStationsList = new ArrayList<>();
		for (FireStation station : fireStationList) {
			if (station.getAddresses().equals(fireStation.getAddresses())) {

				newFireStationsList.add(fireStation);
			} else {
				newFireStationsList.add(station);
			}
		}
		return newFireStationsList;
	}

	/**
	 * Deletes station / address mapping
	 * 
	 * @param fireStationsList    : List of FireStation
	 * @param fireStationToDelete : FireStation to delete from the fire stations
	 *                            list
	 * @return fire stations list without the deleted fire station
	 */
	public List<FireStation> deleteFireStation(List<FireStation> fireStationsList, FireStation fireStationToDelete) {
		List<FireStation> newFireStationsList = new ArrayList<>();
		for (FireStation station : fireStationsList) {
			if (!(station.getStationNumber().equals(fireStationToDelete.getStationNumber()) &&
					station.getAddresses().equals(fireStationToDelete.getAddresses()))) {
				newFireStationsList.add(station);
			}
		}
		return newFireStationsList;
	}

	/**
	 * Retrieves phone numbers of persons served by a fire station
	 * 
	 * @param stationNumber : the number of the fire station (String)
	 * @return a phone numbers list
	 * @throws IOException
	 */
	public List<String> getPhoneNumbers(String stationNumber) throws IOException {
		List<FireStation> fireStationsList = new ArrayList<>();
		fireStationsList = getFireStations();
		List<Person> personsList = new ArrayList<>();
		personsList = personService.getPersons();
		List<String> phoneNumbersList = new ArrayList<>();
		for (FireStation fireStation : fireStationsList) {
			if (fireStation.getStationNumber().equals(stationNumber)) {
				for (Person person : personsList) {
					if (fireStation.getAddresses().contains(person.getAddress())) {
						phoneNumbersList.add(person.getPhone());
					}
				}
			}
		}
		return phoneNumbersList;
	}

	/**
	 * Retrieves a list of persons served by a fire station
	 * 
	 * @param stationNumber : number of the fire station
	 * @return a Map of persons with first and last name, address, phone and the
	 *         count of children and adults
	 * @throws IOException
	 */
	public List<Map<String, String>> getPersonsForFireStation(String stationNumber) throws IOException {
		List<Map<String, String>> persons = new ArrayList<>();
		List<FireStation> fireStationsList = new ArrayList<>();
		fireStationsList = getFireStations();
		List<Person> personsList = new ArrayList<>();
		personsList = personService.getPersons();
		int adultsNumber = 0;
		int childrenNumber = 0;
		for (FireStation fireStation : fireStationsList) {
			if (fireStation.getStationNumber().equals(stationNumber)) {
				for (Person person : personsList) {
					Map<String, String> personMap = new HashMap<>();
					if (fireStation.getAddresses().contains(person.getAddress())) {
						personMap.put("firstName", person.getFirstName());
						personMap.put("lastName", person.getLastName());
						personMap.put("address", person.getAddress());
						personMap.put("phone", person.getPhone());
						long age = personService.calculateAge(person);
						if (age <= 18) {
							childrenNumber++;
						} else {
							adultsNumber++;
						}
						persons.add(personMap);
					}

				}
			}
		}
		Map<String, String> countMap = new HashMap<>();
		countMap.put("children", Long.toString(childrenNumber));
		countMap.put("adults", Long.toString(adultsNumber));
		persons.add(countMap);
		return persons;

	}

	/**
	 * Retrieves a list of persons for an address with the station number
	 * 
	 * @param address : address for the list of persons (String)
	 * @return a list of persons with first and last names, phone, age, medications,
	 *         allergies and fire station number
	 * @throws IOException
	 */
	public List<Map<String, String>> getPersonsDataForAnaddressWithStationNumber(String address) throws IOException {
		List<Map<String, String>> persons = new ArrayList<>();
		List<FireStation> fireStationsList = new ArrayList<>();
		fireStationsList = getFireStations();
		String stationNumber;

		for (FireStation fireStation : fireStationsList) {
			if (fireStation.getAddresses().contains(address)) {
				stationNumber = fireStation.getStationNumber();
				Map<String, String> stationMap = new HashMap<>();
				stationMap.put("station", stationNumber);
				persons.add(stationMap);
			}
		}
		
		persons.addAll(getPersonsDataForAnaddress(address));
		return persons;

	}
	
	/**
	 * Retrieves a list of data's persons served by a list of fire stations
	 * 
	 * @param stationsList : list fire stations for the list of persons
	 * @return a list of person with first and last names, phone, age, medications,
	 *         allergies
	 * @throws IOException 
	 */
	public List<Map<String, String>> getPersonsDataForFireStationList(List<String> stationsList) throws IOException{
		List<Map<String, String>> persons = new ArrayList<>();
		List<FireStation> fireStationsList = repository.getFireStationsDatas();
		List<String> addresses = new ArrayList<>();
		for(FireStation fireStation : fireStationsList) {
			if(stationsList.contains(fireStation.getStationNumber())) {
				for(String address : fireStation.getAddresses()) {
					if(!addresses.contains(address)) {
						addresses.add(address);
					}
				}
			}
		}
		for(String address : addresses) {
			List<Map<String, String>> personsMaps = new ArrayList<>();
			personsMaps = getPersonsDataForAnaddress(address);
			persons.addAll(personsMaps);
		}
		return persons;
		
	}
	
	/**
	 * Retrieves a list of persons for an address
	 * 
	 * @param address : address for the list of persons (String)
	 * @return a list of persons with first and last names, phone, age, medications,
	 *         allergies
	 * @throws IOException
	 */
	public List<Map<String, String>> getPersonsDataForAnaddress(String address) throws IOException {
		List<Map<String, String>> persons = new ArrayList<>();
		List<MedicalRecord> medicalRecordsList = new ArrayList<>();
		medicalRecordsList = medicalRecordRepository.getMedicalRecordsDatas();

		List<Person> personsList = personService.getPersons();
		for (Person person : personsList) {
			Map<String, String> personMap = new HashMap<>();
			if (person.getAddress().equals(address)) {
				personMap.put("firstName", person.getFirstName());
				personMap.put("lastName", person.getLastName());
				personMap.put("phone", person.getPhone());
				long age = personService.calculateAge(person);
				personMap.put("age", Long.toString(age));
				for (MedicalRecord medicalRecord : medicalRecordsList) {

					if (medicalRecord.getFirstName().equals(person.getFirstName()) &&
							medicalRecord.getLastName().equals(person.getLastName())) {
						if (medicalRecord.getMedications() != null) {
							personMap.put("medications", medicalRecord.getMedications().toString());
						}
						if (medicalRecord.getAllergies() != null) {
							personMap.put("allergies", medicalRecord.getAllergies().toString());
						}
					}
				}
				persons.add(personMap);
			}
		}
		return persons;

	}
}