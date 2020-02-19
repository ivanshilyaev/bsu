package by.bsu.tasks.laba01;

public class Runner {
    static final double X0 = 1.5;
    static final double DELTA = 0.5;
    static final double Q = (double) 4 / 17;
    static final double EPS = Math.pow(10, -9);
    static final String DEL = "==================";
    static final double SOLUTION = 1.264591571;

    static double phi(double x) {
        return Math.atan(4 / x);
    }

    static double f(double x) {
        return -(x / 4) + (1 / Math.tan(x));
    }

    static double f1(double x) {
        return -(1 / Math.pow(Math.sin(x), 2)) - 0.25;
    }

    public static void main(String[] args) {
        // 1. МПИ
        System.out.println("1. Метод простых итераций:");
        System.out.print("Априорное число итераций: ");
        System.out.println(Math.round(Math.log(EPS / DELTA) / Math.log(Q)) + 1);
        double x1 = X0;
        double x2 = phi(x1);
        int k = 1;
        while (Math.abs(x2 - x1) >= EPS) {
            x1 = x2;
            x2 = phi(x1);
            ++k;
        }
        System.out.println("Апостериорное число итераций: " + k);
        System.out.printf("Полученное решение: %.9f", x2);
        System.out.println();
        System.out.println(DEL);

        // 2. МН
        System.out.println("2. Метод Ньютона:");
        x1 = X0;
        x2 = x1 - f(x1) / f1(x1);
        k = 1;
        while (Math.abs(x2 - x1) >= EPS) {
            x1 = x2;
            x2 = x1 - f(x1) / f1(x1);
            ++k;
        }
        System.out.println("Апостериорное число итераций: " + k);
        System.out.printf("Полученное решение: %.9f", x2);
        System.out.println();
        System.out.println(DEL);
        System.out.printf("Точное решение: %.9f", SOLUTION);
    }
}
