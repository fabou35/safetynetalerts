package com.safetynet.safetynetalerts.api.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsoniter.any.Any;
import com.safetynet.safetynetalerts.model.Person;

@Repository
public class PersonApiRepository {

	@Autowired
	private DataFileReader reader;
	
	/**
	 * retrieves persons datas from json file
	 * @return List<Person>
	 * @throws IOException
	 */
	public List<Person> getPersonsDatas() throws IOException {
		Any any = reader.readDatas();
		Any personAny = any.get("persons");
		List<Person> personsList = new ArrayList<>();
		for (Any a : personAny) {
			Person person = new Person();
			person.setLastName(a.get("lastName").toString());
			person.setFirstName(a.get("firstName").toString());
			person.setAddress(a.get("address").toString());
			person.setCity(a.get("city").toString());
			person.setZip(a.get("zip").toString());
			person.setPhone(a.get("phone").toString());
			person.setEmail(a.get("email").toString());
			personsList.add(person);
		}

		return personsList;
	}

}
