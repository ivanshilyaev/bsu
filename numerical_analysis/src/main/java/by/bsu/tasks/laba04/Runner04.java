package by.bsu.tasks.laba04;

public class Runner04 {
    private static final double EPS = Math.pow(10, -9);

    private static final double EXACT_RESULT = 1.370762168154;

    private static double f(double x) {
        if (x == 0) return 1;
        if (x == Math.PI / 2) return 2 / Math.PI;
        return Math.sin(x) / x;
    }

    private static double fNast(double t) {
        if (t == -1) return 1;
        return Math.sin(Math.PI / 4 + t * Math.PI / 4) / (1 + t);
    }

    // метод левых прямоугольников
    private static double method1() {
        int iterations = 0;
        int n = 1;
        double y1;
        double h = Math.PI / (2 * n);
        double y2 = h * f(0);
        do {
            ++iterations;
            y1 = y2;
            n *= 2;
            h /= 2;
            y2 = 0;
            for (int i = 1; i <= n; ++i) {
                y2 += h * f((i - 1) * h);
            }
        } while (Math.abs(y2 - y1) > EPS);
        System.out.println("Метод левых прямоугольников:");
        System.out.println("N = " + n);
        System.out.println("h = " + h);
        System.out.println("Число итераций = " + iterations);
        System.out.println("Result = " + y2);
        return y2;
    }

    // метод трапеций
    private static double method2() {
        int iterations = 0;
        int n = 1;
        double y1;
        double h = Math.PI / (2 * n);
        double y2 = h * (f(0) + f(Math.PI / 2)) / 2;
        do {
            ++iterations;
            y1 = y2;
            n *= 2;
            h /= 2;
            y2 = 0;
            for (int i = 1; i <= n; ++i) {
                double f1 = f(h * (i - 1));
                double f2 = f(h * i);
                y2 += h * (f1 + f2) / 2;
            }
        } while (Math.abs(y2 - y1) > EPS);
        System.out.println("Метод трапеций:");
        System.out.println("N = " + n);
        System.out.println("h = " + h);
        System.out.println("Число итераций = " + iterations);
        System.out.println("Result = " + y2);
        return y2;
    }

    // метод Симпсона
    private static double method3() {
        int iterations = 0;
        int n = 1;
        double y1;
        double h = Math.PI / (2 * n);
        double y2 = h * (f(0) + 4 * f(Math.PI / 4) + f(Math.PI / 2)) / 6;
        do {
            ++iterations;
            y1 = y2;
            n *= 2;
            h /= 2;
            y2 = 0;
            for (int i = 1; i <= n; ++i) {
                double x1 = h * (i - 1);
                double x3 = h * i;
                double x2 = (x1 + x3) / 2;
                y2 += h * (f(x1) + 4 * f(x2) + f(x3)) / 6;
            }
        } while (Math.abs(y2 - y1) > EPS);
        System.out.println("Метод Симпсона:");
        System.out.println("N = " + n);
        System.out.println("h = " + h);
        System.out.println("Число итераций = " + iterations);
        System.out.println("Result = " + y2);
        System.out.println();
        return y2;
    }

    private static double nast0() {
        return 2 * fNast(0);
    }

    private static double nast2() {
        double result = 0;
        double[] A = {5.0 / 9, 8.0 / 9, 5.0 / 9};
        double[] x = {-Math.sqrt(15) / 5, 0, Math.sqrt(15) / 5};
        for (int i = 0; i < 3; ++i) {
            result += A[i] * fNast(x[i]);
        }
        return result;
    }

    private static double nast4() {
        double result = 0;
        double[] A = {0.236927, 0.478629, 128.0 / 225, 0.478629, 0.236927};
        double[] x = {-Math.sqrt(245 + 14 * Math.sqrt(70)) / 21,
                -Math.sqrt(245 - 14 * Math.sqrt(70)) / 21,
                0,
                Math.sqrt(245 - 14 * Math.sqrt(70)) / 21,
                Math.sqrt(245 + 14 * Math.sqrt(70)) / 21
        };
        for (int i = 0; i < 5; ++i) {
            result += A[i] * fNast(x[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        double result1 = method1();
        double result2 = method2();
        double result3 = method3();
        double result4 = nast0();
        double result5 = nast2();
        double result6 = nast4();
        System.out.println("КФ НАСТ:");
        System.out.println("n = 0, result = " + result4);
        System.out.println("n = 2, result = " + result5);
        System.out.println("n = 4, result = " + result6);
        System.out.println();
        System.out.println("КФ левых прямоугольник - точный результат = " + Math.abs(EXACT_RESULT - result1));
        System.out.println("КФ трапеций - точный результат = " + Math.abs(EXACT_RESULT - result2));
        System.out.println("КФ Симпсона - точный результат = " + Math.abs(EXACT_RESULT - result3));
        System.out.println("КФ НАСТ при n=0 - точный результат = " + Math.abs(EXACT_RESULT - result4));
        System.out.println("КФ НАСТ при n=2 - точный результат = " + Math.abs(EXACT_RESULT - result5));
        System.out.println("КФ НАСТ при n=4 - точный результат = " + Math.abs(EXACT_RESULT - result6));

    }
}
