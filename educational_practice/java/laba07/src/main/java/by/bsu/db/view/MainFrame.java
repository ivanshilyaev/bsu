package by.bsu.db.view;

import javax.swing.*;

public class MainFrame {
    private JFrame frame;

    public void setTitle(String title) {
        frame.setTitle(title);
    }

    public MainFrame(String title) {
        frame = new JFrame(title);

        frame.setLayout(null);
        frame.setSize(400, 600);
        frame.setVisible(true);
    }
}
