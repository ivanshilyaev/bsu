package by.bsu.applets;

import java.awt.*;
import java.awt.geom.*;

public class RectangleFigure extends Canvas implements Shape {
    private Rectangle2D rectangleFigure;
    private Color borderColor;
    private Color backgroundColor;

    public RectangleFigure(int x, int y, int width, int height,
                           Color borderColor, Color backgroundColor) {
        this.rectangleFigure = new Rectangle2D.Double(x, y, width, height);
        this.borderColor = borderColor;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }

    @Override
    public boolean contains(double x, double y) {
        return false;
    }

    @Override
    public boolean contains(Point2D p) {
        return false;
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return false;
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        return false;
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return false;
    }

    @Override
    public boolean contains(Rectangle2D r) {
        return false;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return null;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return null;
    }

    @Override
    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(backgroundColor);
        graphics2D.fillRect((int) rectangleFigure.getX(), (int) rectangleFigure.getY(),
                (int) rectangleFigure.getWidth(), (int) rectangleFigure.getHeight());
        graphics2D.setColor(borderColor);
        graphics2D.draw(rectangleFigure);
    }
}
