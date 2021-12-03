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
	 * @throws IOException
	 */
	@GetMapping("/firestation")
	public List<FireStation> getFireStations() throws IOException {
		List<FireStation> fireStationsList = new ArrayList<>();
		fireStationsList = service.getFireStations();
		fireStationsList = service.getFireStationsAddressesInSet(fireStationsList);
		fireStationsList = service.getOrdonnedFireStationsList(fireStationsList);
		log.info("GET request for firestation send");
		log.info("Response for the GET request for firestation: " + fireStationsList);
		return fireStationsList;
	}

	/**
	 * Adds a fire station to the fire stations list
	 * 
	 * @param newFireStation : FireStation to add to the fire stations list
	 * @return fire stations list with new fire station
	 * @throws IOException
	 */
	@PostMapping("/firestation")
	public List<FireStation> saveFireStation(@RequestBody FireStation newFireStation) throws IOException {
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
	 * @throws IOException
	 */
	@PutMapping("/firestation")
	public List<FireStation> updateFireStationNumber(@RequestBody FireStation fireStationToUpdate) throws IOException {
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
	 * @throws IOException
	 */
	@DeleteMapping("/firestation")
	public List<FireStation> deleteFireStation(@RequestBody FireStation fireStationToDelete) throws IOException {
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
	 * @throws IOException
	 */
	@GetMapping("/phoneAlert")
	public List<String> getPhoneNumbers(@RequestParam ("firestation") String stationNumber) throws IOException{
		List<String> phoneNumbersList = new ArrayList<>();
		phoneNumbersList = service.getPhoneNumbers(stationNumber);
		return phoneNumbersList;
	}
}
