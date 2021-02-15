package com;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Scanner;

public class PhoneBill implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fullName; // index
    private static final String strFullName = "Full name";
    private String number; // index
    private static final String strNumber = "Number";
    private String date; // index
    private static final String strDate = "Date";
    private String rate;
    private static final String strRate = "Rate";
    private String discount;
    private static final String strDiscount = "Discount";
    private String beginTime;
    private static final String strBeginTime = "Begin time";
    private String endTime;
    private static final String strEndTime = "End time";

    public static final String DEL = ",";

    public PhoneBill() {
    }

    public String getFullName() {
        return fullName;
    }

    public String getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    }

    public String getRate() {
        return rate;
    }

    public String getDiscount() {
        return discount;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    private static boolean validNumber(String str) {
        int n = 0;
        for (int i = 0; i < str.length(); ++i) {
            if (Character.isDigit(str.charAt(i)))
                ++n;
        }
        return ((n == 12 && str.length() == 12) ||
                (n == 12 && str.length() == 13 && str.charAt(0) == '+'));
    }

    static Boolean nextRead(Scanner fin, PrintStream out) {
        return nextRead(strFullName, fin, out);
    }

    private static Boolean nextRead(final String prompt, Scanner fin, PrintStream out) {
        out.print(prompt);
        out.print(": ");
        return fin.hasNextLine();
    }

    public static PhoneBill read(Scanner fin, PrintStream out) throws IOException {
        PhoneBill bill = new PhoneBill();
        bill.fullName = fin.nextLine();
        if (!nextRead(strNumber, fin, out)) return null;
        bill.number = fin.nextLine();
        if (!validNumber(bill.number))
            throw new IOException("Invalid number");
        if (!nextRead(strDate, fin, out)) return null;
        bill.date = fin.nextLine();
        if (!nextRead(strRate, fin, out)) return null;
        bill.rate = fin.nextLine();
        if (!nextRead(strDiscount, fin, out)) return null;
        bill.discount = fin.nextLine();
        if (!nextRead(strBeginTime, fin, out)) return null;
        bill.beginTime = fin.nextLine();
        if (!nextRead(strEndTime, fin, out)) return null;
        bill.endTime = fin.nextLine();
        return bill;
    }

    @Override
    public String toString() {
        return strFullName + ": " + fullName + '\n' +
                strNumber + ": " + number + '\n' +
                strDate + ": " + date + '\n' +
                strRate + ": " + rate + '\n' +
                strDiscount + ": " + discount + '\n' +
                strBeginTime + ": " + beginTime + '\n' +
                strEndTime + ": " + endTime + '\n';
    }
}
