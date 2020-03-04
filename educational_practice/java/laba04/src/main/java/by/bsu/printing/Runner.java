package by.bsu.printing;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.Sides;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;


public class Runner extends JFrame {
    private Shape plot;
    private JButton button;

    Runner() {
        setTitle("Pascal snail");
        setSize(600, 600);
        button = new JButton("Print");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PrinterJob job = PrinterJob.getPrinterJob();
                PageFormat pf = job.defaultPage();
                PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
                printRequestAttributeSet.add(Sides.DUPLEX);
                printRequestAttributeSet.add(OrientationRequested.LANDSCAPE);

                job.setPrintable(new PrintableDocument(plot), pf);
                boolean ok = job.printDialog();
                if (ok) {
                    try {
                        job.print(printRequestAttributeSet);
                    } catch (PrinterException ex) {
                        System.err.print(ex);
                    }
                }
            }
        });
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());
        cp.add(button);
        this.setSize(600, 600);
        setVisible(true);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.BLACK);
        g2.setStroke(new PascalStroke(2));
        g2.draw(new PascalSnail(0.025, 300, 150, 600, 150));
    }

    public static void main(String[] args) {
        new Runner();
    }
}