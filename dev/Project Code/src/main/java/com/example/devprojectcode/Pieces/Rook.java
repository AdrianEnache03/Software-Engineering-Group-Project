/**
 * @(#) Rook.java 1.0 2023/02/07
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */
 
package com.example.devprojectcode.Pieces;

import com.example.devprojectcode.Enums.PieceColour;
import com.example.devprojectcode.Enums.PieceName;

/**
 * A class that sets the attributes and moves for the Rook
 * Inherits attributes from Piece
 *
 * @author George Cooper gwc1
 * @version 0.3 Initial development
 * @see Piece
 */
public class Rook extends Piece {

    
    //---------- INSTANCE VARIABLES ----------//
    private boolean hasMoved;

    //---------- CONSTRUCTORS ----------//
    public Rook(PieceColour colour, PieceName name, int xPos, int yPos) {
        super(colour, name, xPos, yPos);
    }
    public Rook() {super();}

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
        return (x2 == x1 && y2 != y1 || y2 == y1 && x2 != x1);
    }

    /**
     *
     *
     * @param board
     * @param x1 initial x position of rook piece
     * @param x1 initial y position of rook piece
     * @param y2 x position of square to move to
     * @return true if a forward move is blocked by a piece, false if not
     */

    // This method checks if a piece can move forward to a given position on the board
    // It takes in the board as a 2D array of pieces, the current position of the piece and the desired Y-coordinate
    private boolean isPieceBlockMoveForward(Piece[][] board, int x1, int y1, int y2){

        // Initialize a counter and calculate the difference in Y-coordinates
        int counter = 0;
        int Ydiff = y1 - y2 + 1;

        // Loop through the Y-coordinates between the current and desired positions
        for(int i = 1; i < Ydiff; i++){

            // If the counter is 0, check if there is a piece blocking the way
            if(counter == 0){
                if (board[x1][y1 - i] != null){
                    counter++;
                }

                // If the counter is not 0, there is a piece blocking the way and the method returns true
            } else {
                return true;
            }
        }

        // If the method hasn't returned true by now, there are no pieces blocking the way and it returns false
        return false;
    }

    /**
     *
     *
     * @param board
     * @param x1 initial x position of rook piece
     * @param x1 initial y position of rook piece
     * @param y1 x position of square to move to
     * @return true if a forward move is blocked by a piece, false if not
     */

    // This method checks if a piece can move left to a given position on the board
    // It takes in the board as a 2D array of pieces, the current position of the piece and the desired X-coordinate
    private boolean isPieceBlockMoveLeft(Piece[][] board, int x1, int x2, int y1){

        // Initialize a counter and calculate the difference in X-coordinates
        int counter = 0;
        int Xdiff = x1-x2+1;

        // Loop through the X-coordinates between the current and desired positions
        for(int i = 1; i < Xdiff; i++){

            // If the counter is 0, check if there is a piece blocking the way
            if(counter==0){
                if (board[x1-i][y1] != null) {
                    counter++;
                }

                // If the counter is not 0, there is a piece blocking the way and the method returns true
            } else {
                return true;
            }
        }
        return false;
    }
    /**
     *
     *
     * @param board
     * @param x1 initial x position of rook piece
     * @param x1 initial y position of rook piece
     * @param y2 x position of square to move to
     * @return true if a forward move is blocked by a piece, false if not
     */

    // This method checks if a piece can move right to a given position on the board
    // It takes in the board as a 2D array of pieces, the current position of the piece and the desired X-coordinate
    private boolean isPieceBlockMoveRight(Piece[][] board, int x1, int x2, int y2){
        // Initialize a counter and calculate the difference in X-coordinates
        int counter = 0;
        int Xdiff = x2-x1;

        // Loop through the X-coordinates between the current and desired positions
        for(int i = 0; i < Xdiff; i++){

            // If the counter is 0, check if there is a piece blocking the way
            if(counter==0){
                if (board[x1+i+1][y2] != null){
                    counter++;
                }

                // If the counter is not 0, there is a piece blocking the way and the method returns true
            } else {
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if a piece is blocking a backward move for a rook piece.
     *
     * @param board
     * @param x1 initial x position of the piece
     * @param y1 initial y position of the rook
     * @param y2 y position of the square to move to
     * @return true if a backward move is blocked by a piece, false if not
     */

    // This method checks if a piece can move down to a given position on the board
    // It takes in the board as a 2D array of pieces, the current position of the piece and the desired X-coordinate
    private boolean isPieceBlockMoveBack(Piece[][] board, int x1, int y1, int y2){

        // Initialize a counter and calculate the difference in Y-coordinates
        int counter = 0;
        int Ydiff = y2-y1;

        // Loop through the Y-coordinates between the current and desired positions
        for(int i = 0; i < Ydiff; i++){

            // If the counter is 0, check if there is a piece blocking the way
            if(counter==0){
                if (board[x1][y1+i+1] != null){
                    counter++;
                }

                // If the counter is not 0, there is a piece blocking the way and the method returns true
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a piece is blocked from moving to a certain position on the board.
     *
     * @param board
     * @param x1 initial x position of the rook
     * @param x2 initial y position of the rook
     * @param y1 y position of the piece
     * @param y2 x position of the square to move to
     * @return true if move is blocked by a piece, false if not
     */
    @Override
    public boolean isPieceUnblocked(Piece[][] board, int x1, int x2, int y1, int y2) {

        // check if the path is blocked in any of the directions (forward, backward, left, right)
        if (isPieceBlockMoveForward(board, x1, y1, y2) ||
                isPieceBlockMoveBack(board, x1, y1, y2) ||
                isPieceBlockMoveLeft(board, x1, x2, y1) || isPieceBlockMoveRight(board, x1, x2, y2)) {
            return false;
        }
        return true;
    }

}
