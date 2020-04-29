package by.bsu.swimming.funVersion;

import javax.swing.*;
import java.util.concurrent.*;

public class Runner01 {
    private static long redTime;
    private static long greenTime;
    private static long blueTime;

    private static JFrame view;
    private static SwimmerPanel panel;

    private static String buildTime(long time) {
        int minutes = (int) (time / (1000 * 60));
        time -= minutes * 1000 * 60;
        int seconds = (int) (time / 1000);
        time -= seconds * 1000;
        StringBuilder builder = new StringBuilder();
        builder.append("Время: ");
        builder.append(minutes);
        builder.append(":");
        builder.append(seconds);
        builder.append(".");
        builder.append(time);
        return builder.toString();
    }

    private static void processTask(ExecutorService team)
            throws ExecutionException, InterruptedException {
        Callable<Long> redCallable = new Team01(1, panel);
        Callable<Long> greenCallable = new Team01(2, panel);
        Callable<Long> blueCallable = new Team01(3, panel);
        Future<Long> redFuture = team.submit(redCallable);
        Future<Long> greenFuture = team.submit(greenCallable);
        Future<Long> blueFuture = team.submit(blueCallable);
        redTime = redFuture.get();
        greenTime = greenFuture.get();
        blueTime = blueFuture.get();
    }

    public static void main(String[] args) {
        try {
            panel = new SwimmerPanel();

            view = new JFrame();
            view.setVisible(true);
            view.setSize(600, 400);
            view.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            view.setTitle("Swimming");
            view.add(panel);

            panel.startAnimation();

            ExecutorService teams = Executors.newFixedThreadPool(3);
            processTask(teams);
            teams.shutdown();
            System.out.println("1 - " + buildTime(redTime));
            System.out.println("2 - " + buildTime(greenTime));
            System.out.println("3 - " + buildTime(blueTime));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
