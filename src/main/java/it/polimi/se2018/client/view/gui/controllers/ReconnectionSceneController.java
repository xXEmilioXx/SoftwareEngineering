package it.polimi.se2018.client.view.gui.controllers;

import it.polimi.se2018.client.GUIClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReconnectionSceneController implements SceneController {
    private GUIClient guiClient;
    private Stage stage;

    @FXML
    private Label label;

    public void setClientGUI(GUIClient guiClient) {
        this.guiClient = guiClient;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Scene getScene() {
        return label.getScene();
    }

    @Override
    public void changeScene(Scene scene) {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/scenes/GameScene.fxml")));
        try {
            GameSceneController gameSceneController = new GameSceneController(guiClient.getGUIView().getGuiLogic());
            gameSceneController.setClientGUI(guiClient);
            loader.setController(gameSceneController);
            Parent root = loader.load();
            guiClient.getGUIView().getGuiLogic().setSceneController(gameSceneController);
            stage.setMinWidth(1440);
            stage.setMinHeight(900);
            stage.setMaximized(true);
            stage.setResizable(true);
            gameSceneController.setStage(stage);
            scene.setRoot(root);
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE,"ReconnectionSceneController");
        }
    }
}
