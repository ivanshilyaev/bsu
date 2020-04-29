package by.bsu.swimming.funVersion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SwimmerPanel extends JPanel implements ActionListener {
    private ConcurrentLinkedQueue<Swimmer01> swimmers = new ConcurrentLinkedQueue<>();

    public void startAnimation() {
        Timer timer = new Timer(100, this);
        timer.start();
    }

    public ConcurrentLinkedQueue<Swimmer01> getSwimmers() {
        return swimmers;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        Ellipse2D circle;
        for (Swimmer01 swimmer01 : swimmers) {
            circle = new Ellipse2D.Double(swimmer01.getDistance() * 24.0,
                    swimmer01.getLane() * 100.0, 10, 10);
            graphics2D.fill(circle);
        }
    }

    private void move() {
        for (Swimmer01 swimmer01 : swimmers) {
            swimmer01.move();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }
}
