package com.andrewmccall.jamboree;


import com.sun.istack.internal.NotNull;

import javax.persistence.*;

public class Person {


    long id;

    @NotNull
    String firstname;
    @NotNull
    String lastname;
    @NotNull
    String phone;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
