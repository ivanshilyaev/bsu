package by.bsu.swimming.mainVersion;

import javax.swing.*;

public class View {
    private JFrame frame;

    public View(String title, SwimmerPanel panel) {
        frame = new JFrame(title);

        frame.add(panel);
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        panel.startAnimation();
    }
}
