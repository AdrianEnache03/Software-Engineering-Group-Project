/**
* @(#) PauseController.java 0.1 2023/03/07
*
 * @author Fin Aubin (fia5)
 *
* Copyright (c) 2023 Aberystwyth University.
* All rights reserved.
*/

package com.example.devprojectcode.UIControllers;

import com.example.devprojectcode.Game;
import com.example.devprojectcode.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A Class that handles UI control for pause screen
 *
 * @author George Cooper, gwc1
 * @version 0.1 Initial development
 * @see NewGameScreenController
 */
public class
PauseScreenController extends Game {

    /**
     * Method to handle pressing the continue button
     * When button is pressed the user is taken back to the board screen
     * @param actionEvent is called when anything happens in the javaFX window
     */
    public void handleContinueButton(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(getBoard());
        stage.show();
    }

    /**
     * Method to handle when resign button has been pressed.
     * If button is pressed moves to resign screen
     *
     * @param actionEvent when the resign button is pressed
     */
    public void handleResignButton(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Text text = (Text)getResign().lookup("#PlayerName");
        System.out.println(text);
        if(text!=null) {
            text.setText(Game.getCurrentPlayerName() + ", are you sure you would like to resign");
        }stage.setScene(getResign());
        stage.show();
    }

    /**
     * Method to handle when the offer draw button is pressed
     * If button is pressed moves to the get offer draw screen
     *
     * @param actionEvent When the offer draw button is pressed
     */
    public void handleOfferDraw(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Text text = (Text)getOfferDraw().lookup("#PlayerName");
        System.out.println(text);
        if(text!=null) {
            text.setText(Game.getCurrentPlayerName()+" has offered a draw");
        }stage.setScene(getOfferDraw());
        stage.show();
    }

    /**
     * Method to handle when the main menu button is pressed
     * If button is pressed moves to main menu screen
     * Resets board to default
     * @param actionEvent When main menu button is pressed
     */
    public void handleMainMenuButton(ActionEvent actionEvent) throws Exception {
        Stage startScreen = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        startScreen.setScene(getMainMenu());
        Main.reset((Stage) getMainMenu().getWindow(), false);
    }

    /**
     * Method to handle save and quit button
     * Calls method to save game
     * Then quits the game
     *
     * @param actionEvent When the save and quit button is pressed
     * @throws IOException if save file cannot be found
     */
    public void handleSaveAndQuitButton(ActionEvent actionEvent) throws IOException {
        handleSaveButton(actionEvent);
        Platform.exit();
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

    /**
     * Method to handle when the decline button is pressed
     * When button is pressed moves back to board screen
     *
     * @param actionEvent when the decline button is pressed
     */
    public void handleDecline(ActionEvent actionEvent){
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(getBoard());
        stage.show();
    }

    /**
     * Method to handle when the accept button is pressed
     * When button is pressed, sets moves to game over screen
     * and populates the text objects with player names
     *
     * @param actionEvent when the accept button is pressed
     */
    public void handleAccept(ActionEvent actionEvent){
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Text text = (Text)getGameOver().lookup("#Winner");
        System.out.println(text);
        if(text!=null) {
            text.setText(Game.getOtherPlayerName()+" Wins!");
        }
        text = (Text)getGameOver().lookup("#Via");
        System.out.println(text);
        if(text!=null) {
            text.setText("Via Resignation");
        }
        stage.setScene(getGameOver());
        stage.show();
    }

    /**
     * Method to handle the draw button
     * When button is pressed, sets moves to game over screen
     * and populates the text objects with player names
     *
     * @param actionEvent when the draw button is pressed
     */
    public void handleDraw(ActionEvent actionEvent){
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Text text = (Text)getGameOver().lookup("#Winner");
        System.out.println(text);
        if(text!=null) {
            text.setText("both players have Drawn");
        }
        text = (Text)getGameOver().lookup("#Via");
        System.out.println(text);
        if(text!=null) {
            text.setText("");
        }
        stage.setScene(getGameOver());
        stage.show();
    }
}
