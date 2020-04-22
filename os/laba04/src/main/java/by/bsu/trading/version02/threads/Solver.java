package by.bsu.trading.version02.threads;

import by.bsu.trading.version02.Helper;
import by.bsu.trading.version02.bean.Result;
import by.bsu.trading.version02.bean.TradingData;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Solver extends Thread {
    private LinkedList<TradingData> list = new LinkedList<>();
    private OutputChecker outputChecker;
    private boolean shouldBeStopped;

    public Solver(OutputChecker outputChecker) {
        this.outputChecker = outputChecker;
        shouldBeStopped = false;
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

    private double countAverageValue(List<Double> list, int t, int day) {
        double average = 0;
        for (int i = day - t; i < day; ++i) {
            average += list.get(i);
        }
        return average / t;
    }

    private List<String> solve(TradingData tradingData) {
        List<String> result = new ArrayList<>();
        List<Integer> bullDays = new ArrayList<>();
        List<Integer> bearDays = new ArrayList<>();
        for (int i = tradingData.getN(); i <= tradingData.getK(); ++i) {
            double pn = countAverageValue(tradingData.getPrices(), tradingData.getN(), i);
            double pm = countAverageValue(tradingData.getPrices(), tradingData.getM(), i);
            if (pn < pm) {
                bullDays.add(i);
                if (i == tradingData.getN() || bearDays.contains(i - 1)) {
                    result.add("BUY ON DAY " + i);
                }
            }
            if (pn > pm) {
                bearDays.add(i);
                if (i == tradingData.getN() || bullDays.contains(i - 1)) {
                    result.add("SELL ON DAY " + i);
                }
            }
        }
        return result;
    }

    private String makeResultFileName(String inputFileName) {
        File file = new File(inputFileName);
        StringBuilder builder = new StringBuilder();
        builder.append("src/main/resources/");
        builder.append("my");
        builder.append(Helper.getFileName(file));
        builder.append(".out");
        return builder.toString();
    }

    @Override
    public void run() {
        while (!shouldBeStopped || !list.isEmpty()) {
            TradingData tradingData = list.poll();
            if (tradingData != null) {
                List<String> signals = solve(tradingData);
                String resultFileName = makeResultFileName(tradingData.getInputFileName());
                Helper.writeToFile(resultFileName, signals);
                Result result = new Result(tradingData.getId(), resultFileName, tradingData.getOutputFileName());
                outputChecker.getList().push(result);
            }
        }
    }
}
