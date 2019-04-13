package com.teenspirit88.taskx.entity;

import java.io.Serializable;

public class Address implements Serializable {
    private String city;
    private String address;

    public Address(String city, String address) {
        this.city = city;
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
