package by.bsu.trading.version02;

public class View {
    public void displayResult(int id, boolean testPassed) {
        if (testPassed) {
            System.out.println("The test " + id + " is passed");
        } else {
            System.out.println("The test " + id + " isn't passed");
        }
    }
}
