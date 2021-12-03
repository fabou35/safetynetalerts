package com.safetynet.safetynetalerts.test.api.service;

import static org.assertj.core.api.Assertions.assertThat;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.safetynetalerts.api.service.FireStationApiService;
import com.safetynet.safetynetalerts.model.FireStation;

@SpringBootTest
public class FireStationApiServiceTest {

	@Autowired
	private FireStationApiService service;
	
	@Test
	public void getFireStationsReturnsNotEmptyList() throws IOException{
		// GIVEN
		List<FireStation> fireStationsList = new ArrayList<>();
		
		// WHEN
		fireStationsList = service.getFireStations();
		
		// THEN
		assertThat(fireStationsList).isNotNull();
		
	}
	
	@Test
	public void fireStationAddressesAreInSet() {
		// GIVEN
		List<FireStation> fireStationsList = new ArrayList<>();
		List<FireStation> newFireStationsList = new ArrayList<>();
		FireStation fireStation1 = new FireStation();
		FireStation fireStation2 = new FireStation();
		FireStation fireStation3 = new FireStation();
		FireStation fireStation4 = new FireStation();
		fireStation1.setStationNumber("1");
		fireStation1.addAddress("address1");
		
		fireStation2.setStationNumber("2");
		fireStation2.addAddress("address2");
		
		fireStation3.setStationNumber("3");
		fireStation3.addAddress("address3");
		
		fireStation4.setStationNumber("1");
		fireStation4.addAddress("address4");
		
		fireStationsList.add(fireStation1);
		fireStationsList.add(fireStation2);
		fireStationsList.add(fireStation3);
		fireStationsList.add(fireStation4);
		
		// WHEN
		newFireStationsList = service.getFireStationsAddressesInSet(fireStationsList);
		FireStation fireStationResult = new FireStation();
		Set<String> resultSet = new HashSet<>();
		fireStationResult.setStationNumber("1");
		resultSet.add("address1");
		resultSet.add("address4");
		fireStationResult.addAddress(resultSet);
		
		// THEN
		assertThat(newFireStationsList).contains(fireStationResult);
		
	}
	
	@Test
	public void fireStationsListIsOrdonned() throws IOException {
		// GIVEN
		List<FireStation> fireStationsList = new ArrayList<>();
		List<FireStation> newFireStationsList = new ArrayList<>();
		FireStation fireStation1 = new FireStation();
		FireStation fireStation2 = new FireStation();
		fireStation1.setStationNumber("1");
		fireStation1.addAddress("address1");
		fireStation2.setStationNumber("2");
		fireStation2.addAddress("address2");
		fireStationsList.add(fireStation2);
		fireStationsList.add(fireStation1);
		
		// WHEN
		newFireStationsList = service.getOrdonnedFireStationsList(fireStationsList);
		
		// THEN
		assertThat(newFireStationsList.get(0).getStationNumber()).isEqualTo(fireStation1.getStationNumber());
	}
	
	@Test
	public void savesANewAddressForAnExistingFireStationInFireStationsList() {
		// GIVEN
		List<FireStation> fireStationsList = new ArrayList<>();
		
		FireStation fireStation = new FireStation();
		fireStation.setStationNumber("1");
		fireStation.addAddress("address");
		fireStationsList.add(fireStation);
		
		FireStation fireStationTosave = new FireStation();
		fireStationTosave.setStationNumber("1");
		fireStationTosave.addAddress("new address");
		
		// WHEN
		fireStationsList = service.saveFireStation(fireStationsList, fireStationTosave);
		
		// THEN
		assertThat(fireStationsList).contains(fireStationTosave);
		
	}
	
	@Test
	public void updateAFireStationNumberForAnAddressFireStationsList() {
		// GIVEN
		List<FireStation> fireStationList = new ArrayList<>();
		List<FireStation> newFireStationList = new ArrayList<>();
		FireStation fireStation = new FireStation();
		String stationNumber = "1";
		String address = "address1";
		fireStation.setStationNumber(stationNumber);
		fireStation.addAddress(address);
		fireStationList.add(fireStation);
		FireStation newFireStation = new FireStation();
		String newStationNumber = "2";
		newFireStation.setStationNumber(newStationNumber);
		newFireStation.addAddress(address);
		
		// WHEN
		newFireStationList = service.updateFireStationNumber(fireStationList, newFireStation);
		
		// THEN
		assertThat(newFireStationList).contains(newFireStation);
		
	}
	
	@Test
	public void deleteAFireStationFromTheFireStationsList() {
		// GIVEN
		List<FireStation> fireStationsList = new ArrayList<>();
		List<FireStation> newFireStationsList = new ArrayList<>();
		FireStation fireStation1 = new FireStation();
		FireStation fireStation2 = new FireStation();
		fireStation1.setStationNumber("1");
		fireStation1.addAddress("address1");
		fireStation2.setStationNumber("2");
		fireStation2.addAddress("address2");
		fireStationsList.add(fireStation1);
		fireStationsList.add(fireStation2);
		
		// WHEN
		newFireStationsList = service.deleteFireStation(fireStationsList, fireStation1);
		
		// THEN
		assertThat(newFireStationsList).doesNotContain(fireStation1);
	}
	
	@Test
	public void getPhoneNumbersReturnNoEmptyListForAStationNumberFromData() throws IOException {
		// GIVEN
		List<FireStation> fireStationsList = new ArrayList<>();
		fireStationsList = service.getFireStations();
		List<String> phoneNumbersList = new ArrayList<>();
		String stationNumber = fireStationsList.get(0).getStationNumber();
		
		// WHEN
		phoneNumbersList = service.getPhoneNumbers(stationNumber);
		
		// THEN
		assertThat(phoneNumbersList).isNotEmpty();
	}
}
