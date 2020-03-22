package by.bsu.tasks.recurrence;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RecurrenceRunner implements Runnable {

    static class State {
        int first;
        int second;
        int third;
        int transfusions;

        public State() {
            first = 0;
            second = 0;
            third = 0;
            transfusions = Integer.MAX_VALUE;
        }

        public State(int first, int second, int third, int transfusions) {
            this.first = first;
            this.second = second;
            this.third = third;
            this.transfusions = transfusions;
        }
    }

    private static final String IN = "/Users/ivansilaev/Desktop/in.txt";
    private static final String OUT = "/Users/ivansilaev/Desktop/out.txt";

    //private static final String IN = "in.txt";
    //private static final String OUT = "out.txt";

    public static boolean[][] visited = new boolean[101][101];

    public static int countTransfusions(State initialState, int x, List<Integer> marks) {
        int result = Integer.MAX_VALUE;
        Queue<State> states = new LinkedList<>();
        states.add(initialState);
        while (!states.isEmpty()) {
            State state = states.poll();
            if (state.third == x) {
                return state.transfusions;
            }
            if (!visited[state.first][state.second]) {
                // из 1 в 2
                states.add(new State(0, state.second + state.first, state.third, state.transfusions + 1));
                // из 1 в 3
                states.add(new State(0, state.second, state.third + state.first, state.transfusions + 1));
                // из 2 в 1
                states.add(new State(state.first + state.second, 0, state.third, state.transfusions + 1));
                // из 2 в 3
                states.add(new State(state.first, 0, state.third + state.second, state.transfusions + 1));
                // из 3 в 1
                states.add(new State(state.first + state.third, state.second, 0, state.transfusions + 1));
                // из 3 в 2
                states.add(new State(state.first, state.second + state.third, 0, state.transfusions + 1));
                for (Integer mark : marks) {
                    if (state.first > mark) {
                        // из 1 до метки в 2
                        states.add(new State(mark, state.second + (state.first - mark), state.third, state.transfusions + 1));
                        // из 1 до метки в 3
                        states.add(new State(mark, state.second, state.third + (state.first - mark), state.transfusions + 1));
                    }
                    if (state.second > mark) {
                        // из 2 до метки в 1
                        states.add(new State(state.first + (state.second - mark), mark, state.third, state.transfusions + 1));
                        // из 2 до метки в 3
                        states.add(new State(state.first, mark, state.third + (state.second - mark), state.transfusions + 1));
                    }
                    if (state.first < mark && (state.second - (mark - state.first)) > 0) {
                        // из 2 в 1 до метки
                        states.add(new State(mark, state.second - (mark - state.first), state.third, state.transfusions + 1));
                    }
                    if (state.first < mark && (state.third - (mark - state.first)) > 0) {
                        // из 3 в 1 до метки
                        states.add(new State(mark, state.second, state.third - (mark - state.first), state.transfusions + 1));
                    }
                    if (state.second < mark && (state.first - (mark - state.second)) > 0) {
                        // из 1 в 2 до метки
                        states.add(new State(state.first - (mark - state.second), mark, state.third, state.transfusions + 1));
                    }
                    if (state.second < mark && (state.third - (mark - state.second)) > 0) {
                        // из 3 в 2 до метки
                        states.add(new State(state.first, mark, state.third - (mark - state.second), state.transfusions + 1));
                    }
                }
            }
            visited[state.first][state.second] = true;
        }
        return result;
    }

    public static void main(String[] args) {
        new Thread(null, new RecurrenceRunner(), "", 64 * 1024 * 1024).start();
    }

    @Override
    public void run() {
        // need to be measured
        int x = 0;
        int first = 0;
        int second = 0;
        int third = 0;
        List<Integer> marks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(IN))) {
            x = Integer.parseInt(reader.readLine());
            first = Integer.parseInt(reader.readLine());
            second = Integer.parseInt(reader.readLine());
            String[] array = reader.readLine().split(" ");
            for (int i = 0; i < array.length - 1; ++i) {
                marks.add(Integer.parseInt(array[i]));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        third = 100 - first - second;
        State initialState = new State(first, second, third, 0);
        int result = countTransfusions(initialState, x, marks);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUT))) {
            if (result == Integer.MAX_VALUE) {
                writer.write("No solution");
            } else {
                writer.write(String.valueOf(result));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
