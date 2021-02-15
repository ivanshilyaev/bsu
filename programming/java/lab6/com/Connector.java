package com;

import java.io.*;

public class Connector {
    private String filename;

    public Connector(String filename) {
        this.filename = filename;
    }

    public void write(Trip[] trips, Driver[] drivers, Car[] cars) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeInt(trips.length + drivers.length + cars.length);
            for (Trip d : trips) {
                oos.writeObject(d);
            }
            for (Driver d : drivers) {
                oos.writeObject(d);
            }
            for (Car d : cars) {
                oos.writeObject(d);
            }
            oos.flush();
        }
    }

    public Object[] read() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        try (ObjectInputStream oin = new ObjectInputStream(fis)) {
            int length = oin.readInt();
            Object[] objects = new Object[length];
            for (int i = 0; i < length; ++i) {
                objects[i] = oin.readObject();
            }
            return objects;
        }
    }
}
