package edu.iastate.cs.coms309kk03.Buzzr.Client;

import java.util.*;

import edu.iastate.cs.coms309kk03.Buzzr.Person.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

// THIS IS MY SPRING CONTROLLER!
// This is where we come to the part that actually exposes RESTful API's for CRUD operations!
// This reads JSON data from the request and includes JSON data in the response

@RestController
public class ClientController
{

    @Autowired
    private ClientService db;

    @Autowired
    private PersonService personDb;


    // BELOW WE IMPLEMENT RESTFUL API'S FOR EACH CRUD OPERATION

    // GET
    // RESTful API methods for Retrieval operations
    @GetMapping("/clients")
    public List<Client> list()
    {
        return db.listAll();
    }


    //This one uses path variable because it was sent in the path.
    @GetMapping("/client/{id}")
    public ResponseEntity<Client> get(@PathVariable Integer id)
    {
        try
        {
            Client client = db.get(id);
            return new ResponseEntity<Client>(client, HttpStatus.OK);
        } catch (NoSuchElementException e)
        {
            return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
        }
    }



    //POST
    // This one uses RequestBody because it is sending it in the body.
    @PostMapping("/client/{person_id}")
    public Client add(@RequestBody Client client, @PathVariable Integer person_id)
    {
        Person p = personDb.get(person_id);
        client.setPerson(p);

        db.save(client);
        return client;
    }



    //PUT
    // RESTful API method for Update operation
    @PutMapping("/client/{id}")
    public ResponseEntity<Client> update(@RequestBody Client c, @PathVariable Integer id)
    {
        try
        {
            Client old_client = db.get(id);
            old_client.setPerson(c.getPerson());
           // old_client.setAppointment(c.getAppointment());
            db.save(old_client);
            return new ResponseEntity<Client>(old_client, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
        }
    }



    //DELETE
    // RESTful API method for Delete operation
    @DeleteMapping("/client/{id}")
    public String delete(@PathVariable Integer id)
    {
        db.delete(id);
        return "Deleted " + id;
    }

}