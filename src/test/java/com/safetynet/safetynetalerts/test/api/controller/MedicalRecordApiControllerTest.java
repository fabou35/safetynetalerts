package com.safetynet.safetynetalerts.test.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.safetynetalerts.model.MedicalRecord;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordApiControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testGetMedicalRecords() throws Exception {
		mockMvc.perform(get("/medicalRecord"))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void testSaveMedicalRecord() throws Exception{
		// GIVEN		
		MedicalRecord newMedicalRecord = new MedicalRecord();
		newMedicalRecord.setFirstName("first name");
		newMedicalRecord.setLastName("last name");
		newMedicalRecord.setBirthdate("01/02/2021");
		List<String> newMedications = new ArrayList<>();
		newMedications.add("medication1");
		newMedications.add("medication2");
		newMedicalRecord.setMedications(newMedications);
		List<String> newAllergies = new ArrayList<>();
		newAllergies.add("allergie");
		newMedicalRecord.setAllergies(newAllergies);
		
		// WHEN
		JSONObject medicalRecordJsonObject = new JSONObject();
		medicalRecordJsonObject.put("firstName", newMedicalRecord.getFirstName());
		medicalRecordJsonObject.put("lastName", newMedicalRecord.getLastName());
		medicalRecordJsonObject.put("birthdate", newMedicalRecord.getBirthdate());
		medicalRecordJsonObject.put("medications", newMedicalRecord.getMedications().get(0));
		medicalRecordJsonObject.accumulate("medications", newMedicalRecord.getMedications().get(1));
		medicalRecordJsonObject.accumulate("allergies", newMedicalRecord.getAllergies().get(0));
		medicalRecordJsonObject.accumulate("allergies", null);
		
		// THEN
		mockMvc.perform(post("/medicalRecord")
				.contentType(MediaType.APPLICATION_JSON)
				.content(medicalRecordJsonObject.toString()))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void testUpdateMedicalRecord() throws Exception{
		// GIVEN
		MedicalRecord medicalRecordToUpdate = new MedicalRecord();
		medicalRecordToUpdate.setFirstName("first name");
		medicalRecordToUpdate.setLastName("last name");
		medicalRecordToUpdate.setBirthdate("01/02/2021");
		List<String> medications = new ArrayList<>();
		medications.add("medication1");
		medications.add("medication2");
		medicalRecordToUpdate.setMedications(medications);
		List<String> allergies = new ArrayList<>();
		allergies.add("allergie1");
		allergies.add("allergie2");
		medicalRecordToUpdate.setAllergies(allergies);
		
		// WHEN
		JSONObject medicalRecordJsonObject = new JSONObject();
		medicalRecordJsonObject.put("firstName", medicalRecordToUpdate.getFirstName());
		medicalRecordJsonObject.put("lastName", medicalRecordToUpdate.getLastName());
		medicalRecordJsonObject.put("birthdate", medicalRecordToUpdate.getBirthdate());
		medicalRecordJsonObject.put("medications", medicalRecordToUpdate.getMedications().get(0));
		medicalRecordJsonObject.accumulate("medications", medicalRecordToUpdate.getMedications().get(1));
		medicalRecordJsonObject.put("allergies", medicalRecordToUpdate.getAllergies().get(0));
		medicalRecordJsonObject.accumulate("allergies", medicalRecordToUpdate.getAllergies().get(1));
		
		// THEN
		mockMvc.perform(put("/medicalRecord")
				.contentType(MediaType.APPLICATION_JSON)
				.content(medicalRecordJsonObject.toString()))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteMedicalRecord() throws Exception{
		// GIVEN
		MedicalRecord medicalRecordToDelete = new MedicalRecord();
		medicalRecordToDelete.setFirstName("first name");
		medicalRecordToDelete.setLastName("last name");
		medicalRecordToDelete.setBirthdate("01/02/2021");
		List<String> medications = new ArrayList<>();
		medications.add("medication1");
		medications.add("medication2");
		medicalRecordToDelete.setMedications(medications);
		List<String> allergies = new ArrayList<>();
		allergies.add("allergie");
		medicalRecordToDelete.setAllergies(allergies);		
		
		// WHEN
		JSONObject medicalRecordJsonObject = new JSONObject();
		medicalRecordJsonObject.put("firstName", medicalRecordToDelete.getFirstName());
		medicalRecordJsonObject.put("lastName", medicalRecordToDelete.getLastName());
		medicalRecordJsonObject.put("birthdate", medicalRecordToDelete.getBirthdate());
		medicalRecordJsonObject.put("medications", medicalRecordToDelete.getMedications().get(0));
		medicalRecordJsonObject.accumulate("medications", medicalRecordToDelete.getMedications().get(1));	
		medicalRecordJsonObject.put("allergies", medicalRecordToDelete.getAllergies().get(0));
		medicalRecordJsonObject.accumulate("allergies", null);

		// THEN
		mockMvc.perform(delete("/medicalRecord")
				.contentType(MediaType.APPLICATION_JSON)
				.content(medicalRecordJsonObject.toString()))
		.andExpect(status().isOk());
				
	}
}
