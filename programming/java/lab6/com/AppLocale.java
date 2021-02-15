package com;

import java.util.*;

public class AppLocale {
    private static final String strMsg = "Msg";
    private static Locale loc = Locale.getDefault();
    private static ResourceBundle res =
            ResourceBundle.getBundle(AppLocale.strMsg, AppLocale.loc);

    static Locale get() {
        return AppLocale.loc;
    }

    static void set(Locale loc) {
        AppLocale.loc = loc;
        res = ResourceBundle.getBundle(AppLocale.strMsg, AppLocale.loc);
    }

    static ResourceBundle getBundle() {
        return AppLocale.res;
    }

    static String getString(String key) {
        return AppLocale.res.getString(key);
    }

    // Resource keys:

    public static final String car = "car";
    public static final String id = "id";
    public static final String creation = "creation";
    public static final String model = "model";
    public static final String color = "color";
    public static final String yearOfManufacture = "yearOfManufacture";
    public static final String price = "price";

    public static final String dispatcher = "dispatcher";

    public static final String driver = "driver";
    public static final String name = "name";
    public static final String age = "age";
    public static final String gender = "gender";
    public static final String experience = "experience";

    public static final String trip = "trip";
    public static final String from = "from";
    public static final String to = "to";
    public static final String distance = "distance";
    public static final String cost = "cost";

    public static final String home = "home";
    public static final String work = "work";
    public static final String Minsk = "Minsk";
    public static final String Gomel = "Gomel";
    public static final String university = "university";
    public static final String club = "club";

    public static final String Pavel = "Pavel";
    public static final String Danil = "Danil";
    public static final String Ivan = "Ivan";
    public static final String MALE = "MALE";
    public static final String FEMALE = "FEMALE";

    public static final String Nissan = "Nissan";
    public static final String Porsche = "Porsche";
    public static final String BMW = "BMW";
    public static final String white = "white";
    public static final String yellow = "yellow";
    public static final String black = "black";
}
