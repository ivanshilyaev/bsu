public class Column {
    private double[] column;
    protected final int n;

    public Column(Column C) {
        n = C.n;
        column = new double[n];
        for (int i = 0; i < n; ++i)
            column[i] = C.column[i];
    }

    public Column(double[] mas) {
        n = mas.length;
        column = new double[n];
        for (int i = 0; i < n; ++i)
            column[i] = mas[i];
    }

    public Column(int count, double min, double max) {
        n = count;
        column = new double[n];
        for (int i = 0; i < this.n; ++i) {
            column[i] = Solution.round(min + Math.random() * (max - min + 1), 2);
        }
    }

    public double[] getColumn () {
        return column;
    }
}