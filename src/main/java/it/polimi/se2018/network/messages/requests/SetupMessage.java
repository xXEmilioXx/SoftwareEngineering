package it.polimi.se2018.network.messages.requests;

import it.polimi.se2018.model.Window;

/**
 * This class represents a message from the Client to the Server with the window chosen by said player
 * @author Francesco Lorenzo
 */
public class SetupMessage extends Message{
    /**
     * This is the window chosen by the player
     */
    private final Window window;

    public SetupMessage(int playerID, Window window) {
        super(playerID);
        this.window = window;
    }

    /**
     * @return the window chosen by the player
     */
    public Window getWindow() {
        return window;
    }

    /**
     * Uses the handler to handle this specific setup request
     * @param messageHandler is the object who will handle this message
     */
    public void handle(MessageHandler messageHandler){
        messageHandler.performMove(this);
    }
}