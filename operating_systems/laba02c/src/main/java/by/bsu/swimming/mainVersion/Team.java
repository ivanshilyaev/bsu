package by.bsu.swimming.mainVersion;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Team implements Callable<Long> {
    private final int lane; // номер дорожки
    private final SwimmerPanel panel;

    public Team(int lane, SwimmerPanel panel) {
        this.lane = lane;
        this.panel = panel;
    }

    @Override
    public Long call() throws Exception {
        ExecutorService team = Executors.newSingleThreadExecutor();
        CountDownLatch latch = new CountDownLatch(4);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 4; ++i) {
            team.execute(new Swimmer(latch, i + 1, lane, panel));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        team.shutdown();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
