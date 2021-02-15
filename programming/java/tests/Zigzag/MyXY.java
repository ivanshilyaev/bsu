package com;

public class MyXY {
    private double x;
    private double y;

    public MyXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public static MyXY parseMyXY(String word) {
        if (word.charAt(0) == '(')
            word = word.substring(1);
        if (word.charAt(word.length() - 1) == ')')
            word = word.substring(0, word.length() - 1);
        String[] arr = word.split(":");
        try {
            return new MyXY(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public String toString() {
        return "(" + x + ":" + y + ")";
    }
}
