package com.jwolodzko.car_rental.client;

import com.jwolodzko.car_rental.client.dto.ClientRequest;
import com.jwolodzko.car_rental.client.dto.ClientResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientRepository clientRepository;

    public ClientController(ClientService clientService, ClientRepository clientRepository) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    @PostMapping
    public void createClient(@RequestBody ClientRequest clientRequest) {
        clientService.createClient(clientRequest);
    }

    @GetMapping
    public List<ClientResponse> getClients() {
        return clientRepository.findAll().stream().map(ClientResponse::new).toList();
    }
}
