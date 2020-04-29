package by.bsu.swimming.mainVersion;

import javax.swing.*;

public class View {
    private JFrame frame;
    private JButton start;

    public View(String title, SwimmerPanel panel, Referee referee) {
        frame = new JFrame(title);
        start = new JButton("Start");
        start.setBounds(250, 400, 100, 25);
        start.addActionListener(e -> referee.start());

        frame.setLayout(null);
        frame.setSize(600, 500);
        frame.add(start);
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        panel.startAnimation();
    }
}
