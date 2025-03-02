package edu.iastate.cs.coms309kk03.Buzzr.Review;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//This class simply forwards the calls to an implementation of the repository
@Service
@Transactional
public class ReviewService
{

    @Autowired
    private ReviewRepository repo;

    public List<Review> listAll() {
        return repo.findAll();
    }

    public void save(Review review) {
        repo.save(review);
    }

    public Review get(Integer id) {
        return repo.findById(id).get();
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}