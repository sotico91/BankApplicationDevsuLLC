package com.devsu.hackerearth.backend.client.model.mapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

import com.devsu.hackerearth.backend.client.exception.UserNotFoundException;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.Person;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;

@Component
public class MapperClient {

    public ClientDto mapperClientToClientDTO(Client client, Person person) {
        if (client == null || person == null) {
            throw new UserNotFoundException("Client o Person no pueden ser nulos");
        }

        return ClientDto.builder()
                .id(client.getId())
                .name(person.getName())
                .dni(person.getDni())
                .gender(person.getGender())
                .age(person.getAge())
                .address(person.getAddress())
                .phone(person.getPhone())
                .password(client.getPassword())
                .isActive(client.isActive())
                .build();
    }

    public List<ClientDto> mapperClientListToClientDTOList(List<Client> listClient, List<Person> listPerson) {
        return listClient.stream()
                .map(client -> {
                    if (client.getId() == null)
                        return null;

                    Optional<Person> personOutList = listPerson.stream()
                            .filter(p -> p.getId() != null && p.getId().equals(client.getId()))
                            .findFirst();

                    return personOutList.map(person -> mapperClientToClientDTO(client, person)).orElse(null);
                })
                .filter(clientDto -> clientDto != null)
                .collect(Collectors.toList());
    }

    public Client mapperClientDtoToClient(ClientDto clientDto) {
        if (clientDto == null) {
            throw new IllegalArgumentException("ClientDto no puede ser nulo");
        }

        Client client = new Client();
        client.setActive(clientDto.isActive());
        client.setPassword(clientDto.getPassword());

        return client;
    }

    public Client mapperClientDtoToClient(ClientDto clientDto, ClientDto clientDtoExistInBd) {
        if (clientDto == null || clientDtoExistInBd == null) {
            throw new IllegalArgumentException("ClientDto o ClientDtoExistInBd no pueden ser nulos");
        }

        Client client = new Client();
        client.setActive(clientDto.isActive() != clientDtoExistInBd.isActive() ? clientDto.isActive()
                : clientDtoExistInBd.isActive());
        client.setPassword(
                clientDto.getPassword() != null && !clientDto.getPassword().equals(clientDtoExistInBd.getPassword())
                        ? clientDto.getPassword()
                        : clientDtoExistInBd.getPassword());

        return client;
    }

    public Client mapperClientDtoToClientPartial(ClientDto clientDto) {
        if (clientDto == null) {
            throw new IllegalArgumentException("ClientDto no puede ser nulo");
        }

        Client client = new Client();
        client.setActive(clientDto.isActive());
        return client;
    }
}
