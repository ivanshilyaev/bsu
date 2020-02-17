package by.bsu.tasks.basic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Node {
    int value;
    Node right;
    Node left;

    public Node(int value) {
        this.value = value;
        this.right = null;
        this.left = null;
    }
}

class Tree {
    private Node root;

    public boolean isEmpty() {
        return root == null;
    }

    private int findLeastValue(Node node) {
        return node.left == null ? node.value : findLeastValue(node.left);
    }

    public void add(int value) {
        root = addRecursively(value, root);
    }

    public boolean find(int value) {
        return findRecursively(value, root);
    }

    public void delete(int value) {
        root = deleteRecursively(value, root);
    }

    public void inOrderTraverse(List<Integer> list) {
        inOrderTraverseRecursively(root, list);
    }

    public void preOrderTraverse(List<Integer> list) {
        preOrderTraverseRecursively(root, list);
    }

    public void postOrderTraverse(List<Integer> list) {
        postOrderTraverseRecursively(root, list);
    }

    private Node addRecursively(int value, Node node) {
        if (node == null) {
            return new Node(value);
        }
        if (value < node.value) {
            node.left = addRecursively(value, node.left);
        } else if (value > node.value) {
            node.right = addRecursively(value, node.right);
        } else {
            return node;
        }
        return node;
    }

    private boolean findRecursively(int value, Node node) {
        if (node == null) {
            return false;
        }
        if (value == node.value) {
            return true;
        }
        return value < node.value ?
                findRecursively(value, node.left) :
                findRecursively(value, node.right);
    }

    public Node deleteRecursively(int value, Node node) {
        if (node == null) {
            return null;
        }

        if (value < node.value) {
            node.left = deleteRecursively(value, node.left);
        } else if (value > node.value) {
            node.right = deleteRecursively(value, node.right);
        } else {
            // deleting
            if (node.left == null && node.right == null) {
                return null;
            }
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            int leastValue = findLeastValue(node.right);
            node.value = leastValue;
            node.right = deleteRecursively(leastValue, node.right);
            return node;
        }
        return node;
    }

    private void inOrderTraverseRecursively(Node node, List<Integer> list) {
        if (node != null) {
            inOrderTraverseRecursively(node.left, list);
            list.add(node.value);
            inOrderTraverseRecursively(node.right, list);
        }
    }

    private void preOrderTraverseRecursively(Node node, List<Integer> list) {
        if (node != null) {
            list.add(node.value);
            preOrderTraverseRecursively(node.left, list);
            preOrderTraverseRecursively(node.right, list);
        }
    }

    private void postOrderTraverseRecursively(Node node, List<Integer> list) {
        if (node != null) {
            postOrderTraverseRecursively(node.left, list);
            postOrderTraverseRecursively(node.right, list);
            list.add(node.value);
        }
    }
}

public class Runner implements Runnable {
    private static final String IN = "/Users/ivansilaev/Desktop/input.txt";
    private static final String OUT = "/Users/ivansilaev/Desktop/output.txt";

    //private static final String IN = "input.txt";
    //private static final String OUT = "output.txt";

    public static void main(String[] args) {
        new Thread(null, new Runner(), "", 64 * 1024 * 1024).start();
    }

    @Override
    public void run() {
        Tree tree = new Tree();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(IN))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                tree.add(Integer.parseInt(line));
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        List<Integer> list = new ArrayList<>();
        tree.inOrderTraverse(list);
        if (!tree.isEmpty()) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUT))) {
                for (Integer value : list.subList(0, list.size() - 1)) {
                    bufferedWriter.write(value + "\n");
                }
                bufferedWriter.write(String.valueOf(list.get(list.size() - 1)));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
