public class Solution {
    private TridiagonalMatrix A;
    private Column exactY;
    private double[] y;
    private Column f;
    private final int N;
    private double[] alpha;
    private double[] beta;
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        double myTmp = value * factor;
        long tmp = Math.round(myTmp);
        return (double) tmp / factor;
    }

    public Solution(int n, double min, double max) throws Exception {
        N = n;
        A = new TridiagonalMatrix(N+1, min, max);
        exactY = new Column(N+1, min, max); // random solution
        y = new double[N+1];
        alpha = new double [N+1];
        beta = new double [N+2];
        f = new Column(A.multiplyMatrixWithColumn(exactY)); // counting our f
    }

    public void directPassage() {
        alpha[1] = A.getB(0) / A.getC(0);
        beta[1] = f.getColumn()[0] / A.getC(0);
        for (int i = 1; i < N; ++i) {
            alpha[i+1] = A.getB(i) / (A.getC(i) - alpha[i] * A.getA(i));
            beta[i+1] = (f.getColumn()[i] + beta[i] * A.getA(i)) / (A.getC(i) - alpha[i] * A.getA(i));
        }
        beta[N+1] = (f.getColumn()[N] + beta[N] * A.getA(N)) / (A.getC(N) - alpha[N] * A.getA(N));
    }

    public void reversePassage() {
        y[N] = beta[N + 1];
        for (int i = N - 1; i >= 0; --i)
            y[i] = alpha[i + 1] * y[i + 1] + beta[i + 1];
    }

    public void printMatrix() {
        for (int i = 0; i < N + 1; ++i) {
            for (int j = 0; j < this.N + 1; ++j) {
                if (Math.abs(A.getMatrix()[i][j]) < 0.01)
                    System.out.printf("%7.2f ", 0.0);
                else System.out.printf("%7.2f ", A.getMatrix()[i][j]);
            }
            System.out.println();
        }
    }

    public void printMySolution() {
        for (int i = 0; i < y.length; ++i) {
            if (Math.abs(y[i]) < Math.pow(10, -20))
                System.out.println(0.0);
            else System.out.println(y[i]);
        }
    }

    public void printExactSolution() {
        for (int i = 0; i < N+1; ++i)
            System.out.println(exactY.getColumn()[i]);
    }

    public static void main (String[] args) {
        int size;
        double min;
        double max;
        try {
            //Scanner in = new Scanner(System.in);
            //System.out.print("Введите размер матрицы: ");
            size = 9; //in.nextInt();
            /* if (size <= 0) {
                in.close();
                throw new Exception("Input error! [matrixCount <= 0]");
            }
            in.close(); */
            min = -100; max = 100;

            Solution mySystem = new Solution(size, min, max);
            System.out.println("Original matrix:");
            mySystem.printMatrix();
            // counting solution
            mySystem.directPassage();
            mySystem.reversePassage();
            // results
            System.out.println();
            System.out.println("Original solution:");
            mySystem.printExactSolution();
            System.out.println();
            System.out.println("My solution:");
            mySystem.printMySolution();
        }
        catch (Exception b) {
            System.out.println(b.getMessage());
        }
    }
}
