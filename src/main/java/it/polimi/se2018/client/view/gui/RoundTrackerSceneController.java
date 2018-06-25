package it.polimi.se2018.client.view.gui;

import it.polimi.se2018.client.view.gui.button.MenuItemRoundTracker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RoundTrackerSceneController implements Initializable {
    private final List<List<MenuItemRoundTracker>> roundTracker;
    private final GameSceneController gameSceneController;

    @FXML
    private GridPane gridPane;

    RoundTrackerSceneController(List<List<MenuItemRoundTracker>> roundTracker, GameSceneController gameSceneController){
        this.roundTracker = roundTracker;
        this.gameSceneController = gameSceneController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(int i=0; i < roundTracker.size(); i++){
            MenuButton menuButton = new MenuButton();
            menuButton.setStyle("-fx-background-color : transparent;");
            for(int j=0; j<roundTracker.get(i).size(); j++){
                MenuItemRoundTracker menuItemRoundTracker = roundTracker.get(i).get(j);
                menuItemRoundTracker.setOnAction(e -> gameSceneController.buttonCoordinateClicked(((MenuItemRoundTracker)e.getSource()).getCoordinate()));
                menuButton.getItems().add(menuItemRoundTracker);
            }
            gridPane.add(menuButton, 9-i, 0);
            GridPane.setHalignment(menuButton, HPos.CENTER);
            GridPane.setValignment(menuButton, VPos.CENTER);
        }
    }
}
