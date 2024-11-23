/**
 * Main.java 1.0 2023/02/07
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 *
 */

package com.example.devprojectcode; 

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Main class where the program is run, calls the other classes when needed
 *
 * @author Kieran Foy kif11, George Cooper gwc1
 * @version 0.1 Initial development
 * @see Setup
 * @see Game
 * @see Replay
 */
public class Main extends Application {

    //---------- Class Methods ----------//
    public static void main(String[] args) {
        launch(args);
    }
    
    //---------- Instance Variables ----------//
    static Game game;

    //---------- Read-only Properties ----------//
    /**
     * Gets the current game
     * @return The current game
     */
    public Game getGame() {
        return game;
    }

    //---------- Methods ----------//
    /**
     * Begins start of the programs UI
     * @param startScreen The start screen
     */
    public void start(Stage startScreen)
    throws Exception {
        game = new Game();
        game.startUp(startScreen, false);
    }

    /**
     * Clears the players names from the login screen text fields
     * Resets the board to default every time a new game is started
     *
     * @param startScreen
     * @throws Exception
     */
    public static void reset(Stage startScreen, boolean isLogin) throws Exception {
        game = new Game();
        game.startUp(startScreen, isLogin);
    }

}
