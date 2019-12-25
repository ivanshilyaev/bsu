package by.bsu.clientserver.controller.client;

import by.bsu.clientserver.bean.Message;
import by.bsu.clientserver.bean.MessageType;
import by.bsu.clientserver.service.Connection;
import by.bsu.clientserver.view.ConsoleHelper;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client2ToTest {
    protected Connection connection;
    private volatile boolean clientConnected = false;
    private ArrayList<String> mailbox = new ArrayList<String>();
    private String pathForIncomingFiles;

    protected String getServerAddress() {
        ConsoleHelper.writeMessage("Enter server address:");
        return ConsoleHelper.readString();
    }

    protected int getServerPort() {
        ConsoleHelper.writeMessage("Enter server port:");
        return ConsoleHelper.readInt();
    }

    protected String getUserName() {
        ConsoleHelper.writeMessage("Enter user nic to create an email address:");
        return ConsoleHelper.readString() + "@mymail.com";
    }

    protected String getPathForIncomingFiles() {
        ConsoleHelper.writeMessage("Enter file path for incoming files:");
        return ConsoleHelper.readString();
    }

    protected SocketThread getSocketThread() {
        return new SocketThread();
    }

    protected void sendRequestForAListOfUsers() {
        try {
            Message message = new Message(MessageType.LIST_OF_USERS_REQUEST);
            connection.send(message);
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Error with sending a message.");
            clientConnected = false;
        }
    }

    protected void sendTextEmail(String text) {
        try {
            Message message = new Message(MessageType.TEXT_EMAIL, text);
            connection.send(message);
        } catch (IOException e) {
            ConsoleHelper.writeMessage("Error with sending a message.");
            clientConnected = false;
        }
    }

    protected void sendFileEmail(String receiver, String path) {
        try {
            File f = new File(path);
            byte[] file = new byte[(int) f.length()];
            FileInputStream fis = new FileInputStream(f);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(file, 0, file.length);
            Message message = new Message(MessageType.FILE_EMAIL, receiver, file);
            connection.send(message);
            bis.close();
            fis.close();
        } catch (IOException e) {
            ConsoleHelper.writeMessage("An error occurred while sending a message.");
            clientConnected = false;
        }
    }

    public synchronized void run() {
        try {
            SocketThread socketThread = getSocketThread();
            socketThread.setDaemon(true);
            socketThread.start();
            this.wait();
            if (clientConnected) {
                ConsoleHelper.writeMessage("Connection has been established.");
                pathForIncomingFiles = getPathForIncomingFiles();
                ConsoleHelper.writeMessage("Enter 'list' to see the list of users.");
                ConsoleHelper.writeMessage("Enter 'text-email' to sent plain text.");
                ConsoleHelper.writeMessage("Enter 'file-email' to sent a file.");
                ConsoleHelper.writeMessage("Enter ‘exit’ to finish.");
            } else
                ConsoleHelper.writeMessage("An error occurred while working with the client");
            while (clientConnected) {
                String text = ConsoleHelper.readString();
                if (text.equals("exit"))
                    break;
                if (text.equals("list"))
                    sendRequestForAListOfUsers();
                else if (text.equals("text-email")) {
                    ConsoleHelper.writeMessage("Enter user-receiver:");
                    String receiver = ConsoleHelper.readString();
                    ConsoleHelper.writeMessage("Enter your text:");
                    String data = ConsoleHelper.readString();
                    sendTextEmail(receiver + ": " + data);
                } else if (text.equals("file-email")) {
                    ConsoleHelper.writeMessage("Enter user-receiver:");
                    String receiver = ConsoleHelper.readString();
                    ConsoleHelper.writeMessage("Enter full file path:");
                    String path = ConsoleHelper.readString();
                    sendFileEmail(receiver, path);
                } else
                    ConsoleHelper.writeMessage("Error: unxpected command");
            }
        } catch (InterruptedException e) {
            ConsoleHelper.writeMessage("Error");
            System.exit(0);
        }
    }

    public class SocketThread extends Thread {
        protected void printListOfUsers(String users) {
            if (users.isEmpty()) {
                ConsoleHelper.writeMessage("No users connected!");
                return;
            }
            ConsoleHelper.writeMessage("List of users:");
            String array[] = users.split(" ");
            for (String name : array) {
                ConsoleHelper.writeMessage(name);
            }
        }

        protected void processIncomingTextEmail(String data) {
            mailbox.add(data);
            String sender = data.substring(0, data.indexOf(":"));
            data = data.substring(data.indexOf(":") + 1);
            ConsoleHelper.writeMessage("Text email from " + sender + ": " + data);
        }

        protected void processIncomingFileEmail(Message message) {
            String sender = message.getData();
            byte[] file = message.getFile();
            ConsoleHelper.writeMessage("File email from " + sender +
                    ". Open the file to see what you've get");
            String path = pathForIncomingFiles;
            File f = new File(path);
            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            try {
                fos = new FileOutputStream(f);
                bos = new BufferedOutputStream(fos);
                bos.write(file);
                bos.flush();
            } catch (FileNotFoundException e) {
                ConsoleHelper.writeMessage("Error: no such file!");
            } catch (IOException e) {
                ConsoleHelper.writeMessage("An error occurred file writing into file");
            } finally {
                try {
                    bos.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        protected void notifyConnectionStatusChanged(boolean clientConnected) {
            Client2ToTest.this.clientConnected = clientConnected;
            synchronized (Client2ToTest.this) {
                Client2ToTest.this.notify();
            }
        }

        protected void clientHandshake() throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.NAME_REQUEST) {
                    connection.send(new Message(MessageType.USER_NAME, getUserName()));
                } else if (message.getType() == MessageType.NAME_ACCEPTED) {
                    notifyConnectionStatusChanged(true);
                    break;
                } else throw new IOException("Unexpected MessageType");
            }
        }

        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.LIST_OF_USERS_RESPONSE)
                    printListOfUsers(message.getData());
                else if (message.getType() == MessageType.TEXT_EMAIL)
                    processIncomingTextEmail(message.getData());
                else if (message.getType() == MessageType.TEXT_EMAIL_ERROR)
                    ConsoleHelper.writeMessage("Error: no such user in list!");
                else if (message.getType() == MessageType.TEXT_EMAIL_SUCCESS)
                    ConsoleHelper.writeMessage("Text email has been sent successfully");
                else if (message.getType() == MessageType.FILE_EMAIL)
                    processIncomingFileEmail(message);
                else if (message.getType() == MessageType.FILE_EMAIL_ERROR)
                    ConsoleHelper.writeMessage("Error: no such user in list!");
                else if (message.getType() == MessageType.FILE_EMAIL_SUCCESS)
                    ConsoleHelper.writeMessage("File email has been sent successfully");
                else throw new IOException("Unexpected MessageType");
            }
        }

        public void run() {
            String address = getServerAddress();
            int port = getServerPort();
            try {
                Socket socket = new Socket(address, port);
                connection = new Connection(socket);
                clientHandshake();
                clientMainLoop();
            } catch (IOException | ClassNotFoundException e) {
                notifyConnectionStatusChanged(false);
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}

