package com.accela.person.services;

import com.accela.person.entities.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    Optional<Person> add(Person person);

    Optional<Person> edit(Person person);

    boolean delete(Long id);

    Long count ();

    Optional<List<Person>> list ();
}