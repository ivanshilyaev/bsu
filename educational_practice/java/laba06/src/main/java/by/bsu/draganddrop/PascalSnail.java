package by.bsu.draganddrop;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class PascalSnail implements Shape {
    double flatness;
    double aParam;
    double lParam;
    int width;
    int height;

    public PascalSnail(double fl, double ap, double lp, int wh, int he) {
        this.flatness = fl;
        this.aParam = ap;
        this.lParam = lp;
        this.width = wh;
        this.height = he;
    }

    @Override
    public boolean contains(Point2D p) {
        return false;
    }

    @Override
    public boolean contains(Rectangle2D r) {
        return false;
    }

    @Override
    public boolean contains(double x, double y) {
        return false;
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return false;
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return new PascalIterator(this.flatness, this.aParam, this.lParam, this.width, this.height);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return new PascalIterator(flatness, this.aParam, this.lParam, this.width, this.height);
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        return false;
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return false;
    }

    static class PascalIterator implements PathIterator {
        double flatness; // плотность контура
        double aParam; // параметр для уравнения в полярных координатах
        double lParam; // параметр для уравнения в полярных координатах
        double currX;
        double currY;
        double currP; // полярный радиус
        double currFI = -Math.PI; // полярный угол
        int width;
        int height;
        boolean isDone = false;

        public PascalIterator(double fl, double ap, double lp, int wh, int he) {
            this.flatness = fl;
            this.aParam = ap;
            this.lParam = lp;
            this.width = wh;
            this.height = he;
        }

        @Override
        public int currentSegment(float[] coords) {
            if (currFI == -Math.PI) {
                currP = lParam - aParam * Math.sin(currFI);
                // переход от полярных к декартовым координатам
                currX = currP * Math.cos(currFI);
                currY = currP * Math.sin(currFI);
            }
            double x = currX + (double) width / 2;
            double y = (double) height / 2 - currY;
            coords[0] = (float) x;
            coords[1] = (float) y;

            if (currFI == -Math.PI)
                return SEG_MOVETO;

            if (Math.abs(currFI - Math.PI) < flatness)
                isDone = true;

            return SEG_LINETO;
        }

        @Override
        public int currentSegment(double[] coords) {
            if (currFI == -Math.PI) {
                currP = lParam - aParam * Math.sin(currFI);
                // переход от полярных к декартовым координатам
                currX = currP * Math.cos(currFI);
                currY = currP * Math.sin(currFI);
            }
            double x = currX + (double) width / 2;
            double y = (double) height / 2 - currY;
            coords[0] = (float) x;
            coords[1] = (float) y;

            if (currFI == -Math.PI)
                return SEG_MOVETO;

            if (Math.abs(currFI - Math.PI) < flatness)
                isDone = true;

            return SEG_LINETO;
        }

        @Override
        public int getWindingRule() {
            return WIND_NON_ZERO;
        }

        @Override
        public boolean isDone() {
            return isDone;
        }

        @Override
        public void next() {
            currFI += flatness;
            currP = lParam - aParam * Math.sin(this.currFI);
            currY = currP * Math.sin(this.currFI);
            currX = currP * Math.cos(this.currFI);
        }
    }
}
