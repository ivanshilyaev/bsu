package by.bsu.trading.version02.threads;

import by.bsu.trading.version02.View;
import by.bsu.trading.version02.bean.Result;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class OutputChecker extends Thread {
    private LinkedList<Result> list = new LinkedList<>();
    private View view;
    private boolean shouldBeStopped;

    public OutputChecker(View view) {
        this.view = view;
        shouldBeStopped = false;
    }

    public LinkedList<Result> getList() {
        return list;
    }

    public boolean isShouldBeStopped() {
        return shouldBeStopped;
    }

    public void setShouldBeStopped(boolean shouldBeStopped) {
        this.shouldBeStopped = shouldBeStopped;
    }

    @Override
    public void run() {
        while (!shouldBeStopped || !list.isEmpty()) {
            Result result = list.poll();
            if (result != null) {
                System.out.println(result.getTestName() + " outputChecker begin");
                boolean testPassed = true;
                try (BufferedReader reader1 = new BufferedReader(new FileReader(result.getResultFileName()));
                     BufferedReader reader2 = new BufferedReader(new FileReader(result.getOutputFileName()))) {
                    String line1 = reader1.readLine();
                    String line2 = reader2.readLine();
                    while (line1 != null && line2 != null) {
                        if (!line1.equals(line2)) testPassed = false;
                        line1 = reader1.readLine();
                        line2 = reader2.readLine();
                    }
                    if (line1 != null || line2 != null) testPassed = false;
                } catch (IOException e) {
                    // handling error
                }
                view.displayResult(result.getTestName(), testPassed);
                System.out.println(result.getTestName() + " outputChecker end");
            }
        }
    }
}