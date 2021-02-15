package com;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
//import java.util.Enumeration;
import java.util.Locale;

public class Solution {
    public static final int N = 3;

    static Locale createLocale(String[] args) {
        if (args.length == 2) {
            return new Locale(args[0], args[1]);
        } else if (args.length == 4) {
            return new Locale(args[2], args[3]);
        }
        return null;
    }

    static void setupConsole(String[] args) {
        if (args.length >= 2) {
            if (args[0].compareTo("-encoding") == 0) {
                try {
                    System.setOut(new PrintStream(System.out, true, args[1]));
                } catch (UnsupportedEncodingException ex) {
                    System.err.println("Unsupported encoding: " + args[1]);
                    System.exit(1);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            setupConsole(args);
            Locale loc = createLocale(args);
            if (loc == null) {
                System.err.println(
                        "Invalid argument(s)\n" +
                                "Syntax: [-encoding ENCODING_ID] language country\n" +
                                "Example: -encoding Cp855 be BY");
                System.exit(1);
            }
            AppLocale.set(loc);

//            Enumeration<String> e = AppLocale.getBundle().getKeys();
//            while (e.hasMoreElements())
//                System.out.println(e.nextElement());

            Trip[] trips = {
                    new Trip(AppLocale.getString(AppLocale.home), AppLocale.getString(AppLocale.work), 5, 5),
                    new Trip(AppLocale.getString(AppLocale.Minsk), AppLocale.getString(AppLocale.Gomel), 300, 150),
                    new Trip(AppLocale.getString(AppLocale.university), AppLocale.getString(AppLocale.club), 10, 15)
            };
            Driver[] drivers = {
                    new Driver(AppLocale.getString(AppLocale.Pavel), 18, Gender.MALE, 0),
                    new Driver(AppLocale.getString(AppLocale.Danil), 19, Gender.MALE, 2),
                    new Driver(AppLocale.getString(AppLocale.Ivan), 18, Gender.MALE, 1)
            };
            Car[] cars = {
                    new Car(AppLocale.getString(AppLocale.Nissan), AppLocale.getString(AppLocale.white), 2019, 50000),
                    new Car(AppLocale.getString(AppLocale.Porsche), AppLocale.getString(AppLocale.yellow), 2015, 150000),
                    new Car(AppLocale.getString(AppLocale.BMW), AppLocale.getString(AppLocale.black), 1989, 3000),
            };
            Dispatcher dispatcher = new Dispatcher("Ilya");
            System.out.println("---Dispatcher suspends trips between drivers and assigns cars---");
            System.out.println("---Driver with less experience gets a cheaper car and a trip with less distance---");
            dispatcher.distribute(N, trips, drivers, cars);
            dispatcher.suspend(drivers[0]);
            drivers[0].applicationForRepair(cars[0]);

            Connector connector = new Connector("band.dat");
            connector.write(trips, drivers, cars);
            System.out.println("---Serialization is here---\n");
            Object[] objects = connector.read();
            int i = 0;
            Trip[] ourOldTrips = new Trip[objects.length / 3];
            for (; i < objects.length / 3; ++i)
                ourOldTrips[i] = (Trip) objects[i];
            Driver[] ourOldDrivers = new Driver[objects.length / 3];
            for (; i < 2 * objects.length / 3; ++i)
                ourOldDrivers[i - 3] = (Driver) objects[i];
            Car[] ourOldCars = new Car[objects.length / 3];
            for (; i < objects.length; ++i)
                ourOldCars[i - 6] = (Car) objects[i];

            System.out.println("---Our old trips (after serialization)---");
            for (Trip t : ourOldTrips)
                System.out.println(t.toString());
            System.out.println("---Our old drivers (after serialization)---");
            for (Driver d : ourOldDrivers)
                System.out.println(d.toString());
            System.out.println("---Our old cars (after serialization)---");
            for (Car c : ourOldCars)
                System.out.println(c.toString());
        } catch (ClassNotFoundException e) {
            e.getMessage();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
