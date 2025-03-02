package edu.iastate.cs.coms309kk03.Buzzr.Review;

import javax.persistence.*;
import edu.iastate.cs.coms309kk03.Buzzr.BarberProfile.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@Table(name = "reviews")
public class Review
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name="barberProfile_id")
    private BarberProfile barberProfile;

    private String comment;
    private String rating;


    public Review()
    {

    }

    public Review(String comment, String rating)
    {
        this.comment = comment;
        this.rating = rating;
    }


    //id
    public Integer getId() { return id; }
    public void setId(Integer id) {
        this.id = id;
    }


    //Barber Profile
    public BarberProfile getBarberProfile() {
        return barberProfile;
    }
    public void setBarberProfile(BarberProfile barberProfile) {
        this.barberProfile = barberProfile;
    }


    //comment
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }


    //rating
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
}
