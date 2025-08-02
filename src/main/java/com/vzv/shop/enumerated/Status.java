package com.vzv.shop.enumerated;

import java.util.HashMap;
import java.util.Map;

public enum Status {

	ACCEPTED("ACCEPTED"), PROCESSING("PROCESSING"), CONFIRMED("CONFIRMED"), SENT("SENT"),
	DELIVERED("DELIVERED"), CANCELED("CANCELED");

	
	private static final Map<String, Status> BY_LABEL = new HashMap<>();
	
	static {
		for(Status s: values()) {
			BY_LABEL.put(s.label, s);
		}
	}
	
	private final String label;

	Status(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public static Status getByLabel(String label) {
		return BY_LABEL.get(label);
	}
}
