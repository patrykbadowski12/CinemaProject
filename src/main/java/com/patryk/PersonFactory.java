package com.patryk;

import org.springframework.stereotype.Component;

@Component
public class PersonFactory {

	public PersonDbModel create(int seat, String firstName, String lastName, String key) {
	PersonDbModel person = new PersonDbModel();
		person.setSeat(seat);
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setKey(key);

		return person;
	}
}
