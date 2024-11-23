/**
 * @(#) Setup.java 1.0 2023/02/07
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 *
 */

/**
 * A class that handles Functional Requirement 1
 * (Asking the user to Enter their names and choose white or black)
 * Also handles setting up the board and adding the pieces
 *
 * @author Kieran Foy, kif11
 * @version 1.0 Initial development
 * @see Main
 */

import java.util.*;
public class Setup {

    /**
     * Method to ask the user who is playing white what name they would like to use
     * (FR1: Player Setup)
     * Called every time a new game is started
     */
    public String whiteName(){

        String whiteName;

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter name of person playing white: ");
        whiteName = scan.nextLine();

        return whiteName;
    }

    /**
     * Method to ask the user who is playing black what name they would like to use
     * (FR1: Player Setup)
     * Called every time a new game is started
     */
    public String blackName() {

        String blackName;

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter name of person playing black: ");
        blackName = scan.nextLine();

        return blackName;
    }

    /**
     * Method to create the chess board the game will be played on
     * Called every time a new game is started
     */
    public void boardSetup() {
        for (int row = 0; row <= 8; row++) {
            for (int col = 0; col <= 8; col++) {
                // create new square
                // set square xPos = row, yPos = col
                // set square height, width and border
                // add square to chess board
            }
        }
    }

    /**
     * Method to create the pieces the players will be using
     * Called every time a new game is started
     */
    public void addPiecesToBoard() {
        // for (squares)
            // if square is occupied
                // continue
            // addPiecesToBoard, e.g.
            // if xPos = 0 and yPos = 0
                // square.add (type = "Rook", color = "black", xPos = 0, yPos = 0)
    }
}
