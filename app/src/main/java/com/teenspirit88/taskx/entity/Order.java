package com.teenspirit88.taskx.entity;


import java.io.Serializable;

public class Order implements Serializable {

    private int id;
    private Address startAddress;
    private Address endAddress;
    private Price price;
    private String orderTime;
    private Vehicle vehicle;

    public Order(int id, Address startAddress, Address endAddress, Price price, String orderTime, Vehicle vehicle) {
        this.id = id;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.price = price;
        this.orderTime = orderTime;
        this.vehicle = vehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(Address startAddress) {
        this.startAddress = startAddress;
    }

    public Address getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(Address endAddress) {
        this.endAddress = endAddress;
    }

    public Price getprice() {
        return price;
    }

    public void setprice(Price orderPrice) {
        this.price = orderPrice;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
