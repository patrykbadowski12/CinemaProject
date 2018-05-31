package com.patryk;

import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<PersonDbModel, Integer> {

}
