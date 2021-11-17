package com.safetynet.safetynetalerts.test.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.safetynetalerts.api.repository.PersonApiRepository;
import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
public class PersonApiRepositoryTest {

	@Autowired
	private PersonApiRepository repository;
	
	@Test
	public void personsDatasAreRetrieved() throws IOException {
		// GIVEN
		List<Person> personsList = new ArrayList<>();
		
		// WHEN
		personsList = repository.getPersonsDatas();

		// THEN
		assertThat(personsList).isNotEmpty();
		
	}
}
