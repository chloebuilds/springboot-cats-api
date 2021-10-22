package com.example.chloeSpringApi.repos;

import com.example.chloeSpringApi.models.Cat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// ! Our interface extends CrudRepository, which is an interface provided by
// ! org.springframework.data.repository package. This will give our interface
// ! all the methods and final properties that CrudRepository has.
// ! Cat is the type of model, and Integer is the ID type.

@Repository
public interface CatRepo extends CrudRepository<Cat, Integer> {

}
