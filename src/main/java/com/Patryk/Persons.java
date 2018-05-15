package com.Patryk;

class Persons {
	private String name;
	private String key;
	
	public Persons() {}
	
	public Persons(String name, String key) {
		super();
		this.name = name;
		this.key = key;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	
}