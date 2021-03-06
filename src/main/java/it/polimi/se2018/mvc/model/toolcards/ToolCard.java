package it.polimi.se2018.mvc.model.toolcards;

import it.polimi.se2018.client.view.cli.ToolCardCLIHandler;
import it.polimi.se2018.client.view.gui.ToolCardGUIHandler;
import it.polimi.se2018.mvc.controller.ToolCardHandler;
import it.polimi.se2018.mvc.model.Player;
import it.polimi.se2018.mvc.controller.ToolCardCheckerHandler;
import it.polimi.se2018.utils.exceptions.ChangeActionException;
import it.polimi.se2018.utils.exceptions.HaltException;
import it.polimi.se2018.utils.exceptions.ToolCardException;
import it.polimi.se2018.network.messages.requests.ToolCardMessage;

import java.io.Serializable;

/**
 * Abstract Tool Card class.
 * Used to distinguish dynamic type in Visitor pattern.
 */
public abstract class ToolCard implements Serializable {
    private final String title;
    private final String description;
    String imagePath;

    ToolCard(String title, String description) {
        this.title = title;
        this.description = description;
    }
    private String getTitle() { return title; }

    private String getDescription() { return description; }

    public String getImagePath() {
        return imagePath;
    }

    public abstract void handle(ToolCardHandler handler, ToolCardMessage toolCardMessage) throws ToolCardException;

    public abstract ToolCardMessage handleView(ToolCardCLIHandler handler, int toolCardNumber) throws ChangeActionException, HaltException;

    public abstract Boolean handleCheck(ToolCardCheckerHandler handler, boolean isUsed, Player player);

    public abstract void handleGUI(ToolCardGUIHandler handler, int toolCardNumber);

    @Override
    public String toString() {
        return "Title: \"" + getTitle() + "\", Effect: \"" + getDescription() + "\"";
    }
}
