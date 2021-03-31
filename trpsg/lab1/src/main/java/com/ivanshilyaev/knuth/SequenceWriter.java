package com.ivanshilyaev.knuth;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

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

    public static void writeDoubleRandomNumsToFile(String fileName, double[] randomNums) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (double randomNum : randomNums) {
            writer.append(String.valueOf(randomNum))
                .append(System.lineSeparator());
        }
        writer.close();
    }

    public static void writeRandomNumsToBinFile(String fileName, int[] randomNums) throws IOException {
        DataOutputStream os = new DataOutputStream(new FileOutputStream(fileName));
        for (int randomNum : randomNums) {
            os.writeInt(randomNum);
        }
        os.close();
    }
}
