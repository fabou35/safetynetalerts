package com.safetynet.safetynetalerts.test.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.safetynetalerts.api.service.MedicalRecordApiService;
import com.safetynet.safetynetalerts.model.MedicalRecord;

@SpringBootTest
public class MedicalRecordApiServiceTest {

	@Autowired
	private MedicalRecordApiService service;

	@Test
	public void getMedicalRecordsReturnsNotEmptyList() throws IOException {
		// GIVEN
		List<MedicalRecord> medicalRecordsList = new ArrayList<>();

		// WHEN
		medicalRecordsList = service.getMedicalRecords();

		// THEN
		assertThat(medicalRecordsList).isNotNull();
	}

	@Test
	public void savesANewMedicalRecordInTheMedicalRecordsList() {
		// GIVEN
		List<MedicalRecord> medicalRecordsList = new ArrayList<>();
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setFirstName("new first name");
		medicalRecord.setLastName("new last name");
		medicalRecord.setBirthdate("new birthdate");
		List<String> medications = new ArrayList<>();
		medications.add("medication1");
		medications.add("medication2");
		medicalRecord.setMedications(medications);
		List<String> allergies = new ArrayList<>();
		allergies.add("allergie1");

		// WHEN
		medicalRecordsList = service.saveMedicalRecord(medicalRecordsList, medicalRecord);

		// THEN
		assertThat(medicalRecordsList).contains(medicalRecord);

	}

	@Test
	public void updatesAMedicalRecordInTheMedicalRecordsListAddingAMedication() {
		// GIVEN
		List<MedicalRecord> medicalRecordsList = new ArrayList<>();
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setFirstName("first name");
		medicalRecord.setLastName("last name");
		medicalRecord.setBirthdate("birthdate");
		List<String> medicationsList = new ArrayList<>();
		medicationsList.add("medication1");
		medicalRecord.setMedications(medicationsList);
		List<String> allergiesList = new ArrayList<>();
		medicalRecord.setAllergies(allergiesList);
		medicalRecordsList.add(medicalRecord);

		// WHEN
		MedicalRecord newMedicalRecord = new MedicalRecord();
		newMedicalRecord.setFirstName(medicalRecord.getFirstName());
		newMedicalRecord.setLastName(medicalRecord.getLastName());
		newMedicalRecord.setBirthdate(medicalRecord.getBirthdate());
		List<String> newMedicationsList = new ArrayList<>();
		newMedicationsList.add("medication1");
		String newMedication = "medication3";
		newMedicationsList.add(newMedication);
		newMedicalRecord.setMedications(newMedicationsList);
		newMedicalRecord.setAllergies(medicalRecord.getAllergies());

		medicalRecordsList = service.updateMedicalRecord(medicalRecordsList, newMedicalRecord);

		// THEN
		assertThat(medicalRecordsList).contains(newMedicalRecord);

	}

	@Test
	public void updatesAMedicalRecordInTheMedicalRecordsListAddingAnAllergie() {
		// GIVEN
		List<MedicalRecord> medicalRecordsList = new ArrayList<>();
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setFirstName("first name");
		medicalRecord.setLastName("last name");
		medicalRecord.setBirthdate("birthdate");
		List<String> medicationsList = new ArrayList<>();
		medicationsList.add("medication");
		medicalRecord.setMedications(medicationsList);
		List<String> allergiesList = new ArrayList<>();
		allergiesList.add("allergie1");
		medicalRecord.setAllergies(allergiesList);
		medicalRecordsList.add(medicalRecord);

		// WHEN
		MedicalRecord newMedicalRecord = new MedicalRecord();
		newMedicalRecord.setFirstName(medicalRecord.getFirstName());
		newMedicalRecord.setLastName(medicalRecord.getLastName());
		newMedicalRecord.setBirthdate(medicalRecord.getBirthdate());
		newMedicalRecord.setMedications(medicalRecord.getMedications());
		List<String> newAllergiesList = new ArrayList<>();
		newAllergiesList.addAll(allergiesList);
		String newAllergie = "allergie2";
		newAllergiesList.add(newAllergie);
		newMedicalRecord.setAllergies(newAllergiesList);

		medicalRecordsList = service.updateMedicalRecord(medicalRecordsList, newMedicalRecord);

		// THEN
		assertThat(medicalRecordsList).contains(newMedicalRecord);

	}

	@Test
	public void deletesAMedicalRecordFromTheMedicalRecordsListForAFirstNameAndALastName() {
		// GIVEN
		List<MedicalRecord> medicalRecordsList = new ArrayList<>();
		MedicalRecord medicalRecord1 = new MedicalRecord();
		medicalRecord1.setFirstName("first name1");
		medicalRecord1.setLastName("last name1");
		medicalRecord1.setBirthdate("birthdate1");
		List<String> medicationsList1 = new ArrayList<>();
		medicationsList1.add("medication1");
		medicalRecord1.setMedications(medicationsList1);
		List<String> allergiesList1 = new ArrayList<>();
		allergiesList1.add("allergie1");
		medicalRecord1.setAllergies(allergiesList1);
		medicalRecordsList.add(medicalRecord1);
		
		MedicalRecord medicalRecord2 = new MedicalRecord();
		medicalRecord2.setFirstName("first name2");
		medicalRecord2.setLastName("last name2");
		medicalRecord2.setBirthdate("birthdate2");
		List<String> medicationsList2 = new ArrayList<>();
		medicationsList2.add("medication2");
		medicalRecord2.setMedications(medicationsList2);
		List<String> allergiesList2 = new ArrayList<>();
		allergiesList2.add("allergie2");
		medicalRecord2.setAllergies(allergiesList2);
		medicalRecordsList.add(medicalRecord2);
		
		// WHEN
		medicalRecordsList = service.deleteMedicalRecord(medicalRecordsList, medicalRecord1);
		
		// THEN
		assertThat(medicalRecordsList).doesNotContain(medicalRecord1);

	}
}
