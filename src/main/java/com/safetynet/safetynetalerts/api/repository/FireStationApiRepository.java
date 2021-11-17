package com.safetynet.safetynetalerts.api.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsoniter.any.Any;
import com.safetynet.safetynetalerts.model.FireStation;
import com.safetynet.safetynetalerts.model.Person;


@Repository
public class FireStationApiRepository {

	@Autowired
	private DataFileReader reader;

	/**
	 * Retrieves fire stations datas from json file
	 * @return
	 * @throws IOException
	 */
	public List<FireStation> getFireStationsDatas() throws IOException {
		Any any = reader.readDatas();
		Any fireStationAny = any.get("firestations");
		List<FireStation> fireStationsList = new ArrayList<>();
		for (Any f : fireStationAny) {
			FireStation fireStation = new FireStation();
			fireStation.setStationNumber(f.get("station").toString());
			fireStation.addAddress(f.get("address").toString());
			fireStationsList.add(fireStation);
		}
		return fireStationsList;
	}

}
