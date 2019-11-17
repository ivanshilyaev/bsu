package com;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class Trip implements Comparable<Trip>, Serializable {
    private String from;
    private String to;
    private int distance;
    private int cost;

    public final Date creationDate = new Date();
    public String getCreationDate() {
        DateFormat dateFormatter = DateFormat.getDateTimeInstance(
                DateFormat.DEFAULT, DateFormat.DEFAULT, AppLocale.get());
        return dateFormatter.format(creationDate);
    }

    public Trip(String from, String to, int distance, int cost) {
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.cost = cost;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getDistance() {
        return distance;
    }

    public int getCost() {
        return cost;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return AppLocale.getString(AppLocale.trip) + " - " +
                AppLocale.getString(AppLocale.from) + ": " + from + ", " +
                AppLocale.getString(AppLocale.to) + ": " + to + ", " +
                AppLocale.getString(AppLocale.distance) + ": " + distance + ", " +
                AppLocale.getString(AppLocale.cost) + ": " + cost + ", " +
                AppLocale.getString(AppLocale.creation) + ": " + getCreationDate();
    }

    @Override
    public int compareTo(Trip o) {
        return distance - o.distance;
    }
}
