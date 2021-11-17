package com.safetynet.safetynetalerts.api.repository;

import java.io.IOException;

import com.jsoniter.any.Any;

public interface DataReader {
	public Any readDatas() throws IOException;

}
