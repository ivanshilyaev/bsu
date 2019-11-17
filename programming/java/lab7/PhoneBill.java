package com;

import java.io.Serializable;
import java.util.Scanner;

public class PhoneBill implements Serializable {
    private String fullName;
    private String number;
    private String date;
    private String rate;
    private String discount;
    private String beginTime;
    private String endTime;

    public static PhoneBill read(Scanner fin) {
        PhoneBill bill = new PhoneBill();
        bill.fullName = fin.nextLine();
        if (!fin.hasNextLine()) return null;
        bill.number = fin.nextLine();
        if (!fin.hasNextLine()) return null;
        bill.date = fin.nextLine();
        if (!fin.hasNextLine()) return null;
        bill.rate = fin.nextLine();
        if (!fin.hasNextLine()) return null;
        bill.discount = fin.nextLine();
        if (!fin.hasNextLine()) return null;
        bill.beginTime = fin.nextLine();
        if (!fin.hasNextLine()) return null;
        bill.endTime = fin.nextLine();
        return bill;
    }

    @Override
    public String toString() {
        return "PhoneBill{" +
                "fullName='" + fullName + '\'' +
                ", number='" + number + '\'' +
                ", date='" + date + '\'' +
                ", rate='" + rate + '\'' +
                ", discount='" + discount + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
