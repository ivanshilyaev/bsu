package com;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

public class Dispatcher implements Serializable {
    private static transient int nextID = 1;
    private int ID;
    private String name;

    public Dispatcher(String name) {
        ID = nextID++;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dispatcher{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                '}';
    }

    public void distribute(int n, Trip[] trips, Driver[] drivers, Car[] cars) throws IOException {
        Arrays.sort(trips);
        Arrays.sort(drivers);
        Arrays.sort(cars);
        for (int i = 0; i < n; ++i) {
            System.out.println(drivers[i].toString());
            System.out.println(cars[i].toString());
            System.out.println(trips[i].toString());

            // "Водитель делает отметку о состоянии автомобиля"
            cars[i].recalculateCondition(drivers[i].getCarAssessment());
            System.out.println();
        }
    }

    public void suspend(Driver driver) {
        if (!driver.isAvailable())
            System.out.println("Driver is not available already");
        else
            driver.setAvailable(false);
    }
}
