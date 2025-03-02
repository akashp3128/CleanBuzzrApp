package com.example.buzzrfrontend.data.model;

/**
 *
 *
 * Class to handle clientside information of a service (such as a barber)
 */
public class ServiceData extends UserData{
    point location;
    public ServiceData(int id, String name, String userName, String email, String phoneNumber)
    {
        super(id, name, userName, email, phoneNumber, UserType.barber);
        //@TODO add locational parameters.
    }
    public point getLocation() {
        return location;
        //@TODO figure out how locational information should be stored.
    }
    public void setLocation(point location) {
        this.location = location;
        //@TODO figure out how locational information should be stored.
        this.location = location;
    }
}
