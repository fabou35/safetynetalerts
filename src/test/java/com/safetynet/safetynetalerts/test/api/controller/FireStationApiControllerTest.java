package com.safetynet.safetynetalerts.test.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.safetynetalerts.model.FireStation;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationApiControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	/*
	@Test
	public void testGetFireStations() throws Exception{
		mockMvc.perform(get("/firestation"))
			.andExpect(status().isOk());
	}
	*/
	
	@Test
	public void testSaveFireStation() throws Exception {
		// GIVEN
		FireStation fireStation = new FireStation();
		fireStation.addAddress("address");
		fireStation.setStationNumber("1");
		
		// WHEN
		JSONObject fireStationJsonObject = new JSONObject();
		fireStationJsonObject.put("address", fireStation.getAddresses());
		fireStationJsonObject.put("station", fireStation.getStationNumber());
				
		// THEN
		mockMvc.perform(post("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(fireStationJsonObject.toString()))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testUpdateFireStationNumber() throws Exception {
		// GIVEN
		FireStation newFireStation = new FireStation();
		newFireStation.setStationNumber("new station number");
		newFireStation.addAddress("address");
		
		// WHEN
		JSONObject newFireStationJsonObject = new JSONObject();
		newFireStationJsonObject.put("address", newFireStation.getAddresses());
		newFireStationJsonObject.put("station", newFireStation.getStationNumber());
		
		// THEN
		mockMvc.perform(put("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newFireStationJsonObject.toString()))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteFireStation() throws Exception{
		// GIVEN		
		FireStation fireStationToDelete = new FireStation();
		fireStationToDelete.setStationNumber("2");
		fireStationToDelete.addAddress("address2");
		
		// WHEN
		JSONObject fireStationJsonObject = new JSONObject();
		fireStationJsonObject.put("station", fireStationToDelete.getStationNumber());
		
		// THEN
		mockMvc.perform(delete("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(fireStationJsonObject.toString()))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void testDeleteAddress() throws Exception{
		// GIVEN	
		String addressToDelete = "address to delete";
		
		// WHEN
		JSONObject addressJsonObject = new JSONObject();
		addressJsonObject.put("address", addressToDelete);
		
		// THEN
		mockMvc.perform(delete("/firestation")
				.contentType(MediaType.APPLICATION_JSON)
				.content(addressJsonObject.toString()))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void testGetPhoneNumbers() throws Exception {
		// GIVEN
		String stationNumber = "";
		
		// WHEN
		stationNumber = "1";
		
		// THEN
		mockMvc.perform(get("/phoneAlert")
				.param("firestation", stationNumber))
		.andExpect(status().isOk());
	}
}
