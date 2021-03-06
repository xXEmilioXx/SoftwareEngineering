package it.polimi.se2018.client.network;

import it.polimi.se2018.client.Client;
import it.polimi.se2018.client.view.ClientView;
import it.polimi.se2018.network.messages.requests.Message;
import it.polimi.se2018.network.messages.responses.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClientConnection extends ClientConnection implements Runnable {

    /**
     * This is a reference to the socket used to communicate with the Server
     */
    private final Socket socket;

    /**
     * This is the outputstream to send messages on
     */
    private final ObjectOutputStream out;

    /**
     * This is the inputstream to receive messages from
     */
    private final ObjectInputStream in;

    public SocketClientConnection(Client client, ClientView clientView, Socket socket, ObjectInputStream in, ObjectOutputStream out){
        super(clientView);
        this.client = client;
        this.socket = socket;
        this.in = in;
        this.out = out;
        isOpen = true;
        matchPlaying = true;
    }

    @Override
    public void sendMessage(Message message){
        try{
            out.writeObject(message);
        }catch(IOException e){
            disconnect();
        }
    }

    @Override
    public void stop(){
        isOpen = false;
        matchPlaying = false;
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.ALL,e.getMessage());
        }
    }

    @Override
    public void run(){
        while(isOpen){
            try{
                Response response = (Response) in.readObject();
                if (response != null) response.handleClass(this);
            }
            catch(ClassNotFoundException e) {
                Logger logger = Logger.getAnonymousLogger();
                logger.log(Level.ALL,e.getMessage());
            }
            catch (IOException e) {
                disconnect();
            }
        }
    }
}
