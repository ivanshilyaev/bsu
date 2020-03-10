package by.bsu.coordinates;

import javax.swing.*;
import java.awt.*;

public class Runner extends JFrame {
    @Override
    public void paint(Graphics g) {
        setSize(600, 600);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new PascalStroke(2));
        g2d.draw(new PascalSnail(0.025, 300, 150, 600, 150));
    }

    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.setVisible(true);
        runner.pack();
    }
}
