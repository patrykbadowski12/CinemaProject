package com.patryk;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "films")
class Films {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "nameoffilm")
	private String nameOfFilm;

	@Column(name = "time")
	private String time;

	@Column(name = "date")
	private String date;

	@Column(name = "image")
	private String image;

	@OneToMany(mappedBy = "films", fetch = FetchType.LAZY,cascade= {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.DETACH, CascadeType.REFRESH})
	@MapKey(name = "seat")
	private Map<Integer, Persons> mapOfPersons = new HashMap<Integer, Persons>();

	public Films() {
		for (int i = 1; i <= 50; i++) {
			mapOfPersons.put(i, null);
		}
	}

	public Films(Films tempFilm) {
		this.id= tempFilm.getId();
		this.nameOfFilm = tempFilm.getNameOfFilm();
		this.time = tempFilm.getTime();
		this.date = tempFilm.getDate();
		this.image = tempFilm.getImage();
		for (int i = 1; i <= 50; i++) {
			mapOfPersons.put(i, null);
		}

	}

	public Films(String nameOfFilm, String time, String date, String image) {
		this.nameOfFilm = nameOfFilm;
		this.time = time;
		this.date = date;
		this.image = image;
		for (int i = 1; i <= 50; i++) {
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
