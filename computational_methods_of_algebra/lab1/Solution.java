import java.text.DecimalFormat;

public class Solution {
    public static final int SIZE = 9;
    public static double[][] A = new double[SIZE][SIZE];
    public static double[][] A_INVERSE = new double[SIZE][SIZE];
    public static double[] x = new double[SIZE];
    public static double[] myX = new double[SIZE];
    public static double[] f = new double[SIZE];
    public static boolean isDetZero = false;

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static void printMatrix(double[][] A) {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                System.out.printf("%10.2f ", A[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printMatrix2(double[][] A) {
        DecimalFormat format = new DecimalFormat("#.##E00");
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                System.out.printf("%10s ", format.format(A[i][j]));
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void generateMatrix(double[][] A) {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                A[i][j] = round(-99 + Math.random() * 199, 2);
            }
        }
    }

    public static void generateX(double[] x) {
        for (int i = 0; i < SIZE; ++i) {
            x[i] = round(-99 + Math.random() * 199, 2);
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

    public static double[][] multiply(double[][] A, double[][] B) {
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

    public static void main(String[] args) {
        generateMatrix(A);
        System.out.println("Original matrix:");
        printMatrix(A);
        generateX(x);
        countF(A, x);

        // step 1
        solve(A, myX, f);

        System.out.println("Original solution:");
        for (int i = 0; i < SIZE; ++i) {
            System.out.printf("%10.2f ", x[i]);
        }
        System.out.println();

        System.out.println("My solution:");
        for (int i = 0; i < SIZE; ++i) {
            System.out.printf("%10.2f ", myX[i]);
        }
        System.out.println();

        // step 2
        countInverseMatrix(A, A_INVERSE);
        System.out.println("Det(A) = " + det(A) + "\n");
        if (isDetZero) {
            System.out.println("Inverse matrix doesn't exist, because det(A) = 0");
        } else {
            System.out.println("Inverse matrix:");
            printMatrix2(A_INVERSE);
            System.out.println("Check");
            System.out.println("Unit matrix E=A*A^(-1):");
            double[][] E = multiply(A, A_INVERSE);
            printMatrix2(E);
        }

        // step 3
        double n1 = norm_1(A);
        double n2 = norm_2(A_INVERSE);
        double V = n1 * n2;
        System.out.println("V(A) = ||A|| * ||A^(-1)|| = " + V);
        System.out.println(n1);
        System.out.println(n2);
    }
}