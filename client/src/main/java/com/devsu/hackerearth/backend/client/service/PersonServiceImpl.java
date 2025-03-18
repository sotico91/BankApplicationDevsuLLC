package com.devsu.hackerearth.backend.client.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.model.Person;
import com.devsu.hackerearth.backend.client.repository.PersonRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public Person create(Person person) {

        Person personOut = personRepository.save(person);
        return personOut;
    }

    @Override
    public Person getById(Long id) {

        Person person = personRepository.findById(id).orElse(null);
        return person;
    }

    @Override
    public void deleteById(Long id) {

        personRepository.deleteById(id);
    }

    @Override
    public List<Person> getAll() {

        List<Person> listPersons = personRepository.findAll();

        return listPersons;
    }

}
