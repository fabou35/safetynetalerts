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
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.safetynetalerts.api.service.MedicalRecordApiService;
import com.safetynet.safetynetalerts.model.MedicalRecord;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MedicalRecordApiController {

	@Autowired
	private MedicalRecordApiService service;
	
	/**
	 * gets the medical records list
	 * 
	 * @return List<MedicalRecord> the medical records list 
	 * @throws IOException
	 */
	@GetMapping("/medicalRecord")
	public List<MedicalRecord> getMedicalRecords() throws IOException{
		List<MedicalRecord> medicalRecordsList = service.getMedicalRecords();
		log.info("GET request for medicalRecord send");
		log.info("Response for the GET request for medicalRecord: " + medicalRecordsList);
		return medicalRecordsList;
	}
	
	/**
	 * posts a new medical record
	 * 
	 * @param medicalRecord medical record to add to the list
	 * @return List<MedicalRecord> the final medical records list with the medical record added
	 * @throws IOException
	 */
	@PostMapping("/medicalRecord")
	public List<MedicalRecord> saveMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws IOException{
		List<MedicalRecord> medicalRecordsList = new ArrayList<>();
		medicalRecordsList = service.getMedicalRecords();
		medicalRecordsList = service.saveMedicalRecord(medicalRecordsList, medicalRecord);
		log.info("POST request for medicalRecord send");
		log.info("Response for the POST request for medicalRecord: " + medicalRecordsList);
		return medicalRecordsList;
	}
	
	/**
	 * updates data for a medical record
	 * 
	 * @param medicalRecord medical record with new data
	 * @return List<MedicalRecord> the medical records list with the medical record updated
	 * @throws IOException
	 */
	@PutMapping("/medicalRecord")
	public List<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws IOException{
		List<MedicalRecord> medicalRecordsList = new ArrayList<>();
		medicalRecordsList = service.getMedicalRecords();
		medicalRecordsList = service.updateMedicalRecord(medicalRecordsList, medicalRecord);
		log.info("PUT request for medicalRecord send");
		log.info("Response for the PUT request for medicalRecord: " + medicalRecordsList);
		return medicalRecordsList;
	}
	
	/**
	 * deletes a medical record
	 * 
	 * @param medicalRecord medical record to delete
	 * @return List<MedicalRecord> the final list without the medical record deleted
	 * @throws IOException
	 */
	@DeleteMapping("/medicalRecord")
	public List<MedicalRecord> deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws IOException{
		List<MedicalRecord> medicalRecordsList = new ArrayList<>();
		medicalRecordsList = service.getMedicalRecords();
		medicalRecordsList = service.deleteMedicalRecord(medicalRecordsList, medicalRecord);
		log.info("DELETE request for medicalRecord send");
		log.info("Response for the DELETE request for medicalRecord: " + medicalRecordsList);
		return medicalRecordsList;
	}

	
}
