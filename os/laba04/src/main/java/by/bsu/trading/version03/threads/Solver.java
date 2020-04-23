package by.bsu.trading.version03.threads;

import by.bsu.trading.version03.Helper;
import by.bsu.trading.version03.bean.TradingData;
import by.bsu.trading.version03.Runner03;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

public class Solver extends Thread {
    private ConcurrentLinkedQueue<TradingData> queue = new ConcurrentLinkedQueue<>();
    private OutputChecker outputChecker;
    private boolean paused;

    public Solver(OutputChecker outputChecker) {
        this.outputChecker = outputChecker;
        paused = false;
    }

    public ConcurrentLinkedQueue<TradingData> getQueue() {
        return queue;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
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

    @Override
    public void run() {
        while (true) {
            if (!paused) {
                try {
                    Runner03.semaphore2.acquire();
                    TradingData tradingData = queue.poll();
                    List<String> signals = solve(tradingData);
                    String resultFileName = Helper.makeResultFileName(tradingData.getInputFileName());
                    Helper.writeToFile(resultFileName, signals);
                    tradingData.setResultFileName(resultFileName);
                    /*
                     * Это задержка в 1 секунду добавлена для демонстрации возможности
                     * прекращения работы потока, решающего задачи, по нажатию
                     * на кнопку Pause, и возобновления его работы по нажатию
                     * на кнопку Resume, поскольку задача тестировалась в условиях
                     * ограниченного набора тестов.
                     *
                     * Для нормальной быстрой работыы программы следующую строчку
                     * следует убрать.
                     */
                    TimeUnit.MILLISECONDS.sleep(500);
                    /*
                     */
                    outputChecker.getQueue().add(tradingData);
                    Runner03.semaphore3.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
