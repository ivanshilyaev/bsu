package by.bsu.frames;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Sign implements GraphSample {
    static final int WIDTH = 1000, HEIGHT = 500;

    public String getName() {
        return "Paints";
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public void draw(Graphics2D ig, Component c) {
        BufferedImage bufferedImage = new BufferedImage(WIDTH / 2, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setPaint(new GradientPaint(0, 0, new Color(200, 200, 200), 0, HEIGHT, new Color(100, 100, 100)));
        g.fillRect(0, 0, WIDTH / 2, HEIGHT);

        g.setColor(new Color(255, 0, 0));
        g.setStroke(new BasicStroke(50));
        g.drawRect(50, 50, WIDTH / 2 - 100, HEIGHT - 100);

        g.setColor(Color.white);
        g.fillRect(70, 70, WIDTH / 2 - 140, HEIGHT - 140);

        Font font = new Font("Serif", Font.BOLD, 4);
        Font bigFont = font.deriveFont(AffineTransform.getScaleInstance(30.0, 30.0));
        GlyphVector gv = bigFont.createGlyphVector(g.getFontRenderContext(),
                "STOP");
        Shape shapeD = gv.getOutline();

        g.setStroke(new BasicStroke(5.0f));

        Paint shadowPaint = new Color(0, 0, 0, 100);
        AffineTransform shadowTransform = AffineTransform.getShearInstance(-1.0, 0.0);
        shadowTransform.scale(1, 0.4);

        g.translate(90, 280);
        g.setPaint(shadowPaint);
        g.translate(0, 20);
        g.fill(shadowTransform.createTransformedShape(shapeD));
        g.translate(0, -20);
        g.setPaint(new Color(255, 0, 0));
        g.fill(shapeD);

        ig.drawImage(bufferedImage, 0, 0, c);
        ig.drawString("No filters", 10, 15);
        ig.drawImage(new AffineTransformOp(AffineTransform.getRotateInstance(-Math.PI / 4,
                WIDTH / 2 - 250, HEIGHT / 2),
                        AffineTransformOp.TYPE_BICUBIC).filter(bufferedImage, null),
                WIDTH / 2, 0, c);
        ig.drawString("Rotate CCW 45 degrees", WIDTH / 2 + 10, 15);
        ig.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
    }
}
