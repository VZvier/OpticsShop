package com.vzv.shop.enumerated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Gender {

	MAN("man"), WOMAN("woman"), UNIVERSAL("universal");
	
	private static final Map<String, Gender> BY_LABEL = new HashMap<>();
	static {
		for(Gender gender: values()) {
			BY_LABEL.put(gender.label, gender);
		}
	}
	
	private final String label;
	
	Gender(String label){
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public static Gender getByLabel(String label) {
		return BY_LABEL.get(label);
	}

	public static List<String> getLabels(){
		return BY_LABEL.keySet().stream().toList();
	}
}
