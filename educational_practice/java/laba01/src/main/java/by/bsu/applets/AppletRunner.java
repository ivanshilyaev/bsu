package by.bsu.applets;

import java.applet.Applet;
import java.awt.*;
import java.awt.geom.AffineTransform;

/*<applet code="AppletEllipse" width=500 height=300>
<param name=borderColor value=#EFFF00>
<param name=backgroundColor value=#FF0044>
<param name=border value=2>
</applet>*/

public class AppletRunner extends Applet implements Runnable {
    private int height, width, x, y, centerX, centerY;
    private float border;
    private Color borderColor, backgroundColor;
    private EllipseFigure ellipseFigure;
    private double angle;
    private static final double DELTA_ANGLE = Math.toRadians(2);
    private AffineTransform transform = new AffineTransform();
    private static int WINDOW_WIDTH = 500;
    private static int WINDOW_HEIGHT = 400;
    private Thread t = null;

    @Override
    public void init() {
        angle = 0;
        setBackground(Color.WHITE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        try {
            border = Float.parseFloat(this.getParameter("border"));
        } catch (Exception e) {
            border = 1;
        }
        borderColor = getColor(this.getParameter("borderColor"), new Color(0, 0, 0));
        backgroundColor = getColor(this.getParameter("backgroundColor"), new Color(255, 0, 0));
        centerX = WINDOW_WIDTH / 2;
        centerY = WINDOW_HEIGHT / 2;
        height = 50;
        width = 100;
        x = centerX - width / 2;
        y = centerY - height / 2;
        ellipseFigure = new EllipseFigure(x, y, width, height, borderColor, backgroundColor);
    }

    @Override
    public void start() {
        if (t == null) {
            t = new Thread(this);
        }
        t.start();
    }

    @Override
    public void stop() {
        if (t != null) {
            t.interrupt();
            t = null;
        }
    }

    @Override
    public void destroy() {
        if (t != null) {
            t.interrupt();
            t = null;
        }
    }

    @Override
    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setStroke(new BasicStroke(border));
        angle += DELTA_ANGLE;
        transform = AffineTransform.getRotateInstance(angle,
                x + width / 2, y + height / 2);
        graphics2D.setTransform(transform);
        ellipseFigure.paint(graphics2D);
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(25);
            } catch (InterruptedException ignored) {
                break;
            }
        }
    }

    private Color getColor(String strRGB, Color color) {
        if (strRGB != null && strRGB.charAt(0) == '#') {
            try {
                return new Color(Integer.parseInt(strRGB.substring(1), 16));
            } catch (NumberFormatException e) {
                return color;
            }
        }
        return color;
    }
}
