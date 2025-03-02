package edu.iastate.cs.coms309kk03.Buzzr.Barber;

import java.util.List;


import edu.iastate.cs.coms309kk03.Buzzr.Person.*;
import edu.iastate.cs.coms309kk03.Buzzr.Appointment.*;
import edu.iastate.cs.coms309kk03.Buzzr.BarberProfile.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "barbers")
public class Barber implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //one to one
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    @JsonIgnore
    private Person person;


    //one to many
    @OneToMany(mappedBy = "barber")
    @JsonIgnore
    private List<Appointment> appointments;


    //one to one
    @OneToOne(  fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy="barber")
    private BarberProfile barberProfile;


    public Barber()
    {

    }



    //id
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }


    //person
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }


    //appointments
    public List<Appointment> getAppointments() {
        return appointments;
    }
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }


    //BarberProfile
    public BarberProfile getBarberProfile() {
        return barberProfile;
    }
    public void setBarberProfile(BarberProfile barberProfile) {
        this.barberProfile = barberProfile;
    }
}
