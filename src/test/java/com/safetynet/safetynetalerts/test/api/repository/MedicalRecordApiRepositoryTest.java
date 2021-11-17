package com.safetynet.safetynetalerts.test.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.safetynetalerts.api.repository.MedicalRecordApiRepository;
import com.safetynet.safetynetalerts.model.MedicalRecord;

@SpringBootTest
public class MedicalRecordApiRepositoryTest {

	@Autowired
	private MedicalRecordApiRepository repository;
	
	@Test
	public void medicalRecordsDatasAreRetrieved() throws IOException {
		// GIVEN
		List<MedicalRecord> medicalRecordsList = new ArrayList<>();
		
		// WHEN
		medicalRecordsList = repository.getMedicalRecordsDatas();

		// THEN
		assertThat(medicalRecordsList).isNotEmpty();
		
	}
}
