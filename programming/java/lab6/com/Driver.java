package com;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class Driver implements Comparable<Driver>, Serializable {
    private static transient int nextID = 1;
    private int ID;
    private String name;
    private int age;
    private Gender gender;
    private int experience;
    private boolean available;

    public final Date creationDate = new Date();

    public String getCreationDate() {
        DateFormat dateFormatter = DateFormat.getDateTimeInstance(
                DateFormat.DEFAULT, DateFormat.DEFAULT, AppLocale.get());
        return dateFormatter.format(creationDate);
    }

    public Driver(String name, int age, Gender gender, int experience) {
        ID = nextID++;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.experience = experience;
        available = true;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public int getExperience() {
        return experience;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return AppLocale.getString(AppLocale.driver) + " - " +
                AppLocale.getString(AppLocale.id) + ": " + ID + ", " +
                AppLocale.getString(AppLocale.name) + ": " + name + ", " +
                AppLocale.getString(AppLocale.age) + ": " + age + ", " +
                AppLocale.getString(AppLocale.gender) + ": " + AppLocale.getString(gender.toString()) + ", " +
                AppLocale.getString(AppLocale.experience) + ": " + experience + ", " +
                AppLocale.getString(AppLocale.creation) + ": " + getCreationDate();
    }

    @Override
    public int compareTo(Driver o) {
        return experience - o.experience;
    }

    public void applicationForRepair(Car car) {
        if (car.isRepairNeeded())
            System.out.println("Car is being repaired already");
        else
            car.setRepairNeeded(true);
    }

    public int getCarAssessment() {
        return 1 + (int) (Math.random() * 5);
    }
}
