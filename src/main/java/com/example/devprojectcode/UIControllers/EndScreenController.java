/**
 * Controller.java 0.1 2023/03/07
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package com.example.devprojectcode.UIControllers;

import com.example.devprojectcode.Game;
import com.example.devprojectcode.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A Class that handles UI control
 *
 * @author George Cooper, gwc1
 * @author Fin Aubin (fia5)
 *
 * @version 0.1 Initial development
 *
 * @see EndScreenController
 */

public class EndScreenController extends Game {

    /**
     * Method to handle when the new game button is pressed.
     * Changes the scene to the login screen.
     *
     * @param actionEvent When the new game button is pressed.
     */
    @FXML
    private void handleNewGameButton(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(getLogIn());
        Main.reset((Stage) getBoard().getWindow(), true);
    }

    /**
     * Method to handle when the main menu button is pressed.
     * Changes the scene to the main menu.
     * Resets board to default
     *
     * @param actionEvent When the main menu button is pressed.
     */
    @FXML
    private void handleMainMenuButton(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(getMainMenu());
        Main.reset((Stage) getMainMenu().getWindow(), false);
    }

    /**
     * Method to handle when the Quit game button is pressed.
     * Game quits.
     *
     * @param actionEvent When the quit game button is pressed.
     */
    @FXML
    private void handleQuitButton(ActionEvent actionEvent){
        actionEvent.consume();
        Platform.exit();
        System.exit(0);
    }

    /**
     * Method to handle save button
     * When pressed the game is saved
     *
     * @param actionEvent when the save button is pressed
     * @throws IOException if save file cannot be found
     */
    public void handleSaveButton(ActionEvent actionEvent) throws IOException {
        Game.replay.saveGame();
        actionEvent.consume();
    }
}
