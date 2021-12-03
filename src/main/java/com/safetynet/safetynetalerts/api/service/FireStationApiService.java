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
import com.safetynet.safetynetalerts.api.repository.PersonApiRepository;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.Person;

@Service
public class FireStationApiService {

	@Autowired
	private FireStationApiRepository repository;

	@Autowired
	private PersonApiService personService;

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
}