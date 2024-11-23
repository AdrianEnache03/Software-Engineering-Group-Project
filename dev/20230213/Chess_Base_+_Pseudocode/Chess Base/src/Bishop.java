/**
 * @(#) Bishop.java 1.0 2023/02/07
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 *
 */

/**
 * A class that sets the attributes and moves for the Rook
 * Inherits attributes from Piece
 *
 * @author Kieran Foy, kif11
 * @version 1.0 Initial development
 * @see Piece
 */


import java.util.ArrayList;

public class Bishop extends Piece{
    public Bishop(String color, int xPos, int yPos, int score) {
        super(color, xPos, yPos, score);
        this.type = "Bishop";
        this.score = 3;
    }

    /**
     * Method to create and store all the possible moves for the Bishop
     * (FR4: Piece Selection, FR5: Movement)
     * Called every time a Bishop is selected
     */
    public void allMoves() {
        int xPos = this.xPos;
        int yPos = this.yPos;
        ArrayList<String> allMoves = new ArrayList<>();
        // ArrayList for the current possible moves

        //create moves and add them to the allMoves ArrayList

        // for allMoves
        // if the current move is possible
        // add to ArrayList for current possible moves
    }
}