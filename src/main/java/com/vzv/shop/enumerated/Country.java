package com.vzv.shop.enumerated;

import java.util.HashMap;
import java.util.Map;

public enum Country {

	UKRAINE("ukraine"), ITALY("italy"), KOREA("korea"), CHINA("china"), JAPAN("japan"), GERMANY("germany"), FRANCE("france"),
	POLAND("poland"), ROMANIA("romania");
	
	private static final Map<String, Country> BY_LABEL = new HashMap<>();
	
	static {
		for(Country country: values()) {
			BY_LABEL.put(country.label, country);
		}
	}
	
	private final String label;
	
	Country(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public static Country getByLabel(String label) {
		return BY_LABEL.get(label.toLowerCase());
	}
}