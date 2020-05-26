import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Player {
    private static ArrayList<String> messages;
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static String currentPersonName;
    private static Mud.GameClientInterface currentPerson;
    private static Mud.GameClientInterface secondPerson;
    private static int max = 8;
    private static boolean NoOtherPlayers = true;

    public static void main(String[] args) {
        try {
            messages = new ArrayList<>();
            String hostname = "localhost";
            String username = "Home";
            Mud.GameServerInterface server = (Mud.GameServerInterface) Naming.lookup("rmi://" + hostname
                    + ":" + Server.PORT + "/" + username);
            System.out.println("You are connected to Tic Tac Toe!");
            runUser(username, server);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println("Usage: java FreeTimeClient <host> <user>");
            System.exit(1);
        }
    }

    private static void runUser(String username, Mud.GameServerInterface server) throws IOException, InterruptedException {
        currentPersonName = welcome(username, server);
        currentPerson = server.getPerson(currentPersonName);
        String cmd;
        while (true) {
            try {
                System.out.println();
                System.out.println(help);
                cmd = getLine("- ");
                switch (cmd) {
                    case "start":
                        System.out.println("\nChoose player to start a new game:");
                        showPlayers(server);
                        if (!NoOtherPlayers) {
                            StartGame(server, currentPersonName);
                        }
                        break;
                    case "show":
                        showPlayers(server);
                        break;
                    case "help":
                        System.out.println(help);
                        break;
                    case "quit":
                        delClient(server, currentPersonName);
                        System.out.println("Thank you for the game! Bye!");
                        System.out.flush();
                        System.exit(0);
                    case "Yes":
                        ConfirmGame(server);
                        break;
                    case "No":
                        DontConfirmGame(server);
                        break;
                    default:
                        System.out.println("Wrong command.  Try 'help'.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Syntax or other error:");
                System.out.println(e);
                System.out.println("Try 'help'.");
            }
        }
    }

    private static String welcome(String username, Mud.GameServerInterface server) throws IOException, InterruptedException {
        currentPerson = null;
        do {
            String name = getLine("Enter your name: ");
            PrintWriter out = new PrintWriter(System.out);
            Person current = new Person(name, out, in);
            if (server.addPerson(current)) {
                System.out.println("Name \"" + name + "\" has been successfully registered!");
                currentPerson = server.getPerson(name);
            } else {
                System.out.println("Error: name \"" + name + "\" is already in use.");
            }
        } while (currentPerson == null);
        return currentPerson.getName();
    }

    private static String getLine(String prompt) throws InterruptedException {
        String line = null;
        do {
            try {
                System.out.print(prompt);
                System.out.flush();
                line = in.readLine();
                if (line != null) line = line.trim();
            } catch (Exception ignored) {
            }
        } while ((line == null) || (line.length() == 0));

        return line;
    }

    private static void delClient(Mud.GameServerInterface server, String personName) throws IOException, InterruptedException {
        Mud.GameClientInterface person = server.getPerson(personName);
        server.delClient(person);
    }

    private static void showPlayers(Mud.GameServerInterface server) throws IOException, InterruptedException {
        String res = server.getPersons(currentPersonName);
        System.out.println(res);
        if (res.equals("Sorry, there are no more players on the server :("))
            NoOtherPlayers = true;
        else
            NoOtherPlayers = false;
    }

    private static void StartGame(Mud.GameServerInterface server, String personName) throws IOException, InterruptedException {
        String secondPersonName = getLine("- ");
        secondPerson = server.getPerson(secondPersonName);
        while (secondPerson == null) {
            System.out.println("No user with this name. Please, try again!\n");
            secondPersonName = getLine("- ");
            secondPerson = server.getPerson(secondPersonName);
        }
        sendMesssage(server, personName, "Do you want to play a game? (Yes or No)\n");
        secondPerson.setSecondPerson(personName);
        System.out.flush();
        while (true) {
            Thread.sleep(100);
            if (currentPerson.getConfirm()) {
                currentPerson.ShowGame("\n\nThe Game started!\n");
                secondPerson.ShowGame("\n\nThe Game started!\n");
                currentPerson.setFinish(false);
                secondPerson.setFinish(false);
                currentPerson.DrawBoard();
                secondPerson.DrawBoard();
                currentPerson.setGameOn(true);
                currentPerson.setMark("X");
                secondPerson.setMark("O");
                GameStarted();
                break;
            }

        }
    }

    private static void sendMesssage(Mud.GameServerInterface server, String personName, String message) throws IOException, InterruptedException {
        secondPerson.talk(currentPersonName, message);
        secondPerson.addLetter(currentPersonName, message);
    }

    private static void GameStarted() throws RemoteException, InterruptedException {
        while (true) {
            while (true) {
                Thread.sleep(100);
                if (currentPerson.getGameOn()) {
                    break;
                }
            }
            if (!secondPerson.getFinish()) {
                String move = getLine("Now your turn: ");
//                while (currentPerson.getBoard()[Integer.parseInt(move)-1].equals("X") ||
//                        currentPerson.getBoard()[Integer.parseInt(move)-1].equals("O")) {
//                    currentPerson.ShowGame("This cell is used. Please, try again!\n");
//                    move = getLine("- ");
//                }
                switch (move) {
                    case "1":
                        currentPerson.setMove(0, currentPerson.getMark());
                        secondPerson.setMove(0, currentPerson.getMark());
                        break;
                    case "2":
                        currentPerson.setMove(2, currentPerson.getMark());
                        secondPerson.setMove(2, currentPerson.getMark());
                        break;
                    case "3":
                        currentPerson.setMove(4, currentPerson.getMark());
                        secondPerson.setMove(4, currentPerson.getMark());
                        break;
                    case "4":
                        currentPerson.setMove(12, currentPerson.getMark());
                        secondPerson.setMove(12, currentPerson.getMark());
                        break;
                    case "5":
                        currentPerson.setMove(14, currentPerson.getMark());
                        secondPerson.setMove(14, currentPerson.getMark());
                        break;
                    case "6":
                        currentPerson.setMove(16, currentPerson.getMark());
                        secondPerson.setMove(16, currentPerson.getMark());
                        break;
                    case "7":
                        currentPerson.setMove(24, currentPerson.getMark());
                        secondPerson.setMove(24, currentPerson.getMark());
                        break;
                    case "8":
                        currentPerson.setMove(26, currentPerson.getMark());
                        secondPerson.setMove(26, currentPerson.getMark());
                        break;
                    case "9":
                        currentPerson.setMove(28, currentPerson.getMark());
                        secondPerson.setMove(28, currentPerson.getMark());
                        break;
                    default:
                        break;
                }
                secondPerson.ShowGame("Player " + currentPerson.getName() + ":\n");
                currentPerson.DrawBoard();
                secondPerson.DrawBoard();
                int count = 0;
                if (currentPerson.getBoard()[0].equals(currentPerson.getMark()) &&
                        currentPerson.getBoard()[2].equals(currentPerson.getMark()) &&
                        currentPerson.getBoard()[4].equals(currentPerson.getMark())) {
                    currentPerson.ShowGame("Game finished!\n You win!");
                    secondPerson.ShowGame("Game finished!\n" + currentPerson.getName() + " win!");
                    currentPerson.GameFinish();
                    secondPerson.setGameOn(true);
                    break;
                }
                if (currentPerson.getBoard()[12].equals(currentPerson.getMark()) &&
                        currentPerson.getBoard()[14].equals(currentPerson.getMark()) &&
                        currentPerson.getBoard()[16].equals(currentPerson.getMark())) {
                    currentPerson.ShowGame("Game finished!\n You win!");
                    secondPerson.ShowGame("Game finished!\n" + currentPerson.getName() + " win!");
                    currentPerson.GameFinish();
                    secondPerson.setGameOn(true);
                    break;
                }
                if (currentPerson.getBoard()[24].equals(currentPerson.getMark()) &&
                        currentPerson.getBoard()[26].equals(currentPerson.getMark()) &&
                        currentPerson.getBoard()[26].equals(currentPerson.getMark())) {
                    currentPerson.ShowGame("Game finished!\n You win!");
                    secondPerson.ShowGame("Game finished!\n" + currentPerson.getName() + " win!");
                    currentPerson.GameFinish();
                    secondPerson.setGameOn(true);
                    break;
                }
                if (currentPerson.getBoard()[0].equals(currentPerson.getMark()) &&
                        currentPerson.getBoard()[12].equals(currentPerson.getMark()) &&
                        currentPerson.getBoard()[24].equals(currentPerson.getMark())) {
                    currentPerson.ShowGame("Game finished!\n You win!");
                    secondPerson.ShowGame("Game finished!\n" + currentPerson.getName() + " win!");
                    currentPerson.GameFinish();
                    secondPerson.setGameOn(true);
                    break;
                }
                if (currentPerson.getBoard()[2].equals(currentPerson.getMark()) &&
                        currentPerson.getBoard()[14].equals(currentPerson.getMark()) &&
                        currentPerson.getBoard()[26].equals(currentPerson.getMark())) {
                    currentPerson.ShowGame("Game finished!\n You win!");
                    secondPerson.ShowGame("Game finished!\n" + currentPerson.getName() + " win!");
                    currentPerson.GameFinish();
                    secondPerson.setGameOn(true);
                    break;
                }
                if (currentPerson.getBoard()[4].equals(currentPerson.getMark()) &&
                        currentPerson.getBoard()[16].equals(currentPerson.getMark()) &&
                        currentPerson.getBoard()[28].equals(currentPerson.getMark())) {
                    currentPerson.ShowGame("Game finished!\n You win!");
                    secondPerson.ShowGame("Game finished!\n" + currentPerson.getName() + " win!");
                    currentPerson.GameFinish();
                    secondPerson.setGameOn(true);
                    break;
                }
                for (String i : currentPerson.getBoard()) {
                    if (i.equals(" "))
                        count++;
                }
                if (count == 0) {
                    currentPerson.ShowGame("Game finished!" + "Tie!");
                    secondPerson.ShowGame("Game finished!" + "Tie!");
                    currentPerson.GameFinish();
                    secondPerson.setGameOn(true);
                    break;
                }
                secondPerson.setGameOn(true);
                currentPerson.setGameOn(false);
            } else {
                currentPerson.GameFinish();
                break;
            }
        }
    }

    private static void ConfirmGame(Mud.GameServerInterface server) throws RemoteException, InterruptedException {
        secondPerson = server.getPerson(currentPerson.getSecondPerson());
        secondPerson.talk(currentPersonName, "Yes");
        secondPerson.addLetter(currentPersonName, "Yes");
        GameStarted();
    }

    private static void DontConfirmGame(Mud.GameServerInterface server) throws RemoteException, InterruptedException {
        secondPerson = server.getPerson(currentPerson.getSecondPerson());
        secondPerson.talk(currentPersonName, "No");
        secondPerson.addLetter(currentPersonName, "No");
    }

    private static final String help =
            "start: start a new game\n" +
                    "help: show commands\n" +
                    "quit : finish the game\n";
}
