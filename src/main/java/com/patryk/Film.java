package com.patryk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
class Film {

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

	  @OneToMany(
		        cascade = CascadeType.ALL, 
		        orphanRemoval = true
		    )
	private	List<PersonDbModel> mapOfPersons = new ArrayList<PersonDbModel>();

	public Film() {
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

	public List<PersonDbModel> getMapOfPersons() {
		return mapOfPersons;
	}


	public void setMapOfPersons(List<PersonDbModel> mapOfPersons) {
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
