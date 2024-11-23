/**
 * @(#) Game.java 1.0 2023/02/07
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 *
 */

/**
 * A class that handles Functional Requirements 4, 5, 6 and 7
 * (Piece selection, movement, detecting check and checkmate)
 *
 * @author Kieran Foy, kif11
 * @version 1.0 Initial development
 * @see Main
 */

public class Game {

    /**
     * Method to detect if the King of the opposite color of the player who just had their turn is in check or checkmate
     * (FR6: Detecting Check, FR7: Detecting Checkmate)
     * Called after every turn
     */
    private void checkOrCheckmate() {

    }

    /**
     * Method to highlight a piece on the board and display its current legal moves
     * (FR4: Piece Selection)
     * Called every time a player selects a piece
     */
    private void selectPiece(int xPos, int yPos) {
        // if this.color = (player color)
            // outline piece using JavaFX
            // get allMoves
            // show all currently possible moves
    }

    /**
     * Method to remove the highlighting of a piece on the board and stop display its current legal moves
     * (FR4: Piece Selection)
     * Called every time a player selects a new piece, and after a piece has been moved
     */
    private void deselectPiece(int xPos, int yPos) {
        // remove JavaFX outline
        // stop showing possible moves
    }

    /**
     * Method to change the X and Y co-ordinates of a piece to a new legal position
     * (FR5: Movement)
     * Called every time a player selects a sqaure on the board where the currently selected piece can legally move to
     */
    private void movePiece(int xPos, int yPos) {
        // if (possible moves of current piece contain the co-ordinates of selected square)
            // this.xPos = new xPos
            // this.yPos = new yPos
            // deselectPiece(this.xPos, this.yPos)
    }

    /**
     * Method to remove a piece from the board when another piece legally moves to its square
     * Called every time a player selects a square on the board where the currently selected piece can legally move to, and that square is occupied
     */
    private void removePiece(int xPos, int yPos) {
        // set square to empty
        // JavaFX: remove image from display


    }
}
