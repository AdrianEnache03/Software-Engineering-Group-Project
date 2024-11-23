/**
 * @(#) Queen.java 1.0 2023/02/07
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
*/

package com.example.devprojectcode.Pieces;

import com.example.devprojectcode.Enums.PieceColour;
import com.example.devprojectcode.Enums.PieceName;

/**
 * A class that sets the attributes and moves for the Queen
 * This class contains methods to check if a Queen move is blocked by another
 * Inherits attributes from Piece
 *
 * @author George Cooper gwc1
 * @version 0.1 Initial development
 * @version 0.2 Added blocking methods
 * @see Piece
 */
public class Queen extends Piece {
    //---------- CONSTRUCTORS ----------//
    public Queen(PieceColour colour, PieceName name, int xPos, int yPos) {
        super(colour, name, xPos, yPos);
    }

    public Queen() {super();}

    //---------- METHODS----------//
    /**
     *
     *
     * @param x1 initial x position of queen piece
     * @param x2 x position of square to move to
     * @param y1 initial y position of queen piece
     * @param y2 y position of square to move to
     * @return true if move is legal, false if not
     */
    @Override
    public boolean isMoveLegal(int x1, int x2, int y1, int y2) {
        int diffInx = x2-x1;
        int diffIny = y2-y1;
        return(x2==x1 && y2 != y1 ||
                y2==y1 && x2 != x1 ||   //returns legal squares for queen (all squares diagonally and horizontally)
                diffInx == diffIny ||
                -diffInx == diffIny);
    }

