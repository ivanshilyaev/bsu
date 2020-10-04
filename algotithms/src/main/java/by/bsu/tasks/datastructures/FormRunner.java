package by.bsu.tasks.datastructures;

import java.io.*;
import java.util.LinkedList;

public class FormRunner implements Runnable {
    private static final String IN = "/Users/ivansilaev/Desktop/in.txt";
    private static final String OUT = "/Users/ivansilaev/Desktop/out.txt";

    //private static final String IN = "in.txt";
    //private static final String OUT = "out.txt";

    public static void main(String[] args) {
        new Thread(null, new FormRunner(), "", 64 * 1024 * 1024).start();
    }

    class Element {
        int x;
        int y;
        int value;

        public Element(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }

    int n;
    int m;
    Element[][] matrix;
    int D;
    Element maxElement;
    int z;
    int p;
    int[][] delta = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    LinkedList<Element> queue = new LinkedList<>();

    public int findMaxElement() {
        maxElement = matrix[1][1];
        int max = matrix[1][1].value;
        for (int i = 1; i < n + 1; ++i) {
            for (int j = 1; j < m + 1; ++j) {
                if (matrix[i][j].value > max) {
                    maxElement = matrix[i][j];
                    max = matrix[i][j].value;
                }
            }
        }
        return max;
    }

    public Element findMinElement() {
        Element min = maxElement;
        for (int i = 1; i < n + 1; ++i) {
            for (int j = 1; j < m + 1; ++j) {
                if (matrix[i][j].value < min.value && matrix[i][j].value > 0) {
                    min = matrix[i][j];
                }
            }
        }
        return min;
    }

    @Override
    public void run() {
        // чтение из файла
        try (BufferedReader reader = new BufferedReader(new FileReader(IN))) {
            String[] array = reader.readLine().split(" ");
            n = Integer.parseInt(array[0]);
            m = Integer.parseInt(array[1]);
            matrix = new Element[n + 2][m + 2];
            String line = reader.readLine();
            int i = 1;
            while (line != null) {
                array = line.split(" ");
                for (int j = 1; j < m + 1; ++j) {
                    matrix[i][j] = new Element(i, j, Integer.parseInt(array[j - 1]));
                }
                ++i;
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        // окаймляем матрицу нулями
        for (int i = 0; i < n + 2; ++i) {
            matrix[i][0] = new Element(i, 0, 0);
            matrix[i][m + 1] = new Element(i, m + 1, 0);
        }
        for (int j = 0; j < m + 2; ++j) {
            matrix[0][j] = new Element(0, j, 0);
            matrix[n + 1][j] = new Element(n + 1, j, 0);
        }

//        for (int i = 0; i < n+2; ++i) {
//            for (int j = 0; j < m+2; ++j) {
//                System.out.print(matrix[i][j].value + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();

        // решение
        int result = 0;
        D = findMaxElement();
        while (true) {
            Element start = findMinElement();
            z = start.value;
            if (z == D) break;
            p = D;
            queue.push(start);
            int numberOfElementsViewed = 0;
            while (!queue.isEmpty()) {
                Element element = queue.poll();
                ++numberOfElementsViewed;
                element.value = 2001; // элемент просмотрен
                for (int k = 0; k < 4; ++k) {
                    int x = element.x + delta[k][0];
                    int y = element.y + delta[k][1];
                    Element neighbor = matrix[x][y];
                    if (neighbor.value != z) {
                        p = Math.min(p, neighbor.value);
                    } else if (!queue.contains(neighbor)) {
                        queue.add(neighbor);
                    }
                }
            }
//            if (p <= z) {
//                for (int i = 1; i < n+1; ++i) {
//                    for (int j = 1; j < m+1; ++j) {
//                        if (matrix[i][j].value == 2001) matrix[i][j].value = D;
//                    }
//                }
//            }
            if (p > z) {
                result += ((p - z) * numberOfElementsViewed);
//                System.out.println(z);
//                System.out.println(p);
//                System.out.println(numberOfElementsViewed);
                for (int i = 1; i < n + 1; ++i) {
                    for (int j = 1; j < m + 1; ++j) {
                        if (matrix[i][j].value == 2001) matrix[i][j].value = p;
                    }
                }
            }
//            for (int i = 0; i < n+2; ++i) {
//                for (int j = 0; j < m+2; ++j) {
//                    System.out.print(matrix[i][j].value + " ");
//                }
//                System.out.println();
//            }
//            System.out.println();
        }
        // запись в файл
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUT))) {
            writer.write(String.valueOf(result));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
