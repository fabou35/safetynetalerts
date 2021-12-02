package com.safetynet.safetynetalerts.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.api.repository.FireStationApiRepository;
import com.safetynet.safetynetalerts.model.FireStation;

@Service
public class FireStationApiService {

	@Autowired
	private FireStationApiRepository repository;

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
	 * Deletes station mapping for a station number
	 * 
	 * @param fireStationsList : List of FireStation
	 * @param stationToDelete  : FireStation to delete from the fire stations list
	 * @return fire stations list without the deleted fire station
	 */
	public List<FireStation> deleteFireStation(List<FireStation> fireStationsList, String stationToDelete) {
		List<FireStation> newFireStationsList = new ArrayList<>();
		for (FireStation station : fireStationsList) {
			if (!station.getStationNumber().equals(stationToDelete)) {
				newFireStationsList.add(station);
			}
		}
		return newFireStationsList;
	}

	/**
	 * Deletes an address for a station number
	 * 
	 * @param fireStationsList : List of FireStation
	 * @param addressToDelete  : the address (String) to delete
	 * @return fire stations list after an address is deleted for a fire station
	 */
	public List<FireStation> deleteAddress(List<FireStation> fireStationsList, String addressToDelete) {
		List<FireStation> newFireStationsList = new ArrayList<>();
		for (FireStation station : fireStationsList) {
			if (!station.getAddresses().contains(addressToDelete)) {
				newFireStationsList.add(station);
			}
		}
		return newFireStationsList;
	}
}
