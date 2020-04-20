package by.bsu.trading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Solver implements Callable<List<String>> {
    private int m;
    private int n;
    private int k;
    private List<Double> prices;

    public Solver(int m, int n, int k, List<Double> prices) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.prices = prices;
    }

    private double countAverageValue(List<Double> list, int t, int day) {
        double average = 0;
        for (int i = day - t; i < day; ++i) {
            average += list.get(i);
        }
        return average / t;
    }

    @Override
    public List<String> call() throws Exception {
        List<String> result = new ArrayList<>();
        List<Integer> bullDays = new ArrayList<>();
        List<Integer> bearDays = new ArrayList<>();
        for (int i = n; i <= k; ++i) {
            double pn = countAverageValue(prices, n, i);
            double pm = countAverageValue(prices, m, i);
            if (pn < pm) {
                bullDays.add(i);
                if (i == n || bearDays.contains(i - 1)) {
                    result.add("BUY ON DAY " + i);
                }
            }
            if (pn > pm) {
                bearDays.add(i);
                if (i == n || bullDays.contains(i - 1)) {
                    result.add("SELL ON DAY " + i);
                }
            }
        }
        return result;
    }
}
