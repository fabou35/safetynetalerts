package com.safetynet.safetynetalerts.api.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Component;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

@Component
public class DataFileReader implements DataReader {

	private static String dataFilePath = "src/main/resources/data.json";

	private static File jsonFile() {
		return new File(dataFilePath);
	}

	/**
	 * retrieves datas from json file
	 */
	@Override
	public Any readDatas() throws IOException {
		byte[] bytesFile = Files.readAllBytes(jsonFile().toPath());
		JsonIterator jsonIter = JsonIterator.parse(bytesFile);
		Any any = jsonIter.readAny();
		return any;

	}

}
