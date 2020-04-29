package by.bsu.swimming.mainVersion;

public class Runner {
    public static void main(String[] args) {
        SwimmerPanel panel = new SwimmerPanel();
        Referee referee = new Referee(panel);
        View view = new View("Swimming", panel, referee);
    }
}
