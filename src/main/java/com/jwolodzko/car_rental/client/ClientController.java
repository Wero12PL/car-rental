package com.jwolodzko.car_rental.client;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/clients")
public class ClientController {

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostMapping
    public void createClient(@RequestBody Client client) {
        clientRepository.save(client);
    }

    @GetMapping
    public List<Client> getClients() {
        return clientRepository.findAll();
    }
}
