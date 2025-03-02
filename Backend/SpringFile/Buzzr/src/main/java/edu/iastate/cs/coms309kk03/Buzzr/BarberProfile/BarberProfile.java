package edu.iastate.cs.coms309kk03.Buzzr.BarberProfile;

import java.util.List;


import edu.iastate.cs.coms309kk03.Buzzr.Barber.*;
import edu.iastate.cs.coms309kk03.Buzzr.Review.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.io.Serializable;


@Entity
@Table(name = "barberProfiles")
public class BarberProfile implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //one to one
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "barber_id", nullable = false)
    @JsonIgnore
    private Barber barber;


    private String description;

    //one to many
    @OneToMany(mappedBy = "barberProfile")
    @JsonIgnore
    private List<Review> reviews;


    public BarberProfile()
    {

    }
    public BarberProfile(String description)
    {
        this.description = description;
    }


    //id
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) { this.id = id;
    }

    public Barber getBarber() {
        return barber;
    }
    public void setBarber(Barber barber) {
        this.barber = barber;
    }

    //description
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    //Reviews
    public List<Review> getReviews() {
        return reviews;
    }
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}