package com;

import java.applet.Applet;
import java.awt.*;

public class MoveArrow extends Applet implements Runnable {
    private static final long serialVersionUID = 1L;

    private volatile boolean fSuspend = false;

    private Canvas canvas;

    private synchronized boolean getFSuspend() {
        return fSuspend;
    }
    private synchronized void setFSuspend( boolean value ) {
        fSuspend = value;
    }
    public void suspend() { setFSuspend(true); }
    public void resume() { setFSuspend(false); }
    public boolean isSuspended() { return getFSuspend(); }

    final int NUM = 360;
    int RLEN;
    final double UGI2 = 2 * Math.PI / NUM;
    Color arrowColor;

    int i, x1, y1, x2, y2;
    int xl2, yl2, xr2, yr2;
    double ug2;

    Thread t = null;

    public void drawInit() {
        String backgroundColor = getParameter("BackgroundColor");
        String radius = getParameter("Radius");
        String x = getParameter("x");
        String y = getParameter("y");
        String arColor = getParameter("ArrowColor");
        setBackground(new Color(Integer.parseInt(backgroundColor, 16)));
        RLEN = Integer.parseInt(radius);
        i = 0;
        x1 = x2 = Integer.parseInt(x);
        y1 = y2 = Integer.parseInt(y);
        arrowColor = new Color(Integer.parseInt(arColor, 16));
    }
    public void drawNext() {
        ug2 = i * UGI2;
        x2 = (int) Math.round(x1 + RLEN * Math.sin(ug2));
        y2 = (int) Math.round(y1 + RLEN * Math.cos(ug2));
        xl2 = (int) Math.round(x1 + 20 * Math.sin(ug2 - 0.2));
        yl2 = (int) Math.round(y1 + 20 * Math.cos(ug2 - 0.2));
        xr2 = (int) Math.round(x1 + 20 * Math.sin(ug2 + 0.2));
        yr2 = (int) Math.round(y1 + 20 * Math.cos(ug2 + 0.2));
        if ((i += 1) >= NUM) {
            i = 0;
        }
        repaint();
    }
    public void paint(Graphics g) {
        g.setColor(arrowColor);
        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x1, y1, xl2, yl2);
        g.drawLine(x1, y1, xr2, yr2);
    }

    public void init() {
        if (t == null) {
            setFSuspend(false);
            drawInit();
            t = new Thread(this);
        }
        t.start();
    }
    public void start() {
        resume();
    }
    public void stop() {
        suspend();
    }
    public void destroy() {
        if (t != null) {
            t.interrupt();
            t = null;
        }
    }
    public void run() {
        suspend();
        while (true) {
            try {
                Thread.sleep(10);
                if (!isSuspended()) {
                    drawNext();
                }
            } catch (Exception e) {
                break;
            }
        }
    }
}
