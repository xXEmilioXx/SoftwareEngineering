package it.polimi.se2018.client.view.cli;

import it.polimi.se2018.mvc.model.toolcards.*;
import it.polimi.se2018.network.messages.Coordinate;
import it.polimi.se2018.network.messages.requests.ToolCardMessage;
import it.polimi.se2018.utils.exceptions.ChangeActionException;
import it.polimi.se2018.utils.exceptions.HaltException;

/**
 * This class is used by the pattern visitor.
 * It's used to understand and ask to the player the information to use a specific toolcard using the CLI
 */
public class ToolCardCLI implements ToolCardCLIHandler {
    private final int playerID;
    private final CLIData cliModel;
    private final CLIView cliView;

    ToolCardCLI(int playerID, CLIView cliView, CLIData cliModel) {
        this.playerID=playerID;
        this.cliModel = cliModel;
        this.cliView = cliView;
    }

    private ToolCardMessage getToolCardMessage(int toolCardNumber) throws HaltException, ChangeActionException {
        ToolCardMessage toolCardMessage = new ToolCardMessage(playerID, cliModel.getBoard().getStateID(),toolCardNumber);
        toolCardMessage.addStartingPosition(cliView.getCoordinate());
        toolCardMessage.addFinalPosition(cliView.getCoordinate());
        return  toolCardMessage;
    }

    @Override
    public ToolCardMessage getPlayerRequests(CopperFoilBurnisher toolCard, int toolCardNumber) throws HaltException, ChangeActionException {
        return getToolCardMessage(toolCardNumber);
    }

    @Override
    public ToolCardMessage getPlayerRequests(EglomiseBrush toolCard, int toolCardNumber) throws HaltException, ChangeActionException {
        return getToolCardMessage(toolCardNumber);
    }

    @Override
    public ToolCardMessage getPlayerRequests(CorkBackedStraightedge toolCard, int toolCardNumber) throws HaltException, ChangeActionException {
        ToolCardMessage toolCardMessage = new ToolCardMessage(playerID, cliModel.getBoard().getStateID(),toolCardNumber);
        toolCardMessage.addFinalPosition(cliView.getCoordinate());
        return toolCardMessage;
    }

    @Override
    public ToolCardMessage getPlayerRequests(FluxBrush toolCard, int toolCardNumber) {
        return new ToolCardMessage(playerID, cliModel.getBoard().getStateID(),toolCardNumber);
    }

    @Override
    public ToolCardMessage getPlayerRequests(FluxRemover toolCard, int toolCardNumber) {
        return new ToolCardMessage(playerID, cliModel.getBoard().getStateID(),toolCardNumber);
    }

    @Override
    public ToolCardMessage getPlayerRequests(GlazingHammer toolCard, int toolCardNumber) {
        return new ToolCardMessage(playerID, cliModel.getBoard().getStateID(),toolCardNumber);
    }

    @Override
    public ToolCardMessage getPlayerRequests(GrindingStone toolCard, int toolCardNumber) {
        return new ToolCardMessage(playerID, cliModel.getBoard().getStateID(),toolCardNumber);
    }

    @Override
    public ToolCardMessage getPlayerRequests(GrozingPliers toolCard, int toolCardNumber) throws HaltException {
        ToolCardMessage toolCardMessage = new ToolCardMessage(playerID, cliModel.getBoard().getStateID(),toolCardNumber);
        toolCardMessage.setCondition(cliView.getIncrementOrDecrement()!=0);
        return  toolCardMessage;
    }

    @Override
    public ToolCardMessage getPlayerRequests(Lathekin toolCard, int toolCardNumber) throws HaltException, ChangeActionException {
        ToolCardMessage toolCardMessage = new ToolCardMessage(playerID, cliModel.getBoard().getStateID(),toolCardNumber);
        toolCardMessage.addStartingPosition(cliView.getCoordinate());
        toolCardMessage.addFinalPosition(cliView.getCoordinate());
        toolCardMessage.addStartingPosition(cliView.getCoordinate());
        toolCardMessage.addFinalPosition(cliView.getCoordinate());
        return toolCardMessage;
    }

    @Override
    public ToolCardMessage getPlayerRequests(LensCutter toolCard, int toolCardNumber) throws HaltException, ChangeActionException {
        ToolCardMessage toolCardMessage = new ToolCardMessage(playerID, cliModel.getBoard().getStateID(), toolCardNumber);
        toolCardMessage.addRoundTrackerPosition(cliView.getRoundTrackPosition());
        return toolCardMessage;
    }

    @Override
    public ToolCardMessage getPlayerRequests(RunningPliers toolCard, int toolCardNumber) {
        return new ToolCardMessage(playerID, cliModel.getBoard().getStateID(),toolCardNumber);
    }

    @Override
    public ToolCardMessage getPlayerRequests(TapWheel toolCard, int toolCardNumber) throws HaltException, ChangeActionException {
        ToolCardMessage toolCardMessage = new ToolCardMessage(playerID, cliModel.getBoard().getStateID(),toolCardNumber);
        cliView.print("\n\nChoose a die from the Round Tracker\n");
        Coordinate roundTrackPosition = cliView.getRoundTrackPosition();
        toolCardMessage.addRoundTrackerPosition(roundTrackPosition);
        cliView.print("\n\nChoose the first die in your window that you want to move\n");
        toolCardMessage.addStartingPosition(cliView.getCoordinate());
        cliView.print("\n\nChoose the position where you want to move the first die\n");
        toolCardMessage.addFinalPosition(cliView.getCoordinate());
        cliView.print("\n\nDo you want to move another die? [1] yes [2] no\n");
        int choice = cliView.takeInput(1, 2);
        if (choice == 1) {
            cliView.print("\n\nChoose the position where you want to move the second die\n");
            toolCardMessage.addStartingPosition(cliView.getCoordinate());
            cliView.print("\n\nChoose the position where you want to move the second die\n");
            toolCardMessage.addFinalPosition(cliView.getCoordinate());
            toolCardMessage.setCondition(true);
        }
        else toolCardMessage.setCondition(false);
        return toolCardMessage;
    }
}
