/**
 * @(#) Replay.java 0.4 2023/05/04
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 *
 */

package com.example.devprojectcode; 

import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;

import com.example.devprojectcode.Enums.PieceColour;
import com.example.devprojectcode.Enums.PieceName;
import com.example.devprojectcode.Pieces.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javax.swing.*;


/**
 * A class that handles Functional Requirements 10 and 11
 * (Replaying the game and storing the game data)
 *
 * @author Kieran Foy (kif11)
 * @author Adrian Enache (ade12)
 * @author Tate J. A. Moore (tam41)
 *
 * @version 0.1 Initial development
 * @version 0.2 Added numerous functions
 * @version 0.3 Added load and save functions and made amendments to class variables and constructors.
 * @version 0.4 Added javadoc to new functions.
 * @version 0.5 Fixed loading bug.
 */

//=====================
//== CLASS VARIABLES ==
//=====================

public class Replay extends Game {
    private String playerOneName;
    private String playerTwoName;
    private ArrayList<Piece[][]> boardHistory ;
    private String filePath;
    private boolean hasFinished;
    int count = 0;

    /**
    * Constructor called when a new game is created (and names have been assigned).
    *
    * @param playerOneName the name of player 1.
    * @param playerTwoName the name of player 2.
    *
    * Initialises playerOneName, playerTwoName, boardHistory, filePath, hasFinished
    */
    public Replay(String playerOneName, String playerTwoName) {
        this.playerOneName = playerOneName;
        this.playerTwoName = playerTwoName;
        boardHistory = new ArrayList<>();
        filePath = "src/main/resources/com/example/Saved games/"+gameName();
        hasFinished = false;
        addToBoardHistory(copyBoard(chessBoard));
    }

    /**
    * Constructor to be called when loading a game.
    * Initialises the boardHistory.
    */
    public Replay() {
        boardHistory = new ArrayList<>();
    }

    //===================
    //== CLASS METHODS ==
    //===================

    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    /**
     * Function to generate the file name for the file in which a new game will be saved.
     * @return file name
     */
    public String gameName()
    {
        String dateTime = new java.util.Date().toString();
        System.out.println(dateTime);
        String date = dateTime.substring(4,10);
        String year = dateTime.substring(24,28);
        return blackUserName + " vs " + whiteUserName + " " + date + year + ".json";
    }

