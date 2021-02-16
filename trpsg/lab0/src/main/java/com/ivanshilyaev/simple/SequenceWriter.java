package com.ivanshilyaev.simple;

import java.io.*;

public class SequenceWriter {

    public static void writeRandomNumsToFile(String fileName, int n, int[] randomNums) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (int i = 0; i < n - 1; ++i) {
            writer.append(String.valueOf(randomNums[i]))
                    .append(" ")
                    .append(String.valueOf(randomNums[i + 1]))
                    .append(System.lineSeparator());
        }
        writer.close();
    }

    public static void writeRandomNumsToBinFile(String fileName, int n, int[] randomNums) throws IOException {
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
}
