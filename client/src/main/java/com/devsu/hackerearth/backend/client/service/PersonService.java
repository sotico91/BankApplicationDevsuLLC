package com.devsu.hackerearth.backend.client.service;

import java.util.List;

import com.devsu.hackerearth.backend.client.model.Person;

public interface PersonService {

    public Person create(Person person);

    public Person getById(Long id);

    public void deleteById(Long id);

    public List<Person> getAll();

}
