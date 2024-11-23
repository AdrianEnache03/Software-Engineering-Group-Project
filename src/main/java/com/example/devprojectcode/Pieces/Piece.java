/**
 * @(#) Piece.java 1.0 2023/02/07
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 * 
*/

package com.example.devprojectcode.Pieces; 

import com.example.devprojectcode.Enums.PieceColour;
import com.example.devprojectcode.Enums.PieceName;

/**
 * A class that defines all the attributes used by the types of pieces.
 * Pawn, Rook, Knight, Bishop, Queen and King inherit from this class.
 *
 * @author George Cooper gwc1
 * @version 0.1 Initial development
 */
public abstract class Piece {

    //---------- INSTANCE VARIABLES ----------//
    private PieceName name;
    private PieceColour colour;
    int xPos, yPos; //TODO declare private/public

    //---------- CONSTRUCTORS  ----------//
    public Piece(PieceColour colour, PieceName name, int xPos, int yPos) {
        this.colour = colour;
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
    }
    public Piece() {}

    //---------- Read/Write Properties ----------//

    /**

     Sets the x-coordinate of the piece on the board.
     @param xPos the x-coordinate to set
     */
    public void setxPos(int xPos) {
        this.xPos = xPos;
    }
    /**

     Sets the y-coordinate of the piece on the board.
     @param yPos the y-coordinate to set
     */

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
    /**

     Sets the name of the piece.
     @param name the name of the piece to set
     */

    public void setName(PieceName name) {
        this.name = name;
    }

    /**

     Sets the color of the piece.
     @param colour the color of the piece to set
     */
    public void setColour(PieceColour colour) {
        this.colour = colour;
    }

    //---------- Read-Only Properies ----------//
    /**
     * Returns the color of the piece.
     *
     * @return the color of the piece
     */
    public PieceColour getColour() {
        return colour;
    }

    /**
     * Gets the name of a piece
     * @return
     */
    public PieceName getName() {
        return name;
    }

    /**
     * gets the x position of a piece
     * @return x position
     */
    public int getXPos() {
        return xPos;
    }

    /**
     * Gets the y position of a piece
     * @return y position
     */
    public int getYPos() {
        return yPos;
    }

    //---------- METHODS ----------//

    /**
     * Determines whether moving a piece to a set tile is a possible move
     * @param x1 x position of piece
     * @param x2 x position of tile
     * @param y1 y position of piece
     * @param y2 y position of tile
     * @return True if move is possibly legal, false if not
     */
    public abstract boolean isMoveLegal(int x1, int x2, int y1, int y2);

    /**
     * Determines whether a piece is blocked when trying to move to a set tile
     * @param board current state of the board
     * @param x1 x position of piece
     * @param x2 x position of tile
     * @param y1 y position of piece
     * @param y2 y position of tile
     * @return True if piece if blocked, false if not
     */
    public abstract boolean isPieceUnblocked(Piece[][] board, int x1, int x2, int y1, int y2);
}
