package edu.iastate.cs.coms309kk03.Buzzr.Person;

import edu.iastate.cs.coms309kk03.Buzzr.Barber.*;
import edu.iastate.cs.coms309kk03.Buzzr.Client.*;
import edu.iastate.cs.coms309kk03.Buzzr.Admin.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;


import java.io.Serializable;



//import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@Table(name = "persons")
public class Person implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @OneToOne(  fetch = FetchType.LAZY,
                cascade = CascadeType.ALL,
                mappedBy="person")
    private Client client;

    @OneToOne(  fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy="person")
    private Barber barber;

    @OneToOne(  fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy="person")
    private Admin admin;


    public String name;
    public String username;
    public String email;
    public String phonenumber;
    public String password;
    public String userType;

    public Person()
    {

    }
    public Person(String name, String username, String email, String phonenumber, String password, String userType)
    {
        this.name = name;
        this.username = username;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
        this.userType = userType;

    }



    public Integer getId() { return id; }


    //Client
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }


    //Barber
    public Barber getBarber() {
        return barber;
    }
    public void setBarber(Barber barber) {
        this.barber = barber;
    }


    //Name
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }



    //Username
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }



    //Email
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }



    //PhoneNo
    public String getPhoneNo()
    {
        return phonenumber;
    }

    public void setPhoneNo(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }



    //Password
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password) { this.password = password; }


    //Password
    public String getUserType()
    {
        return userType;
    }

    public void setUserType(String userType) { this.userType = userType; }



}
