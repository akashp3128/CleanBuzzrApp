package edu.iastate.cs.coms309kk03.Buzzr.Appointment;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//This class simply forwards the calls to an implementation of the repository
@Service
@Transactional
public class AppointmentService
{

    @Autowired
    private AppointmentsRepository repo;

    public List<Appointment> listAll() {
        return repo.findAll();
    }

    public void save(Appointment appointment) {
        repo.save(appointment);
    }

    public Appointment get(Integer id) {
        return repo.findById(id).get();
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}