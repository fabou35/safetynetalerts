package com.safetynet.safetynetalerts.test.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.safetynetalerts.api.repository.FireStationApiRepository;
import com.safetynet.safetynetalerts.model.FireStation;

@SpringBootTest
public class FireStationApiRepositoryTest {

	@Autowired
	private FireStationApiRepository repository;
	
	@Test
	public void fireStationsDatasAreRetrieved() throws IOException {
		// GIVEN
		List<FireStation> fireStationsList = new ArrayList<>();
		
		// WHEN
		fireStationsList = repository.getFireStationsDatas();

		// THEN
		assertThat(fireStationsList).isNotEmpty();
		
	}
}
