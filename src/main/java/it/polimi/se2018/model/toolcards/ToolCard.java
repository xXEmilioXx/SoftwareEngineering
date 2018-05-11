package it.polimi.se2018.model.toolcards;

import it.polimi.se2018.controller.ToolCardHandler;
import it.polimi.se2018.exceptions.ToolCardException;
import it.polimi.se2018.model.Board;
import it.polimi.se2018.network.messages.requests.ToolCardMessage;

import java.util.List;

public abstract class ToolCard {
    protected final boolean alreadyUsed; //true if this tool card has already been used once
    protected final String imagePath;
    protected final String title;
    protected final Board board;
    protected List<String> playerRequests;
    protected ToolCard(String imagePath, String title, Board board, boolean alreadyUsed) {
        this.imagePath=imagePath;
        this.title=title;
        this.alreadyUsed = alreadyUsed;
        this.board=board;
    }

    public boolean isAlreadyUsed() {return alreadyUsed;}

    public abstract ToolCard setAlreadyUsed();

    public List<String> getPlayerRequests() {
        return playerRequests;
    }

    public abstract void handle(ToolCardHandler handler, ToolCardMessage toolCardMessage) throws ToolCardException;

}