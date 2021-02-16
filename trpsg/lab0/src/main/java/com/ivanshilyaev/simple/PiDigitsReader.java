package com.ivanshilyaev.simple;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PiDigitsReader {

    public void execute(String fileName, int n, int[] nums) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        for (int i = 0; i < n - 1; i += 2) {
            int firstDigit = Character.getNumericValue((char) reader.read());
            int secondDigit = Character.getNumericValue((char) reader.read());
            StringBuilder line1 = new StringBuilder(Integer.toBinaryString(firstDigit));
            while (line1.length() < 4) {
                line1.insert(0, "0");
            }
            StringBuilder line2 = new StringBuilder(Integer.toBinaryString(firstDigit));
            while (line2.length() < 4) {
                line2.insert(0, "0");
            }
            line1.append(line2);
            int number = Integer.parseInt(line1.toString(), 2);
            nums[i / 2] = number;
        }
    }
}
