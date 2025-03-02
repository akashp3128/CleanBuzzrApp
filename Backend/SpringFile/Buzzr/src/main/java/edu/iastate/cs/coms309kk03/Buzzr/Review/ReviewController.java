package edu.iastate.cs.coms309kk03.Buzzr.Review;

import java.util.*;

import  edu.iastate.cs.coms309kk03.Buzzr.BarberProfile.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

// THIS IS MY SPRING CONTROLLER!
// This is where we come to the part that actually exposes RESTful API's for CRUD operations!
// This reads JSON data from the request and includes JSON data in the response

@RestController
public class ReviewController
{
    @Autowired
    private ReviewService db;

    @Autowired
    private BarberProfileService barberProfileDb;

    // BELOW WE IMPLEMENT RESTFUL API'S FOR EACH CRUD OPERATION

    // GET
    // RESTful API methods for Retrieval operations
    @GetMapping("/reviews")
    public List<Review> list() {
        return db.listAll();
    }


    //This one uses path variable because it was sent in the path.
    @GetMapping("/review/{id}")
    public ResponseEntity<Review> get(@PathVariable Integer id) {
        try {
            Review review = db.get(id);
            return new ResponseEntity<Review>(review, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Review>(HttpStatus.NOT_FOUND);
        }
    }


    //POST
    // This one uses RequestBody because it is sending it in the body.
    @PostMapping("/review/{barberProfile_id}")
    public Review add(@RequestBody Review review, @PathVariable Integer barberProfile_id) {

        BarberProfile b = barberProfileDb.get(barberProfile_id);
        review.setBarberProfile(b);

        db.save(review);
        return review;
    }


    //PUT
    // RESTful API method for Update operation
    @PutMapping("/review/{id}")
    public ResponseEntity<Review> update(@RequestBody Review r, @PathVariable Integer id) {
        try {
            Review old_review = db.get(id);
            old_review.setRating(r.getRating());
            old_review.setComment(r.getComment());
            db.save(old_review);
            return new ResponseEntity<Review>(old_review, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Review>(HttpStatus.NOT_FOUND);
        }
    }


    //DELETE
    // RESTful API method for Delete operation
    @DeleteMapping("/review/{id}")
    public String delete(@PathVariable Integer id) {
        db.delete(id);
        return "Deleted " + id;
    }

}