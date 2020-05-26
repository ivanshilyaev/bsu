import java.rmi.Remote;
import java.rmi.RemoteException;

public class Mud {
    static String mudPrefix = "localhost";

    public interface GameServerInterface extends Remote {
        String getPersons(String currentPersonName) throws RemoteException;

        GameClientInterface getPerson(String name) throws RemoteException;

        void delClient(GameClientInterface current) throws RemoteException;

        boolean addPerson(GameClientInterface current) throws RemoteException;
    }

    public interface GameClientInterface extends Remote {
        void talk(String user, String text) throws RemoteException;

        String getName() throws RemoteException;

        String getMark() throws RemoteException;

        void setMark(String mark) throws RemoteException;

        boolean getFinish() throws RemoteException;

        void setFinish(boolean finish) throws RemoteException;

        void addLetter(String user, String letter) throws RemoteException;

        boolean getConfirm() throws RemoteException;

        void setSecondPerson(String name) throws RemoteException;

        String getSecondPerson() throws RemoteException;

        void ShowGame(String text) throws RemoteException;

        void AddCards(String card) throws RemoteException;

        boolean getGameOn() throws RemoteException;

        void setGameOn(boolean on) throws RemoteException;

        void GameFinish() throws RemoteException;

        void DrawBoard() throws RemoteException;

        void setMove(int move, String mark) throws RemoteException;

        String[] getBoard() throws RemoteException;
    }
}
