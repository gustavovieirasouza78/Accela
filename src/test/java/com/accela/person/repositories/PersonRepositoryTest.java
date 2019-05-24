package com.accela.person.repositories;

import static org.junit.Assert.assertEquals;

import com.accela.person.entities.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "test")
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    private static final Long id = 10l;
    private static final String name = "Theresa";
    private static final String surname = "May";

    @Before
    public void setUp() throws Exception {
        this.personRepository.deleteAll();
        Person person = this.personRepository.save(getPerson());
    }

    @After
    public final void tearDown() {
        this.personRepository.deleteAll();
    }


    @Test
    public void testEdit() {
        Person person = getPerson();
        person.setSurname("April");
        Person personSaved = this.personRepository.save(person);

        assertEquals(personSaved.getSurname(), "April");
    }

    @Test
    public void testCountPersons() {
        long persons = this.personRepository.count();
        assertEquals(persons, 1l);
    }

    @Test
    public void testListPerson() {
        List<Person> personList = this.personRepository.findAll();
        assertEquals(personList.size(), 1);
    }

    private Person getPerson() {
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setSurname(surname);
        return person;
    }

}
