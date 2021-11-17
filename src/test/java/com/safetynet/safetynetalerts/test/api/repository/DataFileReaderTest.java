package com.safetynet.safetynetalerts.test.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jsoniter.any.Any;
import com.safetynet.safetynetalerts.api.repository.DataFileReader;

@SpringBootTest
public class DataFileReaderTest {

	@Autowired
	DataFileReader reader;
	
	@Test
	public void datasAreRetrievedFromJsonFile () throws IOException{
		Any any = reader.readDatas();
		
		assertThat(any).isNotNull();
	}
}
