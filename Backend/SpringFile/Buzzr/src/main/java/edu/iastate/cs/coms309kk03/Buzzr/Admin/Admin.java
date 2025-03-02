package edu.iastate.cs.coms309kk03.Buzzr.Admin;

import java.util.List;


import edu.iastate.cs.coms309kk03.Buzzr.Person.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "admins")
public class Admin implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //one to one
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    @JsonIgnore
    private Person person;


    public Admin()
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


}
