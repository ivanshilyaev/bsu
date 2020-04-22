package by.bsu.trading.version01;

import java.util.List;
import java.util.concurrent.Callable;

public class InputChecker implements Callable<Boolean> {
    private static final int MAX_PARAM_VALUE = 100;
    private static final int MAX_DAY_VALUE = 10000;

    private int m;
    private int n;
    private int k;
    private List<Double> prices;

    public InputChecker(int m, int n, int k, List<Double> prices) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.prices = prices;
    }

    @Override
    public Boolean call() throws Exception {
        if (m >= n) return false;
        if (n > MAX_PARAM_VALUE) return false;
        if (k > MAX_DAY_VALUE) return false;
        return true;
    }
}
