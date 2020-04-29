package by.bsu.swimming;

import javax.swing.*;

public class MoveCircle {
    public static void main(String[] args) {
        Circle circle = new Circle(0, 50, 5);
        JFrame jFrame = new JFrame();
        jFrame.add(circle);
        jFrame.setVisible(true);
        jFrame.setSize(600, 400);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setTitle("Circle");
    }
}
