package by.bsu.tasks.laba01v2.view;

import by.bsu.tasks.laba01v2.service.FileThread;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileFrame {
    private JFrame frame;
    public JTextField textField1;
    public JTextField textField2;
    public JTextField textField3;
    public JTextField textField4;
    public JButton button1;
    public JButton button2;
    public JButton button3;

    public boolean isPaused;

    private FileThread fileThread;

    public FileFrame(String title, FileThread fileThread) {
        frame = new JFrame(title);
        this.fileThread = fileThread;

        isPaused = true;

        JLabel label1 = new JLabel("Regex:");
        label1.setBounds(50, 50, 100, 25);
        textField1 = new JTextField();
        textField1.setBounds(150, 50, 200, 25);

        JLabel label2 = new JLabel("String to find:");
        label2.setBounds(50, 100, 100, 25);
        textField2 = new JTextField();
        textField2.setBounds(150, 100, 200, 25);

        JLabel label3 = new JLabel("Directory path:");
        label3.setBounds(50, 150, 100, 25);
        textField3 = new JTextField();
        textField3.setBounds(150, 150, 200, 25);

        button1 = new JButton("Start");
        button1.setBounds(50, 200, 100, 25);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPaused = false;
            }
        });

        button2 = new JButton("Stop");
        button2.setBounds(150, 200, 100, 25);

        button3 = new JButton("Pause");
        button3.setBounds(250, 200, 100, 25);

        // result
        textField4 = new JTextField();
        textField4.setBounds(50, 250, 500, 300);

        frame.setLayout(null);
        frame.add(label1);
        frame.add(textField1);
        frame.add(label2);
        frame.add(textField2);
        frame.add(label3);
        frame.add(textField3);
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);
        frame.add(textField4);
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
}
