package com.devsu.hackerearth.backend.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.Person;

@SpringBootTest
public class sampleTest {

    private Client client;

    @BeforeEach
    void setUp() {

        Person person = new Person();
        person.setId(1L);
        person.setName("Nestur Alvarez");
        person.setDni("655999");
        person.setGender("Male");
        person.setAge(35);
        person.setAddress("Calle 134 # 69-58");
        person.setPhone("45532699");

        client = new Client();
        client.setId(1L);
        client.setPerson(person);
        client.setPassword("securePassword");
        client.setActive(true);
    }

    @Test
    void testClientAttributes() {

        assertNotNull(client);
        assertEquals(1L, client.getId());
        assertEquals("securePassword", client.getPassword());
        assertTrue(client.isActive());

        assertNotNull(client.getPerson());
        assertEquals("Nestur Alvarez", client.getPerson().getName());
        assertEquals("655999", client.getPerson().getDni());
    }

    @Test
    void testClientAssociationWithPerson() {

        assertNotNull(client.getPerson());
        assertEquals("Nestur Alvarez", client.getPerson().getName());
        assertEquals("Calle 134 # 69-58", client.getPerson().getAddress());
    }
}
