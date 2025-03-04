package edu.iastate.cs.coms309kk03.Buzzr.Barber;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//This class simply forwards the calls to an implementation of the repository
@Service
@Transactional
public class BarberService
{

    @Autowired
    private BarberRepository repo;

    public List<Barber> listAll() {
        return repo.findAll();
    }

    public void save(Barber barber) {
        repo.save(barber);
    }

    public Barber get(Integer id) {
        return repo.findById(id).get();
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}