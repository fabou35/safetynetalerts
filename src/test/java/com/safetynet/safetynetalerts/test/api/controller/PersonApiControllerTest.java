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

import com.safetynet.safetynetalerts.model.Person;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonApiControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetPersons() throws Exception {
		
		mockMvc.perform(get("/person"))
				.andExpect(status().isOk());
	}

	@Test
	public void testSavePerson() throws Exception {
		// GIVEN
		Person personToSave = new Person();
		personToSave.setLastName("new last name");
		personToSave.setFirstName("new first name");
		personToSave.setAddress("new address");
		personToSave.setCity("new city");
		personToSave.setZip("new zip");
		personToSave.setPhone("new phone");
		personToSave.setEmail("new email");

		// WHEN
		JSONObject personJsonObject = new JSONObject();
		personJsonObject.put("lastName", personToSave.getLastName());
		personJsonObject.put("firstName", personToSave.getFirstName());
		personJsonObject.put("address", personToSave.getAddress());
		personJsonObject.put("city", personToSave.getCity());
		personJsonObject.put("zip", personToSave.getZip());
		personJsonObject.put("phone", personToSave.getPhone());
		personJsonObject.put("email", personToSave.getEmail());

		
		// THEN
		mockMvc.perform(post("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(personJsonObject.toString()))
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdatePerson() throws Exception {
		// GIVEN
		Person personUpdated = new Person();
		personUpdated.setLastName("last name");
		personUpdated.setFirstName("first name");
		personUpdated.setAddress("new address");
		personUpdated.setCity("city");
		personUpdated.setZip("zip");
		personUpdated.setPhone("phone");
		personUpdated.setEmail("email");
		
		// WHEN
		JSONObject personJsonObject = new JSONObject();
		personJsonObject.put("lastName", personUpdated.getLastName());
		personJsonObject.put("firstName", personUpdated.getFirstName());
		personJsonObject.put("address", personUpdated.getAddress());
		personJsonObject.put("city", personUpdated.getCity());
		personJsonObject.put("zip", personUpdated.getZip());
		personJsonObject.put("phone", personUpdated.getPhone());
		personJsonObject.put("email", personUpdated.getEmail());

		// THEN
		mockMvc.perform(put("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(personJsonObject.toString()))
				.andExpect(status().isOk());
	}

	@Test
	public void testDeletePerson() throws Exception {
		// GIVEN
		Person personToDelete = new Person();
		personToDelete.setLastName("last name");
		personToDelete.setFirstName("first name");
		personToDelete.setAddress("address");
		personToDelete.setCity("city");
		personToDelete.setZip("zip");
		personToDelete.setPhone("phone");
		personToDelete.setEmail("email");

		// WHEN
		JSONObject personJsonObject = new JSONObject();
		personJsonObject.put("lastName", personToDelete.getLastName());
		personJsonObject.put("firstName", personToDelete.getFirstName());
		personJsonObject.put("address", personToDelete.getAddress());
		personJsonObject.put("city", personToDelete.getCity());
		personJsonObject.put("zip", personToDelete.getZip());
		personJsonObject.put("phone", personToDelete.getPhone());
		personJsonObject.put("email", personToDelete.getEmail());

		// THEN
		mockMvc.perform(delete("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(personJsonObject.toString()))
				.andExpect(status().isOk());
	}
}
