import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class Server extends UnicastRemoteObject implements Mud.GameServerInterface {

    static int PORT = 2020;
    ArrayList<Mud.GameClientInterface> ClientList;
    private String userName;

    public Server(String name) throws RemoteException {
        this.userName = name;
        ClientList = new ArrayList<>();
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(args[0]);
            LocateRegistry.createRegistry(2020);
            System.setProperty("java.rmi.server.hostname", "10.160.0.189");
            Naming.rebind("rmi://" + Mud.mudPrefix + ":" + PORT + "/" + server.userName, server);
            System.out.println("The server has been started");


        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println("Usage: java FreeTimeServer <username> \n");
            System.exit(1);
        }
    }

    @Override
    public Mud.GameClientInterface getPerson(String name) throws RemoteException {
        Optional<Mud.GameClientInterface> optional = ClientList.stream().filter(p -> {
            try {
                return Objects.equals(p.getName(), name);
            } catch (RemoteException e) {
                System.err.println(e.getMessage());
            }
            return false;
        }).findFirst();
        System.out.println("List of players:");
        for (Mud.GameClientInterface person : ClientList) {
            System.out.println(person.getName());
        }
        System.out.println("\n");
        return optional.orElse(null);
    }

    @Override
    public void delClient(Mud.GameClientInterface current) throws RemoteException {
        ClientList.remove(current);
    }

    public String getPersons(String currentPersonName) throws RemoteException {
        String res = ("");
        for (Mud.GameClientInterface i : ClientList) {
            if (!i.getName().equals(currentPersonName) && !i.getGameOn())
                res += i.getName() + "\n";
        }
        if (res.equals("")) {
            res = "Sorry, there are no more players on the server :(";
        }
        return res;
    }

    @Override
    public boolean addPerson(Mud.GameClientInterface current) throws RemoteException {
        Optional<Mud.GameClientInterface> optional = ClientList.stream().filter(p -> {
            try {
                return Objects.equals(p.getName(), current.getName());
            } catch (RemoteException e) {
                System.err.println(e.getMessage());
            }
            return false;
        }).findFirst();
        if (optional.isEmpty()) {
            ClientList.add(current);
            return true;
        }
        return false;
    }
}
