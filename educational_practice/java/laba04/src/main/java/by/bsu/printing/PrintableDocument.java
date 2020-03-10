package by.bsu.printing;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class PrintableDocument implements Printable {
    private static String fileName = "/Users/ivansilaev/Desktop/bsu/educational_practice/java/laba04/src/main/java/by/bsu/printing/PascalSnail.java";
    private Shape plot;

    public PrintableDocument(Shape pl) {
        this.plot = pl;
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) {
        if (pageIndex != 0)
            return NO_SUCH_PAGE;
        int x = 50;
        int y = (int) pf.getImageableHeight() / 2 + 50;
        // pf.getImageableHeight() = 783
        // pf.getImageableWidth() = 559
        Graphics2D g2d = (Graphics2D) g;
        String line;
        g2d.setFont(new Font("Times", Font.PLAIN, 14));
        g2d.drawString("Pascal Snail", (int) (pf.getImageableWidth() / 2 - 20), 50);
        g2d.setFont(new Font("Times", Font.PLAIN, 5));
        try (BufferedReader f = new BufferedReader(new FileReader(fileName));) {
            while ((line = f.readLine()) != null) {
                g2d.drawString(line, x, y);
                y += 5;
                if (y > pf.getImageableHeight() - 20) {
                    y = (int) pf.getImageableHeight() / 2 + 50;
                    x += 170;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        plot = new PascalSnail(0.025, 200, 100, 600, 150);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new PascalStroke(2));
        g2d.draw(plot);
        return PAGE_EXISTS;
    }
}