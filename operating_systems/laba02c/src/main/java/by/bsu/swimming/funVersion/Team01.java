package by.bsu.swimming.funVersion;

import javax.swing.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Team01 implements Callable<Long> {
    private int lane; // номер дорожки
    private SwimmerPanel panel;

    public Team01(int lane, SwimmerPanel panel) {
        this.lane = lane;
        this.panel = panel;
    }

    @Override
    public Long call() throws Exception {
        ExecutorService red = Executors.newSingleThreadExecutor();
        CountDownLatch latch = new CountDownLatch(4);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 4; ++i) {
            red.execute(new Swimmer01(latch, i + 1, lane, panel));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        red.shutdown();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
