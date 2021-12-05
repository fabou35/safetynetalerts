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
	 * Gets the medical records list
	 * 
	 * @return the medical records list
	 */
	@GetMapping("/medicalRecord")
	public List<MedicalRecord> getMedicalRecords() {
		List<MedicalRecord> medicalRecordsList = service.getMedicalRecords();
		log.info("GET request for medicalRecord send");
		log.info("Response for the GET request for medicalRecord: " + medicalRecordsList);
		return medicalRecordsList;
	}

	/**
	 * Posts a new medical record
	 * 
	 * @param medicalRecord : MedicalRecord to add to the medical records list
	 * @return medical records list with the new medical record saved
	 */
	@PostMapping("/medicalRecord")
	public List<MedicalRecord> saveMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		List<MedicalRecord> medicalRecordsList = new ArrayList<>();
		medicalRecordsList = service.getMedicalRecords();
		medicalRecordsList = service.saveMedicalRecord(medicalRecordsList, medicalRecord);
		log.info("POST request for medicalRecord send");
		log.info("Response for the POST request for medicalRecord: " + medicalRecordsList);
		return medicalRecordsList;
	}

	/**
	 * Updates data for a medical record
	 * 
	 * @param medicalRecord : MedicalRecord to update in the medical records
	 * @return medical records list with updated medical record
	 */
	@PutMapping("/medicalRecord")
	public List<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		List<MedicalRecord> medicalRecordsList = new ArrayList<>();
		medicalRecordsList = service.getMedicalRecords();
		medicalRecordsList = service.updateMedicalRecord(medicalRecordsList, medicalRecord);
		log.info("PUT request for medicalRecord send");
		log.info("Response for the PUT request for medicalRecord: " + medicalRecordsList);
		return medicalRecordsList;
	}

	/**
	 * Deletes a medical record
	 * 
	 * @param medicalRecord : MedicalRecord to delete from the medical records list
	 * @return medical records list without the deleted medical record
	 */
	@DeleteMapping("/medicalRecord")
	public List<MedicalRecord> deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		List<MedicalRecord> medicalRecordsList = new ArrayList<>();
		medicalRecordsList = service.getMedicalRecords();
		medicalRecordsList = service.deleteMedicalRecord(medicalRecordsList, medicalRecord);
		log.info("DELETE request for medicalRecord send");
		log.info("Response for the DELETE request for medicalRecord: " + medicalRecordsList);
		return medicalRecordsList;
	}

}
