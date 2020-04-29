package by.bsu.swimming.mainVersion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SwimmerPanel extends JPanel implements ActionListener {
    private ConcurrentLinkedQueue<Swimmer> swimmers = new ConcurrentLinkedQueue<>();

    public SwimmerPanel() {
        this.setLayout(null);
        this.setSize(600, 400);
    }

    public void startAnimation() {
        // for testing
        Timer timer = new Timer(10, this);
        timer.start();
    }

    public ConcurrentLinkedQueue<Swimmer> getSwimmers() {
        return swimmers;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.drawLine(0, 60, 600, 60);
        graphics2D.drawLine(0, 160, 600, 160);
        graphics2D.drawLine(0, 260, 600, 260);
        graphics2D.drawLine(0, 360, 600, 360);
        Ellipse2D circle;
        for (Swimmer swimmer : swimmers) {
            circle = new Ellipse2D.Double(swimmer.getDistance() * 2.0,
                    swimmer.getLane() * 100.0, 20, 20);
            graphics2D.setColor(swimmer.getColor());
            graphics2D.fill(circle);
        }
    }

    private void move() {
        for (Swimmer swimmer : swimmers) {
            swimmer.move();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }
}
