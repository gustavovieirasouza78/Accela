package com.accela.person.services;

import com.accela.person.entities.Person;
import com.accela.person.repositories.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "test")
public class PersonServiceTest {

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Before
    public void setUp() throws Exception {
        BDDMockito.given(this.personRepository.existsById(Mockito.anyLong())).willReturn(false);
        BDDMockito.given(this.personRepository.save(Mockito.any(Person.class))).willReturn(new Person());
        BDDMockito.given(this.personRepository.count()).willReturn(new Long(10L));
        BDDMockito.given(this.personRepository.findAll()).willReturn(new ArrayList<Person>());
    }

    @Test
    public void testAdd(){
        Optional<Person> person = this.personService.add(new Person());
        assertTrue(person.isPresent());
    }

    @Test
    public void testEdit(){
        Optional<Person> person = this.personService.edit(new Person());
        assertTrue(person.isPresent());
    }

    @Test
    public void testCount(){
        assertEquals(new Long(10L), this.personService.count());
    }


    @Test
    public void testList(){
        Optional<List<Person>> persons = this.personService.list();
        assertTrue(persons.isPresent());
    }



}