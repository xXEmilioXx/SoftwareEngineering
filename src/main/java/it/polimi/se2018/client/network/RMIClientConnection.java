package it.polimi.se2018.client.network;

import it.polimi.se2018.client.view.ClientView;
import it.polimi.se2018.network.connections.rmi.RemoteConnection;
import it.polimi.se2018.network.messages.requests.Message;
import it.polimi.se2018.network.messages.responses.Response;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIClientConnection implements ClientConnection, RemoteConnection, Runnable {
    private final ClientView clientView;
    private RemoteConnection serverConnection;
    private final List<Response> events;
    private boolean isOpen;

    public RMIClientConnection(ClientView clientView) {
        this.clientView = clientView;
        events = new ArrayList<>();
        isOpen = true;
    }

    public void setServerConnection(RemoteConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    @Override
    public void getResponse(Response response) {
        synchronized (events) {
            events.add(response);
            events.notifyAll();
        }
    }

    @Override
    public void getMessage(Message message) { //not implemented client-side
    }

    @Override
    public void updateView() {
        synchronized (events) {
            while (events.isEmpty()) {
                try {
                    events.wait();
                } catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }
        clientView.handleNetworkInput(events.remove(0));
    }

    @Override
    public void sendMessage(Message message){
        try {
            serverConnection.getMessage(message);
        }
        catch(RemoteException e) {
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.ALL,e.getMessage());
        }

    }

    @Override
    public void stop() {
        isOpen = false;
    }

    @Override
    public void run() {
        while(isOpen) {
            updateView();
        }
    }
}
