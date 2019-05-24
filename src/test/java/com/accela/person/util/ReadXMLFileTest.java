package com.accela.person.util;

import com.accela.person.entities.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "test")
public class ReadXMLFileTest {

    private File file;

    ReadXMLFile readXMLFile;

    @Before
    public void setUp() throws Exception {
        file = new ClassPathResource("example.xml").getFile();
    }


    @Test
    public void parseXMLTest(){
        List<Person> personList= readXMLFile.parseXML(file);

        assertEquals(personList.size(), 2);
    }

}
