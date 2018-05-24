package it.polimi.se2018.client.network;

import it.polimi.se2018.network.connections.rmi.RemoteConnection;
import it.polimi.se2018.network.connections.rmi.RemoteManager;
import it.polimi.se2018.client.view.ClientView;
import it.polimi.se2018.client.view.cli.CLIClientView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private static final int PORT = 1234;
    private static final String HOST = "127.0.0.1";
    private ClientView clientView;
    private ClientConnection clientConnection;
    private int playerID;
    private String playerName;
    private Socket socket;
    private boolean setup;
    private final Scanner input;
    private final PrintStream output;

    private Client() {
        setup = true;
        input = new Scanner(System.in);
        output = new PrintStream(System.out);
    }

    private String getPlayerName() {
        output.println("Choose your nickname");
        return input.nextLine();
    }

    private void chooseConnection() {
        output.println("What type of connection do you want to use?");
        output.println("Type 1 for Socket, 2 for RMI");
        if (input.nextInt() == 1) {
            input.nextLine();
            createSocketConnection();
        }
        else {
            input.nextLine();
            createRMIConnection();
        }
    }

    private void createRMIConnection() {
        try {
            RemoteManager manager = (RemoteManager) Naming.lookup("//localhost/RemoteManager");
            playerID = manager.getID();
            while (setup) {
                playerName = getPlayerName();
                setup = manager.checkName(playerID, playerName);
                output.println(setup ? "This nickname is already taken, please choose another one" : "Your nickname is ok");
            }
            clientView = new CLIClientView(playerID);
            clientConnection = new RMIClientConnection(clientView);
            manager.addClient(playerID, playerName, (RemoteConnection) UnicastRemoteObject.exportObject((RemoteConnection) clientConnection, 0));
            RemoteConnection serverConnection = (RemoteConnection) Naming.lookup("//localhost/ServerConnection" + playerID);
            ((RMIClientConnection) clientConnection).setServerConnection(serverConnection);
            clientView.setClientConnection(clientConnection);
            new Thread((RMIClientConnection) clientConnection).start();
        }
        catch(RemoteException | NotBoundException | MalformedURLException e) {
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.ALL,e.getMessage());
        }
    }

    private void createSocketConnection(){
        try{
            socket = new Socket(HOST, PORT);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            playerID = (int) in.readObject();
            while (setup) {
                playerName = getPlayerName();
                out.writeObject(playerName);
                setup = (boolean) in.readObject();
                output.println(setup ? "This nickname is already taken, please choose another one" : "Your nickname is ok");
            }
            clientView = new CLIClientView(playerID);
            clientConnection = new SocketClientConnection(socket, clientView,in,out);
            clientView.setClientConnection(clientConnection);
            new Thread((SocketClientConnection) clientConnection).start();
        } catch(IOException | ClassNotFoundException e){
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.ALL,e.getMessage());
        }
    }



    public static void main(String[] args) {
        Client client = new Client();
        client.chooseConnection();
    }
}
