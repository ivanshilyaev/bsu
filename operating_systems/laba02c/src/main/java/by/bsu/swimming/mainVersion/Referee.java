package by.bsu.swimming.mainVersion;

import javax.swing.*;
import java.util.concurrent.*;

public class Referee extends Thread {
    private static long redTime; // 1
    private static long greenTime; // 2
    private static long blueTime; // 3
    private SwimmerPanel panel;

    public Referee(SwimmerPanel panel) {
        this.panel = panel;
    }

    private String buildTime(long time) {
        int minutes = (int) (time / (1000 * 60));
        time -= minutes * 1000 * 60;
        int seconds = (int) (time / 1000);
        time -= seconds * 1000;
        StringBuilder builder = new StringBuilder();
        builder.append(" - время ");
        builder.append(minutes);
        builder.append(":");
        builder.append(seconds);
        builder.append(".");
        builder.append(time);
        return builder.toString();
    }

    private String buildTeamResult(int place, int teamNumber) {
        String teamName;
        String time;
        switch (teamNumber) {
            case 1:
                teamName = "«красные»";
                time = buildTime(redTime);
                break;
            case 2:
                teamName = "«зелёные»";
                time = buildTime(greenTime);
                break;
            case 3:
                teamName = "«синие»";
                time = buildTime(blueTime);
                break;
            default:
                teamName = "";
                time = "";
                break;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(place);
        builder.append(" место - команда ");
        builder.append(teamName);
        builder.append(time);
        builder.append("\r\n");
        return builder.toString();
    }

    private String buildResult() {
        StringBuilder builder = new StringBuilder();
        if (redTime < greenTime && redTime < blueTime) {
            builder.append(buildTeamResult(1, 1));
            if (greenTime < blueTime) {
                builder.append(buildTeamResult(2, 2));
                builder.append(buildTeamResult(3, 3));
            } else {
                builder.append(buildTeamResult(2, 3));
                builder.append(buildTeamResult(3, 2));
            }
        } else if (greenTime < blueTime) {
            builder.append(buildTeamResult(1, 2));
            if (redTime < blueTime) {
                builder.append(buildTeamResult(2, 1));
                builder.append(buildTeamResult(3, 3));
            } else {
                builder.append(buildTeamResult(2, 3));
                builder.append(buildTeamResult(3, 1));
            }
        } else {
            builder.append(buildTeamResult(1, 3));
            if (redTime < greenTime) {
                builder.append(buildTeamResult(2, 1));
                builder.append(buildTeamResult(3, 2));
            } else {
                builder.append(buildTeamResult(2, 2));
                builder.append(buildTeamResult(3, 1));
            }
        }
        return builder.toString();
    }

    private void processTask(ExecutorService team)
            throws ExecutionException, InterruptedException {
        Callable<Long> redCallable = new Team(1, panel);
        Callable<Long> greenCallable = new Team(2, panel);
        Callable<Long> blueCallable = new Team(3, panel);
        Future<Long> redFuture = team.submit(redCallable);
        Future<Long> greenFuture = team.submit(greenCallable);
        Future<Long> blueFuture = team.submit(blueCallable);
        redTime = redFuture.get();
        greenTime = greenFuture.get();
        blueTime = blueFuture.get();
    }

    @Override
    public void run() {
        try {
            ExecutorService teams = Executors.newFixedThreadPool(3);
            processTask(teams);
            teams.shutdown();
            JOptionPane.showMessageDialog(panel, buildResult());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
