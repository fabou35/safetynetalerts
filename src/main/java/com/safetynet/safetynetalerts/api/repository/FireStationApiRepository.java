package com.safetynet.safetynetalerts.api.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsoniter.any.Any;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.Person;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class FireStationApiRepository {

	@Autowired
	private DataFileReader reader;

	/**
	 * Retrieves fire stations data from json file
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<FireStation> getFireStationsDatas() {
		List<FireStation> fireStationsList = new ArrayList<>();
		Any any = reader.readDatas();
		try {
			Any fireStationAny = any.get("firestations");

			for (Any f : fireStationAny) {
				FireStation fireStation = new FireStation();
				fireStation.setStationNumber(f.get("station").toString());
				fireStation.addAddress(f.get("address").toString());
				fireStationsList.add(fireStation);
			}
		} catch (Exception e) {
			log.error("Impossible to retrieve fire stations list from data: " + e.toString());
		}
		return fireStationsList;
	}

}
