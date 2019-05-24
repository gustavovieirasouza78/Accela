package com.accela.person.services.impl;

import com.accela.person.entities.Person;
import com.accela.person.repositories.PersonRepository;
import com.accela.person.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public Optional<Person> add(Person person) {
        if (this.personRepository.existsById(person.getId())){
            return Optional.empty();
        }else {
            return Optional.ofNullable(this.personRepository.save(person));
        }
    }

    @Override
    public Optional<Person> edit(Person person) {
        return Optional.ofNullable(this.personRepository.save(person));
    }

    @Override
    public boolean delete(Long id) {
        try {
            this.personRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            return false;
        }
        return true;
    }

    @Override
    public Long count() {
        return this.personRepository.count();
    }

    @Override
    public Optional<List<Person>> list() {
        return Optional.ofNullable(this.personRepository.findAll());
    }
}

