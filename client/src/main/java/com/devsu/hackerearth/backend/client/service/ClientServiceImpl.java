package com.devsu.hackerearth.backend.client.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.exception.UserNotFoundException;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.Person;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.model.mapper.MapperClient;
import com.devsu.hackerearth.backend.client.model.mapper.MapperPerson;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	private final PersonService personService;
	private final MapperClient mapperClient;
	private final MapperPerson mapperperson;

	@Override
	public List<ClientDto> getAll() {
		// Get all clients

		List<Client> listClients = clientRepository.findAll();
		List<Person> listPesons = personService.getAll();

		List<ClientDto> clientDTOList = mapperClient.mapperClientListToClientDTOList(listClients, listPesons);
		return clientDTOList;
	}

	@Override
	public ClientDto getById(Long id) {

		Person person = personService.getById(id);
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Cliente con Id " + id + " no encontrado"));

		ClientDto clientOut = mapperClient.mapperClientToClientDTO(client, person);
		return clientOut;
	}

	@Transactional
	@Override
	public ClientDto create(ClientDto clientDto) {
		// Create client
		Person person = personService.create(mapperperson.clientDtoTOpPerson(clientDto));

		Client clientIn = mapperClient.mapperClientDtoToClient(clientDto);
		clientIn.setPerson(person);
		Client clientOut = clientRepository.save(clientIn);

		ClientDto clientDtoOut = mapperClient.mapperClientToClientDTO(clientOut, person);
		return clientDtoOut;
	}

	@Override
	public ClientDto update(ClientDto clientDto) {

		ClientDto clientDtoIfExist = getById(clientDto.getId());
		Person person = personService.getById(clientDto.getId());

		// Keep the ids so both saves update the existing rows instead of inserting new ones
		Person personIn = mapperperson.clientDtoTOPersonUpdate(person, clientDto);
		personIn.setId(person.getId());
		Person personOut = personService.create(personIn);

		Client clientIn = mapperClient.mapperClientDtoToClient(clientDto, clientDtoIfExist);
		clientIn.setId(personOut.getId());
		clientIn.setPerson(personOut);

		Client clientOut = clientRepository.saveAndFlush(clientIn);

		ClientDto clientDtoOut = mapperClient.mapperClientToClientDTO(clientOut, personOut);
		return clientDtoOut;

	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		// Partial update account

		ClientDto clientDto = getById(id);
		Person person = personService.getById(id);

		Client clientIn = mapperClient.mapperClientDtoToClientPartial(clientDto, partialClientDto);
		clientIn.setId(person.getId());
		clientIn.setPerson(person);
		Client clientOut = clientRepository.save(clientIn);
		ClientDto clientDtoOut = mapperClient.mapperClientToClientDTO(clientOut, person);

		return clientDtoOut;
	}

	@Override
	public void deleteById(Long id) {
		// Delete client

		getById(id);

		clientRepository.deleteById(id);
	}
}
