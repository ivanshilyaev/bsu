package by.bsu.trading.version02.threads;

import by.bsu.trading.version02.View;
import by.bsu.trading.version02.bean.TradingData;

import java.util.LinkedList;

public class InputChecker extends Thread {
    private static final int MAX_PARAM_VALUE = 100;
    private static final int MAX_DAY_VALUE = 10000;

    private LinkedList<TradingData> list = new LinkedList<>();
    private Solver solver;
    private View view;
    private boolean shouldBeStopped;

    public InputChecker(Solver solver, View view) {
        this.solver = solver;
        this.view = view;
        this.shouldBeStopped = false;
    }

    public LinkedList<TradingData> getList() {
        return list;
    }

    public boolean isShouldBeStopped() {
        return shouldBeStopped;
    }

    public void setShouldBeStopped(boolean shouldBeStopped) {
        this.shouldBeStopped = shouldBeStopped;
    }

    private boolean check(TradingData tradingData) {
        if (tradingData.getM() >= tradingData.getN()) return false;
        if (tradingData.getN() > MAX_PARAM_VALUE) return false;
        if (tradingData.getK() > MAX_DAY_VALUE) return false;
        return true;
    }

    @Override
    public void run() {
        while (!shouldBeStopped || !list.isEmpty()) {
            TradingData tradingData = list.poll();
            if (tradingData != null) {
                if (check(tradingData)) {
                    solver.getList().push(tradingData);
                } else {
                    view.printInputCheckerError(tradingData.getTestName());
                }
            }
        }
    }
}
