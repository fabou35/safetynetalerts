package com.safetynet.safetynetalerts.api.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsoniter.any.Any;
import com.safetynet.safetynetalerts.model.MedicalRecord;

@Repository
public class MedicalRecordApiRepository {

	@Autowired
	private DataFileReader reader;
	
	/**
	 * Retrieves medical records datas from json file
	 * @return
	 * @throws IOException
	 */
	public List<MedicalRecord> getMedicalRecordsDatas() throws IOException {
		Any any = reader.readDatas();
		Any medicalRecordsAny = any.get("medicalrecords");
		List<MedicalRecord> medicalRecordsList = new ArrayList<>();
		for (Any m : medicalRecordsAny) {
			MedicalRecord medicalRecords = new MedicalRecord();
			medicalRecords.setFirstName(m.get("firstName").toString());
			medicalRecords.setLastName(m.get("lastName").toString());
			medicalRecords.setBirthdate(m.get("birthdate").toString());

			Any medicationsAny = m.get("medications");
			List<String> medicationsList = new ArrayList<>();
			if (!medicationsAny.toString().equals("[]")) {
				for (Any med : medicationsAny) {
					medicationsList.add(med.toString());
				}
				medicalRecords.setMedications(medicationsList);
			}

			Any allergiesAny = m.get("allergies");
			List<String> allergiesList = new ArrayList<>();
			if (!allergiesAny.toString().equals("[]")) {
				for (Any allergieAny : allergiesAny) {
					allergiesList.add(allergieAny.toString());
				}
				medicalRecords.setAllergies(allergiesList);
			}
			medicalRecordsList.add(medicalRecords);
		}

		return medicalRecordsList;
	}

}
