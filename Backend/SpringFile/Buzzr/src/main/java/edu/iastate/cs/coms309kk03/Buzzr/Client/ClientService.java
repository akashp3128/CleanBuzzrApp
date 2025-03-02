package edu.iastate.cs.coms309kk03.Buzzr.Client;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//This class simply forwards the calls to an implementation of the repository
@Service
@Transactional
public class ClientService
{

    @Autowired
    private ClientRepository repo;

    public List<Client> listAll() {
        return repo.findAll();
    }

    public void save(Client client) {
        repo.save(client);
    }

    public Client get(Integer id) {
        return repo.findById(id).get();
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}