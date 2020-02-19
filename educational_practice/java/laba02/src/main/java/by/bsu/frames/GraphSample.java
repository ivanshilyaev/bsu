package by.bsu.frames;

import java.awt.*;

public interface GraphSample {
    String getName();

    int getWidth();

    int getHeight();

    void draw(Graphics2D g, Component c);
}
