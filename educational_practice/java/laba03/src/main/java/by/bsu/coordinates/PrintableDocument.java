package by.bsu.coordinates;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class PrintableDocument implements Printable {
    private static String fileName = "/Users/ivansilaev/Desktop/bsu/educational_practice/java/laba03/src/main/java/by/bsu/coordinates/PascalSnail.java";
    private Shape plot;

    public PrintableDocument(Shape pl) {
        this.plot = pl;
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) {
        if (pageIndex != 0)
            return NO_SUCH_PAGE;
        int a = 100;
        int b = (int)pf.getImageableHeight()*3/4;
        Graphics2D g2d = (Graphics2D) g;
        String c;
        g2d.setFont(new Font("Serif", Font.PLAIN, 26));
        g2d.drawString("Pascal Snail: (x^2 + y^2 - 2rx)^2 - l^2(x^2 + y^2) = 0" , (int) (pf.getImageableWidth()/2-150),100);
        Rectangle2D outline = new Rectangle2D.Double(0,0, pf.getImageableWidth()*2, pf.getImageableHeight()*2);
        g2d.setFont(new Font("Serif", Font.PLAIN, 5));
        try (BufferedReader f = new BufferedReader(new FileReader(fileName))) {
            while ((c = f.readLine()) != null) {
                g2d.drawString(c, a, b);
                b+=5;
                if(b>pf.getImageableHeight()*5/4-40) {
                    b=(int)pf.getImageableHeight()*3/4;
                    a+=150;
                }

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        plot = new PascalSnail(0.025, 300, 150, 600, 150);
        g2d.draw(outline);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new PascalStroke(2));
        g2d.draw(plot);
        return PAGE_EXISTS;
    }
}