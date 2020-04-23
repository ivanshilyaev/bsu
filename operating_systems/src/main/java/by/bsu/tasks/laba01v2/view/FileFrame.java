package by.bsu.tasks.laba01v2.view;

import by.bsu.tasks.laba01v2.service.FileThread;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FileFrame {
    private JFrame frame;
    private JPanel contentPane;
    public JTextField textField1;
    public JTextField textField2;
    public JTextField textField3;
    public JCheckBox jCheckBox;
    public JButton button1;
    public JButton button2;
    public JButton button3;
    public JRadioButton jRadioButton1;
    public JRadioButton jRadioButton2;
    public JRadioButton jRadioButton3;
    public JTextArea jTextArea;
    public JScrollPane jScrollPane;

    public boolean isPaused;
    public boolean isStopped;

    public void setTitle(String title) {
        frame.setTitle(title);
    }

    public FileFrame(String title, FileThread fileThread) {
        frame = new JFrame(title);

        isPaused = true;
        isStopped = false;

        contentPane = new JPanel();
        contentPane.setLayout(null);
        frame.setContentPane(contentPane);

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

        // searching in subdirectories
        jCheckBox = new JCheckBox("Search in subdirectories");
        jCheckBox.setBounds(50, 200, 300, 25);

        // priority
        jRadioButton1 = new JRadioButton("MIN PRIORITY");
        jRadioButton1.setBounds(50, 250, 300, 25);
        jRadioButton2 = new JRadioButton("NORM PRIORITY");
        jRadioButton2.setBounds(50, 300, 300, 25);
        jRadioButton3 = new JRadioButton("MAX PRIORITY");
        jRadioButton3.setBounds(50, 350, 300, 25);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(jRadioButton1);
        buttonGroup.add(jRadioButton2);
        buttonGroup.add(jRadioButton3);

        // start
        button1 = new JButton("Start");
        button1.setBounds(50, 400, 100, 25);
        button1.addActionListener(e -> isPaused = false);

        // stop
        button2 = new JButton("Stop");
        button2.setBounds(150, 400, 100, 25);
        button2.addActionListener(e -> {
            isStopped = true;
        });

        // pause
        button3 = new JButton("Pause");
        button3.setBounds(250, 400, 100, 25);
        button3.addActionListener(e -> {
            if (button3.getText().equals("Pause")) {
                isPaused = true;
                button3.setText("Resume");
            } else {
                isPaused = false;
                button3.setText("Pause");
            }
        });

        // result
        jTextArea = new JTextArea();
        jScrollPane = new JScrollPane();
        jScrollPane.setBounds(50, 450, 500, 300);
        contentPane.add(jScrollPane);
        jScrollPane.setViewportView(jTextArea);

        // exit
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
            }
        });

        frame.setLayout(null);
        frame.add(label1);
        frame.add(textField1);
        frame.add(label2);
        frame.add(textField2);
        frame.add(label3);
        frame.add(textField3);
        frame.add(jCheckBox);
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);
        frame.add(jRadioButton1);
        frame.add(jRadioButton2);
        frame.add(jRadioButton3);
        frame.setSize(600, 800);
        frame.setVisible(true);
    }
}