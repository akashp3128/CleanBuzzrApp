package edu.iastate.cs.coms309kk03.Buzzr.BarberProfile;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//This class simply forwards the calls to an implementation of the repository
@Service
@Transactional
public class BarberProfileService
{

    @Autowired
    private BarberProfileRepository repo;

    public List<BarberProfile> listAll() {
        return repo.findAll();
    }

    public void save(BarberProfile barberProfile) {
        repo.save(barberProfile);
    }

    public BarberProfile get(Integer id) {
        return repo.findById(id).get();
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}