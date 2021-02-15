package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        try {
            if (args.length >= 1) {
                if (args[0].compareTo("-a") == 0) {
                    // Append file with new object from System.in
                    append_file();
                } else if (args[0].compareTo("-p") == 0) {
                    // Prints data file
                    print_file();
                } else if (args[0].compareTo("-d") == 0) {
                    // Delete data file
                    delete_file();
                } else {
                    System.err.println("Option is not realised: " + args[0]);
                    System.exit(1);
                }
            } else {
                System.err.println("Test: Nothing to do!");
            }
        } catch (Exception e) {
            System.err.println("Run/time error: " + e);
            System.exit(1);
        }
        System.out.println("Test finished...");
        System.exit(0);
    }

    static final String filename = "PhoneBull.dat";

    private static Scanner fin = new Scanner(System.in);

    static PhoneBill read_book() {
        if (fin.hasNextLine()) {
            return PhoneBill.read(fin);
        }
        return null;
    }

    static void delete_file() {
        File f = new File(filename);
        f.delete();
    }

    static void append_file() throws FileNotFoundException, IOException {
        PhoneBill bill;
        System.out.println("Enter phone bill data:");
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            while ((bill = read_book()) != null) {
                Buffer.writeObject(raf, bill);
            }
        }
    }

    static void print_file()
            throws FileNotFoundException, IOException, ClassNotFoundException {
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            long pos;
            while ((pos = raf.getFilePointer()) < raf.length()) {
                PhoneBill bill = (PhoneBill) Buffer.readObject(raf, pos);
                System.out.println(pos + ": " + bill);
            }
        }
    }
}

