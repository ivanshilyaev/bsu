package by.bsu.swimming;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Team implements Callable<Long> {
    private int lane; // номер дорожки

    public Team(int lane) {
        this.lane = lane;
    }

    @Override
    public Long call() throws Exception {
        ExecutorService red = Executors.newSingleThreadExecutor();
        CountDownLatch latch = new CountDownLatch(4);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 4; ++i) {
            red.execute(new Swimmer(latch, i + 1, lane));
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
