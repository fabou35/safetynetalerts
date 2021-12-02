package com.safetynet.safetynetalerts.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.safetynetalerts.api.repository.MedicalRecordApiRepository;
import com.safetynet.safetynetalerts.model.MedicalRecord;

@Service
public class MedicalRecordApiService {

	@Autowired
	private MedicalRecordApiRepository repository;

	/**
	 * Retrieves medical records data from MedicalRecordApiRepository
	 * 
	 * @return list of medical records retrieved from data
	 * @throws IOException
	 */
	public List<MedicalRecord> getMedicalRecords() throws IOException {
		return repository.getMedicalRecordsDatas();
	}

	/**
	 * Adds a medical record to the medical records list
	 * 
	 * @param medicalRecordsList : List of MedicalRecord
	 * @param medicalRecord      : MedicalRecord to add to the medical records list
	 * @return medical records list with the new medical record saved
	 */
	public List<MedicalRecord> saveMedicalRecord(List<MedicalRecord> medicalRecordsList, MedicalRecord medicalRecord) {
		medicalRecordsList.add(medicalRecord);
		return medicalRecordsList;
	}

	/**
	 * Updates a medical record from medical records list
	 * 
	 * @param medicalRecordsList : List of MedicalRecord
	 * @param medicalRecord      : MedicalRecord to update in the medical records
	 *                           list
	 * @return medical records list with updated medical record
	 */
	public List<MedicalRecord> updateMedicalRecord(List<MedicalRecord> medicalRecordsList,
			MedicalRecord medicalRecord) {
		List<MedicalRecord> newMedicalRecordsList = new ArrayList<>();
		for (MedicalRecord record : medicalRecordsList) {
			if (record.getFirstName().equals(medicalRecord.getFirstName()) &&
					record.getLastName().equals(medicalRecord.getLastName())) {
				newMedicalRecordsList.add(medicalRecord);
			} else {
				newMedicalRecordsList.add(record);
			}
		}
		return newMedicalRecordsList;
	}

	/**
	 * Deletes a medical record from medical records list
	 * 
	 * @param medicalRecordsList : List of MedicalRecord
	 * @param medicalRecord      : MedicalRecord to delete from the medical records
	 *                           list
	 * @return medical records list without the deleted medical record
	 */
	public List<MedicalRecord> deleteMedicalRecord(List<MedicalRecord> medicalRecordsList,
			MedicalRecord medicalRecord) {
		List<MedicalRecord> newMedicalRecordsList = new ArrayList<>();
		for (MedicalRecord record : medicalRecordsList) {
			if (!(record.getFirstName().equals(medicalRecord.getFirstName()) &&
					record.getLastName().equals(medicalRecord.getLastName()))) {
				newMedicalRecordsList.add(record);
			}
		}
		return newMedicalRecordsList;
	}
}
