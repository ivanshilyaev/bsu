package com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// Условие: на вход подаются строки в формате "(x1:y1) (x2:y2) ..."
// Составить ломанную, вычислить её длину. Отсортировать ломанные
// по убыванию длины. В случае неверных данных сообщить,
// в какой строке произошла ошибка.

public class Test {
    public static ArrayList<MyLom> list = new ArrayList<>();

    static void procLine(String line, int num) {
        MyLom o = MyLom.parseMyLom(line, num);
        if (o != null) {
            o.countLength();
            list.add(o);
        }
        else
            System.out.println("Error in line number " + num);
    }

    static void printRes() {
        if (list.isEmpty()) {
            System.out.println("No data!");
            return;
        }
        Collections.sort(list);
        for (MyLom e : list) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = 0;
        System.out.println("Enter strings:");
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isEmpty())
                break;
            procLine(line, num);
            ++num;
        }
        printRes();
        sc.close();
    }
}
