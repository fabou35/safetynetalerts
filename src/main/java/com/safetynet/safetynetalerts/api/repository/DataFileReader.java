package com.safetynet.safetynetalerts.api.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Component;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataFileReader implements DataReader {

	private static String dataFilePath = "src/main/resources/data.json";

	private static File jsonFile() {
		return new File(dataFilePath);
	}

	/**
	 * retrieves data from json file
	 */
	@Override
	public Any readDatas() {
		Any any = null;
		try {
			byte[] bytesFile = Files.readAllBytes(jsonFile().toPath());
			JsonIterator jsonIter = JsonIterator.parse(bytesFile);
			any = jsonIter.readAny();
		} catch (IOException e) {
			log.error("File reading error: " + dataFilePath);
		}
		return any;

	}

}
