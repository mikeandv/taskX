package com.teenspirit88.taskx.entity;

import java.io.Serializable;

public class Vehicle implements Serializable {
    private String regNumber;
    private String modelName;
    private String photo;
    private String driverName;

    public Vehicle(String regNumber, String modelName, String photo, String driverName) {
        this.regNumber = regNumber;
        this.modelName = modelName;
        this.photo = photo;
        this.driverName = driverName;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
