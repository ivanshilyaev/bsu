package by.bsu.trading.version02;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class View {
    private JFrame frame;
    private JPanel contentPane;
    private JLabel jLabel1;
    public JTextField textField1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JTextArea jTextArea;
    private JScrollPane jScrollPane;

    private boolean pauseSolveThread;

    private boolean shouldStart;

    public boolean isShouldStart() {
        return shouldStart;
    }

    public String getDirectory() {
        return textField1.getText();
    }

    public boolean isPauseSolveThread() {
        return pauseSolveThread;
    }

    public View(String title) {
        frame = new JFrame(title);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        frame.setContentPane(contentPane);

        jLabel1 = new JLabel("Directory:");
        jLabel1.setBounds(50, 50, 100, 25);
        textField1 = new JTextField();
        textField1.setBounds(150, 50, 300, 25);

        // start
        button1 = new JButton("Start");
        button1.setBounds(50, 100, 100, 25);
        button1.addActionListener(e -> shouldStart = true);

//        // stop solve thread
//        button2 = new JButton("Stop");
//        button2.setBounds(200, 100, 100, 25);
//        button2.addActionListener(e -> pauseSolveThread = true);
//
//        // resume
//        button3 = new JButton("Resume");
//        button3.setBounds(350, 100, 100, 25);
//        button3.addActionListener(e -> pauseSolveThread = false);

        // result
        jTextArea = new JTextArea();
        jScrollPane = new JScrollPane();
        jScrollPane.setBounds(50, 150, 500, 200);
        contentPane.add(jScrollPane);
        jScrollPane.setViewportView(jTextArea);

        frame.setLayout(null);
        frame.add(jLabel1);
        frame.add(textField1);
        frame.add(button1);
        //frame.add(button2);
        //frame.add(button3);
        frame.setSize(600, 600);
        frame.setVisible(true);
    }

    public synchronized void printInputCheckerError(String testName) {
        Document document = jTextArea.getDocument();
        String message = "Input data in the test " + testName + " isn't correct";
        try {
            document.insertString(document.getLength(), message + " \r\n", null);
        } catch (BadLocationException e) {
            //
        }
    }

    public synchronized void displayResult(String testName, boolean testPassed) {
        String message;
        if (testPassed) {
            message = "The test " + testName + " is passed";
        } else {
            message = "The test " + testName + " isn't passed";
        }
        Document document = jTextArea.getDocument();
        try {
            document.insertString(document.getLength(), message + " \r\n", null);
        } catch (BadLocationException e) {
            //
        }
    }
}


//package by.bsu.trading.version02;
//
//public class View {
//    public synchronized void printInputCheckerError(String testName) {
//        String message = "Input data in the test " + testName + " isn't correct";
//        System.out.println(message);
//    }
//
//    public synchronized void displayResult(String testName, boolean testPassed) {
//        String message;
//        if (testPassed) {
//            message = "The test " + testName + " is passed";
//        } else {
//            message = "The test " + testName + " isn't passed";
//        }
//        System.out.println(message);
//    }
//}
