/**
* LoginController.java 0.1 2023/03/07
* 
* Copyright (c) 2023 Aberystwyth University.
* All rights reserved.
* 
*/

package com.example.devprojectcode.UIControllers;

import com.example.devprojectcode.Game;
import com.example.devprojectcode.Replay;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * A Class that handles UI control for login screen
 * 
 * @author George Cooper, gwc1
 * @version 0.1 Initial development
 * @see NewGameScreenController
 */

public class NewGameScreenController extends Game {

    /**
     * Method to handle cancel button in login screen
     * If the button is pressed the user will be taken back to the main menu.
     *
     * @param actionEvent The button being pressed
     */
    public void handleCancelButton(ActionEvent actionEvent) {
        Stage startScreen = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        startScreen.setScene(getMainMenu());
    }

    /**
     * Method to handle submit button from the login screen
     * If button is pressed the user is taken to the board
     * If escape key is pressed the pause menu appears
     *
     * @param actionEvent The button being pressed
     */
    public void handleSubmitButton(ActionEvent actionEvent) {
        playerNames(actionEvent);
        if(checkNames()) {
            Stage mainBoard = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene board = getBoard();
            mainBoard.setScene(board);
            Text BName = (Text) mainBoard.getScene().lookup("#playerOne");
            BName.setText(getBlackUserName());
            Text WName = (Text) mainBoard.getScene().lookup("#playerTwo");
            WName.setText(getWhiteUserName());
            board.setOnKeyPressed(t -> {
                KeyCode key = t.getCode();
                if (key == KeyCode.ESCAPE) {
                    Scene pause=getPause();
                    Text text = (Text)pause.lookup("#PlayerName");
                    System.out.println(text);
                    if(text!=null) {
                        System.out.println(Game.getCurrentPlayerName());
                        text.setText(Game.getCurrentPlayerName());
                    }
                    mainBoard.setScene(pause);
                    mainBoard.show();
                }
            });
        } else {
            System.err.println("1 or both names are invalid");
            System.err.println("Names must be between 1-20 characters, and not include special characters");
        }
    }

    /**
     * Verifies that both usernames are between 1-20 characters and includes no special characters
     * Returns true if all conditions are met
     */
    private Boolean checkNames() {
        return (blackUserName.length() < 20 && blackUserName.length() > 0 && blackUserName.matches("[a-zA-Z0-9]+")) &&
                (whiteUserName.length() < 20 && whiteUserName.length() > 0 && whiteUserName.matches("[a-zA-Z0-9]+"));
    }

    /**
     * Fetch names from text field, assign to variables
     * Pass to game class
     */
    public void playerNames(ActionEvent actionEvent) {
        Button b = (Button) actionEvent.getSource();
        Pane p = (Pane) b.getParent();
        TextField bText = (TextField) p.lookup("#blackName");
        TextField wText = (TextField) p.lookup("#whiteName");
        blackUserName =  bText.getText();
        whiteUserName = wText.getText();
        replay = new Replay(blackUserName, whiteUserName);
        System.out.println(blackUserName);
        System.out.println(whiteUserName);
    }
}
