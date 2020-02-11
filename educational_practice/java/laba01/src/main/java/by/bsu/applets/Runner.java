package by.bsu.applets;

import java.applet.Applet;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Runner extends Applet implements Runnable {
    private int ellipseHeight, ellipseWidth, ellispeX, ellipseY, centerX, centerY;
    private int rectangleHeight, rectangleWidth, rectangleX, rectangleY;
    private float border;
    private Color ellipseBorderColor, ellispeBackgroundColor;
    private Color rectangleBorderColor, rectangleBackgroundColor;
    private EllipseFigure ellipseFigure;
    private RectangleFigure rectangleFigure;
    private double angle1, angle2;
    private static final double DELTA_ANGLE = Math.toRadians(2);
    private AffineTransform transform1 = new AffineTransform();
    private AffineTransform transform2 = new AffineTransform();
    private static int WINDOW_WIDTH = 500;
    private static int WINDOW_HEIGHT = 400;
    private Thread t = null;

    @Override
    public void init() {
        angle1 = 0;
        angle2 = 0;
        setBackground(Color.WHITE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        try {
            border = Float.parseFloat(this.getParameter("border"));
        } catch (Exception e) {
            border = 3;
        }
        ellipseBorderColor = getColor(this.getParameter("ellipseBorderColor"), new Color(0, 0, 0));
        ellispeBackgroundColor = getColor(this.getParameter("ellipseBackgroundColor"), new Color(255, 0, 0));
        rectangleBorderColor = getColor(this.getParameter("rectangleBorderColor"), new Color(0, 0, 0));
        rectangleBackgroundColor = getColor(this.getParameter("rectangleBackgroundColor"), new Color(0, 255, 0));
        centerX = WINDOW_WIDTH / 2;
        centerY = WINDOW_HEIGHT / 2;
        ellipseHeight = 100;
        ellipseWidth = 200;
        rectangleHeight = 100;
        rectangleWidth = 200;
        ellispeX = centerX - ellipseWidth / 2;
        ellipseY = centerY - ellipseHeight / 2;
        rectangleX = centerX - ellipseWidth / 2;
        rectangleY = centerY - ellipseHeight / 2;
        rectangleFigure = new RectangleFigure(rectangleX, rectangleY, rectangleWidth, rectangleHeight,
                rectangleBorderColor, rectangleBackgroundColor);
        ellipseFigure = new EllipseFigure(ellispeX, ellipseY, ellipseWidth, ellipseHeight,
                ellipseBorderColor, ellispeBackgroundColor);
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
        angle1 += DELTA_ANGLE;
        angle2 -= DELTA_ANGLE;
        transform1 = AffineTransform.getRotateInstance(angle1,
                centerX, centerY);
        transform2 = AffineTransform.getRotateInstance(angle2,
                centerX, centerY);
        graphics2D.setTransform(transform2);
        rectangleFigure.paint(graphics2D);
        graphics2D.setTransform(transform1);
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
