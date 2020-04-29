package by.bsu.swimming.mainVersion;

import java.awt.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Team implements Callable<Long> {
    private final int lane; // номер дорожки
    private final SwimmerPanel panel;
    private boolean quit;

    public Team(int lane, SwimmerPanel panel) {
        this.lane = lane;
        this.panel = panel;
        quit = false;
    }

    public void setQuit(boolean quit) {
        this.quit = quit;
    }

    @Override
    public Long call() throws Exception {
        ExecutorService team = Executors.newSingleThreadExecutor();
        CountDownLatch latch = new CountDownLatch(4);
        long startTime = System.currentTimeMillis();
        Color color;
        switch (lane) {
            case 1:
                color = Color.RED;
                break;
            case 2:
                color = Color.GREEN;
                break;
            case 3:
                color = Color.BLUE;
                break;
            default:
                color = Color.BLACK;
                break;
        }
        for (int i = 0; i < 4; ++i) {
            team.execute(new Swimmer(latch, i + 1, lane, color,
                    panel, this));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        team.shutdown();
        long endTime = System.currentTimeMillis();
        if (quit) return Long.MAX_VALUE;
        return endTime - startTime;
    }
}
