// variant 5

public class Test {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Invalid number of arguments");
            System.exit(1);
        }
        double x = Double.parseDouble(args[0]);
        if (x >= 1 || x <= -1) {
            System.err.println("Invalid argument: " + x);
            System.exit(1);
        }
        int k = Integer.parseInt(args[1]);
        if (k <= 1) {
            System.err.println("Invalid argument: " + k);
            System.exit(1);
        }
        String fmt = "%." + args[1] + "f\n";
        System.out.printf(fmt, fTaylor(x, k));
        System.out.printf(fmt, 1 / Math.sqrt(1 + x));
    }

    public static double fTaylor(double x, int k) {
        double sum = 0, s1 = 1, s2 = 0;
        double eps = Math.pow(10, -k - 1);
        int n = 1;
        do {
            s2 = s1;
            sum += s2;
            s1 *= (-1) * (2 * n - 1) * x / (2 * n);
            ++n;
        } while (Math.abs(s1) > eps);
        return sum;
    }
}
