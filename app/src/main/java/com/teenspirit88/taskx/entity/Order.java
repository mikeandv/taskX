package com.teenspirit88.taskx.entity;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Order implements Serializable {
    private static final String FORMAT="yyyy-MM-dd'T'HH:mm:ssZ";

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

    public String getFormatedOrderDate(String format) {
        SimpleDateFormat utcForm = new SimpleDateFormat(FORMAT, Locale.ROOT);
        SimpleDateFormat appForm = new SimpleDateFormat(format, Locale.getDefault());

        try{
            Date date = utcForm.parse(orderTime);
            return appForm.format(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getDateAsLong() {
        SimpleDateFormat utcForm = new SimpleDateFormat(FORMAT, Locale.ROOT);
        try {
            Date date = utcForm.parse(orderTime);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
