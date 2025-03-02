package edu.iastate.cs.coms309kk03.Buzzr.Admin;

import java.util.*;

import edu.iastate.cs.coms309kk03.Buzzr.Person.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

// THIS IS MY SPRING CONTROLLER!
// This is where we come to the part that actually exposes RESTful API's for CRUD operations!
// This reads JSON data from the request and includes JSON data in the response

@RestController
public class AdminController {

    @Autowired
    private AdminService db;

    @Autowired
    private PersonService personDb;


    // BELOW WE IMPLEMENT RESTFUL API'S FOR EACH CRUD OPERATION

    // GET
    // RESTful API methods for Retrieval operations
    @GetMapping("/admins")
    public List<Admin> list() {
        return db.listAll();
    }


    //This one uses path variable because it was sent in the path.
    @GetMapping("/admin/{id}")
    public ResponseEntity<Admin> get(@PathVariable Integer id) {
        try {
            Admin admin = db.get(id);
            return new ResponseEntity<Admin>(admin, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Admin>(HttpStatus.NOT_FOUND);
        }
    }


    //POST
    // This one uses RequestBody because it is sending it in the body.
    @PostMapping("/admin/{person_id}")
    public Admin add(@RequestBody Admin admin, @PathVariable Integer person_id) {
        Person p = personDb.get(person_id);
        admin.setPerson(p);

        db.save(admin);
        return admin;
    }


    //PUT
    // RESTful API method for Update operation
    @PutMapping("/admin/{id}")
    public ResponseEntity<Admin> update(@RequestBody Admin a, @PathVariable Integer id) {
        try {
            Admin old_admin = db.get(id);
            old_admin.setPerson(a.getPerson());
            // old_client.setAppointment(c.getAppointment());
            db.save(old_admin);
            return new ResponseEntity<Admin>(old_admin, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Admin>(HttpStatus.NOT_FOUND);
        }
    }


    //DELETE
    // RESTful API method for Delete operation
    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable Integer id) {
        db.delete(id);
        return "Deleted " + id;
    }

}

