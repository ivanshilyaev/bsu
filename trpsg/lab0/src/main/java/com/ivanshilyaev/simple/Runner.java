package com.ivanshilyaev.simple;

import java.io.*;

public class Runner {
    private static final String FILE_NAME = "src/main/resources/result.txt";
    private static final String BIN_FILE_NAME = "src/main/resources/result.bin";

    private static void writeRandomNumsToFile(String fileName, int n, int[] randomNums) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (int i = 0; i < n - 1; ++i) {
            writer.append(String.valueOf(randomNums[i]))
                    .append(" ")
                    .append(String.valueOf(randomNums[i + 1]))
                    .append(System.lineSeparator());
        }
        writer.close();
    }

    private static void writeRandomNumsToBinFile(String fileName, int n, int[] randomNums) throws IOException {
        String[] bin = new String[n];
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            bin[i] = Integer.toBinaryString(randomNums[i]);
            maxLen = Math.max(maxLen, bin[i].length());
        }
        ByteArrayOutputStream bout = new ByteArrayOutputStream(n * 4);
        DataOutputStream dataOutputStream = new DataOutputStream(bout);
        for (int i = 0; i < n; i++) {
            if (bin[i].length() != maxLen)
                bin[i] = String.format("%" + maxLen + "s", bin[i]).replace(' ', '0');
            dataOutputStream.writeChars(bin[i]);
        }
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        bout.writeTo(fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    private static void first() throws IOException {
        int seed = 27;
        int mult = 21;
        int inc = 43;
        int m = 100;
        int n = 101;
        int[] randomNums = new int[n];
        Generators.lcg(seed, mult, inc, m, n, randomNums);
        writeRandomNumsToFile(FILE_NAME, n, randomNums);
        writeRandomNumsToBinFile(BIN_FILE_NAME, n, randomNums);
    }

    private static void second() throws IOException {
        int m = (int) (Math.pow(2, 20) + 14 * 10133);
        int seed = 0;
        int mult = 75;
        int inc = 3;
        int n = m + 1;
        int[] randomNums = new int[n];
        Generators.lcg(seed, mult, inc, m, n, randomNums);
        writeRandomNumsToFile(FILE_NAME, n, randomNums);
        writeRandomNumsToBinFile(BIN_FILE_NAME, n, randomNums);
    }

    public static void main(String[] args) throws IOException {
        // 1.
        first();
        // 2.
        second();
    }
}
