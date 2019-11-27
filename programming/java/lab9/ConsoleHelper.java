package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {
        String text = null;
        try {
            text = reader.readLine();
        }
        catch (IOException e) {
            System.out.println("An error occurred while trying to read text. Try again:");
            return readString();
        }
        return text;
    }

    public static int readInt() {
        int number = 0;
        try {
            number = Integer.parseInt(readString());
        }
        catch (NumberFormatException e) {
            System.out.println("An error occurred while trying to read text. Try again:");
            return readInt();
        }
        return number;
    }
}
