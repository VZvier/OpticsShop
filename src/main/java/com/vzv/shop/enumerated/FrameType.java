package com.vzv.shop.enumerated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FrameType {

	PLASTIC("plastic"), PLASTIC_SEMI_RIMLESS("plastic semi-rimless"), METAL("metal"), METAL_SEMI_RIMLESS("metal semi-rimless"), RIMLESS("rimless");

	private static final Map<String, FrameType> BY_LABEL = new HashMap<>();
	private static final Map<FrameType, String> NAME_BY_LABEL = new HashMap<>();

	static {
		for (FrameType t: values()){
			BY_LABEL.put(t.label, t);
			NAME_BY_LABEL.put(t, t.toString());
		}
	}
	private final String label;
	
	FrameType(String type){
		this.label = type;
	}
	
	public static FrameType getByLabel(String label) {
		return BY_LABEL.get(label);
	}

	public String getNameByLabel() {
		return NAME_BY_LABEL.get(this);
	}

	public String getLabel() {
		return this.label;
	}

	public static List<String> getLabels(){
		return BY_LABEL.keySet().stream().toList();
	}
}
