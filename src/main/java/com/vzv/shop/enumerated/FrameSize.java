package com.vzv.shop.enumerated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FrameSize {

	CHILDISH("childish"), ADULT("adult");
	
	private static final Map<String, FrameSize> BY_LABEL = new HashMap<>();
	
	static {
		for(FrameSize f: values()) {
			BY_LABEL.put(f.label, f);
		}
	}
	
	private final String label;
	
	FrameSize(String label){
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public static FrameSize getByLabel(String label) {
		return BY_LABEL.get(label);
	}

	public static List<String> getLabels(){
		return BY_LABEL.keySet().stream().toList();
	}
}