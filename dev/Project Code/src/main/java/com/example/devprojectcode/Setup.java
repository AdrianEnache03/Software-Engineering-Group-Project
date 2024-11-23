/**
 * @(#) Setup.java 1.0 2023/02/07
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 *
*/

package com.example.devprojectcode;

import com.example.devprojectcode.Enums.PieceColour;
import com.example.devprojectcode.Enums.PieceName;
import com.example.devprojectcode.Pieces.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * A class that handles Functional Requirement 1
 * (Asking the user to Enter their names and choose white or black)
 * Also handles setting up the board and adding the pieces
 *
 * @author Finley Aubin (fia5)
 * @author George Cooper (gwc1)
 * @version 0.1 Initial development
 * @version 0.2 Minor additions
 * @see Main
 * 
 */
public class Setup {
    
    //---------- INSTANCE VARIABLES ----------//
    private Piece[][] chessBoard;

    //---------- Constructors ----------//
    public Setup() {
        this.chessBoard = new Piece[8][8];
    }

    //---------- READ-ONLY PROPERTIES ----------//
    /**
     * For testing purposes only
     *
     * @return chessBoard
     */
    public Piece[][] getBoard() {
        return chessBoard;
    }

    //---------- METHODS ----------//

    /**
     * Method to generate the start screen that is loaded when the Setup class is called
     * @return Returns the startscreen
     * @throws IOException
     */
    public Scene startScreen() throws IOException{
        Parent base;
        Scene startScreen;
        base = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("startupScene.fxml")));
        startScreen = new Scene(base);
        return startScreen;
    }

    /**
     * Method to generate the game over screen that is loaded when gameOver() is called
     * @return Returns the game over screen
     * @throws IOException
     */
    public Scene gameOverScreen() throws IOException{
        Parent base;
        Scene gameOverScreen;
        base = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gameOver.fxml")));
        gameOverScreen = new Scene(base);
        return gameOverScreen;
    }

    /**
     * Method to generate the main menu
     *
     * @param startScreen Stage
     * @return the main menu scene
     * @throws IOException
     */
    public Scene gameBoard (Stage startScreen) throws IOException{
        if(startScreen==null){
            startScreen=new Stage();
        }
        FXMLLoader startScreenLoader;
        Parent base;
        startScreenLoader = new FXMLLoader(getClass().getResource("mainBoard.fxml"));
        base = startScreenLoader.load();
        startScreen.setScene(new Scene(base));
        startScreen.sizeToScene();
        startScreen.setTitle("Chess Tutor");
        startScreen.getIcons().add(new Image(new FileInputStream("src/main/resources/com/example/devprojectcode/Pieces/White_Queen.png")));

        return startScreen.getScene();
    }

    /**
     * Method to generate the signin page
     *
     * @return the signin scene
     * @throws IOException
     */
    public Scene signIn () throws IOException{
        Parent base;
        Scene login;
        base = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginScreen.fxml")));
        login = new Scene(base);
        return login;
    }

    /**
     * Method to generate the pause menu scene
     *
     * @param startScreen the stage
     * @return the pause menu scene
     * @throws IOException
     */
    public Scene pauseMenu (Stage startScreen) throws IOException{
        if(startScreen==null){
            startScreen=new Stage();
        }
        FXMLLoader pauseMenuLoader;
        Parent start;
        pauseMenuLoader = new FXMLLoader(getClass().getResource("pause.fxml"));
        start = pauseMenuLoader.load();
        startScreen.setScene(new Scene(start));
        startScreen.sizeToScene();
        //startScreen.setTitle("Pause");
        return startScreen.getScene();
    }

    /**
     * Method to generate the pause menu scene
     *
     * @param startScreen the stage
     * @return the Pawn Promotion menu scene
     * @throws IOException
     */
    public Scene PawnPromotionMenu (Stage startScreen) throws IOException{
        if(startScreen==null){
            startScreen=new Stage();
        }
        FXMLLoader PawnPromotionMenuLoader;
        Parent start;
        PawnPromotionMenuLoader = new FXMLLoader(getClass().getResource("pawnPromotion.fxml"));
        start = PawnPromotionMenuLoader.load();
        startScreen.setScene(new Scene(start));
        startScreen.sizeToScene();
        //startScreen.setTitle("Chess Project");
        return startScreen.getScene();
    }

    /**
     * Method to generate the resign menu scene
     * @param startScreen The starting screen that is set when the Setup class is called
     * @return the Resign menu screen
     * @throws IOException
     */
    public Scene ResignMenu (Stage startScreen) throws IOException{
        if(startScreen==null){
            startScreen=new Stage();
        }
        FXMLLoader ResignMenuLoader;
        Parent start;

        ResignMenuLoader = new FXMLLoader(getClass().getResource("resignation.fxml"));
        start = ResignMenuLoader.load();
        startScreen.setScene(new Scene(start));
        startScreen.sizeToScene();
        //startScreen.setTitle("Resign");
        return startScreen.getScene();
    }

    /**
     * Method to generate the Offer Draw screen
     * @param startScreen The starting screen that is set when the Setup class is called
     * @return the Draw menu screen
     * @throws IOException
     */
    public Scene OfferDrawMenu (Stage startScreen) throws IOException{
        if(startScreen==null){
            startScreen=new Stage();
        }
        FXMLLoader ResignMenuLoader;
        Parent start;

        ResignMenuLoader = new FXMLLoader(getClass().getResource("offerDraw.fxml"));
        start = ResignMenuLoader.load();
        startScreen.setScene(new Scene(start));
        startScreen.sizeToScene();
        //startScreen.setTitle("Offer Draw");
        return startScreen.getScene();
    }

    /**
     * Method to create the chess board the game will be played on and fill it with all pieces in their starting location
     * Called every time a new game is started
     * @return the chessboard 2D array of Pieces
     */
    public Piece[][] boardSetup() {
        int y1;
        int y2;
        y1 = 1;
        y2 = 0;
        

        //TODO add comment to exlain loop
        for (int i = 0; i< 2; i++) {
            PieceColour colour = PieceColour.values()[i];

            //TODO add comment to explain loop
            for (int x = 0; x < 8; x++) {
                chessBoard[x][y1] = new Pawn(colour, PieceName.PAWN, x, y1);
            }
            chessBoard[0][y2] = new Rook(colour, PieceName.ROOK, 0, y2);
            chessBoard[1][y2] = new Knight(colour, PieceName.KNIGHT, 1, y2);
            chessBoard[2][y2] = new Bishop(colour, PieceName.BISHOP, 2, y2);
            chessBoard[4][y2] = new King(colour, PieceName.KING, 4, y2);
            chessBoard[3][y2] = new Queen(colour, PieceName.QUEEN, 3, y2);
            chessBoard[5][y2] = new Bishop(colour, PieceName.BISHOP, 5, y2);
            chessBoard[6][y2] = new Knight(colour, PieceName.KNIGHT, 6, y2);
            chessBoard[7][y2] = new Rook(colour, PieceName.ROOK, 7, y2);
            y1 = 6;
            y2 = 7;
        }
        return chessBoard;
    }

    /**
     * For testing purposes only
     *
     * @param chessBoard
     */
    public void printBoard(Piece[][] chessBoard){
        System.out.println(Arrays.deepToString(chessBoard).replace("], ", "]\n"));
        System.out.println(chessBoard[0][7].getColour());
        System.out.print(chessBoard[0][7].getName());
    }

}
