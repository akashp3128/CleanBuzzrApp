package edu.iastate.cs.coms309kk03.Buzzr.Appointment;

import javax.persistence.*;
import edu.iastate.cs.coms309kk03.Buzzr.Client.*;
import edu.iastate.cs.coms309kk03.Buzzr.Barber.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@Table(name = "appointments")
public class Appointment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name="barber_id")
    private Barber barber;

    private String date;
    private String time;
    private String style;
    private String price;
    private String location;

    public Appointment()
    {

    }

    public Appointment(Client client, String date, String time, String style, String price, String location)
    {
        this.client = client;
        this.date = date;
        this.time = time;
        this.style = style;
        this.price = price;
        this.location = location;
    }


    public Integer getId() { return id; }

    //date
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    //time
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    //style
    public String getStyle() {
        return style;
    }
    public void setStyle(String style) {
        this.style = style;
    }

    //price
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    //location
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

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
}
