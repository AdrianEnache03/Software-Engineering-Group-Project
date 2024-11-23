/**
 * @(#) Knight.java 1.0 2023/02/07
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 *
 */

package com.example.devprojectcode.Pieces;

import com.example.devprojectcode.Enums.PieceColour;
import com.example.devprojectcode.Enums.PieceName;

/**
 * A class that sets the attributes and moves for the Knight
 * Inherits attributes from Piece
 *
 * @author George Cooper (gwc1)
 * @version 0.1 Initial development
 * @see Piece
 */
public class Knight extends Piece {

    //---------- CONSTRUCTORS ----------//
    public Knight(PieceColour colour, PieceName name, int xPos, int yPos) {
        super(colour, name, xPos, yPos);
    }
    public Knight() {super();}

    //---------- METHODS----------//
    /**
     *
     * Method to check if a move is legal
     *
     * @param x2 new x position
     * @param y2 new y position
     * @return True if move is legal, false if move is illegal
     */
    public boolean isMoveLegal(int x1, int x2, int y1, int y2) {

        //returns legal moves for knight
        return x2 == x1 + 1 && y2 == y1 + 2 || //two spaces forward one space to the right
                x2 == x1 - 1 && y2 == y1 + 2 || //two spaces forward one space to the left
                x2 == x1 + 1 && y2 == y1 - 2 || //two spaces backward one space to the right
                x2 == x1 - 1 && y2 == y1 - 2 || //two spaces backward one space to the left
                x2 == x1 + 2 && y2 == y1 + 1 || //two spaces right one space to the forward
                x2 == x1 - 2 && y2 == y1 + 1 || //two spaces left one space forward
                x2 == x1 + 2 && y2 == y1 - 1 || //two spaces right one space backward
                x2 == x1 - 2 && y2 == y1 - 1; //two spaces left one space backward
    }

    @Override
    public boolean isPieceUnblocked(Piece[][] board, int x1, int x2, int y1, int y2) {
        return true;
    }
}
