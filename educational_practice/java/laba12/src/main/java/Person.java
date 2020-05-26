import java.io.BufferedReader;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class Person extends UnicastRemoteObject implements Mud.GameClientInterface {
    private String name;
    private PrintWriter out;
    private final BufferedReader in;
    private HashMap<String, String> letters;
    private boolean ConfirmPlay = false;
    private String SecondPerson = "";
    private ArrayList<String> cards = new ArrayList<>();
    private static boolean GameOn = false;
    private static String mark;
    private static boolean finish = false;
    public String[] board = {" ", "|", " ", "|", " ", "\n",
            "-", "|", "-", "|", "-", "\n",
            " ", "|", " ", "|", " ", "\n",
            "-", "|", "-", "|", "-", "\n",
            " ", "|", " ", "|", " ", "\n"};

    Person(String name, PrintWriter out, BufferedReader in) throws RemoteException {
        this.name = name;
        this.out = out;
        this.in = in;
        letters = new HashMap<>();
    }

    @Override
    public void setMove(int move, String mark) throws RemoteException {
        board[move] = mark;
    }

    @Override
    public String[] getBoard() throws RemoteException {
        return board;
    }

    @Override
    public void DrawBoard() throws RemoteException {
        for (String i : board) {
            out.print(i);
        }
        out.flush();
    }

    @Override
    public String getMark() throws RemoteException {
        return mark;
    }

    @Override
    public void setMark(String mark) throws RemoteException {
        this.mark = mark;
    }

    @Override
    public boolean getFinish() throws RemoteException {
        return finish;
    }

    @Override
    public void setFinish(boolean finish) throws RemoteException {
        this.finish = finish;
    }

    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public boolean getConfirm() throws RemoteException {
        return ConfirmPlay;
    }

    @Override
    public void talk(String user, String text) throws RemoteException {
        out.print(user + ": " + text);
        out.flush();
    }

    @Override
    public void ShowGame(String text) throws RemoteException {
        out.print(text);
        out.flush();
    }

    @Override
    public void addLetter(String user, String letter) throws RemoteException {
        if (letter.equals("Yes")) {
            ConfirmPlay = true;
        }
        if (letter.equals("No")) {
            ConfirmPlay = false;
        }
        letters.put(user, letter);
    }

    @Override
    public String toString() {
        return "{" + name + "}";
    }

    @Override
    public void setSecondPerson(String name) throws RemoteException {
        SecondPerson = name;
    }

    @Override
    public String getSecondPerson() throws RemoteException {
        return SecondPerson;
    }

    @Override
    public void AddCards(String card) throws RemoteException {
        cards.add(card);
    }

    @Override
    public void setGameOn(boolean on) throws RemoteException {
        GameOn = on;
    }

    @Override
    public boolean getGameOn() throws RemoteException {
        return GameOn;
    }

    @Override
    public void GameFinish() throws RemoteException {
        board = new String[]{" ", "|", " ", "|", " ", "\n",
                "-", "|", "-", "|", "-", "\n",
                " ", "|", " ", "|", " ", "\n",
                "-", "|", "-", "|", "-", "\n",
                " ", "|", " ", "|", " ", "\n",
                " ", "|", " ", "|", " ", "\n"};
        ConfirmPlay = false;
        finish = true;
        GameOn = false;
    }
}
