package by.bsu.swimming;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class Circle extends JPanel implements ActionListener {
    Timer timer = new Timer(5, this);
    private double x;
    private double y;
    private double velX;

    public Circle(double x, double y, double velX) {
        this.x = x;
        this.y = y;
        this.velX = velX;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        Ellipse2D circle = new Ellipse2D.Double(x, y, 10, 10);
        graphics2D.fill(circle);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (x < 0 || x > 570) velX = -velX;
        x += velX;
        repaint();
    }
}
