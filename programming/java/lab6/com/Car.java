package com;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class Car implements Comparable<Car>, Serializable {
    private static transient int nextID = 1;
    private int ID;
    private String model;
    private String color;
    private int yearOfManufacture;
    private int price;
    private boolean isRepairNeeded;
    private int condition;
    private int numberOfAssessments;

    public final Date creationDate = new Date();
    public String getCreationDate() {
        DateFormat dateFormatter = DateFormat.getDateTimeInstance(
                DateFormat.DEFAULT, DateFormat.DEFAULT, AppLocale.get());
        return dateFormatter.format(creationDate);
    }

    public Car(String model, String color, int yearOfManufacture, int price) {
        ID = nextID++;
        this.model = model;
        this.color = color;
        this.yearOfManufacture = yearOfManufacture;
        this.price = price;
        isRepairNeeded = false;
        condition = 5;
        numberOfAssessments = 0;
    }

    public int getID() {
        return ID;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    public int getPrice() {
        return price;
    }

    public boolean isRepairNeeded() {
        return isRepairNeeded;
    }

    public void setRepairNeeded(boolean repairNeeded) {
        isRepairNeeded = repairNeeded;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setYearOfManufacture(int yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return AppLocale.getString(AppLocale.car) + " - " +
                AppLocale.getString(AppLocale.id) + ": " + ID + ", " +
                AppLocale.getString(AppLocale.model) + ": " + model + ", " +
                AppLocale.getString(AppLocale.color) + ": " + color + ", " +
                AppLocale.getString(AppLocale.yearOfManufacture) + ": " + yearOfManufacture + ", " +
                AppLocale.getString(AppLocale.price) + ": " + price + ", " +
                AppLocale.getString(AppLocale.creation) + ": " + getCreationDate();
    }

    @Override
    public int compareTo(Car o) {
        return price - o.price;
    }

    public void recalculateCondition(int c) throws IOException {
        if (c<1 || c>5)
            throw new IOException();
        else {
            condition = (condition * numberOfAssessments + c) / ++numberOfAssessments;
        }
    }
}
