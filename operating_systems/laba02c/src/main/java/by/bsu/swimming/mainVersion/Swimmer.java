package by.bsu.swimming.mainVersion;

import java.awt.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Swimmer implements Runnable {
    private static final int RIGHT_BORDER = 10;
    private static final int POOL_LENGTH = 300 - RIGHT_BORDER;
    private int relayRaceNumber; // {1, 2, 3, 4}
    private int speed; // {1, 2, 3, 4, 5}
    private int lane; // номер дорожки
    private CountDownLatch latch;
    private Color color;
    private SwimmerPanel panel;
    private Team team;
    private int distance;
    private boolean stopped;
    private boolean quit;

    public Color getColor() {
        return color;
    }

    public Swimmer(CountDownLatch latch, int relayRaceNumber, int lane,
                   Color color, SwimmerPanel panel, Team team) {
        speed = ThreadLocalRandom.current().nextInt(1, 6);
        this.relayRaceNumber = relayRaceNumber;
        this.latch = latch;
        this.lane = lane;
        this.color = color;
        this.panel = panel;
        this.team = team;
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
        speed = ThreadLocalRandom.current().nextInt(1, 6);
    }

    private boolean quitTheDistance() {
        return ThreadLocalRandom.current().nextInt(1, 1000) == 999;
    }

    public void move() {
        switch (relayRaceNumber % 2) {
            case 0: {
                // заплыв справа налево
                if (distance - speed < 0) distance = 0;
                else distance -= speed;
                /*
                 * Скорость пловца равна speed м/с, speed in {1, 2, 3, 4, 5}.
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
        quit = quitTheDistance();
    }

    @Override
    public void run() {
        panel.getSwimmers().add(this);
        while (!stopped) {
            if (quit) {
                team.setQuit(true);
                while (latch.getCount() > 0) latch.countDown();
                while (true) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        panel.getSwimmers().remove(this);
        latch.countDown();
    }
}
