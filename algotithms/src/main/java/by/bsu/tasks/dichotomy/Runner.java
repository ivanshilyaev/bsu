package by.bsu.tasks.dichotomy;

import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        int hiddenNumber = scanner.nextInt();
        int p1 = 1;
        int p2 = num;
        int temp = 0;
        while (p1 != p2) {
            int half = (int) Math.floor((p2 - p1 + 1) / 2);
            int middle = p1 + half;
            if (hiddenNumber >= p1 && hiddenNumber <= middle - 1) {
                p2 = middle - 1;
            } else {
                p1 = middle;
            }
            ++temp;
        }
        System.out.println(p1);
        System.out.println("Number of iterations: " + temp);
    }
}
