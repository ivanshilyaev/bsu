package com;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    private static class Handler extends Thread {
        private Socket socket;

        private Handler(Socket socket) {
            this.socket = socket;
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            connection.send(new Message(MessageType.NAME_REQUEST));
            String name;
            while (true) {
                Message message = connection.receive();
                name = message.getData();
                if (message.getType() == MessageType.USER_NAME && !name.isEmpty() &&
                        !connectionMap.containsKey(name))
                    break;
            }
            connectionMap.put(name, connection);
            connection.send(new Message(MessageType.NAME_ACCEPTED));
            return name;
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.LIST_OF_USERS_REQUEST) {
                    StringBuilder response = new StringBuilder();
                    for (Map.Entry<String, Connection> pair : connectionMap.entrySet()) {
                        if (!pair.getKey().equals(userName))
                            response.append(pair.getKey()).append(" ");
                    }
                    connection.send(new Message(MessageType.LIST_OF_USERS_RESPONSE, response.toString()));
                }
                else if (message.getType() == MessageType.TEXT_EMAIL) {
                    String data = message.getData();
                    String receiver = data.substring(0, data.indexOf(":"));
                    data = data.substring(data.indexOf(":") + 2);
                    data = userName + ": " + data;
                    if (!connectionMap.containsKey(receiver))
                        connection.send(new Message(MessageType.TEXT_EMAIL_ERROR));
                    else {
                        connectionMap.get(receiver).send(new Message(MessageType.TEXT_EMAIL, data));
                        connection.send(new Message(MessageType.TEXT_EMAIL_SUCCESS));
                    }
                }
                else if (message.getType() == MessageType.FILE_EMAIL) {
                    String receiver = message.getData();
                    if (!connectionMap.containsKey(receiver))
                        connection.send(new Message(MessageType.FILE_EMAIL_ERROR));
                    else {
                        connectionMap.get(receiver).send(new Message(MessageType.FILE_EMAIL, userName, message.getFile()));
                        connection.send(new Message(MessageType.FILE_EMAIL_SUCCESS));
                    }
                }
                else
                    ConsoleHelper.writeMessage("An error occurred while receiving a message");
            }

        }

        public void run() {
            ConsoleHelper.writeMessage(formatter.format(new Date()) +
                    " A new connection with remote address has been established: " +
                    socket.getRemoteSocketAddress());
            Connection connection = null;
            String userName = null;
            try {
                connection = new Connection(socket);
                userName = serverHandshake(connection);
                serverMainLoop(connection, userName);
            }
            catch (ClassNotFoundException | IOException e) {
                try {
                    if (connection != null)
                    connection.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                connectionMap.remove(userName);
                ConsoleHelper.writeMessage(formatter.format(new Date()) +
                        " Connection with remote has been closed: " +
                        socket.getRemoteSocketAddress());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ConsoleHelper.writeMessage("Enter server port:");
        int port = ConsoleHelper.readInt();
        ServerSocket serverSocket = new ServerSocket(port);
        ConsoleHelper.writeMessage("The server is running");
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                Handler handler = new Handler(socket);
                handler.start();
            }
        }
        catch (Exception e) {
            ConsoleHelper.writeMessage("An error occurred");
            serverSocket.close();
        }
    }
}
