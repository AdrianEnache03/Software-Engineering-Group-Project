/**
* King.java 1.0 2023/02/07
*
* Copyright (c) 2023 Aberystwyth University.
* All rights reserved.
*
*/

package com.example.devprojectcode.Pieces;

import com.example.devprojectcode.Enums.PieceColour;
import com.example.devprojectcode.Enums.PieceName;

/**
 * A class that sets the attributes and moves for the King
 * Inherits attributes from Piece
 *
 * @author George Cooper gwc1
 * @version 0.1 Initial development
 * @see Piece
 */
public class King extends Piece {
    //---------- INSTANCE VARIABLES ----------//
    private boolean hasMoved;

    //---------- CONSTRUCTORS ----------//
    public King(PieceColour colour, PieceName name, int xPos, int yPos) {
        super(colour, name, xPos, yPos);
    }
    public King() {super(); }

    //---------- READ/WRITE PROPERTIES ----------//
    
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    //---------- READ-ONLY PROPERTIES ----------//
    public boolean isHasMoved() {
        return hasMoved;
    }

    //---------- METHODS----------//
    @Override
    public boolean isMoveLegal(int x1, int x2, int y1, int y2) {

        //returns legal moves for king, any adjacent square
        return(x2==x1 && y2 == y1+1 ||
                y2==y1 && x2 == x1+1 ||
                x2==x1 && y2 == y1-1 ||
                y2==y1 && x2 == x1-1 ||
                x2==x1 +1 && y2 == y1 +1 ||
                x2==x1 -1 && y2 == y1 -1 ||
                x2==x1 +1 && y2 == y1 -1 ||
                x2==x1 -1 && y2 == y1 +1);
    }

    /**
     * checks if the piece is unblocked in its movement
     *
     * @param board current state of the board
     * @param x1 x position of piece
     * @param x2 x position of tile
     * @param y1 y position of piece
     * @param y2 y position of tile
     * @return
     */
    @Override
    public boolean isPieceUnblocked(Piece[][] board, int x1, int x2, int y1, int y2) {

        return true;
    }

}