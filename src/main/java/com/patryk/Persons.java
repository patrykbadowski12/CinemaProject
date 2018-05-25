package com.patryk;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="persons")
class Persons {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name="seat")
	private int seat;	
	
	@Column(name="firstname")
	private String firstName;
	
	@Column(name="lastname")
	private String lastName;

	@Column(name="key")
	private String key;
	
	@ManyToOne
	@JoinColumn(name="films_id")
	private Films films;
	
	public Persons() {}
	
	
	
	public Persons(int seat, String firstName, String lastName, String key) {
		super();
		this.seat = seat;
		this.firstName = firstName;
		this.lastName = lastName;
		this.key = key;
	}



	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Films getFilms() {
		return films;
	}

	public void setFilms(Films films) {
		this.films = films;
	}

	@Override
	public String toString() {
		return "Persons [id=" + id + ", seat=" + seat + ", firstName=" + firstName + ", lastName=" + lastName + ", key="
				+ key + "]";
	}
	

	
}