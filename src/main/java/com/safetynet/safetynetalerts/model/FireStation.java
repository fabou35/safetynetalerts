package com.safetynet.safetynetalerts.model;

import java.util.HashSet;


import java.util.Set;
import java.util.stream.Collectors;

import com.safetynet.safetynetalerts.model.FireStation;

import lombok.Data;

@Data
public class FireStation implements Comparable<FireStation>{

	private String stationNumber;
	private Set<String> addresses = new HashSet<>();
	
	public void addAddress(Set<String> addresses) {
		for (String address : addresses) {
			this.addresses.add(address);
		}
	}

	public void addAddress(String address) {
		this.addresses.add(address);
	}

	public Set<String> getAddresses() {
		return addresses.stream().collect(Collectors.toSet());
	}


	@Override
	public int compareTo(FireStation firestation) {
		return (Integer.parseInt(this.stationNumber) - Integer.parseInt(firestation.stationNumber));
	}
	
	
}
