package by.bsu.swimming;

import java.util.concurrent.*;

public class Runner {
    private static long redTime;
    private static long greenTime;
    private static long blueTime;

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
        Callable<Long> redCallable = new Team(1);
        Callable<Long> greenCallable = new Team(2);
        Callable<Long> blueCallable = new Team(3);
        Future<Long> redFuture = team.submit(redCallable);
        Future<Long> greenFuture = team.submit(greenCallable);
        Future<Long> blueFuture = team.submit(blueCallable);
        redTime = redFuture.get();
        greenTime = greenFuture.get();
        blueTime = blueFuture.get();
    }

    public static void main(String[] args) {
        try {
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