    public boolean readLine() {

        JFileChooser jd = "src/main/resources/com/example/Saved games//" == null ? new JFileChooser() : new JFileChooser("src/main/resources/com/example/Saved games//");

        jd.setDialogTitle("Choose input file");

        int returnVal= jd.showOpenDialog(null);

        /* If user didn't select a file and click ok, return null Path object*/
        if (returnVal != JFileChooser.APPROVE_OPTION) { return false; }

        String path = jd.getSelectedFile().toPath().toString();
        //Check if file ends with json, if so, return true, otherwise, return false.
        if (path.endsWith(".json"))
        {
            filePath = path;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Adds a new board / turn to the board history.
     * To be used where at the end of each turn, the board will be added to board history.
     *
     * @param boardToAdd the new board to be added.
     */
    public void addToBoardHistory(Piece[][] boardToAdd){
        boardHistory.add(boardToAdd);
        System.out.println(boardHistory.get(boardHistory.size()-1)[0][0].getName());
    }

    public Piece[][] getBoard(int boardIndex) {

        //if the board which is being retrieved is still inside the array, then copy the board to the index specified when loaded.
        if (boardIndex >= 0 && boardIndex < boardHistory.size())
        {
            return copyBoard(boardHistory.get(boardIndex));
        }
        else {
            System.out.println("boardIndex out of bounds.");
            return null;
        }
    }

    public int getBoardHistoryLength() {
        return boardHistory.size();
    }

    //--------------
    //--Saving Games

    /**
     * Saves the game.
     * 'game' includes info such as: player names, whether the game has finished and the history of all past boards / turns
     */
    public void saveGame() throws IOException
    {
        OutputStream outputStream = new FileOutputStream(filePath);
        Writer outputStreamWriter = new OutputStreamWriter(outputStream);

        JsonWriter writer = new JsonWriter(outputStreamWriter);
        writer.setIndent("    ");
        writer.beginObject();

        writeGameInfo(writer);
        writePastHistory(writer);

        writer.endObject();
        writer.close();
    }

    /**
     * Writes the player names and whether the game has finished to the json file.
     */
    void writeGameInfo(JsonWriter writer) throws IOException
    {
        writer.name("plr1").value(playerOneName);
        writer.name("plr2").value(playerTwoName);
        writer.name("hasFinished").value(hasFinished);
    }

    /**
     * Writes the board history of all past boards / turns to the json file.
     */
    void writePastHistory(JsonWriter writer) throws IOException
    {
        writer.name("boardHistory");
        writer.beginArray(); //go into board history

        // For all of the boards stored in boardHistory, run through them in write mode.
        for (Piece[][] board : boardHistory)
        {
            writer.beginArray(); //go into board.

            //Add all of the x and y values into a 2d array
            for (int x = 0; x < 8; x++)
            {
                writer.beginArray(); //go into each 1D array of 2D board array.

                for (int y = 0; y < 8; y++)
                {

                    //Add additional data to each piece if there should be a piece in this location.
                    if (board[x][y] != null)
                    {

                        writer.beginObject();

                        writer.name("type").value(board[x][y].getName().getLabel());
                        writer.name("colour").value(board[x][y].getColour().getColourLabel());
                        writer.name("x").value(x);
                        writer.name("y").value(y);

                        // Check special circumstances for different pieces.
                        switch (board[x][y].getName()) {
                            case PAWN -> {
                                writer.name("isFirstTurn").value(((Pawn) board[x][y]).isFirstTurn());
                                writer.name("isEnPassantable").value(((Pawn) board[x][y]).isEnPassantable());
                                writer.name("isHasEnPassanted").value(((Pawn) board[x][y]).hasEnPassanted());
                            }
                            case KING -> writer.name("hasMoved").value(((King) board[x][y]).isHasMoved());
                            case ROOK -> writer.name("hasMoved").value(((Rook) board[x][y]).isHasMoved());
                        }

                        writer.endObject();

                    }
                }
                writer.endArray();
            }
            writer.endArray();
        }
        writer.endArray();
    }

    //---------------
    //--Loading Games

    /**
     * Loads a game.
     * 'game' includes info such as: player names, whether the game has finished and the history of all past boards / turns
     *
     * @return boolean on if successful (true) or not (false.)
     */
    public boolean loadGame() {
        try
        {
            JsonReader reader = new JsonReader(new FileReader(filePath));

            reader.beginObject();

            loadGameInfo(reader);
            loadBoardHistory(reader);

            reader.endObject();

            blackUserName = playerOneName;
            whiteUserName = playerTwoName;

            return true;
        }
        catch (Exception e)
        {
            System.out.println("Error in loading: " + e);
            return false;
        }
    }

    /**
     * Loads the player names and whether the game has finished from a json file.
     */
    void loadGameInfo(JsonReader reader) throws IOException
    {
        reader.nextName();
        playerOneName = reader.nextString();
        reader.nextName();
        playerTwoName = reader.nextString();
        reader.nextName();
        hasFinished = reader.nextBoolean();
    }

    /**
     * Loads the board history of all past boards / turns from a json file.
     */
    void loadBoardHistory(JsonReader reader) throws IOException
    {

        Piece[][] board;

        reader.nextName();
        reader.beginArray();

        //while board history is not fully read. / For every board...
        while (reader.hasNext())
        {

            //begin reading board.
            reader.beginArray();

            board = new Piece[9][9];

            //For each column
            while (reader.hasNext())
            {

                //begin reading column.
                reader.beginArray();

                //From the start of the column to the end.
                while (reader.hasNext())
                {
                    reader.beginObject();

                    reader.nextName();
                    Piece piece = stringToPiece(reader.nextString());

                    reader.nextName();
                    piece.setColour(stringToPieceColour(reader.nextString()));

                    reader.nextName();
                    piece.setxPos(reader.nextInt());

                    reader.nextName();
                    piece.setyPos(reader.nextInt());

                    //Checka and run special circumstances
                    switch (piece.getName()) {
                        case PAWN -> {
                            reader.nextName();
                            ((Pawn) piece).setFirstTurn(reader.nextBoolean());
                            reader.nextName();
                            ((Pawn) piece).setEnPassantable(reader.nextBoolean());
                            reader.nextName();
                            ((Pawn) piece).setEnPassanted(reader.nextBoolean());
                        }
                        case KING -> {
                            reader.nextName();
                            ((King) piece).setHasMoved(reader.nextBoolean());
                        }
                        case ROOK -> {
                            reader.nextName();
                            ((Rook) piece).setHasMoved(reader.nextBoolean());
                        }
                    }

                    board[piece.getXPos()][piece.getYPos()] = piece;

                    reader.endObject();
                }

                reader.endArray(); //end reading column.
            }
            reader.endArray();

            boardHistory.add(copyBoard(board));

        }
        reader.endArray();
    }

    /**
     * From a given String pieceName, it finds what Piece it represents, and returns a Piece and sets its PieceName to whatever piece it is.
     * E.g. 'p' would return a Pawn() with PieceName = PieceName.PAWN
     *
     * @param pieceName The String pieceName which will be used to find what piece it represents.
     */
    Piece stringToPiece(String pieceName)
    {
        Piece piece;

        switch (pieceName) {
            case "b" -> {
                piece = new Bishop();
                piece.setName(PieceName.BISHOP);
            }
            case "k" -> {
                piece = new King();
                piece.setName(PieceName.KING);
            }
            case "kn" -> {
                piece = new Knight();
                piece.setName(PieceName.KNIGHT);
            }
            case "q" -> {
                piece = new Queen();
                piece.setName(PieceName.QUEEN);
            }
            case "r" -> {
                piece = new Rook();
                piece.setName(PieceName.ROOK);
            }
            case "p" -> {
                piece = new Pawn();
                piece.setName(PieceName.PAWN);
            }
            default -> piece = null;
        }
        return piece;
    }

    /**
     * From a given String pieceColour, it finds what PieceColour it represents, and returns such PieceColour.
     *
     * @param pieceName The string which will be used to find the PieceName it represents.
     *
     * @return returns a PieceColour.
     */
    PieceColour stringToPieceColour(String pieceName)
    {
        return switch (pieceName) {
            case "b" -> PieceColour.BLACK;
            case "w" -> PieceColour.WHITE;
            default -> null;
        };
    }

    /**
     * Method to navigate forwards or backwards through the moves the players have played
     * @param direction whether the player is going forwards or backwards
     * @throws FileNotFoundException
     */
    public void navigate(char direction) throws FileNotFoundException {
            if (direction=='l') {
                    count--;
                    if (count<0){
                        count=0;
                    }
                    printBoard(copyBoard(boardHistory.get(count)));
                    System.out.println("left");
                    Game.displayBoardCmd(copyBoard(boardHistory.get(count)));

            } else {
                    if (count>=boardHistory.size()-1){
                        count=boardHistory.size()-1;
                    }else{
                        count++;
                        printBoard(copyBoard(boardHistory.get(count)));
                        System.out.println("right");
                        Game.displayBoardCmd(copyBoard(boardHistory.get(count)));
                    }
            }
    }

    /**
     * Method to print the image of the current board
     * @param pieces the pieces currently on the board
     * @throws FileNotFoundException
     */
    public void printBoard(Piece[][] pieces) throws FileNotFoundException {
        System.out.println(pieces);
        GridPane gridPane = (GridPane) getBoard().lookup("#Grid");
        gridPane.getChildren().clear();
        for(int y=0;y<8;y++){
            for(int x=0;x<8;x++){
                Piece piece=pieces[x][y];
                String colour;
                String type;
                if (piece !=null){
                    if (piece.getColour()==PieceColour.BLACK){
                        colour="Black";
                    }else{
                        colour="White";
                    }
                    if (piece.getName()==PieceName.PAWN){
                        type="Pawn";
                    }else if (piece.getName()==PieceName.BISHOP){
                        type="Bishop";
                    }else if (piece.getName()==PieceName.KING){
                        type="King";
                    }else if (piece.getName()==PieceName.KNIGHT){
                        type="Knight";
                    }else if (piece.getName()==PieceName.QUEEN){
                        type="Queen";
                    }
                    else{
                        type="Rook";
                    }
                    Image image= new Image(new FileInputStream("src/main/resources/com/example/devprojectcode/Pieces/"+colour+"_"+type+".png"));
                    ImageView imageView=new ImageView();
                    imageView.setImage(image);
                    imageView.setFitWidth(93.75);
                    imageView.setFitHeight(93.75);

                    gridPane.add(imageView,x,y);
                }
            }
        }
    }
}


