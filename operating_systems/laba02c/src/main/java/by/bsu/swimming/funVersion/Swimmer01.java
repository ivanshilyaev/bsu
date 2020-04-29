package by.bsu.swimming.funVersion;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Swimmer01 implements Runnable {
    private static final int POOL_LENGTH = 25;
    private final int relayRaceNumber; // {1, 2, 3, 4}
    private int speed; // {1, 2, 3}
    private final int lane; // номер дорожки
    private final CountDownLatch latch;
    private int distance;
    private boolean stopped;
    private SwimmerPanel panel;

    public Swimmer01(CountDownLatch latch, int relayRaceNumber, int lane, SwimmerPanel panel) {
        speed = ThreadLocalRandom.current().nextInt(1, 4);
        this.relayRaceNumber = relayRaceNumber;
        this.latch = latch;
        this.lane = lane;
        this.panel = panel;
        stopped = false;
    }

    public int getLane() {
        return lane;
    }

    public int getDistance() {
        return distance;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    private void changeSpeed() {
        speed = ThreadLocalRandom.current().nextInt(1, 4);
    }

    public void move() {
        int step;
        switch (relayRaceNumber % 2) {
            case 0: {
                // заплыв справа налево
                if (distance - speed < 0) {
                    step = distance;
                    distance = 0;
                } else {
                    distance -= speed;
                    step = speed;
                }
                // отображение процесса заплыва
                for (int i = 0; i < step; ++i) {
                    //System.out.print("-");
                }
                /*
                 * Условно скорость пловца равна speed м/с, speed in {1, 2, 3}.
                 * После 5 итераций скорость плоцва изменяется, чтобы лидер в
                 * соревнованиях мог измениться.
                 */
                changeSpeed();
                if (distance <= 0) stopped = true;
                break;
            }
            case 1: {
                // заплыв слева направо
                if (distance + speed > POOL_LENGTH) {
                    step = POOL_LENGTH - distance;
                    distance = POOL_LENGTH;
                } else {
                    distance += speed;
                    step = speed;
                }
                for (int i = 0; i < step; ++i) {
                    //System.out.print("-");
                }
                changeSpeed();
                if (distance >= POOL_LENGTH) stopped = true;
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void run() {
        switch (relayRaceNumber % 2) {
            case 0:
                distance = POOL_LENGTH;
                break;
            case 1:
                distance = 0;
                break;
            default:
                break;
        }
        panel.getSwimmers().add(this);
        while (!stopped) {
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        panel.getSwimmers().remove(this);
        System.out.println(lane + "-" + relayRaceNumber + " finished!");
        latch.countDown();
    }
}
