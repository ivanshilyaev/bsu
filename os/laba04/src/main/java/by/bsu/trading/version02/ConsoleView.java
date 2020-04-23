package by.bsu.trading.version02;

public class ConsoleView {
    public synchronized void printInputCheckerError(String testName) {
        String message = "Input data in the test " + testName + " isn't correct";
        System.out.println(message);
    }

    public synchronized void displayResult(String testName, boolean testPassed) {
        String message;
        if (testPassed) {
            message = "The test " + testName + " is passed";
        } else {
            message = "The test " + testName + " isn't passed";
        }
        System.out.println(message);
    }
}
