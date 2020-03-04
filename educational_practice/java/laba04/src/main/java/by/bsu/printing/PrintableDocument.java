package by.bsu.printing;

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
    private static String fileName = "/Users/ivansilaev/Desktop/bsu/educational_practice/java/laba04/src/main/java/by/bsu/printing/PascalSnail.java";
    private Shape plot;

    public PrintableDocument(Shape pl) {
        this.plot = pl;
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) {
        if (pageIndex != 0)
            return NO_SUCH_PAGE;
        int a = 100, b = (int) pf.getImageableHeight() * 3 / 4;
        Graphics2D g2d = (Graphics2D) g;
        String c;
        g2d.setFont(new Font("Serif", Font.PLAIN, 5));
        g2d.drawString("Pascal Snail", (int) (pf.getImageableWidth() / 2), 100);
        Rectangle2D outline = new Rectangle2D.Double(0, 0, pf.getImageableWidth() * 2, pf.getImageableHeight() * 2 - 40);
        g2d.setFont(new Font("Serif", Font.PLAIN, 20));
        try (BufferedReader f = new BufferedReader(new FileReader(fileName));) {
            while ((c = f.readLine()) != null) {

//         				if (c.length()>149)
//         				{
//         					String d = c.substring(149);
//         					g2d.drawString(d, a,b);
//          				   b+=5;
//          				   if(b>pf.getImageableHeight()*5/4-40)
//          				   {
//          					   b=(int)pf.getImageableHeight()*3/4;
//          					   a+=150;
//          				   }
//          				   String e = c.substring(150, c.length()-1);
//          				   g2d.drawString(e, a,b);
//          				   b+=5;
//          				   if(b>pf.getImageableHeight()*5/4-40)
//          				   {
//          					   b=(int)pf.getImageableHeight()*3/4;
//          					   a+=150;
//          				   }
//
//         				}
//         				else {
                g2d.drawString(c, a, b);
                b += 5;
                if (b > pf.getImageableHeight() * 5 / 4 - 40) {
                    b = (int) pf.getImageableHeight() * 3 / 4;
                    a += 150;
                }
            }

//         			}
        } catch (IOException e) {
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