package by.bsu.swimming.version01;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Swimmer implements Runnable {
    private static final int POOL_LENGTH = 25;
    private final int relayRaceNumber; // {1, 2, 3, 4}
    private int speed; // {1, 2, 3}
    private final int lane; // номер дорожки
    private final CountDownLatch latch;

    public Swimmer(CountDownLatch latch, int relayRaceNumber, int lane) {
        speed = ThreadLocalRandom.current().nextInt(1, 4);
        this.relayRaceNumber = relayRaceNumber;
        this.latch = latch;
        this.lane = lane;
    }

    private void changeSpeed() {
        speed = ThreadLocalRandom.current().nextInt(1, 4);
    }

    @Override
    public void run() {
        int distance;
        int count = 0;
        int step;
        switch (relayRaceNumber % 2) {
            case 0: {
                // заплыв справа налево
                distance = POOL_LENGTH;
                while (distance > 0) {
                    if (distance - speed < 0) {
                        step = distance;
                        distance = 0;
                    } else {
                        distance -= speed;
                        step = speed;
                    }
                    ++count;
                    // отображение процесса заплыва
                    for (int i = 0; i < step; ++i) {
                        //System.out.print("-");
                    }
                    /*
                     * Условно скорость пловца равна speed м/с, speed in {1, 2, 3}.
                     * После 5 итераций скорость плоцва изменяется, чтобы лидер в
                     * соревнованиях мог измениться.
                     */
                    if (count == 5) {
                        changeSpeed();
                        count = 0;
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case 1: {
                // заплыв слева направо
                distance = 0;
                while (distance < POOL_LENGTH) {
                    if (distance + speed > POOL_LENGTH) {
                        step = POOL_LENGTH - distance;
                        distance = POOL_LENGTH;
                    } else {
                        distance += speed;
                        step = speed;
                    }
                    ++count;
                    for (int i = 0; i < step; ++i) {
                        //System.out.print("-");
                    }
                    if (count == 5) {
                        changeSpeed();
                        count = 0;
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            default:
                break;
        }
        System.out.println(lane + "-" + relayRaceNumber + " finished!");
        latch.countDown();
    }
}
