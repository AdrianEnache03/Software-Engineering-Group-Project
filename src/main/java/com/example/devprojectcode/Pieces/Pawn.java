/**
 * @(#) Pawn.java 1.0 2023/02/07
 * 
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
*/

 package com.example.devprojectcode.Pieces;

import com.example.devprojectcode.Enums.PieceColour;
import com.example.devprojectcode.Enums.PieceName;
import com.example.devprojectcode.Game;
import javafx.stage.Stage;

/**
 * A class that sets the attributes and moves for the Pawn
 * Inherits attributes from Piece
 * 
 * @author George Cooper, gwc1
 * @author Adrian Enache, ade12
 * @author Finley Aubin, fia5
 * @version 0.1 Initial development
 * @version 0.2 implements isPieceBlocked
 * @see Piece
 **/
public class Pawn extends Piece {

    //---------- CLASS METHODS ----------//

    /**
     * Promotes a pawn at a given position to the selected piece
     *
     * @param pieceName    name of the piece to promote to
     * @param xPos         x position of the pawn
     * @param yPos         y position of the pawn
     * @param storedColour colour of the piece
     */
    public static void changePiece(PieceName pieceName, int xPos, int yPos, PieceColour storedColour) {

        // Promotes pawn to a Knight if selected
        if (pieceName.equals(PieceName.KNIGHT)) {
            Game.chessBoard[xPos][yPos] = new Knight(storedColour, PieceName.KNIGHT, xPos, yPos);
        }

        // Promotes pawn to a Bishop if selected
        if (pieceName.equals(PieceName.BISHOP)) {
            Game.chessBoard[xPos][yPos] = new Bishop(storedColour, PieceName.BISHOP, xPos, yPos);
        }

        // Promotes pawn to a Rook if selected
        if (pieceName.equals(PieceName.ROOK)) {
            Game.chessBoard[xPos][yPos] = new Rook(storedColour, PieceName.ROOK, xPos, yPos);
        }

        // Promotes pawn to a Queen if selected
        if (pieceName.equals(PieceName.QUEEN)) {
            Game.chessBoard[xPos][yPos] = new Rook(storedColour, PieceName.QUEEN, xPos, yPos);
        }

        System.out.println(Game.chessBoard.toString());
        Stage mainBoard = (Stage) Game.getPawnPromotion().getWindow();
        mainBoard.setScene(Game.getBoard());
    }

    //---------- INSTANCE VARIABLES ----------//
    Boolean isFirstTurn;
    Boolean isEnPassantable;
    Boolean hasEnPassented;

    //---------- CONSTRUCTORS ----------//
    public Pawn(PieceColour colour, PieceName name, int xPos, int yPos) {
        super(colour, name, xPos, yPos);
        this.isFirstTurn = true;
        this.isEnPassantable = false;
        this.hasEnPassented = false;
    }

    public Pawn() {
        super();
    }

    //---------- READ/WRITE PROPERTIES ----------//

    /**
     * Set whether it's the pawn first turn or not
     *
     * @param firstTurn value to set to
     */
    public void setFirstTurn(Boolean firstTurn) {
        isFirstTurn = firstTurn;
    }

    /**
     * Set whether the pawn can be taken via en passant
     *
     * @param enPassantable value to set to
     */
    public void setEnPassantable(Boolean enPassantable) {
        isEnPassantable = enPassantable;
    }

    /**
     * Set whether the pawn is able to take another pawn via en passant
     *
     * @param hasEnPassanted value to set to
     */
    public void setEnPassanted(Boolean hasEnPassanted) {
        hasEnPassented = hasEnPassanted;
    }

    //---------- READ-ONLY PROPERTIES ----------//

    /**
     * Find whether a pawn can be taken via en passant
     *
     * @return true if the pawn can be taken via en passant, false if not
     */
    public Boolean isEnPassantable() {
        return isEnPassantable;
    }

    /**
     * Find whether a pawn has taken via en passant
     *
     * @return true if the pawn has taken via en passant, false if not
     */
    public Boolean hasEnPassanted() {
        return hasEnPassented;
    }

    /**
     * Find whether it's a pawns first turn
     *
     * @return true if it's the pawns first turn, false if not
     */
    public Boolean isFirstTurn() {
        return isFirstTurn;
    }

    //---------- METHODS----------//

