package it.polimi.se2018.client;


import it.polimi.se2018.client.network.ClientConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class Client {
    private boolean disconnected;
    private boolean gameEnded;
    protected boolean setup;
    protected int playerID;
    protected String nickname;
    int port;
    String ip;
    ClientConnection clientConnection;

    public Client() {
        disconnected = false;
        gameEnded = false;
        setup = true;
    }

    abstract void startNewGame();
    abstract void handleDisconnection();

    synchronized void waitForAction() {
        while(!disconnected && !gameEnded) {
            try {
                this.wait();
            } catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
        if(gameEnded) {
            gameEnded = false;
            startNewGame();
        }
        else {
            disconnected = false;
            handleDisconnection();
        }
    }

    void getDefaultParams(boolean isSocket) {
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("NetworkProperties.txt");
        try(BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String[] tokens = sb.toString().split(";");
            ip = tokens[0].split(":")[1];
            port = Integer.valueOf((isSocket? tokens[2].split(":")[1] : tokens[1].split(":")[1]));
        }
        catch (IOException e) {
            System.exit(1);
        }
    }

    public synchronized void setDisconnected() {
        disconnected = true;
        this.notifyAll();
    }

    public synchronized void setGameEnded() {
        gameEnded = true;
        this.notifyAll();
    }
}
