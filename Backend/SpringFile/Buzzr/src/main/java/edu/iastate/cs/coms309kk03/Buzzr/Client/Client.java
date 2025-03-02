package edu.iastate.cs.coms309kk03.Buzzr.Client;

import java.util.List;


import edu.iastate.cs.coms309kk03.Buzzr.Person.*;
import edu.iastate.cs.coms309kk03.Buzzr.Appointment.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;




//import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@Table(name = "clients")
public class Client implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //one to one
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    @JsonIgnore
    private Person person;
    //    @RestResource(path = "clientPerson", rel="person")



    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Appointment> appointments;



    public Client()
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
}