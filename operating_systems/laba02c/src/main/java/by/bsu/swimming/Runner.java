package by.bsu.swimming;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Runner {
    public static void main(String[] args) {
        ExecutorService red = Executors.newSingleThreadExecutor();
        CountDownLatch latch = new CountDownLatch(4);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 4; ++i) {
            red.execute(new Swimmer(latch));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        red.shutdown();
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        int minutes = (int) (time / (1000 * 60));
        time -= minutes * 1000 * 60;
        int seconds = (int) (time / 1000);
        time -= seconds * 1000;
        StringBuilder result = new StringBuilder();
        result.append("Время: ");
        result.append(minutes);
        result.append(":");
        result.append(seconds);
        result.append(".");
        result.append(time);
        System.out.println(result.toString());
    }
}
