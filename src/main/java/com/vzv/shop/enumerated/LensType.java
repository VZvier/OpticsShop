package com.vzv.shop.enumerated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum LensType {

	ASTIGMATIC("astigmatic"), STIGMATIC("stigmatic"), COMPUTER("computer"),
	COMPUTER_BB("blue-block"), PHOTOCHROM_BROWN("photochrom(brown)"),
	PHOTOCHROM_GRAY("photochrom(gray)"), ASPHERICAL("aspherical"), ORANGE("orange"),
	UV_PROTECTION("UV-protection");
	
	private static final Map<String, LensType> BY_LABEL = new HashMap<>();
	
	static {
		for(LensType lensType: values()) {
			BY_LABEL.put(lensType.label, lensType);
		}
	}
	
	private final String label;

	LensType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public static LensType getByLabel(String label) {
		return BY_LABEL.get(label);
	}

	public static List<String> getLabels(){
		return BY_LABEL.keySet().stream().toList();
	}
}