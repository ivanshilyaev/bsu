package by.bsu.swimming.mainVersion;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Swimmer implements Runnable {
    private static final int POOL_LENGTH = 25;
    private final int relayRaceNumber; // {1, 2, 3, 4}
    private int speed; // {1, 2, 3}
    private final int lane; // номер дорожки
    private final CountDownLatch latch;
    private int distance;
    private boolean stopped;
    private final SwimmerPanel panel;

    public Swimmer(CountDownLatch latch, int relayRaceNumber, int lane, SwimmerPanel panel) {
        speed = ThreadLocalRandom.current().nextInt(1, 4);
        this.relayRaceNumber = relayRaceNumber;
        this.latch = latch;
        this.lane = lane;
        this.panel = panel;
        stopped = false;
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
    }

    public int getLane() {
        return lane;
    }

    public int getDistance() {
        return distance;
    }

    private void changeSpeed() {
        speed = ThreadLocalRandom.current().nextInt(1, 4);
    }

    public void move() {
        switch (relayRaceNumber % 2) {
            case 0: {
                // заплыв справа налево
                if (distance - speed < 0) distance = 0;
                else distance -= speed;
                /*
                 * Скорость пловца равна speed м/с, speed in {1, 2, 3}.
                 * Скорость изменяется, чтобы лидер в соревнованиях мог измениться.
                 */
                changeSpeed();
                if (distance <= 0) stopped = true;
                break;
            }
            case 1: {
                // заплыв слева направо
                if (distance + speed > POOL_LENGTH) distance = POOL_LENGTH;
                else distance += speed;
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
