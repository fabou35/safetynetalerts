package com.safetynet.safetynetalerts.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class FireStation {

	private String stationNumber;
	private Set<String> addresses = new HashSet<>();
	
	public void addAddress(String address) {
		this.addresses.add(address);
}
}
