package edu.iastate.cs.coms309kk03.Buzzr.Barber;

import java.util.*;

import edu.iastate.cs.coms309kk03.Buzzr.Person.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

// THIS IS MY SPRING CONTROLLER!
// This is where we come to the part that actually exposes RESTful API's for CRUD operations!
// This reads JSON data from the request and includes JSON data in the response

@RestController
public class BarberController
{

    @Autowired
    private BarberService db;

    @Autowired
    private PersonService personDb;


    // BELOW WE IMPLEMENT RESTFUL API'S FOR EACH CRUD OPERATION

    // GET
    // RESTful API methods for Retrieval operations
    @GetMapping("/barbers")
    public List<Barber> list()
    {
        return db.listAll();
    }


    //This one uses path variable because it was sent in the path.
    @GetMapping("/barber/{id}")
    public ResponseEntity<Barber> get(@PathVariable Integer id)
    {
        try
        {
            Barber barber = db.get(id);
            return new ResponseEntity<Barber>(barber, HttpStatus.OK);
        } catch (NoSuchElementException e)
        {
            return new ResponseEntity<Barber>(HttpStatus.NOT_FOUND);
        }
    }



    //POST
    // This one uses RequestBody because it is sending it in the body.
    @PostMapping("/barber/{person_id}")
    public Barber add(@RequestBody Barber barber, @PathVariable Integer person_id)
    {
        Person p = personDb.get(person_id);
        barber.setPerson(p);

        db.save(barber);
        return barber;
    }



    //PUT
    // RESTful API method for Update operation
    @PutMapping("/barber/{id}")
    public ResponseEntity<Barber> update(@RequestBody Barber b, @PathVariable Integer id)
    {
        try
        {
            Barber old_barber = db.get(id);
            old_barber.setPerson(b.getPerson());
            // old_client.setAppointment(c.getAppointment());
            db.save(old_barber);
            return new ResponseEntity<Barber>(old_barber, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Barber>(HttpStatus.NOT_FOUND);
        }
    }



    //DELETE
    // RESTful API method for Delete operation
    @DeleteMapping("/barber/{id}")
    public String delete(@PathVariable Integer id)
    {
        db.delete(id);
        return "Deleted " + id;
    }

}