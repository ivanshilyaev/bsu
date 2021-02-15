package com;

public class Solution {
    private static final int SIZE = 9;
    private static double[][] A = new double[SIZE][SIZE];
    private static double[][] E = new double[SIZE][SIZE];
    public static double[][] A_INVERSE = new double[SIZE][SIZE];
    private static double[][] A_TRANSPOSED = new double[SIZE][SIZE];
    private static double[] x = new double[SIZE];
    private static double[] myX = new double[SIZE];
    private static double[] f = new double[SIZE];
    private static boolean isDetZero = false;

    private static final double EPS = 0.0000001;

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static void printMatrix2(double[][] A) {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                System.out.printf("%10.2f ", A[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printMatrix4(double[][] A) {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                System.out.printf("%13.4f ", A[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printVector2(double[] x) {
        for (int i = 0; i < SIZE; ++i) {
            System.out.printf("%10.2f\n", x[i]);
        }
        System.out.println("\n");
    }

    public static void printVector10(double[] x) {
        for (int i = 0; i < SIZE; ++i) {
            System.out.printf("%15.10f\n", x[i]);
        }
        System.out.println("\n");
    }

    public static void printVector12(double[] x) {
        for (int i = 0; i < SIZE; ++i) {
            System.out.printf("%17.12f\n", x[i]);
        }
        System.out.println("\n");
    }

    public static void generateMatrix(double[][] A) {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = i; j < SIZE; ++j) {
                A[i][j] = round(-99 + Math.random() * 199, 2);
                A[j][i] = A[i][j];
            }
        }
    }

    public static void generateE() {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                E[i][j] = 0;
            }
        }
        for (int i = 0; i < SIZE; ++i)
            E[i][i] = 1;
    }

    public static void generateX(double[] x) {
        for (int i = 0; i < SIZE; ++i) {
            x[i] = round(-99 + Math.random() * 199, 10);
        }
    }

    public static void countF(double[][] A, double[] x) {
        for (int i = 0; i < SIZE; ++i) {
            f[i] = 0;
        }
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                f[i] += A[i][j] * x[j];
            }
        }
    }

    // решение по методу Гаусса без выбора главного элемента
    public static void solve(double[][] myA, double[] myX, double[] f) {
        double[][] A = new double[SIZE][SIZE];
        for (int i = 0; i < SIZE; ++i) {
            System.arraycopy(myA[i], 0, A[i], 0, SIZE);
        }

        for (int k = 1; k < SIZE; ++k) {
            for (int i = k; i < SIZE; ++i) {
                f[i] -= f[k - 1] * A[i][k - 1] / A[k - 1][k - 1];
                double tmp = A[i][k - 1];
                for (int j = 0; j < SIZE; ++j) {
                    A[i][j] -= A[k - 1][j] * tmp / A[k - 1][k - 1];
                }
            }
        }

        myX[SIZE - 1] = f[SIZE - 1] / A[SIZE - 1][SIZE - 1];
        for (int i = SIZE - 2; i >= 0; --i) {
            double sum = 0;
            for (int j = i + 1; j < SIZE; ++j)
                sum += A[i][j] * myX[j];
            myX[i] = (f[i] - sum) / A[i][i];
        }
    }

    public static double det(double[][] myA) {
        double det = 1;
        double[][] A = new double[SIZE][SIZE];
        for (int i = 0; i < SIZE; ++i) {
            System.arraycopy(myA[i], 0, A[i], 0, SIZE);
        }
        for (int k = 1; k < SIZE; ++k) {
            for (int i = k; i < SIZE; ++i) {
                double tmp = A[i][k - 1];
                for (int j = 0; j < SIZE; ++j) {
                    A[i][j] -= A[k - 1][j] * tmp / A[k - 1][k - 1];
                }
            }
        }
        for (int i = 0; i < SIZE; ++i) {
            det *= A[i][i];
        }
        return det;
    }

