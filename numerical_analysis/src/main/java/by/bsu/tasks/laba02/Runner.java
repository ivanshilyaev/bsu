package by.bsu.tasks.laba02;

public class Runner {
    private static int N = 4;

    private static double countX(int i) {
        return 1 + 0.33 * i;
    }

    private static double countF(double x) {
        return Math.cosh(x) - x * x * Math.sin(x);
    }

    // method for solving system of linear equations
    public static void solve(double[][] G, double[] a, double[] b, int n) {
        for (int k = 1; k < n; ++k) {
            for (int i = k; i < n; ++i) {
                b[i] -= b[k - 1] * G[i][k - 1] / G[k - 1][k - 1];
                double tmp = G[i][k - 1];
                for (int j = 0; j < n; ++j) {
                    G[i][j] -= G[k - 1][j] * tmp / G[k - 1][k - 1];
                }
            }
        }

        a[n-1] = b[n-1] / G[n-1][n-1];
        for (int i=n-2; i>=0; --i) {
            double sum = 0;
            for (int j=i+1; j<n; ++j)
                sum += G[i][j] * a[j];
            a[i] = (b[i] - sum) / G[i][i];
        }
    }

    private static void printMatrix(double[][] g, int n) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.printf("%.3f ", g[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
//        // n = 1
//        System.out.println("n = 1");
//        double[][] g = {{5}};
//        double sum = 0;
//        for (int i = 0; i < N; ++i) {
//            sum += countF(countX(i));
//        }
//        double[] a = new double[1];
//        double[] b = {sum};
//        System.out.println("Матрица G:");
//        printMatrix(g, 1);
//        System.out.println("Вектор b:");
//        System.out.println(b[0]);
//        solve(g, a, b, 1);
//        sum = 0;
//        for (int i = 0; i < N; ++i) {
//            sum += Math.pow(a[0] - countF(countX(i)), 2);
//        }
//        double t = Math.sqrt(sum);
//        sum = 0;
//        for (int i = 0; i < N; ++i) {
//            sum += Math.pow(countF(countX(i)), 2);
//        }
//        double delta = t * 100 / Math.sqrt(sum);
//        System.out.printf("Q1 = %.3f \n", a[0]);
//        System.out.printf("t = %.3f; ", t); System.out.printf("delta = %.2f%% \n", delta);
//        System.out.println();
//
//        // n = 2
//        System.out.println("n = 2");
//        g = new double[2][2];
//        a = new double[2];
//        b = new double[2];
//        g[0][0] = 5;
//        for (int i = 0; i < N; ++i) {
//            g[0][1] += countX(i);
//        }
//        g[1][0] = g[0][1];
//        for (int i = 0; i < N; ++i) {
//            g[1][1] += Math.pow(countX(i), 2);
//        }
//        for (int i = 0; i < N; ++i) {
//            b[0] += countF(countX(i));
//            b[1] += countF(countX(i)) * countX(i);
//        }
//        System.out.println("Матрица G:");
//        printMatrix(g, 2);
//        System.out.println("Вектор b:");
//        System.out.println(b[0] + " " + b[1]);
//        solve(g, a, b, 2);
//        sum = 0;
//        for (int i = 0; i < N; ++i) {
//            sum += Math.pow(a[0] + a[1] * countX(i)  - countF(countX(i)), 2);
//        }
//        t = Math.sqrt(sum);
//        sum = 0;
//        for (int i = 0; i < N; ++i) {
//            sum += Math.pow(countF(countX(i)), 2);
//        }
//        delta = t * 100 / Math.sqrt(sum);
//        System.out.printf("Q1 = %.3f%.3f*x \n", a[0], a[1]);
//        System.out.printf("t = %.3f; ", t); System.out.printf("delta = %.2f%% \n", delta);
//        System.out.println();
//
//        // n = 3
//        System.out.println("n = 3");
//        g = new double[3][3];
//        a = new double[3];
//        b = new double[3];
//        g[0][0] = 5;
//        for (int i = 0; i < N; ++i) {
//            g[0][1] += countX(i);
//        }
//        g[1][0] = g[0][1];
//        for (int i = 0; i < N; ++i) {
//            g[0][2] += Math.pow(countX(i), 2);
//        }
//        g[1][1] = g[0][2];
//        g[2][0] = g[0][2];
//        for (int i = 0; i < N; ++i) {
//            g[1][2] += Math.pow(countX(i), 3);
//        }
//        g[2][1] = g[1][2];
//        for (int i = 0; i < N; ++i) {
//            g[2][2] += Math.pow(countX(i), 4);
//        }
//        for (int i = 0; i < N; ++i) {
//            b[0] += countF(countX(i));
//            b[1] += countF(countX(i)) * countX(i);
//            b[2] += countF(countX(i)) * countX(i) *  countX(i);
//        }
//        System.out.println("Матрица G:");
//        printMatrix(g, 3);
//        System.out.println("Вектор b:");
//        System.out.println(b[0] + " " + b[1] + " " + b[2]);
//        solve(g, a, b, 3);
//        sum = 0;
//        for (int i = 0; i < N; ++i) {
//            sum += Math.pow(a[0] + (a[1] * countX(i)) +  (a[2] * countX(i) * countX(i))
//                    - countF(countX(i)), 2);
//        }
//        t = Math.sqrt(sum);
//        sum = 0;
//        for (int i = 0; i < N; ++i) {
//            sum += Math.pow(countF(countX(i)), 2);
//        }
//        delta = t * 100 / Math.sqrt(sum);
//        System.out.printf("Q1 = %.3f+%.3f*x%.3f*x^2 \n", a[0], a[1], a[2]);
//        System.out.printf("t = %.3f; ", t); System.out.printf("delta = %.2f%% \n", delta);
//        System.out.println();
//        for (int i = 0; i < N; ++i) {
//            System.out.println(countF(countX(i)));
//        }


        // n = 4
        System.out.println("n = 4");
        double[][] g = new double[4][4];
        double[] a = new double[4];
        double[] b = new double[4];
        g[0][0] = 4;
        for (int i = 0; i < N; ++i) {
            g[0][1] += countX(i);
        }
        g[1][0] = g[0][1];
        for (int i = 0; i < N; ++i) {
            g[0][2] += Math.pow(countX(i), 2);
        }
        g[1][1] = g[0][2];
        g[2][0] = g[0][2];
        for (int i = 0; i < N; ++i) {
            g[0][3] += Math.pow(countX(i), 3);
        }
        g[3][0] = g[0][3];
        g[2][1] = g[0][3];
        g[1][2] = g[0][3];
        for (int i = 0; i < N; ++i) {
            g[1][3] += Math.pow(countX(i), 4);
        }
        g[3][1] = g[1][3];
        g[2][2] = g[1][3];
        for (int i = 0; i < N; ++i) {
            g[2][3] += Math.pow(countX(i), 5);
        }
        g[3][2] = g[2][3];
        for (int i = 0; i < N; ++i) {
            g[3][3] += Math.pow(countX(i), 6);
        }
        for (int i = 0; i < N; ++i) {
            b[0] += countF(countX(i));
            b[1] += countF(countX(i)) * countX(i);
            b[2] += countF(countX(i)) * countX(i) * countX(i);
            b[3] += countF(countX(i)) * countX(i) * countX(i) * countX(i);
        }
        System.out.println("Матрица G:");
        printMatrix(g, 4);
        System.out.println("Вектор b:");
        System.out.println(b[0] + " " + b[1] + " " + b[2] + " " + b[3]);
        solve(g, a, b, 4);
        double sum = 0;
        for (int i = 0; i < N; ++i) {
            sum += Math.pow(a[0] + (a[1] * countX(i)) + (a[2] * countX(i) * countX(i))
                    + (a[3] * countX(i) * countX(i) * countX(i))
                    - countF(countX(i)), 3);
        }
        double t = Math.pow(sum, 0.33);
        sum = 0;
        for (int i = 0; i < N; ++i) {
            sum += Math.pow(countF(countX(i)), 3);
        }
        double delta = t * 100 / Math.pow(sum, 0.33);
        System.out.printf("Q1 = %.3f+%.3f*x+%.3f*x^2+%.3f*x^3 \n", a[0], a[1], a[2], a[3]);
        System.out.printf("t = %.3f; ", t);
        System.out.printf("delta = %.2f%% \n", delta);
        System.out.println();
        for (int i = 0; i < N; ++i) {
            System.out.println(countF(countX(i)));
        }

    }
}
