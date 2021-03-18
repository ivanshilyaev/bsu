package com.ivanshilyaev.lll;

public class LLLService {

    private final MathService mathService = new MathService();

    public double calculateMu(int n, int[] vector, int[] newVector) {
        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i < n; i++) {
            sum1 += (vector[i] * newVector[i]);
            sum2 += (newVector[i] * newVector[i]);
        }
        return (double) sum1 / sum2;
    }

    public void gramSchmidtProcess(int m, int n, int[][] b, int[][] bStar, double[][] mu) {
        if (n >= 0) System.arraycopy(b[0], 0, bStar[0], 0, n);
        System.out.println("mu:");
        for (int k = 1; k < m; k++) {
            mu[k] = new double[k];
            for (int i = 0; i < k; i++) {
                mu[k][i] = calculateMu(n, b[k], bStar[i]);
                System.out.println(mu[k][i] + " " + (Math.abs(mu[k][i]) <= 0.5 ? "+" : "-"));
            }
            for (int i = 0; i < n; i++) {
                bStar[k][i] = b[k][i];
                for (int j = 0; j < k; j++) {
                    bStar[k][i] -= (Math.round(mu[k][j]) * bStar[j][i]);
                }
            }
        }
    }

    public boolean checkForLLL(int m, int n, double delta, int[][] bStar, double[][] mu) {
        boolean result = true;
        // first condition
        for (int i = 1; i < mu.length; ++i) {
            for (int j = 0; j < mu[i].length; ++j) {
                if (Math.abs(mu[i][j]) > 0.5) {
                    result = false;
                    break;
                }
            }
        }
        // second condition
        for (int i = 1; i < m; i++) {
            double left = (delta - Math.pow(mu[i][i - 1], 2))
                * mathService.calculateSquareEuclideanNorm(n, bStar[i - 1]);
            double right = mathService.calculateSquareEuclideanNorm(n, bStar[i]);
            if (left > right) {
                System.out.println(left + " > " + right);
                result = false;
            } else {
                System.out.println(left + " <= " + right);
            }
        }
        return result;
    }

    public void transformToLLL(
        int m, int n, double delta, int[][] b,
        int[][] bStar, double[][] mu
    ) {
        int k = 1;
        while (k < m) {
            for (int j = k - 1; j >= 0; --j) {
                if (Math.abs(mu[k][j]) > 0.5) {
                    for (int i = 0; i < n; ++i) {
                        b[k][i] = b[k][i] - (int) Math.round(mu[k][j]) * b[j][i];
                    }
                    for (int i = 0; i < k; ++i) {
                        mu[k][i] = calculateMu(n, b[k], bStar[i]);
                    }
                }
            }
            if ((delta - Math.pow(mu[k][k - 1], 2))
                * mathService.calculateSquareEuclideanNorm(n, bStar[k - 1])
                <= mathService.calculateSquareEuclideanNorm(n, bStar[k])) {
                ++k;
            } else {
                int[] temp = b[k];
                b[k] = b[k - 1];
                b[k - 1] = temp;
                for (int i = 0; i < n; ++i) {
                    bStar[k][i] = b[k][i];
                    for (int j = 0; j < k; j++) {
                        bStar[k][i] -= Math.round(mu[k][j]) * bStar[j][i];
                    }
                }
                for (int i = 0; i < n; ++i) {
                    bStar[k - 1][i] = b[k - 1][i];
                    for (int j = 0; j < k - 1; ++j) {
                        bStar[k - 1][i] -= Math.round(mu[k - 1][j]) * bStar[j][i];
                    }
                }
                k = Math.max(k - 1, 1);
                for (int i = 0; i < k; ++i) {
                    mu[k][i] = calculateMu(n, b[k], bStar[i]);
                }
                for (int i = 0; i < k - 1; ++i) {
                    mu[k - 1][i] = calculateMu(n, b[k - 1], bStar[i]);
                }
            }
            if (k == m) {
                for (int i = 1; i < mu.length; ++i) {
                    for (int j = 0; j < mu[i].length; ++j) {
                        if (Math.abs(mu[i][j]) > 0.5) {
                            k = 1;
                            break;
                        }
                    }
                }
            }
        }
    }
}