    public static void countInverseMatrix(double[][] A, double[][] A_INVERSE) {
        if (det(A) == 0) {
            isDetZero = true;
            return;
        }
        double[] myf = new double[SIZE];
        double[] myX = new double[SIZE];
        for (int l = 0; l < SIZE; ++l) {
            for (int i = 0; i < SIZE; ++i)
                myf[i] = 0;
            myf[l] = 1;
            solve(A, myX, myf);
            for (int i = 0; i < SIZE; ++i) {
                A_INVERSE[i][l] = myX[i];
            }
        }
    }

    public static void countTransposedMatrix(double[][] A, double[][] A_TRANSPOSED) {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                A_TRANSPOSED[i][j] = A[j][i];
            }
        }
    }

    // сложение матриц
    public static double[][] addMatrix(double[][] A, double[][] B) {
        double[][] C = new double[SIZE][SIZE];
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    // вычитание матриц
    public static double[][] subtractMatrix(double[][] A, double[][] B) {
        double[][] C = new double[SIZE][SIZE];
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                C[i][j] = A[i][j] - B[i][j];
            }
        }
        return C;
    }

    // умножение матриц
    public static double[][] multiplyMatrix(double[][] A, double[][] B) {
        double[][] C = new double[SIZE][SIZE];
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                for (int k = 0; k < SIZE; ++k) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    // умножение матрицы на число
    public static double[][] multiplyMatrixByNumber(double[][] A, double n) {
        double[][] C = new double[SIZE][SIZE];
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                C[i][j] = A[i][j] * n;
            }
        }
        return C;
    }

    // деление матрицы на число
    public static double[][] divideMatrixByNumber(double[][] A, double n) {
        double[][] C = new double[SIZE][SIZE];
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                C[i][j] = A[i][j] / n;
            }
        }
        return C;
    }

    // деление вектора на число
    public static double[] divideVectorByNumber(double[] f, double n) {
        double[] v = new double[SIZE];
        for (int i = 0; i < SIZE; ++i) {
            v[i] = f[i] / n;
        }
        return v;
    }

    // умножение матрицы на вектор
    public static double[] multipleMatrixByVector(double[][] A, double[] x) {
        double[] v = new double[SIZE];
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                v[i] += A[i][j] * x[j];
            }
        }
        return v;
    }

    // сложение векторов
    public static double[] addVectorToVector(double[] a, double[] b) {
        double[] v = new double[SIZE];
        for (int i = 0; i < SIZE; ++i) {
            v[i] = a[i] + b[i];
        }
        return v;
    }

    // вычитание векторов
    public static double[] subtractVectorFromVector(double[] a, double[] b) {
        double[] v = new double[SIZE];
        for (int i = 0; i < SIZE; ++i) {
            v[i] = a[i] - b[i];
        }
        return v;
    }

    // норма вектора
    public static double vectorNorm(double[] a) {
        double res = Math.abs(a[0]);
        for (int i = 1; i < SIZE; ++i) {
            if (Math.abs(a[i]) > res)
                res = Math.abs(a[i]);
        }
        return res;
    }

    // сумма по столбцам
    public static double norm_1(double[][] A) {
        double max = 0;
        for (int j = 0; j < SIZE; ++j) {
            double sum = 0;
            for (int i = 0; i < SIZE; ++i) {
                sum += Math.abs(A[i][j]);
            }
            if (sum > max)
                max = sum;
        }
        return max;
    }

    // сумма по строкам
    public static double norm_2(double[][] A) {
        double max = 0;
        for (int i = 0; i < SIZE; ++i) {
            double sum = 0;
            for (int j = 0; j < SIZE; ++j) {
                sum += Math.abs(A[i][j]);
            }
            if (sum > max)
                max = sum;
        }
        return max;
    }

    // simple-iteration method
    public static int solveSIM(double[][] B, double[] g) {
        double[] x0 = g;
        double[] x1 = addVectorToVector(multipleMatrixByVector(B, x0), g);
        int iterationNumber = 0;
        while (vectorNorm(subtractVectorFromVector(x1, x0)) > EPS) {
            x0 = x1;
            x1 = addVectorToVector(multipleMatrixByVector(B, x0), g);
            ++iterationNumber;
        }
        System.out.println("Solution:");
        printVector10(x1);
        System.out.println("Number of iterations: " + iterationNumber + "\n");
        return iterationNumber;
    }

    // relaxation method
    public static int solveRelaxation(double[][] A1, double q) {
        // counting L, R, D
        double[][] L = new double[SIZE][SIZE];
        double[][] R = new double[SIZE][SIZE];
        double[][] D = new double[SIZE][SIZE];
        for (int i = 1; i < SIZE; ++i) {
            System.arraycopy(A1[i], 0, L[i], 0, i);
        }
        for (int i = 0; i < SIZE - 1; ++i) {
            for (int j = i + 1; j < SIZE; ++j) {
                R[i][j] = A1[i][j];
            }
        }
        for (int i = 0; i < SIZE; ++i) {
            D[i][i] = A1[i][i];
        }

        // counting new B and g
        double[][] B1 = addMatrix(D, multiplyMatrixByNumber(L, q)); // B1 = D + q*L
        double[][] B = new double[SIZE][SIZE];
        countInverseMatrix(B1, B); // B = B1^(-1)
        double[] g = multipleMatrixByVector(multiplyMatrixByNumber(B, q), f);
        B = multiplyMatrix(B, subtractMatrix(D, multiplyMatrixByNumber(addMatrix(D, R), q))); // B = (B1)^(-1) * (D - q(D + R))

        // finding solution
        double[] x0 = g;
        x = addVectorToVector(multipleMatrixByVector(B, x0), g);
        int iterationNumber = 0;
        while (vectorNorm(subtractVectorFromVector(x, x0)) / q > EPS) {
            x0 = x;
            x = addVectorToVector(multipleMatrixByVector(B, x0), g);
            ++iterationNumber;
        }
        return iterationNumber;
    }

    public static void main(String[] args) {
        // 1
        System.out.println("---#1 метод простых итераций---\n");
        generateMatrix(A);
        System.out.println("A:");
        printMatrix2(A);
        generateX(myX);
        countF(A, myX);
        System.out.println("f:");
        printVector10(f);

        countTransposedMatrix(A, A_TRANSPOSED);
        double[][] A1 = multiplyMatrix(A_TRANSPOSED, A);
        f = multipleMatrixByVector(A_TRANSPOSED, f);
        System.out.println("A1 = A^T * A:");
        printMatrix4(A1);
        System.out.println("f1:");
        printVector12(f);
        System.out.println("My solution:");
        printVector10(myX);

        generateE();
        double n = norm_1(A1);
        double[][] B = subtractMatrix(E, divideMatrixByNumber(A1, n)); // B = E - (A / ||A||)
        double[] g = divideVectorByNumber(f, n);
        int it1 = solveSIM(B, g);

        //2
        System.out.println("---#2 метод Зейделя---");
        int it2 = solveRelaxation(A1, 1);
        printVector10(x);
        System.out.println("Number of iterations: " + it2 + "\n");

        //3
        System.out.println("---#3 метод релаксации---");
        double qMin = 0.1;
        int itMin = solveRelaxation(A1, qMin);
        // [0,1; 1,9]
        double[] a = new double[19];
        double[] b = new double[19];
        int j = 0;
        for (double q = 0.2; q < 2; q += 0.1) {
            int tmp = solveRelaxation(A1, q);
            System.out.println("q = " + round(qMin, 1));
            System.out.println("Number of iterations: " + tmp + "\n");
            if (tmp < itMin) {
                itMin = tmp;
                qMin = q;
            }
            a[j] = q;
            b[j++] = tmp;
        }
        // [qMin-0.1; qMin+0.1]
        double tmpMin = qMin;
        for (double q = tmpMin - 0.1; q < tmpMin + 0.1; q += 0.01) {
            int tmp = solveRelaxation(A1, q);
            if (tmp < itMin) {
                itMin = tmp;
                qMin = q;
            }
        }
        System.out.println("q with least number of iterations:");
        int tmp = solveRelaxation(A1, qMin);
        System.out.println("q = " + round(qMin, 2));
        System.out.println("Solution:");
        printVector10(x);
        System.out.println("Number of iterations: " + tmp + "\n");
    }
}
