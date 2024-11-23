/**
 * Game.java 1.0 2023/02/07
 * 
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 * 
 */

package com.example.devprojectcode;

import com.example.devprojectcode.Enums.PieceColour;
import com.example.devprojectcode.Enums.PieceName;
import com.example.devprojectcode.Pieces.King;
import com.example.devprojectcode.Pieces.Pawn;
import com.example.devprojectcode.Pieces.Piece;
import com.example.devprojectcode.Pieces.Rook;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A class that handles Functional Requirements 4, 5, 6 and 7
 * (Piece selection, movement, detecting check and checkmate)
 *
 * @author Kieran Foy (kif11)
 * @author Fin Aubin (fia5)
 * @author George Cooper (gwc1)
 * @version 0.1 Initial development
 * @version 0.2 Added methods to handle en passant
 * @version 0.3 Added methods to handle castling
 * @see Main
 */
public class Game {

    //---------- Class Variables ----------//
    public static String blackUserName;
    public static String whiteUserName;
    private static Scene PawnPromotion;
    public static Piece[][] chessBoard;
    public static int turnTracker; //odd number white turn, even number blacks turn
    public static Replay replay;
    private static Scene board;
    private static Scene mainMenu;
    private static Scene logIn;
    private static Scene pause;
    private static Scene gameOver;
    private static Scene resign;
    private static Scene offerDraw;

    //---------- Instance Variables ----------//
    public boolean isPlayerInCheck = false;
    public boolean hasCastled = false;
    public boolean leftSide = false;
    private boolean canDisplayPawnPromotion = true;
    private boolean checkCheckFlag = false;

