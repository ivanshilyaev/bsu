package by.bsu.trading.version03.threads;

import by.bsu.trading.version03.FrameView;
import by.bsu.trading.version03.Runner03;
import by.bsu.trading.version03.ConsoleView;
import by.bsu.trading.version03.bean.TradingData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OutputChecker extends Thread {
    private ConcurrentLinkedQueue<TradingData> queue = new ConcurrentLinkedQueue<>();
    private FrameView view;

    public OutputChecker(FrameView view) {
        this.view = view;
    }

    public ConcurrentLinkedQueue<TradingData> getQueue() {
        return queue;
    }

    private boolean checkFiles(String output, String result) {
        boolean testPassed = true;
        try (BufferedReader reader1 = new BufferedReader(new FileReader(output));
             BufferedReader reader2 = new BufferedReader(new FileReader(result))) {
            String line1 = reader1.readLine();
            String line2 = reader2.readLine();
            while (line1 != null && line2 != null) {
                if (!line1.equals(line2)) testPassed = false;
                line1 = reader1.readLine();
                line2 = reader2.readLine();
            }
            if (line1 != null || line2 != null) testPassed = false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return testPassed;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Runner03.semaphore3.acquire();
                TradingData tradingData = queue.poll();
                boolean testPassed = checkFiles(tradingData.getOutputFileName(),
                        tradingData.getResultFileName());
                view.displayResult(tradingData.getTestName(), testPassed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
