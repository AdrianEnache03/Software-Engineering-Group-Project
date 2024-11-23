package com.example.devprojectcode;

import com.example.devprojectcode.Enums.PieceColour;
import com.example.devprojectcode.Enums.PieceName;
import com.example.devprojectcode.Pieces.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @(#) SetupTest.java 0.2 2023/05/01
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 *
 */

/**
 * A class that tests the methods defined in Setup.java, uses JUnit5 to do so.
 *
 * @author Tate J. A. Moore (tam41)
 *
 * @version 0.1 - Added boardSetup() which tests if setup.boardSetup() can correctly set up all the pieces in their starting positions.
 * @version 0.2 - Adjusted boardSetup() as the king pieces were constructed as having knight piece types.
 *
 * @see Setup
 */
class SetupTest {

    @Test
    void startScreen() {
    }

    @Test
    void gameBoard() {
    }

    @Test
    void signIn() {
    }

    @Test
    void pauseMenu() {
    }

    @Test
    void whiteName() {
    }

    @Test
    void blackName() {
    }

//    @Test
//    void boardSetup() {
//        Setup setup = new Setup();
//        Piece[][] board = setup.boardSetup();
//        int yPawnRow = 1;
//        int yBackRow = 0;
//        for (int colour = 0; colour < 2; colour++) {
//            for (int x = 0; x < 8; x++) {
//                assertEquals(PieceName.PAWN, board[x][yPawnRow].getName());
//            }
//
//            assertEquals(PieceName.ROOK, board[0][yBackRow].getName());
//            assertEquals(PieceName.KNIGHT, board[1][yBackRow].getName());
//            assertEquals(PieceName.BISHOP, board[2][yBackRow].getName());
//            assertEquals(PieceName.QUEEN, board[3][yBackRow].getName());
//            assertEquals(PieceName.KING, board[4][yBackRow].getName());
//            assertEquals(PieceName.BISHOP, board[5][yBackRow].getName());
//            assertEquals(PieceName.KNIGHT, board[6][yBackRow].getName());
//            assertEquals(PieceName.ROOK, board[7][yBackRow].getName());
//
//            yPawnRow = 6;
//            yBackRow = 7;
//        }
//
//
//    }

    //yes it's hardcoded, no loops.
    //I would loop it but then it's pretty much the same as how it's setup in Setup.
    //This way we can be 100% where everything is.
    /**
     * tests if setup.boardSetup() can correctly set up all the pieces in their starting positions.
     * 1. Hardcodes all pieces into their starting positions onto Piece[][] expectedBoard - although slow process and poor readability, this is 100% how the board is.
     * 2. Calls boardSetup() onto Piece[][] resultBoard - this will be tested against expectedBoard.
     * 3. Checks (resultBoard against expectedBoard) to see if the corresponding black pieces are all the same piece type and colour.
     * 4. Checks (resultBoard against expectedBoard) to see if the corresponding empty pieces are indeed null and have no piece there.
     * 5. Checks (resultBoard against expectedBoard) to see if the corresponding white pieces are all the same piece type and colour.
     */
    @Test
    void boardSetup() {
        Setup setup = new Setup();
        Piece[][] resultBoard = setup.boardSetup();
        Piece[][] expectedBoard = new Piece[8][8];

         //------------------
        //--BACK ROW PIECES--

        //BLACK
        expectedBoard[0][0] = new Rook(PieceColour.BLACK, PieceName.ROOK, 0, 0);
        expectedBoard[1][0] = new Knight(PieceColour.BLACK, PieceName.KNIGHT, 1, 0);
        expectedBoard[2][0] = new Bishop(PieceColour.BLACK, PieceName.BISHOP, 2, 0);
        expectedBoard[3][0] = new Queen(PieceColour.BLACK, PieceName.QUEEN, 3, 0);
        expectedBoard[4][0] = new King(PieceColour.BLACK, PieceName.KING, 4, 0);
        expectedBoard[5][0] = new Bishop(PieceColour.BLACK, PieceName.BISHOP, 5, 0);
        expectedBoard[6][0] = new Knight(PieceColour.BLACK, PieceName.KNIGHT, 6, 0);
        expectedBoard[7][0] = new Rook(PieceColour.BLACK, PieceName.ROOK, 7, 0);

        //WHITE
        expectedBoard[0][7] = new Rook(PieceColour.WHITE, PieceName.ROOK, 0, 7);
        expectedBoard[1][7] = new Knight(PieceColour.WHITE, PieceName.KNIGHT, 1, 7);
        expectedBoard[2][7] = new Bishop(PieceColour.WHITE, PieceName.BISHOP, 2, 7);
        expectedBoard[3][7] = new Queen(PieceColour.WHITE, PieceName.QUEEN, 3, 7);
        expectedBoard[4][7] = new King(PieceColour.WHITE, PieceName.KING, 4, 7);
        expectedBoard[5][7] = new Bishop(PieceColour.WHITE, PieceName.BISHOP, 5, 7);
        expectedBoard[6][7] = new Knight(PieceColour.WHITE, PieceName.KNIGHT, 6, 7);
        expectedBoard[7][7] = new Rook(PieceColour.WHITE, PieceName.ROOK, 7, 7);


         //--------
        //--PAWNS--

        //BLACK PAWNS
        expectedBoard[0][1] = new Pawn(PieceColour.BLACK, PieceName.PAWN, 0, 1);
        expectedBoard[1][1] = new Pawn(PieceColour.BLACK, PieceName.PAWN, 1, 1);
        expectedBoard[2][1] = new Pawn(PieceColour.BLACK, PieceName.PAWN, 2, 1);
        expectedBoard[3][1] = new Pawn(PieceColour.BLACK, PieceName.PAWN, 3, 1);
        expectedBoard[4][1] = new Pawn(PieceColour.BLACK, PieceName.PAWN, 4, 1);
        expectedBoard[5][1] = new Pawn(PieceColour.BLACK, PieceName.PAWN, 5, 1);
        expectedBoard[6][1] = new Pawn(PieceColour.BLACK, PieceName.PAWN, 6, 1);
        expectedBoard[7][1] = new Pawn(PieceColour.BLACK, PieceName.PAWN, 7, 1);

        //WHITE PAWNS
        expectedBoard[0][6] = new Pawn(PieceColour.WHITE, PieceName.PAWN, 0, 6);
        expectedBoard[1][6] = new Pawn(PieceColour.WHITE, PieceName.PAWN, 1, 6);
        expectedBoard[2][6] = new Pawn(PieceColour.WHITE, PieceName.PAWN, 2, 6);
        expectedBoard[3][6] = new Pawn(PieceColour.WHITE, PieceName.PAWN, 3, 6);
        expectedBoard[4][6] = new Pawn(PieceColour.WHITE, PieceName.PAWN, 4, 6);
        expectedBoard[5][6] = new Pawn(PieceColour.WHITE, PieceName.PAWN, 5, 6);
        expectedBoard[6][6] = new Pawn(PieceColour.WHITE, PieceName.PAWN, 6, 6);
        expectedBoard[7][6] = new Pawn(PieceColour.WHITE, PieceName.PAWN, 7, 6);

        //Testing
        //assertArrayEquals(expectedBoard,resultBoard); -- no worth

        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                assertEquals(expectedBoard[x][y].getName(), resultBoard[x][y].getName());
                assertEquals(expectedBoard[x][y].getColour(), resultBoard[x][y].getColour());
            }
        }

        for (int y = 2; y < 6; y++) {
            for (int x = 0; x < 8; x++) {
                assertNull(resultBoard[x][y]);
            }
        }

        for (int y = 6; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                assertEquals(expectedBoard[x][y].getName(), resultBoard[x][y].getName());
                assertEquals(expectedBoard[x][y].getColour(), resultBoard[x][y].getColour());
            }
        }
    }

    @Test
    void addPiecesToBoard() {
    }
}
