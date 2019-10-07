public class TridiagonalMatrix {
    private double[][] matrix;
    protected final int n;

    public TridiagonalMatrix(int count, double min, double max) {
        n = count;
        matrix = new double[n][n];
        do {
            matrix[0][0] = Solution.round(min + Math.random() * (max - min + 1), 2);
            matrix[n-1][n-1] = Solution.round(min + Math.random() * (max - min + 1), 2);
            matrix[0][1] = Solution.round(min + Math.random() * (max - min + 1), 2);
            matrix[n-1][n-2] = Solution.round(min + Math.random() * (max - min + 1), 2);
        }
        while (getC(0)==0 || getC(n-1)==0 || Math.abs(getC(0)) < Math.abs(getB(0)) || Math.abs(getC(n-1)) <= Math.abs(getA(n-1)));
        // 1 and 3 conditions
        for (int i = 1; i < this.n - 1; ++i) {
            do {
                matrix[i][i] = Solution.round(min + Math.random() * (max - min + 1), 2);
                matrix[i][i - 1] = Solution.round(min + Math.random() * (max - min + 1), 2);
                matrix[i][i + 1] = Solution.round(min + Math.random() * (max - min + 1), 2);
            }
            while (getA(i) == 0 || getB(i) == 0 || Math.abs(getC(i)) < (Math.abs(getA(i)) + Math.abs(getB(i))));
            // 1 and 2 conditions
        }
    }

    public TridiagonalMatrix(TridiagonalMatrix M) {
        n = M.n;
        matrix = new double[n][n];
        for (int i = 0; i < n; ++i)
            System.arraycopy(M.matrix[i], 0, matrix[i], 0, n);
    }

    public double getA(int idx) {
        if (idx < 1 || idx >= this.n)
            throw new IndexOutOfBoundsException("Bad value of index: " + idx + ". Index must be >=1 and <" + this.n + ".");
        return (-1)*matrix[idx][idx-1];
    }

    public double getB(int idx) {
        if (idx < 0 || idx >= this.n - 1)
            throw new IndexOutOfBoundsException("Bad value of index: " + idx + ". Index must be >=0 and <" + (this.n-1) + ".");
        return (-1)*matrix[idx][idx+1];
    }

    public double getC(int idx) {
        if (idx < 0 || idx >= this.n)
            throw new IndexOutOfBoundsException("Bad value of index: " + idx + ". Index must be >=0 and <" + this.n + ".");
        return matrix[idx][idx];
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public Column multiplyMatrixWithColumn(Column C) throws Exception {
        if (C.n != this.n)
            throw new Exception("Wrong size of matrix");
        double[] ans = new double[C.n];
        for (int i = 0; i < this.n; ++i)
            for (int j = 0; j < this.n; ++j)
                ans[i] += matrix[i][j] * C.getColumn()[j];
        return new Column(ans);
    }
}