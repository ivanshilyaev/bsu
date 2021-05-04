package com.ivanshilyaev.nist;

public class Utils {

    public static int calculateRank(int m, int n, int[][] b) {
        int[][] copyVectors = new int[m][n];
        for (int i = 0; i < m; i++) {
            System.arraycopy(b[i], 0, copyVectors[i], 0, n);
        }
        double EPS = 1E-9;
        int rank = Math.max(n, m);
        boolean[] line_used = new boolean[m];
        for (int i = 0; i < n; ++i) {
            int j;
            for (j = 0; j < m; ++j)
                if (!line_used[j] && Math.abs(copyVectors[j][i]) > EPS)
                    break;
            if (j == m)
                --rank;
            else {
                line_used[j] = true;
                for (int p = i + 1; p < n; ++p)
                    copyVectors[j][p] /= copyVectors[j][i];
                for (int k = 0; k < m; ++k)
                    if (k != j && Math.abs(copyVectors[k][i]) > EPS)
                        for (int p = i + 1; p < n; ++p)
                            copyVectors[k][p] -= copyVectors[j][p] * copyVectors[k][i];
            }
        }
        return rank;
    }
}
