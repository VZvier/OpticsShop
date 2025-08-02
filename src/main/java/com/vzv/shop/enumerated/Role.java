package com.vzv.shop.enumerated;

import java.util.HashMap;
import java.util.Map;

public enum Role {

	USER("ROLE_USER"), STAFF("ROLE_STAFF"), SYS_ADMIN("ROLE_SYSADMIN");
	
	
	private static final Map<String, Role> BY_LABEL = new HashMap<>();
	
	static {
		for(Role role: values()) {
			BY_LABEL.put(role.label, role);
		}
	}
	
	private final String label;
	
	Role(String label){
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public static Role getByLabel(String label) {
		return BY_LABEL.get(label);
	}
}