    /**
     *
     *
     * @param board chess board array
     * @param x1 initial x position of queen piece
     * @param x2 x position of square to move to
     * @param y1 initial y position of queen piece
     * @return true if a forward right move is blocked by a piece, false if not
     */
    public boolean isPieceBlockForwardRight(Piece[][] board, int x1, int x2, int y1){
        int XDiff = x2-x1;
        int counter = 0;

        //Iterates through squares in the upwards right diagonal to see if the squares are blocked
        for(int i = 1; i <= XDiff; i++){
            if(counter==0){
                // check if current index (x1+i, y1+i) is within the bounds of the board and has a piece
                if ((x1+i>=0 && x1+i<=7) && (y1+i>=0 && y1+i<=7)) {
                    if (board[x1 + i][y1 + i] != null) {
                        counter++;
                    }
                }
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
     * @param x1 initial x position of queen piece
     * @param x2 x position of square to move to
     * @param y1 initial y position of queen piece
     * @return true if a forward left move is blocked by a piece, false if not
     */
    public boolean isPieceBlockForwardLeft(Piece[][] board, int x1, int x2, int y1){
        int XDiff = x1-x2;
        int counter = 0;

        //iterates through squares to the up left diagonal of it to see if the piece is blocked
        for(int i = 1; i <= XDiff; i++){
            if(counter==0){
                // check if current index (x1-i, y1+i) is within the bounds of the board and has a piece
                if ((x1-i>=0 && x1-i<=7) && (y1+i>=0 && y1+i<=7)) {
                    if (board[x1 - i][y1 + i] != null) {
                        counter++;
                    }
                }
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
     * @param x1 initial x position of queen piece
     * @param x2 x position of square to move to
     * @param y1 initial y position of queen piece
     * @return true if a backward right move is blocked by a piece, false if not
     */
    public boolean isPieceBlockBackwardRight(Piece[][] board, int x1, int x2, int y1){
        int XDiff = x2-x1;
        int counter = 0;

        //Iterates through squares to the down diagonal right of it to check if it is blocked
        for(int i = 1; i <= XDiff; i++){
            if(counter==0){

                // check if current index (x1+i, y1-i) is within the bounds of the board and has a piece
                if ((x1+i>=0 && x1+i<=7) && (y1-i>=0 && y1-i<=7)) {
                    if (board[x1 + i][y1 - i] != null) {
                        counter++;
                    }
                }
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
     * @param x1 initial x position of queen piece
     * @param x2 x position of square to move to
     * @param y1 initial y position of queen piece
     * @return true if a backward left move is blocked by a piece, false if not
     */
    public boolean isPieceBlockBackwardLeft(Piece[][] board, int x1, int x2, int y1){
        int XDiff = x1-x2;
        int counter = 0;

        //Iterates through squares to the down diagonal left of it to see if it is blocked
        for(int i = 1; i <= XDiff; i++){
            if(counter==0){

                // check if current index (x1-i, y1-i) is within the bounds of the board and has a piece
                if ((x1-i>=0 && x1-i<=7) && (y1-i>=0 && y1-i<=7)) {
                    if (board[x1 - i][y1 - i] != null) {
                        counter++;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     *Checks if there is a piece blocking a pawn's forward move in a straight line.
     * @param board
     * @param x1 initial x position of queen piece
     * @param y1 initial y position of queen piece
     * @param y2 y position of square to move to
     * @return true if a forward move is blocked by a piece, false if not
     */
    private boolean isPieceBlockMoveForward(Piece[][] board, int x1, int y1, int y2){
        int counter = 0;
        int Ydiff = y1-y2+1;

        //iterates through squares in-front of it and checks if it is blocked
        for(int i = 1; i < Ydiff; i++){
            if(counter==0){
                if (board[x1][y1-i] != null){
                    counter++;
                }
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
     * @param x1 initial x position of queen piece
     * @param x2 x position of square to move to
     * @param y1 initial y position of queen piece
     * @return true if a left move is blocked by a piece, false if not
     */
    private boolean isPieceBlockMoveLeft(Piece[][] board, int x1, int x2, int y1){
        int counter = 0;
        int Xdiff = x1-x2+1;

        //Iterates through the squares to the left to see if the way is blocked
        for(int i = 1; i < Xdiff; i++){
            if(counter==0){
                if (board[x1-i][y1] != null) {
                    counter++;
                }
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
     * @param x1 initial x position of queen piece
     * @param x2 x position of square to move to
     * @param y2 y position of square to move to
     * @return true if a right move is blocked by a piece, false if not
     */
    private boolean isPieceBlockMoveRight(Piece[][] board, int x1, int x2, int y2){
        int counter = 0;
        int Xdiff = x2-x1;

        //Iterate through the tiles to the right to see if the way is blocked
        for(int i = 0; i < Xdiff; i++){
            if(counter==0){
                if (board[x1+i+1][y2] != null){
                    counter++;
                }
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
     * @param x1 initial x position of queen piece
     * @param y1 initial y position of queen piece
     * @param y2 y position of square to move to
     * @return true if a backward move is blocked by a piece, false if not
     */
    private boolean isPieceBlockMoveBack(Piece[][] board, int x1, int y1, int y2){
        int counter = 0;
        int Ydiff = y2-y1;

        //Iterate through the squares down to see if the way is blocked
        for(int i = 0; i < Ydiff; i++){
            if(counter==0){
                if (board[x1][y1+i+1] != null){
                    counter++;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to test if a queen piece is blocked.
     * If the queen is making a sideways move the forward, back, left and right rule methods are called
     * Else it is assumed the queen is making a diagonal move and the diagonal rule methods are called
     * If and move returns true, the queen is blocked and the method returns false
     *
     * @param board
     * @param x1 initial x position of queen piece
     * @param x2 x position of square to move to
     * @param y1 initial y position of queen piece
     * @param y2 y position of square to move to
     * @return True if piece is not blocked, false if blocked
     */
    @Override
    public boolean isPieceUnblocked(Piece[][] board, int x1, int x2, int y1, int y2) {

        // If the piece is moving either horizontally or vertically
        if (x2 == x1 && y2 != y1 || y2 == y1 && x2 != x1) {

            // Check if there are any pieces blocking the path
            if (isPieceBlockMoveForward(board, x1, y1, y2) ||
                    isPieceBlockMoveBack(board, x1, y1, y2) ||
                    isPieceBlockMoveLeft(board, x1, x2, y1) ||
                    isPieceBlockMoveRight(board, x1, x2, y2)) {
                return false;
            }
        } else {

            // If the piece is moving diagonally
            if (x2 < x1) {
                if (y2 < y1) {
                    return !isPieceBlockBackwardLeft(board, x1, x2, y1);
                }
                return !isPieceBlockForwardLeft(board, x1, x2, y1);
            }
            if (x2 > x1) {
                if (y2 < y1) {
                    return !isPieceBlockBackwardRight(board, x1, x2, y1);
                }
                return !isPieceBlockForwardRight(board, x1, x2, y1);
            }
        }
        return true;
    }
}
