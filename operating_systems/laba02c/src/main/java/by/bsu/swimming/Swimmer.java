package by.bsu.swimming;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Swimmer extends Thread {
    private static int nextId = 0;
    private static final int POOL_LENGTH = 25;
    private int id;
    private int speed; // 1, 2, 3
    private CountDownLatch latch;

    public Swimmer(CountDownLatch latch) {
        id = ++nextId;
        speed = ThreadLocalRandom.current().nextInt(1, 4);
        this.latch = latch;
    }

    private void changeSpeed() {
        speed = ThreadLocalRandom.current().nextInt(1, 4);
    }

    @Override
    public void run() {
        int distance;
        int count = 0;
        int step;
        switch (id % 2) {
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
                        System.out.print("-");
                    }
                    /*
                     * Условно скорость пловца равна speed м/с. speed in {1,2,3}.
                     * После 5 итераций скорость плоцва изменяется, чтобы лидер в
                     * соревнованиях мог меняться.
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
                        System.out.print("-");
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
        System.out.println();
        latch.countDown();
    }
}
