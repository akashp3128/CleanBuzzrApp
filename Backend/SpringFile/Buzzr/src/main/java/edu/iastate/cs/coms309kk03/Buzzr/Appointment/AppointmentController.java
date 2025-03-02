package edu.iastate.cs.coms309kk03.Buzzr.Appointment;

import java.util.*;

import  edu.iastate.cs.coms309kk03.Buzzr.Client.*;
import  edu.iastate.cs.coms309kk03.Buzzr.Barber.*;


import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

// THIS IS MY SPRING CONTROLLER!
// This is where we come to the part that actually exposes RESTful API's for CRUD operations!
// This reads JSON data from the request and includes JSON data in the response

@RestController
public class AppointmentController {

    @Autowired
    private AppointmentService db;

    @Autowired
    private ClientService clientDb;

    @Autowired
    private BarberService barberDb;

    // BELOW WE IMPLEMENT RESTFUL API'S FOR EACH CRUD OPERATION

    // GET
    // RESTful API methods for Retrieval operations
    @GetMapping("/appointments")
    public List<Appointment> list() {
        return db.listAll();
    }


    //This one uses path variable because it was sent in the path.
    @GetMapping("/appointment/{id}")
    public ResponseEntity<Appointment> get(@PathVariable Integer id) {
        try {
            Appointment appointment = db.get(id);
            return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Appointment>(HttpStatus.NOT_FOUND);
        }
    }


    //POST
    // This one uses RequestBody because it is sending it in the body.
    @PostMapping("/appointment/{client_id}/{barber_id}")
    public Appointment add(@RequestBody Appointment appointment, @PathVariable Integer client_id, @PathVariable Integer barber_id)
    {
        Client c = clientDb.get(client_id);
        appointment.setClient(c);

        Barber b = barberDb.get(barber_id);
        appointment.setBarber(b);

        db.save(appointment);
        return appointment;
    }


    //PUT
    // RESTful API method for Update operation
    @PutMapping("/appointment/{id}")
    public ResponseEntity<Appointment> update(@RequestBody Appointment a, @PathVariable Integer id) {
        try {
            Appointment old_appointment = db.get(id);
            old_appointment.setDate(a.getDate());
            old_appointment.setTime(a.getTime());
            old_appointment.setStyle(a.getStyle());
            old_appointment.setPrice(a.getPrice());
            old_appointment.setLocation(a.getLocation());
            db.save(old_appointment);
            return new ResponseEntity<Appointment>(old_appointment, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Appointment>(HttpStatus.NOT_FOUND);
        }
    }


    //DELETE
    // RESTful API method for Delete operation
    @DeleteMapping("/appointment/{id}")
    public String delete(@PathVariable Integer id) {
        db.delete(id);
        return "Deleted " + id;
    }

}