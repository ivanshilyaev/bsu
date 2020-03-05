package by.bsu.tasks.binarytree;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Node {
    int value;
    Node right;
    Node left;
    /*
     * высота вершины
     */
    int h;
    /*
     * Вес (сумма ключей вершин этого пути)
     * самого тяжёлого пути, выходящего из
     * данной вершины, и имеющего длину h
     */
    int w;
    /*
     * длина наибольшего полупути
     * с корнем в данной вершине
     * (maximum semipath length)
     */
    int msl;

    public Node(int value) {
        this.value = value;
        this.right = null;
        this.left = null;
    }
}

class Tree {
    Node root;

    public boolean isEmpty() {
        return root == null;
    }

    private int findLeastValue(Node node) {
        return node.left == null ? node.value : findLeastValue(node.left);
    }

    public void add(int value) {
        root = addRecursively(value, root);
    }

    public Node find(int value) {
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

    public int countTags() {
        return countTagsRecursively(root, 0);
    }

    public Node findPath(int length, Node comparingNode) {
        return findPathRecursively(root, length, comparingNode);
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

    private Node findRecursively(int value, Node node) {
        if (node == null) {
            return null;
        }
        if (value == node.value) {
            return node;
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

    private void countHeight(Node node) {
        if (node.left == null && node.right == null) {
            node.h = 0;
        } else if (node.left == null) {
            node.h = node.right.h + 1;
        } else if (node.right == null) {
            node.h = node.left.h + 1;
        } else {
            node.h = Math.max(node.left.h, node.right.h) + 1;
        }
    }

    private void countWeight(Node node) {
        if (node.left == null && node.right == null) {
            node.w = node.value;
        } else if (node.left == null) {
            node.w = node.right.w + node.value;
        } else if (node.right == null) {
            node.w = node.left.w + node.value;
        } else {
            node.w = node.left.h > node.right.h ? node.left.w + node.value :
                    node.right.w + node.value;
        }
    }

    private void countMSL(Node node) {
        if (node.left != null && node.right != null) {
            node.msl = node.left.h + node.right.h + 2;
        } else {
            node.msl = node.h;
        }
    }

    /*
     * Метод подсчитывает метки данной вершины
     * (высоту, вес и длину наибольшего полупути
     * с корнем в данной вершине)
     * и возвращает длину наибольшего полупути
     * во всём дереве
     */
    private int countTagsRecursively(Node node, int max) {
        if (node != null) {
            max = countTagsRecursively(node.left, max);
            max = countTagsRecursively(node.right, max);
            countHeight(node);
            countWeight(node);
            countMSL(node);
            return Math.max(node.msl, max);
        }
        return max;
    }

    private int weight(Node node) {
        int sum = node.value;
        if (node.left != null) {
            sum += node.left.w;
        }
        if (node.right != null) {
            sum += node.right.w;
        }
        return sum;
    }

    /*
     * Метод находит наибольший полупуть
     * с максимальным весом
     * (то есть искомый путь задачи)
     * и возвращает корень этого пути
     */
    private Node findPathRecursively(Node node, int length, Node comparingNode) {
        if (node != null) {
            comparingNode = findPathRecursively(node.left, length, comparingNode);
            comparingNode = findPathRecursively(node.right, length, comparingNode);
            // !!!
            if (node.msl == length && weight(node) > weight(comparingNode)) {
                return node;
            }
        }
        return comparingNode;
    }

    // Ошибка была в этом методе. Неправильно перерасчитывал,
    // сколько вершин до средней в пути оставалось пройти
    public Node findCentralNode(Node node, int length) {
        if ((length == 0) || (node.right == null && node.left == null)) {
            return node;
        } else {
            if (node.left != null && node.right != null) {
                if (node.left.h == node.right.h) {
                    return node;
                } else if (node.left.h > node.right.h) {
                    // !
                    if (node.msl == 2 * length) {
                        length = (node.left.h - node.right.h) / 2;
                    }
                    return findCentralNode(node.left, --length);
                } else {
                    // !
                    if (node.msl == 2 * length) {
                        length = (node.right.h - node.left.h) / 2;
                    }
                    return findCentralNode(node.right, --length);
                }
            } else {
                --length;
                if (node.left != null) {
                    return findCentralNode(node.left, length);
                } else {
                    return findCentralNode(node.right, length);
                }
            }
        }
    }
}

public class Runner implements Runnable {
    private static final String IN = "/Users/ivansilaev/Desktop/in.txt";
    private static final String OUT = "/Users/ivansilaev/Desktop/out.txt";

    //private static final String IN = "in.txt";
    //private static final String OUT = "out.txt";

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

        // нахождение высоты каждой вершины
        int length = tree.countTags();
        // !!!
        //Node desiredRoot = tree.root;
        Node desiredRoot = new Node(Integer.MIN_VALUE);
        desiredRoot.h = 0;
        desiredRoot.w = desiredRoot.value;
        desiredRoot.msl = 0;
        desiredRoot = tree.findPath(length, desiredRoot);
        //System.out.println(desiredRoot.value);
        if (length % 2 == 0) {
            Node center = tree.findCentralNode(desiredRoot, length / 2);
            //System.out.println(center.value);
            if (center.value != desiredRoot.value) {
                tree.delete(center.value);
            }
        }
        tree.delete(desiredRoot.value);

        List<Integer> list = new ArrayList<>();
        tree.preOrderTraverse(list);
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