    /**
     * Determine whether a pawn move is legal or not
     * checks all legal moves, if the pawn moves 2 spaces sets EnPassantable to true
     *
     * @param x1 x position of pawn
     * @param x2 x position of square to move to
     * @param y1 y position of pawn
     * @param y2 y position of square to move to
     * @return true if move is legal, false if move is not legal
     */
    public boolean isMoveLegal(int x1, int x2, int y1, int y2) {

        //check for white pieces
        if (this.getColour() == PieceColour.BLACK) {

            // Check for first move (for double first move)
            if (isFirstTurn) {

                // Check if the piece is moving 2 squares forward or 1 squares forward
                if (x2 == x1 && y2 == y1 + 2 || x2 == xPos && y2 == y1 + 1) {
                    return true;
                }
            }

            // Check if the piece is moving 1 square forward or diagonally taking an opponent's piece
            return x2 == x1 && y2 == y1 + 1 || takePiece(x1, x2, y1, y2);

            //check for black pieces
        } else if (this.getColour() == PieceColour.WHITE) {
            if (isFirstTurn) {
                if (x2 == x1 && y2 == y1 - 2 || x2 == x1 && y2 == y1 - 1) {
                    return true;
                }
            }
            return x2 == x1 && y2 == y1 - 1 || takePiece(x1, x2, y1, y2);
        }
        return false;
    }

    /**
     * Determines whether the pawn is able to take a piece
     *
     * @param board current state of the board
     * @param x1    x position of pawn
     * @param x2    x position of piece to take
     * @param y1    y position of pawn
     * @param y2    y position of piece to take
     * @return
     */
    private boolean isPieceAbleToTake(Piece[][] board, int x1, int x2, int y1, int y2) {

        // Check if there is a piece at the target square
        if (board[x2][y2] != null) {

            // Check if the piece is able to take the opponent's piece
            if (takePiece(x1, x2, y1, y2)) {
                return true;
            }
        }

        // The piece is not able to take an opponent's piece
        return false;
    }

    /**
     * Determine whether the pawn is blocked by another piece
     *
     * @param board current state of the board
     * @param x1    x position of pawn
     * @param x2    x position of tile to move to
     * @param y1    y position of pawn
     * @param y2    y position of tile to move to
     * @return True if pawn is blocked, false if not
     */
    @Override
    public boolean isPieceUnblocked(Piece[][] board, int x1, int x2, int y1, int y2) {
        if (this.getColour() == PieceColour.BLACK) {

            // Check if the move is a diagonal take
            if (y2 == y1 + 1) {
                if (x2 == x1 + 1 || x2 == x1 - 1) {

                    // Return true if a piece is able to be taken on the diagonal
                    return (isPieceAbleToTake(board, x1, x2, y1, y2));
                }
            }
            if (this.isFirstTurn) {
                if (board[x2][y2] != null) {

                    // there's a piece in the square blocking the pawn
                    return false;
                }
            }

            // the square in front of the pawn is empty, allowing the pawn to move forward
            return board[x1][y1 + 1] == null;

            //same for white
        } else if (this.getColour() == PieceColour.WHITE) {
            if (y2 == y1 - 1) {
                if (x2 == x1 + 1 || x2 == x1 - 1) {
                    return (isPieceAbleToTake(board, x1, x2, y1, y2));
                }
            }
            if (this.isFirstTurn) {
                if (board[x2][y2] != null) {

                    // there's a piece in the square blocking the pawn
                    return false;
                }
            }

            // the square in front of the pawn is empty, allowing it to move forward
            return board[x1][y1 - 1] == null;
        }
        return false;
    }

    /**
     * Determines if a piece can take another piece.
     *
     * @param x1 the initial x position of the piece
     * @param x2 the final x position of the piece
     * @param y1 the initial y position of the piece
     * @param y2 the final y position of the piece
     * @return true if the piece can take another piece, false if not
     */
    public boolean takePiece(int x1, int x2, int y1, int y2) {

        // Check if the piece is black
        if (getColour() == PieceColour.BLACK) {

            // Check if the piece is moving forward and to the right
            if (x2 == x1 + 1) {
                return y2 == y1 + 1;
            }

            // Check if the piece is moving forward and to the left
            else if (x2 == x1 - 1) {
                return y2 == y1 + 1;
            }

            // Otherwise, the piece cannot take another piece
            else {
                return false;
            }
        }

        // Check if the piece is white
        else if (getColour() == PieceColour.WHITE) {

            // Check if the piece is moving forward and to the right
            if (x2 == x1 + 1) {
                return y2 == y1 - 1;
            }

            // Check if the piece is moving forward and to the left
            else if (x2 == x1 - 1) {
                return y2 == y1 - 1;
            }

            // Otherwise, the piece cannot take another piece
            else {
                return false;
            }
        }

        // If the color is not recognized, the piece cannot take another piece
        else {
            return false;
        }
    }
}