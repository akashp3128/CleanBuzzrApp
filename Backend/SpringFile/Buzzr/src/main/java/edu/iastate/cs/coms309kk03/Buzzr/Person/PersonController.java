package edu.iastate.cs.coms309kk03.Buzzr.Person;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

import edu.iastate.cs.coms309kk03.Buzzr.Client.*;

// THIS IS MY SPRING CONTROLLER!
// This is where we come to the part that actually exposes RESTful API's for CRUD operations!
// This reads JSON data from the request and includes JSON data in the response

@RestController
public class PersonController
{

    @Autowired
    private PersonService db;



    // BELOW WE IMPLEMENT RESTFUL API'S FOR EACH CRUD OPERATION

    // GET
    // RESTful API methods for Retrieval operations
    @GetMapping("/persons")
    public List<Person> list()
    {
        return db.listAll();
    }


    //This one uses path variable because it was sent in the path.
    @GetMapping("/person/{id}")
    public ResponseEntity<Person> get(@PathVariable Integer id)
    {
        try
        {
            Person person = db.get(id);
            return new ResponseEntity<Person>(person, HttpStatus.OK);
        } catch (NoSuchElementException e)
        {
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }
    }



    //POST
    // This one uses RequestBody because it is sending it in the body.
    @PostMapping("/person")
    public Person add(@RequestBody Person person)
    {
        db.save(person);


        return person;
    }



    //PUT
    // RESTful API method for Update operation
    @PutMapping("/person/{id}")
    public ResponseEntity<Person> update(@RequestBody Person p, @PathVariable Integer id)
    {
        try
        {
            Person old_person = db.get(id);

            old_person.setName(p.getName());
            old_person.setUsername(p.getUsername());
            old_person.setEmail(p.getEmail());
            old_person.setPhoneNo(p.getPhoneNo());
            old_person.setPassword(p.getPassword());
            old_person.setUserType(p.getUserType());
            db.save(old_person);


            return new ResponseEntity<Person>(old_person, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }
    }



    //DELETE
    // RESTful API method for Delete operation
    @DeleteMapping("/person/{id}")
    public String delete(@PathVariable Integer id)
    {
        db.delete(id);
        return "Deleted " + id;
    }

}