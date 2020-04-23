package by.bsu.trading.version03.threads;

import by.bsu.trading.version03.FrameView;
import by.bsu.trading.version03.bean.TradingData;
import by.bsu.trading.version03.Runner03;

import java.util.concurrent.ConcurrentLinkedQueue;

public class InputChecker extends Thread {
    private static final int MAX_PARAM_VALUE = 100;
    private static final int MAX_DAY_VALUE = 10000;
    private static final int MAX_PRICE_VALUE = 100;

    private ConcurrentLinkedQueue<TradingData> queue = new ConcurrentLinkedQueue<>();
    private Solver solver;
    private FrameView view;

    public InputChecker(Solver solver, FrameView view) {
        this.solver = solver;
        this.view = view;
    }

    public ConcurrentLinkedQueue<TradingData> getQueue() {
        return queue;
    }

    private boolean check(TradingData tradingData) {
        if (tradingData.getM() < 1) return false;
        if (tradingData.getN() > MAX_PARAM_VALUE) return false;
        if (tradingData.getM() >= tradingData.getN()) return false;
        if (tradingData.getK() > MAX_DAY_VALUE) return false;
        if (tradingData.getN() >= tradingData.getK()) return false;
        if (tradingData.getPrices().size() != tradingData.getK()) return false;
        for (Double price : tradingData.getPrices()) {
            if (price < 0 || price > MAX_PRICE_VALUE) return false;
        }
        return true;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Runner03.semaphore1.acquire();
                TradingData tradingData = queue.poll();
                if (check(tradingData)) {
                    solver.getQueue().add(tradingData);
                    Runner03.semaphore2.release();
                } else {
                    view.printInputCheckerError(tradingData.getTestName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
