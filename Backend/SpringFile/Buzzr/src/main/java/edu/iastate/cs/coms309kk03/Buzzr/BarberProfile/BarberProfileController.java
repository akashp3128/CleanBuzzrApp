package edu.iastate.cs.coms309kk03.Buzzr.BarberProfile;

import java.util.*;

import edu.iastate.cs.coms309kk03.Buzzr.Barber.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

// THIS IS MY SPRING CONTROLLER!
// This is where we come to the part that actually exposes RESTful API's for CRUD operations!
// This reads JSON data from the request and includes JSON data in the response

@RestController
public class BarberProfileController {

    @Autowired
    private BarberProfileService db;

    @Autowired
    private BarberService barberDb;


    // BELOW WE IMPLEMENT RESTFUL API'S FOR EACH CRUD OPERATION

    // GET
    // RESTful API methods for Retrieval operations
    @GetMapping("/barberProfiles")
    public List<BarberProfile> list() {
        return db.listAll();
    }


    //This one uses path variable because it was sent in the path.
    @GetMapping("/barberProfile/{id}")
    public ResponseEntity<BarberProfile> get(@PathVariable Integer id) {
        try {
            BarberProfile barberProfile = db.get(id);
            return new ResponseEntity<BarberProfile>(barberProfile, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<BarberProfile>(HttpStatus.NOT_FOUND);
        }
    }


    //POST
    // This one uses RequestBody because it is sending it in the body.
    @PostMapping("/barberProfile/{barber_id}")
    public BarberProfile add(@RequestBody BarberProfile barberProfile, @PathVariable Integer barber_id) {
        Barber b = barberDb.get(barber_id);
        barberProfile.setBarber(b);

        db.save(barberProfile);
        return barberProfile;
    }


    //PUT
    // RESTful API method for Update operation
    @PutMapping("/barberProfile/{barber_id}/{barberProfile_id}")
    public ResponseEntity<BarberProfile> update(@RequestBody BarberProfile b, @PathVariable Integer barber_id, @PathVariable Integer barberProfile_id) {
        try {
            Barber barb = barberDb.get(barber_id);
            BarberProfile old_barberProfile = db.get(barberProfile_id);
            old_barberProfile.setBarber(barb);
            old_barberProfile.setDescription(b.getDescription());
            db.save(old_barberProfile);
            return new ResponseEntity<BarberProfile>(old_barberProfile, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<BarberProfile>(HttpStatus.NOT_FOUND);
        }
    }


    //DELETE
    // RESTful API method for Delete operation
    @DeleteMapping("/barberProfile/{id}")
    public String delete(@PathVariable Integer id) {
        db.delete(id);
        return "Deleted " + id;
    }

}