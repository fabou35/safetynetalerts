package com.safetynet.safetynetalerts.api.controller;

import java.util.ArrayList;
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

import com.safetynet.safetynetalerts.api.service.FireStationApiService;
import com.safetynet.safetynetalerts.model.FireStation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FireStationApiController {

	@Autowired
	private FireStationApiService service;

	/**
	 * Gets the ordered fire stations list
	 * 
	 * @return List of ordered FireStation
	 */
	/*@GetMapping("/firestation")
	public List<FireStation> getFireStations(@RequestParam ("stationNumber") String stationNumber) {
		List<FireStation> fireStationsList = new ArrayList<>();
		fireStationsList = service.getFireStations();
		fireStationsList = service.getFireStationsAddressesInSet(fireStationsList);
		fireStationsList = service.getOrdonnedFireStationsList(fireStationsList);
		log.info("GET request for firestation send");
		log.info("Response for the GET request for firestation: " + fireStationsList);
		return fireStationsList;
	}*/

	/**
	 * Adds a fire station to the fire stations list
	 * 
	 * @param newFireStation : FireStation to add to the fire stations list
	 * @return fire stations list with new fire station
	 */
	@PostMapping("/firestation")
	public List<FireStation> saveFireStation(@RequestBody FireStation newFireStation) {
		List<FireStation> fireStationsList = new ArrayList<>();
		fireStationsList = service.getFireStations();
		fireStationsList = service.saveFireStation(fireStationsList, newFireStation);
		log.info("POST request for firestation send with body: " + newFireStation);
		log.info("Response for the POST request for firestation: " + fireStationsList);
		return fireStationsList;

	}

	/**
	 * Updates data for a fire station
	 * 
	 * @param fireStationToUpdate FireStation to update in the fire stations list
	 * @return List<FireStation> fire stations list with a fire station updated
	 */
	@PutMapping("/firestation")
	public List<FireStation> updateFireStationNumber(@RequestBody FireStation fireStationToUpdate) {
		List<FireStation> fireStationsList = new ArrayList<>();
		fireStationsList = service.getFireStations();
		fireStationsList = service.updateFireStationNumber(fireStationsList, fireStationToUpdate);
		fireStationsList = service.getOrdonnedFireStationsList(fireStationsList);
		log.info("PUT request for firestation send with body: " + fireStationToUpdate);
		log.info("Response for the PUT request for firestation: " + fireStationsList);
		return fireStationsList;
	}

	/**
	 * Deletes a fire station or address mapping from fire stations list
	 * 
	 * @param mappingToDelete : stationNumber or address to delete (String)
	 * @return fire stations list without the deleted mapping
	 */
	@DeleteMapping("/firestation")
	public List<FireStation> deleteFireStation(@RequestBody FireStation fireStationToDelete) {
		List<FireStation> fireStationsList = new ArrayList<>();
		fireStationsList = service.getFireStations();
		fireStationsList = service.deleteFireStation(fireStationsList, fireStationToDelete);
		fireStationsList = service.getFireStationsAddressesInSet(fireStationsList);
		fireStationsList = service.getOrdonnedFireStationsList(fireStationsList);
		log.info("DELETE request for firestation send with body: " + fireStationToDelete);
		log.info("Response for the DELETE request for firestation: " + fireStationsList);
		return fireStationsList;

	}

	/**
	 * Get phone numbers of persons served by a fire station
	 * 
	 * @param stationNumber : the number of the fire station (String)
	 * @return a phone numbers list
	 */
	@GetMapping("/phoneAlert")
	public List<String> getPhoneNumbers(@RequestParam ("firestation") String stationNumber) {
		List<String> phoneNumbersList = new ArrayList<>();
		phoneNumbersList = service.getPhoneNumbers(stationNumber);
		return phoneNumbersList;
	}
	
	/**
	 * Gets a list of persons served by a fire station
	 * 
	 * @param stationNumber : number of the fire station
	 * @return a Map of persons with first and last name, address, phone and the
	 *         count of children and adults
	 */
	@GetMapping("/firestation")
	public List<Map<String, String>> getPersonsForFireStation(@RequestParam("stationNumber") String stationNumber) {
		List<Map<String, String>> personsList = new ArrayList<>();
		personsList = service.getPersonsForFireStation(stationNumber);
		return personsList;
		
	}
	
	/**
	 * Gets a list of persons for an address
	 * 
	 * @param address : address for the list of persons (String)
	 * @return a list of persons with first and last names, phone, age, medications,
	 *         allergies and fire station number
	 */
	@GetMapping("/fire")
	public List<Map<String, String>> getPersonsDataForAnaddress(@RequestParam("address") String address) {
		List<Map<String, String>> personsList = new ArrayList<>();
		personsList = service.getPersonsDataForAnaddressWithStationNumber(address);
		return personsList;
		
	}
	
	/**
	 * Gets a list of data's persons served by a list of fire stations
	 * 
	  * @param stationsList : list fire stations for the list of persons
	 * @return a list of person with first and last names, phone, age, medications,
	 *         allergies
	 */
	@GetMapping("/flood/stations")
	public List<Map<String, String>> getPersonsDataForFireStationList(@RequestParam("stations") List<String> stationsList) {
		List<Map<String, String>> personsList = new ArrayList<>();
		personsList = service.getPersonsDataForFireStationList(stationsList);
		return personsList;
	}
}
