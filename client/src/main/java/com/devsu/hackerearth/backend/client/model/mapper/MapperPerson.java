package com.devsu.hackerearth.backend.client.model.mapper;

import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.client.model.Person;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;

@Component
public class MapperPerson {

    public Person clientDtoTOpPerson(ClientDto clientdto) {

        Person person = new Person();
        person.setDni(clientdto.getDni());
        person.setAddress(clientdto.getAddress());
        person.setAge(clientdto.getAge());
        person.setGender(clientdto.getGender());
        person.setName(clientdto.getName());
        person.setPhone(clientdto.getPhone());

        return person;
    }

    public Person clientDtoTOPersonUpdate(Person personIn, ClientDto clientdt) {
        Person person = new Person();
        person.setDni(clientdt.getDni() != null && clientdt.getDni() != personIn.getDni() ? clientdt.getDni()
                : personIn.getDni());
        person.setAddress(
                clientdt.getAddress() != null && clientdt.getAddress() != personIn.getAddress() ? clientdt.getAddress()
                        : personIn.getAddress());
        person.setAge(clientdt.getAge() != 0 && clientdt.getAge() != personIn.getAge() ? clientdt.getAge()
                : personIn.getAge());
        person.setGender(
                clientdt.getGender() != null && clientdt.getGender() != personIn.getGender() ? clientdt.getGender()
                        : personIn.getGender());
        person.setName(
                clientdt.getName() != null && clientdt.getName() != personIn.getName() ? clientdt.getName()
                        : personIn.getName());

        person.setPhone(clientdt.getPhone() != null && clientdt.getPhone() != personIn.getPhone() ? clientdt.getPhone()
                : personIn.getPhone());

        return person;
    }

}
