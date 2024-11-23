package com.example.devprojectcode;

import com.example.devprojectcode.Enums.PieceColour;
import com.example.devprojectcode.Enums.PieceName;
import com.example.devprojectcode.Pieces.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @(#) GameTest.java 0.12 2023/05/01
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 *
 */

/**
 * A class that tests the methods defined in Game.java, uses JUnit5 to do so.
 *
 * @author Kieran Foy (kif11)
 * @author Tate J. A. Moore (tam41)
 *
 * @version 0.1 - 1. Changed test instance lifestyle to per class -  game and set up don't have defined and initialised every test. 2. Added methods to initialise game, set up and the board as well as resetting the board to starting positions every test. 3. Added testPawnCheckMove() for checking pawn movement.
 * @version 0.2 - Added testTurnChecker().
 * @version 0.3 - Added testQueenCheckMove().
 * @version 0.4 - Added testKingCheckMove().
 * @version 0.5 - Added testKnightCheckMove().
 * @version 0.6 - Added testBishopCheckMove().
 * @version 0.7 - Added testRookCheckMove().
 * @version 0.8 - Added to testPawnCheckMove(), to include tests for moving a pawn 2 spaces when it has already moved.
 * @version 0.9 - Added test method to check castling can be done.
 * @version 0.10 - Added testEnPassant() and enPassantSetup().
 * @version 0.11 - Added testUpdateBoard().
 * @version 0.12 - Added several tests for testing check and checkmate.
 * @version 0.13 - Added testInCheckMovement() for testing check movement restriction.
 * @version 0.14 - Added testCastling() to tests if the castling special case works
 * @version 0.15 - Added testPawnDeliveringCheck() and more check tests for other pieces
 * @version 0.16 - Added testBlackKingCheckmate() and testWhiteKingCheckmate to test if the program recognises when one of the two Kings are in checkmate
 *
 * @see Game
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameTest {

    private static Game game;
    private static Setup setup;

    /**
     * Before all the tests run, this defines and initialises game, setup and the setup chessboard with all piece's in their starting positions.
     */
    @BeforeAll
    static void initialiseGame() {
        game = new Game();
        setup = new Setup();
        setup.boardSetup();
    }

    /**
     * Runs before each test.
     * Resets the turn tracker so that it's white's turn.
     * Resets chessboard - sets the Game's chessboard to setup's chessboard (which has all the pieces in their starting positions.).
     */
    @BeforeEach
    void resetBoard() {
        Game.turnTracker = 1;
        Game.chessBoard = setup.getBoard();
    }

    /**
     * Checks to see if turnChecker() works.
     * turnChecker() checks that it is the right player's turn.
     *
     * 1. When it is white's turn, tries moving a black pawn (false) and then a white pawn (true.)
     * 2. When it is black's turn, tries moving a black pawn (true) and then a white pawn (false.)
     */
    @Test
    void testTurnChecker() throws IOException {

        //From starting board

        //White's turn

        //attempts to move a black pawn at 0,1 to 0,3 when it is white's turn - FALSE
        assertFalse(game.checkMove(0, 0, 1, 3));

        //attempts to move a white pawn at 0,6 to 0,4 when it is white's turn - TRUE
        assertTrue(game.checkMove(0, 0, 6, 4));

        Game.turnTracker = 2; //Black's turn


        //attempts to move a black pawn at 0,1 to 0,3 when it is black's turn - TRUE
        assertTrue(game.checkMove(0, 0, 1, 3));

        //attempts to move a white pawn at 0,6 to 0,4 when it is black's turn - FALSE
        assertFalse(game.checkMove(0, 0, 6, 4));
    }

    /**
     * Checks to see if checkMove() works for moving a pawn
     * checkMove() checks to see if the given movement of a piece is legal.
     *
     * 1. Tries moving a black pawn within and out of a pawn's movement range.
     * 2. Tries taking pieces with a pawn.
     * 3. Tries a white pawn moving and through pieces.
     * 4. Tries moving a pawn 2 spaces (and 1) when it has already moved before.
     */
    @Test
    void testPawnCheckMove() throws IOException {

        //From starting board

        //turn tracker
        Game.turnTracker = 2; //black's turn

        //-------------------------------
        //--MOVING WITHIN MOVEMENT RANGE--

        //--TRUE--

        //moving a black pawn at 0,1 to 0,3 - TRUE
        assertTrue(game.checkMove(0, 0, 1, 3));

        //moving a black pawn at 0,1 to 0,2 - TRUE
        assertTrue(game.checkMove(0, 0, 1, 2));

        //--FALSE--

        //moving a black pawn at 0,1 to 0,4 - FALSE
        assertFalse(game.checkMove(0, 0, 1, 4));

        //moving a black pawn at 0,1 to 0,0 - FALSE
        assertFalse(game.checkMove(0, 0, 1, 0));

        //moving a black pawn at 0,1 to 1,0 - FALSE
        assertFalse(game.checkMove(0, 1, 1, 0));

        //moving a black pawn at 0,1 to 1,2 - FALSE
        assertFalse(game.checkMove(0, 1, 1, 2));

        //-----------------
        //--TAKING PIECES--

        //new black pawn at 0,5
        Game.chessBoard[0][5] = new Pawn(PieceColour.BLACK, PieceName.PAWN, 0, 5);

        //a black pawn at 0,5 takes a white piece at 1,6 - TRUE
        assertTrue(game.checkMove(0, 1, 5, 6));

        //a black pawn at 0,5 takes a white piece at 0,6 - FALSE
        assertFalse(game.checkMove(0, 0, 5, 6));

         //----------------------------------
        //--MOVING THROUGH AND ONTO PIECES--

        Game.turnTracker = 1; //white's turn

        //A white pawn at 0,6 moves to 0,4 when there is a black pawn at 0,5 (tries to step over it) - False
        assertFalse(game.checkMove(0, 0, 6, 4));

        //A white pawn at 0,6 moves to 0,5 when there is a black pawn - False
        assertFalse(game.checkMove(0, 0, 6, 5));

        //-----------------------------------------------------
        //--TESTING IF PAWN CAN MOVE 2 SQUARES IF NOT 1ST MOVE--

        Pawn pawn = new Pawn(PieceColour.WHITE, PieceName.PAWN, 0, 6);
        pawn.setFirstTurn(false);  //not first move so can't move 2 squares.

        Game.chessBoard[0][4] = pawn; //white pawn at 0,4

        //A white pawn at 0,4 moves to 0,3 when there is a black pawn - True
        assertTrue(game.checkMove(0, 0, 4, 3));

        //white pawn at 0,4 moves to 0,2 - False
        assertFalse(game.checkMove(0, 0, 4, 2));

    }

    /**
     * Checks to see if checkMove() works for moving a knight
     * checkMove() checks to see if the given movement of a piece is legal.
     *
     * 1. Tries moving onto and through friendly pieces.
     * 2. Tries moving within and out of a king's movement range onto free space.
     */
    @Test
    void testKnightCheckMove()throws IOException {

        //From starting board

        //------------------------------------------
        //--MOVING ONTO AND THROUGH FRIENDLY PIECES--

        //Attempts to move white knight at 1,7 through white pieces onto 3,6 - FALSE
        assertFalse(game.checkMove(1, 3, 7, 6));

        //Attempts to move white knight at 6,7 to white pawn at 4,6 - FALSE
        assertFalse(game.checkMove(6, 4, 7, 6));

        //----------------------------------
        //--REGULAR MOVING ONTO FREE SPACE--

        Game.chessBoard = new Piece[8][8];

        //new white king at 3,3
        Game.chessBoard[3][3] = new Knight(PieceColour.WHITE, PieceName.KNIGHT, 3, 3);

        //--CORRECT / TRUE--

        //Moving knight at 3,3 to 2,1 (Left 1, Up 2)
        assertTrue(game.checkMove(3, 2, 3, 1));

        //Moving knight at 3,3 to 1,2 (Left 2, Up 1)
        assertTrue(game.checkMove(3, 1, 3, 2));

        //Moving knight at 3,3 to 1,4 (Left 2, Down 1)
        assertTrue(game.checkMove(3, 1, 3, 4));

        //Moving knight at 3,3 to 2,5 (Left 1, Down 2)
        assertTrue(game.checkMove(3, 2, 3, 5));

        //Moving knight at 3,3 to 4,5 (Right 1, Down 2)
        assertTrue(game.checkMove(3, 4, 3, 5));

        //Moving knight at 3,3 to 5,4 (Right 2, Down 1)
        assertTrue(game.checkMove(3, 5, 3, 4));

        //Moving knight at 3,3 to 5,2 (Right 2, Up 1)
        assertTrue(game.checkMove(3, 5, 3, 2));

        //Moving knight at 3,3 to 4,1 (Right 1, Up 2)
        assertTrue(game.checkMove(3, 4, 3, 1));

        //--INCORRECT / FALSE--

        //Moving knight at 3,3 to 3,4 (invalid movement, doesn't move far enough)
        assertFalse(game.checkMove(3, 3, 3, 4));

        //Moving knight at 3,3 to 1,5 (invalid movement, moves too far)
        assertFalse(game.checkMove(3, 1, 3, 5));

    }

    /**
     * Checks to see if checkMove() works for moving a rook
     * checkMove() checks to see if the given movement of a piece is legal.
     *
     * 1. Tries moving onto and through friendly pieces.
     * 2. Tries moving within and out of a rook's movement range onto free space.
     */
    @Test
    void testRookCheckMove() throws IOException{

        //From starting board

        //------------------------------------------
        //--MOVING ONTO AND THROUGH FRIENDLY PIECES--

        //Attempts to move white rook at 0,7 through white pieces onto 2,6 - FALSE
        assertFalse(game.checkMove(0, 2, 7, 6));

        //Attempts to move white rook at 0,7 to white pawn at 0,6 - FALSE
        assertFalse(game.checkMove(0, 0, 7, 6));

        //----------------------------------
        //--REGULAR MOVING ONTO FREE SPACE--

        Game.chessBoard = new Piece[8][8];

        //new white rook at 3,3
        Game.chessBoard[3][3] = new Rook(PieceColour.WHITE, PieceName.ROOK, 3, 3);

        //--CORRECT / TRUE--

        //Moving rook at 3,3 to 3,1 (Up)
        assertTrue(game.checkMove(3, 3, 3, 1));

        //Moving rook at 3,3 to 0,3 (Left)
        assertTrue(game.checkMove(3, 0, 3, 3));

        //Moving rook at 3,3 to 7,3 (Right)
        assertTrue(game.checkMove(3, 7, 3, 3));

        //Moving rook at 3,3 to 3,5 (Down)
        assertTrue(game.checkMove(3, 3, 3, 5));

        //--INCORRECT / FALSE--

        //Moving rook at 3,3 to 2,2 (invalid movement, moves diagonally)
        assertFalse(game.checkMove(3, 2, 3, 2));

        //adds opposition black pawn at 3,1
        Game.chessBoard[3][3] = new Pawn(PieceColour.BLACK, PieceName.PAWN, 3, 1);

        //Moving rook at 3,3 to 3,0 (invalid movement, skips over piece)
        assertFalse(game.checkMove(3, 3, 3, 0));

    }

    /**
     * Checks to see if checkMove() works for moving a bishop
     * checkMove() checks to see if the given movement of a piece is legal.
     *
     * 1. Tries moving onto and through friendly pieces.
     * 2. Tries moving within and out of a bishop's movement range onto free space.
     */
    @Test
    void testBishopCheckMove() throws IOException{

        //From starting board

        //------------------------------------------
        //--MOVING ONTO AND THROUGH FRIENDLY PIECES--

        //Attempts to move white bishop at 2,7 through white pieces onto 5,4 - FALSE
        assertFalse(game.checkMove(2, 5, 7, 4));

        //Attempts to move white bishop at 0,7 to white pawn at 6,3 - FALSE
        assertFalse(game.checkMove(0, 6, 7, 3));

        //----------------------------------
        //--REGULAR MOVING ONTO FREE SPACE--

        Game.chessBoard = new Piece[8][8];

        //new white bishop at 3,3
        Game.chessBoard[3][3] = new Bishop(PieceColour.WHITE, PieceName.BISHOP, 3, 3);

        //--CORRECT / TRUE--

        //Moving bishop at 3,3 to 4,2 (Up-Right Diagonal)
        assertTrue(game.checkMove(3, 4, 3, 2));

        //Moving bishop at 3,3 to 5,5 (Down-Right Diagonal)
        assertTrue(game.checkMove(3, 5, 3, 5));

        //Moving bishop at 3,3 to 2,5 (Down-Left Diagonal)
        assertTrue(game.checkMove(3, 2, 3, 5));

        //Moving bishop at 3,3 to 1,1 (Up-Left Diagonal)
        assertTrue(game.checkMove(3, 1, 3, 1));

        //--INCORRECT / FALSE--

        //adds opposition black pawn at 1,1
        Game.chessBoard[1][1] = new Pawn(PieceColour.BLACK, PieceName.PAWN, 1, 1);

        //Moving bishop at 3,3 to 0,0 (invalid movement, skips over piece)
        assertFalse(game.checkMove(3, 0, 3, 0));

        //Moving bishop at 3,3 to 7,3 (invalid movement, moves vertically)
        assertFalse(game.checkMove(3, 7, 3, 3));

        //Moving bishop at 3,3 to 3,5 (invalid movement, moves horizontally)
        assertFalse(game.checkMove(3, 3, 3, 5));

    }

    /**
     * Checks to see if checkMove() works for moving a queen
     * checkMove() checks to see if the given movement of a piece is legal.
     *
     * 1. Tries moving onto and through friendly pieces.
     * 2. Tries moving within and out of a queen's movement range onto free space.
     */
    @Test
    void testQueenCheckMove()throws IOException {

        //From starting board

        //------------------------------------------
        //--MOVING ONTO AND THROUGH FRIENDLY PIECES--

        //Attempts to move white queen at 3,7 through white pieces onto 3,5 - FALSE
        assertFalse(game.checkMove(3, 3, 7, 5));

        //Attempts to move white queen at 3,7 to white pawn at 3,6 - FALSE
        assertFalse(game.checkMove(3, 3, 7, 6));

        //----------------------------------
        //--REGULAR MOVING ONTO FREE SPACE--

        Game.chessBoard = new Piece[8][8];

        //new white queen at 3,3
        Game.chessBoard[3][3] = new Queen(PieceColour.WHITE, PieceName.QUEEN, 3, 3);

        //--CORRECT / TRUE--

        //Moving queen at 3,3 to 3,0 (Up)
        assertTrue(game.checkMove(3, 3, 3, 0));

        //Moving queen at 3,3 to 3,7 (Down)
        assertTrue(game.checkMove(3, 3, 3, 7));

        //Moving queen at 3,3 to 7,3 (Right)
        assertTrue(game.checkMove(3, 7, 3, 3));

        //Moving queen at 3,3 to 0,3 (Left)
        assertTrue(game.checkMove(3, 0, 3, 3));

        //Moving queen at 3,3 to 0,0 (Left-up diagonal)
        assertTrue(game.checkMove(3, 0, 3, 0));

        //Moving queen at 3,3 to 0,7 (Left-down diagonal)
        assertTrue(game.checkMove(3, 0, 3, 7));

        //Moving queen at 3,3 to 7,0 (Right-up diagonal)
        assertTrue(game.checkMove(3, 7, 3, 0));

        //Moving queen at 3,3 to 7,7 (Right-down diagonal)
        assertTrue(game.checkMove(3, 7, 3, 7));

        //--INCORRECT / FALSE--

        //Moving queen at 3,3 to 0,5
        assertFalse(game.checkMove(3, 0, 3, 5));

        //Moving queen at 3,3 to 4,5
        assertFalse(game.checkMove(3, 4, 3, 5));

        //Moving queen at 3,3 to 6,2
        assertFalse(game.checkMove(3, 6, 3, 2));
    }

    /**
     * Checks to see if checkMove() works for moving a king
     * checkMove() checks to see if the given movement of a piece is legal.
     *
     * 1. Tries moving onto friendly pieces.
     * 2. Tries moving within and out of a king's movement range onto free space.
     */
    @Test
    void testKingCheckMove() throws IOException{

        //From starting board

        //------------------------------------------
        //--MOVING ONTO FRIENDLY PIECES--

        //Attempts to move white king at 4,7 to white pawn at 4,6 - FALSE
        assertFalse(game.checkMove(4, 4, 7, 6));

        //----------------------------------
        //--REGULAR MOVING ONTO FREE SPACE--

        Game.chessBoard = new Piece[8][8];

        //new white king at 3,3
        Game.chessBoard[3][3] = new King(PieceColour.WHITE, PieceName.KING, 3, 3);

        //--CORRECT / TRUE--

        //Moving king at 3,3 to 3,2 (Up)
        assertTrue(game.checkMove(3, 3, 3, 2));

        //Moving king at 3,3 to 3,4 (Down)
        assertTrue(game.checkMove(3, 3, 3, 4));

        //Moving king at 3,3 to 4,3 (Right)
        assertTrue(game.checkMove(3, 4, 3, 3));

        //Moving king at 3,3 to 2,3 (Left)
        assertTrue(game.checkMove(3, 2, 3, 3));

        //Moving king at 3,3 to 2,2 (Left-up diagonal)
        assertTrue(game.checkMove(3, 2, 3, 2));

        //Moving king at 3,3 to 2,4 (Left-down diagonal)
        assertTrue(game.checkMove(3, 2, 3, 4));

        //Moving king at 3,3 to 4,2 (Right-up diagonal)
        assertTrue(game.checkMove(3, 4, 3, 2));

        //Moving king at 3,3 to 4,4 (Right-down diagonal)
        assertTrue(game.checkMove(3, 4, 3, 4));

        //--INCORRECT / FALSE--

        //Moving king at 3,3 to 5,3 (too far)
        assertFalse(game.checkMove(3, 5, 3, 3));

        //Moving king at 3,3 to 4,5 (invalid movement)
        assertFalse(game.checkMove(3, 4, 3, 5));

        //Moving king at 3,3 to 6,2 (invalid movement)
        assertFalse(game.checkMove(3, 6, 3, 2));
    }


    /**
     * Setups the board for testEnPassant().
     * White piece at 1,3
     * Moves black piece at 0,1 -> 0,3
     */
    void enPassantSetup() throws IOException {
        //new white pawn at 1,3
        Game.chessBoard[1][3] = new Pawn(PieceColour.WHITE, PieceName.PAWN, 1, 3);

        Game.turnTracker = 2; //black's turn

        //moves black pawn at 0,1 -> 0,3
        game.updateBoard(0,0,1,3);
    }

    /**
     * Checks to see if en passant works
     *
     * 1. Tests to see if you can actually en passant.
     * 2. Testing en passant rule where the pawn that's being taken must just have moved.
     * 3. Tests if en passant where the pawn that's being taken must has only moved 1 square.
     */
    @Test
    void testEnPassant() throws IOException {

        //----------------------------
        //--TESTING EN PASSANT WORKS--

        enPassantSetup();

        //tries to move white pawn at 1,3 -> 0,2 - TRUE
        assertTrue(game.checkMove(1, 0, 3, 2));

//        //moves white pawn at 1,3 -> 0,2
//        game.updateBoard(1, 0, 3, 2); //EN PASSANT METHOD?
//
//        //is there a white pawn at 0,2 - true
//        assertEquals(PieceColour.WHITE,Game.chessBoard[0][2].getColour());
//
//        //is there nothing at 0,3 - black's previous pos (taken)
//        assertNull(Game.chessBoard[0][3]);
//
//        //is there nothing at 1,3 - white pawn's previous pos (moved)
//        assertNull(Game.chessBoard[1][3]);

        //--------------------------------------------------------------------------------
        //--TESTING EN PASSANT DOESN'T WORK IF 2 SQUARE ADVANCE WAS MORE THAN 1 TURN AGO--

        resetBoard();

        enPassantSetup();

        //random movement to make it so the 2 square advancement was not on the previous turn.
        game.updateBoard(7, 7, 6, 5);

        //tries to move white pawn at 1,3 -> 0,2 - FALSE
        assertFalse(game.checkMove(1, 0, 3, 2));

        //--------------------------------------------------
        //--TESTING EN PASSANT IF NOT 2 SQUARE ADVANCEMENT--

        resetBoard();

        //new white pawn at 1,2
        Game.chessBoard[1][2] = new Pawn(PieceColour.WHITE, PieceName.PAWN, 1, 3);

        Game.turnTracker = 2; //black's turn

        //moves black pawn at 0,1 -> 0,2
        game.updateBoard(0,0,1,2);

        //tries to move white pawn at 1,2 -> 0,1 - FALSE
        assertFalse(game.checkMove(1, 0, 2, 1));
    }

    /**
     * Checks to see if updateBoard(..) works correctly.
     * updateBoard(..) updates the board for a given move.
     *
     * 1. Tests if the board updates correctly after movement onto free space.
     * 2. Tests if the board updates correctly after taking another piece
     */
    @Test
    void testUpdateBoard() throws IOException {

        //----------------------------
        //--MOVEMENT ONTO FREE SPACE--

        //Moves white pawn at 0,6 -> 0,4
        game.updateBoard(0,0,6,4);

        //No piece anymore left at 0,6
        assertNull(Game.chessBoard[0][6]);

        //Checks pawn is at 0,4 now.
        assertEquals(PieceName.PAWN, Game.chessBoard[0][4].getName());

        //------------------------
        //--TAKING ANOTHER PIECE--

        //new black piece at 1,3
        Game.chessBoard[1][3] = new Pawn(PieceColour.BLACK, PieceName.PAWN, 1, 3);

        //Moves black pawn at 1,3 -> 0,4
        game.updateBoard(1,0,3,4);

        //No piece anymore left at 1,3
        assertNull(Game.chessBoard[1][3]);

        //Checks pawn at 0,4 is black now.
        assertEquals(PieceColour.BLACK, Game.chessBoard[0][4].getColour());
    }

    @Test
    void testCheckMove() {
    }


    @Test
    void startUp() {
    }

    @Test
    void getBoard() {
    }

    @Test
    void getMainMenu() {
    }

    @Test
    void getLogIn() {
    }

    @Test
    void getPause() {
    }

    /**
     * Checks to see if checkMove() works for the castling special case
     * checkMove() checks to see if the given movement of a piece is legal.
     *
     * 1. Tries moving King two spaces to the left or right if it and the two rooks of the same color haven't moved
     * 2. Only need to assert that the King cannot move too far in the incorrect test cases, all other possible moves in this scenario are covered by testKingCheckMove()
     *
     */
    @Test
    void testCastling() throws IOException {

        Game.chessBoard = new Piece[8][8];

        //add white rooks
        Game.chessBoard[0][7] = new Rook(PieceColour.WHITE, PieceName.ROOK, 0, 7);
        Game.chessBoard[7][7] = new Rook(PieceColour.WHITE, PieceName.ROOK, 7, 7);

        //add black rooks
        Game.chessBoard[0][0] = new Rook(PieceColour.BLACK, PieceName.ROOK, 0, 0);
        Game.chessBoard[7][0] = new Rook(PieceColour.BLACK, PieceName.ROOK, 7, 0);

        // add kings
        Game.chessBoard[4][7] = new King(PieceColour.WHITE, PieceName.KING, 4, 7);
        Game.chessBoard[4][0] = new King(PieceColour.BLACK, PieceName.KING, 4, 0);

        //--CORRECT / TRUE--

        //Moving white king at 4,7 to 2,7 (rook at 0,7 moves to 3,7)
        assertTrue(game.checkMove(4, 2, 7, 7));
        assertTrue(game.checkMove(0, 3, 7, 7));

        //Moving white king at 4,7 to 6,7 (rook at 7,7 moves to 5,7)
        assertTrue(game.checkMove(4, 6, 7, 7));
        assertTrue(game.checkMove(7, 5, 7, 7));

        //Moving black king at 4,0 to 2,0 (rook at 0,0 moves to 3,0)
        assertTrue(game.checkMove(4, 2, 0, 0));
        assertTrue(game.checkMove(0, 3, 0, 0));

        //Moving black king at 4,0 to 6,7 (rook at 7,0 moves to 5,0)
        assertTrue(game.checkMove(4, 6, 0, 0));
        assertTrue(game.checkMove(7, 5, 0, 0));

        //--INCORRECT / FALSE--

        //Moving white king at 4,7 to 1,7 (Invalid movement, too far)
        assertTrue(game.checkMove(4, 1, 7, 7));

        //Moving white king at 4,7 to 7,7 (Invalid movement, too far)
        assertTrue(game.checkMove(4, 7, 7, 7));

        //Moving black king at 4,0 to 1,0 (Invalid movement, too far)
        assertTrue(game.checkMove(4, 1, 0, 0));

        //Moving black king at 4,0 to 7,0 (Invalid movement, too far)
        assertTrue(game.checkMove(4, 7, 0, 0));

    }

    /**
     * Checks to see if checkMove() works for a King when it is checked by a pawn
     * checkMove() checks to see if the given movement of a piece is legal.
     * Checks to see if isCheck is called
     *
     * 1. Sees if isCheck is called (program recognised the king is in check)
     * 2. Sees if the king can escape check (either by moving or taking a piece)
     *
     */
    @Test
    void testPawnDeliveringCheck() throws IOException {

        Game.chessBoard = new Piece[8][8];

        //add white king
        Game.chessBoard[4][7] = new King(PieceColour.WHITE, PieceName.KING, 4, 7);

        //add black king
        Game.chessBoard[7][0] = new King(PieceColour.BLACK, PieceName.KING, 7, 0);

        //add white pawn
        Game.chessBoard[6][1] = new Pawn(PieceColour.WHITE, PieceName.PAWN, 6, 1);

        //asserts that the isCheck condition is true
        assertTrue(game.isCheck(6,1));

        //asserts king can move out of check
        assertTrue(game.checkMove(7,7, 0, 1));
        assertTrue(game.checkMove(7,6, 0, 0));

        //asserts king can take pawn to get out of check
        assertTrue(game.checkMove(7,6, 0, 1)); //move diagonally left and down

    }

    /**
     * Checks to see if checkMove() works for a King when it is checked by a knight
     * checkMove() checks to see if the given movement of a piece is legal.
     * Checks to see if isCheck is called
     *
     * 1. Sees if isCheck is called (program recognised the king is in check)
     * 2. Sees if the king can escape check (either by moving or taking a piece)
     *
     */
    @Test
    void testKnightDeliveringCheck() throws IOException {

        Game.chessBoard = new Piece[8][8];

        //add black king
        Game.chessBoard[4][0] = new King(PieceColour.BLACK, PieceName.KING, 4, 0);

        //add white king
        Game.chessBoard[7][7] = new King(PieceColour.WHITE, PieceName.KING, 7, 7);

        //add black knight
        Game.chessBoard[6][5] = new Knight(PieceColour.BLACK, PieceName.KNIGHT, 6, 5);

        //asserts that the isCheck condition is true
        assertTrue(game.isCheck(6,5));

        //asserts king can move out of check
        assertTrue(game.checkMove(7,6, 7, 7)); //move left
        assertTrue(game.checkMove(7,7, 7, 6)); //move down
        assertTrue(game.checkMove(7,6, 7, 6)); //move diagonally left and down
    }

    /**
     * Checks to see if checkMove() works for a King when it is checked by a bishop
     * checkMove() checks to see if the given movement of a piece is legal.
     * Checks to see if isCheck is called
     *
     * 1. Sees if isCheck is called (program recognised the king is in check)
     * 2. Sees if the king can escape check (either by moving or taking a piece)
     *
     */
    @Test
    void testBishopDeliveringCheck() throws IOException {

        Game.chessBoard = new Piece[8][8];

        //add black king
        Game.chessBoard[6][0] = new King(PieceColour.BLACK, PieceName.KING, 6, 0);

        //add white king
        Game.chessBoard[4][7] = new King(PieceColour.WHITE, PieceName.KING, 4, 7);

        //add white bishop
        Game.chessBoard[2][4] = new Bishop(PieceColour.WHITE, PieceName.BISHOP, 2, 4);

        //asserts that the isCheck condition is true
        assertTrue(game.isCheck(2,4));

        //asserts king can move out of check
        assertTrue(game.checkMove(6,5, 0, 0)); //move left
        assertTrue(game.checkMove(6,6, 0, 1)); //move down
        assertTrue(game.checkMove(6,7, 0, 0)); //move right
        assertTrue(game.checkMove(6,7, 0, 1)); //move diagonally right and down
    }

    /**
     * Checks to see if checkMove() works for a King when it is checked by a rook
     * checkMove() checks to see if the given movement of a piece is legal.
     * Checks to see if isCheck is called
     *
     * 1. Sees if isCheck is called (program recognised the king is in check)
     * 2. Sees if the king can escape check (either by moving or taking a piece)
     *
     */
    @Test
    void testRookDeliveringCheck() throws IOException {

        Game.chessBoard = new Piece[8][8];

        //add black king
        Game.chessBoard[4][0] = new King(PieceColour.BLACK, PieceName.KING, 4, 0);

        //add white king
        Game.chessBoard[7][7] = new King(PieceColour.WHITE, PieceName.KING, 7, 7);

        //add black rook
        Game.chessBoard[5][7] = new Rook(PieceColour.BLACK, PieceName.ROOK, 5, 7);

        //asserts that the isCheck condition is true
        assertTrue(game.isCheck(5,7));

        //asserts king can move out of check
        assertTrue(game.checkMove(7,6, 7, 6)); //move diagonally left and down
        assertTrue(game.checkMove(7,7, 7, 6)); //move down
    }

    /**
     * Checks to see if checkMove() works for a King when it is checked by a queen
     * checkMove() checks to see if the given movement of a piece is legal.
     * Checks to see if isCheck is called
     *
     * 1. Sees if isCheck is called (program recognised the king is in check)
     * 2. Sees if the king can escape check (either by moving or taking a piece)
     *
     */
    @Test
    void testQueenDeliveringCheck() throws IOException {

        Game.chessBoard = new Piece[8][8];

        //add white king
        Game.chessBoard[4][7] = new King(PieceColour.WHITE, PieceName.KING, 4, 7);

        //add black king
        Game.chessBoard[7][0] = new King(PieceColour.BLACK, PieceName.KING, 7, 0);

        //add white queen
        Game.chessBoard[5][0] = new Queen(PieceColour.WHITE, PieceName.QUEEN, 5, 0);

        //asserts that the isCheck condition is true
        assertTrue(game.isCheck(5,0));

        //asserts king can move out of check
        assertTrue(game.checkMove(7,7,0,1)); //move down
    }

    /**
     * Checks to see if isCheckMate() is called when the king is in checkmate
     *
     * 1. Sees if isCheckMate is called (program recognised the king is in checkmate)
     * 2. Assures the king can't escape checkmate
     *
     */
    @Test
    void testBlackKingCheckmate() throws IOException {

        Game.chessBoard = new Piece[8][8];

        //add black king
        Game.chessBoard[7][0] = new King(PieceColour.BLACK, PieceName.KING, 7, 0);

        //add white king
        Game.chessBoard[4][7] = new King(PieceColour.WHITE, PieceName.KING, 4, 7);

        //add white pawn
        Game.chessBoard[6][1] = new Pawn(PieceColour.WHITE, PieceName.PAWN, 6, 1);

        //add black pawn
        Game.chessBoard[7][1] = new Pawn(PieceColour.BLACK, PieceName.PAWN, 7, 1);

        //add white queen
        Game.chessBoard[5][1] = new Queen(PieceColour.WHITE, PieceName.QUEEN, 5, 1);

        //asserts if isCheckMate() is called
        assertTrue(game.isCheckMate());

    }

    /**
     * Checks to see if isCheckMate() is called when the king is in checkmate
     *
     * 1. Sees if isCheckMate is called (program recognised the king is in checkmate)
     * 2. Assures the king can't escape checkmate
     *
     */
    @Test
    void testWhiteKingCheckmate() throws IOException {

        Game.chessBoard = new Piece[8][8];

        //add black king
        Game.chessBoard[4][0] = new King(PieceColour.BLACK, PieceName.KING, 4, 0);


        //add white king
        Game.chessBoard[5][7] = new King(PieceColour.WHITE, PieceName.KING, 5, 7);

        //add black pawn
        Game.chessBoard[6][5] = new Pawn(PieceColour.BLACK, PieceName.PAWN, 6, 5);

        //add black queen
        Game.chessBoard[5][6] = new Queen(PieceColour.BLACK, PieceName.QUEEN, 5, 6);

        //asserts if isCheckMate() is called
        assertTrue(game.isCheckMate());

    }

    /**
     * Checks to see if movement is correctly restricted when in check.
     *
     * 1. Tries to move a rook onto legal and illegal squares.
     * 2. Tries to move the king onto legal and illegal squares.
     */
    @Test
    void testInCheckMovement() throws IOException {

        //--------------------
        //--SETTING UP BOARD--

        //empty chessboard
        Game.chessBoard = new Piece[9][9];

        //Pieces
        Game.chessBoard[1][6] = new Rook(PieceColour.WHITE, PieceName.ROOK, 1, 6);
        Game.chessBoard[3][3] = new King(PieceColour.WHITE, PieceName.KING, 3, 3);
        Game.chessBoard[1][1] = new Bishop(PieceColour.BLACK, PieceName.BISHOP, 1, 1);
        //White rook at 1,6
        //White king at 3,3
        //Black bishop at 1,1 - putting king in check.

         //--------------
        //--MOVING ROOK--

        //white rook (1,6) tries to take black bishop (1,1) - TRUE (cancels check)
        assertTrue(game.checkMove(1, 1, 6, 1));

        //white rook (1,6) tries to move to 6,6 - False (does not cancel check)
        assertFalse(game.checkMove(1, 6, 6, 6));

         //---------------
        //--MOVING KING--

        //white king (3,3) tries to move to 2,2 - False (does not cancel check)
        assertFalse(game.checkMove(3, 2, 3, 2));

        //white king (3,3) tries to move to 4,4 - False (does not cancel check)
        assertFalse(game.checkMove(3, 2, 4, 4));

        //white king (3,3) tries to move to 3,1 - True (cancels check)
        assertTrue(game.checkMove(3, 3, 3, 1));

        //white king (3,3) tries to move to 3,1 - True (cancels check)
        assertTrue(game.checkMove(3, 2, 3, 2));

        //white king (3,3) tries to move to 4,1 - True (cancels check)
        assertTrue(game.checkMove(3, 4, 3, 1));

        //white king (3,3) tries to move to 2,3 - True (cancels check)
        assertTrue(game.checkMove(3, 2, 3, 3));

        //white king (3,3) tries to move to 4,3 - True (cancels check)
        assertTrue(game.checkMove(3, 4, 3, 3));

        //white king (3,3) tries to move to 2,4 - True (cancels check)
        assertTrue(game.checkMove(3, 2, 3, 4));

        //white king (3,3) tries to move to 3,4 - True (cancels check)
        assertTrue(game.checkMove(3, 3, 3, 4));
    }
}

