/**
 * @(#) Bishop.java 1.0 2023/02/07
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 *
 */

package com.example.devprojectcode.Pieces;

import com.example.devprojectcode.Enums.PieceColour;
import com.example.devprojectcode.Enums.PieceName;

/**
* A class that sets the attributes and moves for the Rook
* Inherits attributes from Piece
*
* @author George Cooper gwc1
* @version 0.1 Initial development
* @see Piece
*/
public class Bishop extends Piece {

    //---------- CONSTRUCTORS ----------//
    public Bishop(PieceColour colour, PieceName name, int xPos, int yPos) {
        super(colour, name, xPos, yPos);
    }
    public Bishop() {super();}

    //---------- METHODS----------//

    /**
     * checks if the moves are legal
     *
     * @param x1 x position of piece
     * @param x2 x position of tile
     * @param y1 y position of piece
     * @param y2 y position of tile
     * @return
     */
    @Override
    public boolean isMoveLegal(int x1, int x2, int y1, int y2) {
        int diffInx = x2-x1;
        int diffIny = y2-y1;
        return diffInx == diffIny || -diffInx == diffIny;
    }

    /**
     * Checks whether the bishop is blocked when travelling in the forward right diagonal
     * @param board current state of the board
     * @param x1 x value of bishop
     * @param x2 x value to move to
     * @param y1 y value of bishop
     * @return true if the piece is blocked, false if not
     */
    public boolean isPieceBlockForwardRight(Piece[][] board, int x1, int x2, int y1){
        int XDiff = x2-x1;
        int counter = 0;

        //For all spaces between the start and end of bishop's move
        for(int i = 1; i <= XDiff; i++){

            //if no pieces have been identified yet
            if(counter==0){

                //if move is in bounds
                if ((x1+i>=0 && x1+i<=7) && (y1+i>=0 && y1+i<=7)) {

                    //if space contains a piece increase the piece counter
                    if (board[x1 + i][y1 + i] != null) {
                        counter++;
                    }
                }
            }

            //Else piece is blocked
            else {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the bishop is blocked when travelling in the forward left diagonal
     * @param board current state of the board
     * @param x1 x value of bishop
     * @param x2 x value to move to
     * @param y1 y value of bishop
     * @return true if the piece is blocked, false if not
     */
    public boolean isPieceBlockForwardLeft(Piece[][] board, int x1, int x2, int y1){
        int XDiff = x1-x2;
        int counter = 0;

        //For all spaces between the start and end of bishop's move
        for(int i = 1; i <= XDiff; i++){

            //if no pieces have been identified yet
            if(counter==0){

                //if move is in bounds
                if ((x1-i>=0 && x1-i<=7) && (y1+i>=0 && y1+i<=7)) {

                    //if space contains a piece increase the piece counter
                    if (board[x1 - i][y1 + i] != null) {
                        counter++;
                    }
                }
            }

            //Else piece is blocked
            else {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the bishop is blocked when travelling in the backward right diagonal
     * @param board current state of the board
     * @param x1 x value of bishop
     * @param x2 x value to move to
     * @param y1 y value of bishop
     * @return true if the piece is blocked, false if not
     */
    public boolean isPieceBlockBackwardRight(Piece[][] board, int x1, int x2, int y1){
        int XDiff = x2-x1;
        int counter = 0;

        //For all spaces between the start and end of bishop's move
        for(int i = 1; i <= XDiff; i++){

            //if no pieces have been identified yet
            if(counter==0){

                //if move is in bounds
                if ((x1+i>=0 && x1+i<=7) && (y1-i>=0 && y1-i<=7)) {

                    //if space contains a piece increase the piece counter
                    if (board[x1 + i][y1 - i] != null) {
                        counter++;
                    }
                }
            }

            //Else piece is blocked
            else {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the bishop is blocked when travelling in the backward left diagonal
     * @param board current state of the board
     * @param x1 x value of bishop
     * @param x2 x value to move to
     * @param y1 y value of bishop
     * @return true if the piece is blocked, false if not
     */
    public boolean isPieceBlockBackwardLeft(Piece[][] board, int x1, int x2, int y1){
        int XDiff = x1-x2;
        int counter = 0;

        //For all spaces between the start and end of bishop's move
        for(int i = 1; i <= XDiff; i++){

            //if no pieces have been identified yet
            if(counter==0){

                //if move is in bounds
                if ((x1-i>=0 && x1-i<=7) && (y1-i>=0 && y1-i<=7)) {

                    //if space contains a piece increase the piece counter
                    if (board[x1 - i][y1 - i] != null) {
                        counter++;
                    }
                }
            }

            //Else piece is blocked
            else {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check if a bishop is blocked by another piece
     * Identifies the direction of the move, calls the appropriate method
     *
     * @param board current state of the board
     * @param x1 x position of piece
     * @param x2 x position of tile
     * @param y1 y position of piece
     * @param y2 y position of tile
     * @return true if piece is unblocked, false if blocked
     */
    @Override
    public boolean isPieceUnblocked(Piece[][] board, int x1, int x2, int y1, int y2) {

        //If diagonal is to the left
        if(x2<x1){

            //if diagonal is backwards
            if(y2<y1){
                return !isPieceBlockBackwardLeft(board, x1, x2, y1);
            }
            return !isPieceBlockForwardLeft(board, x1, x2, y1);
        }

        //If diagonal is to the left
        if(x2>x1) {

            //If diagonal is backwards
            if (y2 < y1) {
                return !isPieceBlockBackwardRight(board, x1, x2, y1);
            }
            return !isPieceBlockForwardRight(board, x1, x2, y1);
        }
        return true;
    }
}