    /**
     * Method to detect if the King of the opposite color of the player who just had their turn is in check or checkmate
     * (FR6: Detecting Check, FR7: Detecting Checkmate)
     * Called after every turn
     * Method to detect when check has occurred. for all squares on board, if piece in coordinates x1, y2 can take the king
     * return true.
     */
    public Boolean isCheck(int x1, int y1) throws IOException {

        // for all spaces in the board, if the space is not null and is a king, check if the currently selected piece can move onto the king. If yes, the king is in check
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard.length; j++) {
                if (chessBoard[i][j] != null) {
                    if (chessBoard[i][j].getName().equals(PieceName.KING)) {
                        if (checkMove(x1, i, y1, j)) {
                            //GameScreenController.showCheck(i,j);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static Piece[][] copyBoard(Piece[][] board) {
        Piece[][] oldBoard = new Piece[board.length][];

        // for all spaces in the board, clone into a new board
        for (int b = 0; b < board.length; b++) {
            oldBoard[b] = board[b].clone();
        }
        return oldBoard;
    }

    /**
     * Method to detect if the opponent's king is in check
     * Called after every turn
     *
     * @param x1 The x co-ordinate of the currently selected piece
     * @param x2 The x co-ordinate of the opponent's king
     * @param y1 The y co-ordinate of the currently selected piece
     * @param y2 The y co-ordinate of the opponent's king
     * @return Returns true if the opponent's king is in check, false if not
     * @throws IOException
     */
    public Boolean isOpponentCheck(int x1, int x2, int y1, int y2) throws IOException {
        Piece[][] old = copyBoard(chessBoard);
        chessBoard[x1][y1].setyPos(y2);
        chessBoard[x1][y1].setxPos(x2);
        chessBoard[x2][y2] = chessBoard[x1][y1];
        chessBoard[x1][y1] = null;
        ArrayList<Piece> pieces = getOtherPlayerPieces();

        // for all pieces, if the currently selected piece can take the kind but the king can move safely to a new position, opponent is in check
        for (Piece p : pieces) {
            checkCheckFlag = true;
            canDisplayPawnPromotion = false;
            if (isCheck(p.getXPos(), p.getYPos())) {
                chessBoard = old;
                chessBoard[x1][y1].setyPos(y1);
                chessBoard[x1][y1].setxPos(x1);
                checkCheckFlag = false;
                canDisplayPawnPromotion = true;
                return true;
            }
            checkCheckFlag = false;
            canDisplayPawnPromotion = true;
        }
        chessBoard = old;
        chessBoard[x1][y1].setyPos(y1);
        chessBoard[x1][y1].setxPos(x1);
        return false;
    }

    /**
     * Method to return an arrayList of other players pieces
     *
     * @return
     */
    private ArrayList<Piece> getOtherPlayerPieces(){
        ArrayList<Piece> pieces = new ArrayList<>();

        // for all pieces on the board, check the color of the pieces. If the color isn't the color of the current player, add to the arraylist of pieces
        for (Piece[] value : chessBoard) {
            for (int j = 0; j < chessBoard.length; j++) {
                if (value[j] != null) {
                    if (turnChecker("b")) {
                        if (value[j].getColour().equals(PieceColour.WHITE)) {
                            pieces.add(value[j]);
                        }
                    } else {
                        if (value[j].getColour().equals(PieceColour.BLACK)) {
                            pieces.add(value[j]);
                        }
                    }
                }
            }
        }
        return pieces;
    }

    /**
     * Detects if the current players king is in checkmate
     * If the other players king is in checkmate, it returns false
     *
     * @return Returns true if the king is in checkmate, false if not
     * @throws IOException
     */
    public Boolean isCheckMate() throws IOException {
        ArrayList<Piece> pieces = new ArrayList<>();

        // for all pieces on the board, if the square isn't empty, add the piece to the arraylist of pieces depending on color
        for (Piece[] value : chessBoard) {
            for (int j = 0; j < chessBoard.length; j++) {
                if (value[j] != null) {
                    if (turnChecker("b")) {
                        if (value[j].getColour().equals(PieceColour.BLACK)) {
                            pieces.add(value[j]);
                        }
                    } else {
                        if (value[j].getColour().equals(PieceColour.WHITE)) {
                            pieces.add(value[j]);
                        }
                    }
                }
            }
        }

        // for all pieces on the board, if the opponent's king is in check and the king can move out of it, return false. if the opponent's king can't safely move, they have been checkmated
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard.length; j++) {
                for (Piece p : pieces) {
                    if (isOpponentCheck(p.getXPos(), i, p.getYPos(), j).equals(false)) {
                        if (checkMove(p.getXPos(), i, p.getYPos(), j)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Method to check if en passant move is legal
     * If move is legal returns true
     *
     * @param p pawn to move
     * @param x1 x coordinate of pawn to move
     * @param x2 x coordinate of square to move pawn to
     * @param y1 y coordinate of pawn to move
     * @param y2 y coordinate of square to move to
     * @param diff 1 if pawn to take is to right, -1 if pawn to take is to left.
     * @param col +1 if pawn is black and needs to move forward, -1 if pawn is white and needs to move back.
     * @return true if move is legal, false if not
     */
    private boolean enPassantCheck(Pawn p, int x1, int x2, int y1, int y2, int diff, int col) {
        Pawn p2 = (Pawn) chessBoard[x1 + diff][y1]; //the pawn to take

        // if the pawn can be en passant-ed, and if the current pawn can move to en passant the opposition's pawn, set enPassanted to true
        if (p2.isEnPassantable()) {
            if (chessBoard[x1 + diff][y1].getColour() != p.getColour()) {
                if (x2 == x1 + diff && y2 == y1 + col) {
                    p.setEnPassanted(true);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method for castling
     * checks if the move is legal or not
     * checks to see if both players have moved
     * checks to see if space to move to is legal
     * checks to see if spaces between king and rook are free
     *
     * @param x1 x position of king
     * @param x2 x position of square to move to
     * @param y1 y position of king
     * @param y2 y position of square to move to
     * @return true if castling move legal, false if not legal
     */
    private boolean castling(int x1, int x2, int y1, int y2) {

        // if the piece is not a king, return false
        if (chessBoard[x1][y1].getName() != PieceName.KING) {
            return false;
        }

        // if the king hasn't moved and the queen space is empty, king can perform a left-side castle
        King k = (King) chessBoard[x1][y1];
        if(x2 == x1-2  && y2 == y1) {
            leftSide = true;
        }

        // if the king and the rooks haven't moved, and the queen space is empty, perform a left-side castle. if not, perform a right-side castle
        if((x2 == x1-2 || x2 == x1+2) && (y2 == y1) ) {

            //if the king hasn't moved
            if(!k.isHasMoved()) {
                for (int i = -4; i < 4; i = i + 7) {

                    //checks for both left (-4) and right (+3) castling
                    if (x1 + i <= 7 && x1 + i >= 0) {
                        if (chessBoard[x1 + i][y1] != null) {
                            if (chessBoard[x1 + i][y1].getName().equals(PieceName.ROOK)) { //if rook is in correct space
                                Rook r = (Rook) chessBoard[x1 + i][y1];
                                if (!r.isHasMoved()) {

                                    //if the rook hasn't moved
                                    if (chessBoard[x2][y2] == null && chessBoard[x2 - 1][y2] == null) {

                                        //if knight and rook space are empty
                                        if (leftSide) {
                                            return chessBoard[x2 + 1][y2] == null; //if queen space is empty
                                        }
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Method to detect if enPassantMove is legal or not
     * Adjusts col depending on colour so piece moves in correct direction
     * Makes checks to determine if square is out of bounds or not
     *
     * @param x1 x position of pawn
     * @param x2 x position of square to move to
     * @param y1 y position of pawn
     * @param y2 y position of square to move to
     * @return true if legal, false if not legal
     */
    private boolean enPassant(int x1, int x2, int y1, int y2) {

        // return false if the current piece isn't a pawn
        int col;
        if (chessBoard[x1][y1].getName() != PieceName.PAWN) {
            return false;
        }
        Pawn p = (Pawn) chessBoard[x1][y1];

        // increment/decrement the column based on the color
        if (p.getColour() == PieceColour.BLACK) {
            col = 1;
        } else {
            col = -1;
        }

        // for the space around the pawn, if the space has a pawn, check if it can be en passant-ed
        for (int i = -1; i < 2; i = i + 2) {
            if (x1 + i > -1 && x1 + i <= 7) {
                if (chessBoard[x1 + i][y1] != null) {
                    if (chessBoard[x1 + i][y1].getName() == PieceName.PAWN) {
                        return (enPassantCheck(p, x1, x2, y1, y2, i, col));
                    }
                }
            }
        }
        return false;
    }

    /**
     * startUp method, sets the screens of the game
     *
     * @param startScreen the screen displayed when startScreen() is called
     * @throws Exception
     */
    public void startUp(Stage startScreen, Boolean isLogin) throws Exception {
        turnTracker = 1;
        Setup setup = new Setup();
        mainMenu = setup.startScreen();
        logIn = setup.signIn();
        board = setup.gameBoard(startScreen);
        pause = setup.pauseMenu(startScreen);
        PawnPromotion = setup.PawnPromotionMenu(startScreen);
        chessBoard = setup.boardSetup();
        gameOver = setup.gameOverScreen();
        resign = setup.ResignMenu(startScreen);
        offerDraw = setup.OfferDrawMenu(startScreen);
        //replay = new Replay(); - moved to playerNames(..) in NewGameScreenController, not sure if it will work so why I kept this here - Tate

        // if isLogin is true, set the start screen to the login screen. else, set to main menu screen
        if(startScreen==null){
            startScreen=new Stage();
        }
        if (isLogin) {
            startScreen.setScene(logIn);
        }
        else {
            startScreen.setScene(mainMenu);
        }
        startScreen.show();
    }

    /**
     *
     * Method to check if move is legal
     * Checks if the colour of the piece to move matches the colour of the player whose turn it is
     * if move is legal the board array is updated to reflect new board state
     * saves the new board state
     *
     * @param x1 initial x position of square
     * @param x2 initial x position of square to move to
     * @param y1 initial y position of square
     * @param y2 initial y position of square to move to
     * @return true if move is legal else false
     *
     */
    public boolean checkMove(int x1, int x2, int y1, int y2) throws IOException {

        // if the space isn't empty and it's that color's turn, check what moves are currently possible
        if (chessBoard[x1][y1] != null) {
            if (turnChecker(chessBoard[x1][y1].getColour().getColourLabel()) || isPlayerInCheck || checkCheckFlag) { //if it is this players move
                Piece p1 = chessBoard[x1][y1];

                if (p1.getName().equals(PieceName.PAWN)) {
                    pawnPromotion((Pawn) p1, y1);
                }

                if (chessBoard[x2][y2] != null) {
                    Piece p2 = chessBoard[x2][y2];
                    if (p1.getColour() == p2.getColour()) {
                        return false;
                    }
                }

                if (castling(x1, x2, y1, y2)) {
                    hasCastled = true;
                    return true;
                }

                if (enPassant(x1, x2, y1, y2)) {
                    return true;
                }
                    if(!isOpponentCheck(x1, x2, y1, y2)){
                        if (p1.isMoveLegal(x1, x2, y1, y2)) {
                            return p1.isPieceUnblocked(chessBoard, x1, x2, y1, y2);
                    }
                }
            }
        }
        return false;
    }

    /**
     * Method to print the board on the command line
     * Loops through the x and y co-ordinates and prints out the piece information depending on the contents of the current cell
     * @param chessBoard
     */
    public static void displayBoardCmd(Piece[][] chessBoard) {
        System.out.println();

        // for all spaces in the chessboard, print to command line
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (chessBoard[x][y]!=null) {
                    System.out.println("x="+x + " y=" + y + " " + chessBoard[x][y].getName() +" "+ chessBoard[x][y].getColour());
                }
            }
        }
        System.out.println();
    }


    /**
     * Method to update the chessBoard array if the move is deemed legal
     * If the piece is a pawn it's first turn indicator is set to false
     * Updates the array to reflect new board after moving pieces
     * The turn counter is increased by one
     * And the game saves
     *
     * @param x1 Initial x position of piece
     * @param x2 x position to move the piece to
     * @param y1 Initial y position of piece
     * @param y2 y position to move the piece to
     * @throws IOException if file to save to cannot be found
     */
    public void updateBoard(int x1, int x2, int y1, int y2) throws IOException {

        // if the space contains a pawn and is en passant-able, set enPassantable() to true
        if (chessBoard[x1][y1].getName() == PieceName.PAWN) {
            Pawn p = (Pawn) chessBoard[x1][y1];
            if(p.isFirstTurn()) {
                if (p.getColour().equals(PieceColour.WHITE)) {
                    if (x2 == x1 & y2 == y1 - 2) {
                        p.setEnPassantable(true);
                    }
                } else {
                    if (x2 == x1 & y2 == y1 + 2) {
                        p.setEnPassantable(true);
                    }
                }
            } else {
                p.setEnPassantable(false);
            }
            p.setFirstTurn(false);
        }
        if (chessBoard[x1][y1].getName() == PieceName.KING) {
            King k = (King) chessBoard[x1][y1];
            k.setHasMoved(true);
        }
        if (chessBoard[x1][y1].getName() == PieceName.ROOK) {
            Rook r = (Rook) chessBoard[x1][y1];
            r.setHasMoved(true);
        }
        if(hasCastled){
            updateCastling(x1, x2, y1);
        }
        chessBoard[x2][y2] = chessBoard[x1][y1];
        chessBoard[x1][y1] = null;
        chessBoard[x2][y2].setxPos(x2);
        chessBoard[x2][y2].setyPos(y2);
        canDisplayPawnPromotion = true;
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard.length; j++) {
                if (isCheck(i, j)) {
                    isPlayerInCheck = true;
                    System.out.println("Check");
                }
            }
        }
    }

    /**
     * Method to update the rook to correct space in the array after castling
     *
     * @param x1 initial x coordinate of king
     * @param x2 x coordinate of space king is moving to
     * @param y1 y coordinate of rook
     */
    private void updateCastling(int x1, int x2, int y1){

    //
        if(leftSide){
            chessBoard[x2+1][y1] = chessBoard[x1-4][y1];
            chessBoard[x1-4][y1] = null;
            chessBoard[x2+1][y1].setxPos(x2+1);
        }
        chessBoard[x2-1][y1] = chessBoard[x1+3][y1];
        chessBoard[x1+3][y1] = null;
        chessBoard[x2-1][y1].setxPos(x2-2);
    }

    /**
     * Method to handle when a game over is detected.
     * Creates a new scene.
     * Populates with the correct player names.
     * switches to the gameOver scene.
     *
     */
    public void gameOver() {
        Stage stage = (Stage) getBoard().getWindow();
        stage.setScene(gameOver);
        Text winner = (Text) gameOver.lookup("#Winner");
        Text via = (Text) gameOver.lookup("#Via");
        winner.setText(getOtherPlayerName() + " wins");
        via.setText("via checkmate");
        stage.show();
        replay.setHasFinished(true);
    }

    /**
     * checks if a pawns that has been moved is eligible to be promoted
     * @param p1 the pawn
     * @param y its y position
     * @return if it can be promoted
     */
    public boolean pawnPromotion(Pawn p1, int y) {
        //displayPawnPromotionScreen();
        if (p1.getColour().equals(PieceColour.BLACK)) {
            if (y == 7) {
                displayPawnPromotionScreen();
            }
        }
        if (p1.getColour().equals(PieceColour.WHITE)) {
            if (y == 0) {
                displayPawnPromotionScreen();
            }
        }
        /*
        if (isOpponentCheck(x,y,p1.getXPos(),p1.getYPos())) {
            isPlayerInCheck = true;
            System.out.println("Check");
        }
        */
        return true;
    }

    /**
     * Displays the screen for the pawn promotion
     * User can select between promoting a pawn to a queen, rook, knight or bishop
     */
    private void displayPawnPromotionScreen() {
        if (canDisplayPawnPromotion) {
            System.out.println("board");
            System.out.println(Game.getBoard());
            System.out.println("Window");
            System.out.println(getBoard().getWindow());
            Stage mainBoard = (Stage) getBoard().getWindow();
            Scene pawnPromotion = Game.GetPawnPromotion();
            if(mainBoard!=null){
                mainBoard.setScene(pawnPromotion);
            }
            canDisplayPawnPromotion = false;
        }
    }

    public void load() throws IOException {
        Replay replay = new Replay();
        replay.loadGame();
    }

    /**
     * Checks if it is the right players turn
     *
     * @param colour The colour of the piece the user is trying to move
     * @return true if it's the correct players colour, else false
     */
    protected boolean turnChecker(String colour) {
        if (Objects.equals(colour, "b") && turnTracker % 2 == 0) {
            return true;
        }
        return Objects.equals(colour, "w") && turnTracker % 2 == 1;
        //System.out.println("Not this colours turn");
    }

    public static Scene getBoard() {
        return board;
    }

    public static Scene getPawnPromotion() {
        return PawnPromotion;
    }

    public Scene getMainMenu() {
        return mainMenu;
    }

    public Scene getLogIn() {
        return logIn;
    }

    public Scene getPause() {
        return pause;
    }

    public Scene getGameOver() {
        return gameOver;
    }

    public Scene getResign(){
        return resign;
    }
    public Scene getOfferDraw(){
        return offerDraw;
    }

    public static Scene GetPawnPromotion() {
        return PawnPromotion;
    }

    public static String getBlackUserName() {
        return blackUserName;
    }
    public static String getWhiteUserName() {
        return whiteUserName;
    }

    public static String getCurrentPlayerName(){
        if(turnTracker%2==0){
            return blackUserName;
        }else {
            return whiteUserName;
        }
    }

    public Replay getReplay() {
        return replay;
    }

    public static String getOtherPlayerName(){
        if(turnTracker%2==0){
            return whiteUserName;
        }else {
            return blackUserName;
        }
    }
}