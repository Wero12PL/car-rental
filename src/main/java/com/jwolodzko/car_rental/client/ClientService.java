package com.jwolodzko.car_rental.client;

import com.jwolodzko.car_rental.client.dto.ClientRequest;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void createClient(ClientRequest clientRequest) {
        Client client = new Client();
        client.setName(clientRequest.name());
        client.setSurname(clientRequest.surname());
        client.setAddress(clientRequest.address());
        client.setBirthDate(clientRequest.birthDate());
        client.setEmail(clientRequest.email());
        client.setPhoneNumber(clientRequest.phoneNumber());
        client.setCompany(clientRequest.company());

        clientRepository.save(client);
    }
}
