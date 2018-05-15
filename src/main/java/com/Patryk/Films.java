package com.Patryk;

import java.util.HashMap;
import java.util.Map;

public class Films {
	
	private int id;
	private String nameOfFilm;
	private String time;
	private String date;
	private String image;
	
	public Map<Integer, Persons> mapOfPersons = new HashMap<Integer, Persons>();
	
	public Films() {
		for(int i=1; i<=50; i++) {
			mapOfPersons.put(i, null);
		}
	}
	
	public Films(int id, String nameOfFilm, String time, String date, String image) {
		super();
		this.id = id;
		this.nameOfFilm = nameOfFilm;
		this.time = time;
		this.date = date;
		this.image = image;
		for(int i=1; i<=50; i++) {
			mapOfPersons.put(i, null);
		}
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameOfFilm() {
		return nameOfFilm;
	}

	public void setNameOfFilm(String nameOfFilm) {
		this.nameOfFilm = nameOfFilm;
	}

	public Map<Integer, Persons> getMapOfPersons() {
		return mapOfPersons;
	}

	public void setMapOfPersons(Map<Integer, Persons> mapOfPersons) {
		this.mapOfPersons = mapOfPersons;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Films [id=" + id + ", nameOfFilm=" + nameOfFilm + ", time=" + time + ", date=" + date + ", image="
				+ image + "]";
	}
	
	

	
}
