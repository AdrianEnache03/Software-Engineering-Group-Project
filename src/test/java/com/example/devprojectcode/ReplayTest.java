package com.example.devprojectcode;

import com.example.devprojectcode.Enums.PieceColour;
import com.example.devprojectcode.Enums.PieceName;
import com.example.devprojectcode.Pieces.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @(#) ReplauTest.java 0.1 2023/30/04
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 *
 */

/**
 * A class that tests the methods defined in Replay.java, uses JUnit5 to do so.
 *
 * @author Kieran Foy (kif11)
 * @author Tate J. A. Moore (tam41)
 *
 * @version 0.1 - Created test file for Replay.java
 *
 * @see Replay
 */

class ReplayTest {

    ArrayList<Piece[][]> boardHistory = new ArrayList<>();
    String plr1;
    String plr2;
    Boolean hasFinished;

    @Disabled
    void saveGame() throws IOException {
        Replay replay = new Replay();
        Piece[][] board;

        Setup setup = new Setup();
        setup.boardSetup();
        board = setup.getBoard();

        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            FileWriter writer = new FileWriter("m:/test.json", true);

            writer.write(gson.toJson(board));
            writer.write("\n");
            writer.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found: " + e);
        }
        catch(IOException e) {
            System.out.println("Is a directory or a file that cannot be created, edited or accessed." + e);
        }
    }

    @Disabled
    void loadGame() throws IOException {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("m:/test.json"));

        //will hold all the past boards - chosen for fast data access.
        ArrayList<String> boardHistory = new ArrayList<String>();


        //loads the past boards into boardHistory
        while (bufferedReader.ready()) {
            boardHistory.add(bufferedReader.readLine());
        }

        int numTurns = boardHistory.size();

        JsonReader reader = new JsonReader(new StringReader(boardHistory.get(0)));
        System.out.println(reader.peek()); //BEGIN_ARRAY (2D i.e. x=0-7,y=0-7)
        reader.beginArray();

        JsonToken token = reader.peek();
        System.out.println(token); //BEGIN_ARRAY (1D i.e. x=0,y=0-7)
        reader.beginArray();

        token = reader.peek();
        System.out.println(token); //BEGIN_OBJECT (1st element: black rook at 0,0)

        reader.beginObject();

//        token = reader.peek(); //A token = 1 object?
//        System.out.println(token); //NAME

        System.out.println(reader.nextName()); //name
        System.out.println(reader.nextString()); //ROOK

        System.out.println(reader.nextName()); //colour
        System.out.println(reader.nextString());//BLACK

        System.out.println(reader.nextName()); //xPos
        System.out.println(reader.nextString()); //0

        System.out.println(reader.nextName()); //yPos
        System.out.println(reader.nextString()); //0

        token = reader.peek();
        System.out.println(token); // END_OBJECT
        reader.endObject();

        //End of token / object

        //===========================================
        System.out.println("=========================");

        token = reader.peek(); //A token = 1 object?
        System.out.println(token); //

        reader.beginObject();

        System.out.println(reader.nextName()); //Colour
        System.out.println(reader.nextString()); //

        System.out.println(reader.nextName()); //name
        System.out.println(reader.nextString());//

        System.out.println(reader.nextName()); //xPos
        System.out.println(reader.nextString()); //

        System.out.println(reader.nextName()); //yPos
        System.out.println(reader.nextString()); //

    }

    @Disabled
    void loadGame2() throws IOException
    {

        BufferedReader bufferedReader = new BufferedReader(new FileReader("m:/test.json"));

        //will hold all the past boards - chosen for fast data access.
        ArrayList<String> boardHistory = new ArrayList<String>();

        //loads the past boards into boardHistory
        while (bufferedReader.ready()) {
            boardHistory.add(bufferedReader.readLine());
        }

        int numTurns = boardHistory.size();

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Piece[][].class, new BoardAdapter());
        Gson gson = builder.create();
        Piece[][] boardCool = gson.fromJson(boardHistory.get(0),Piece[][].class);

        System.out.println("============================\n============================\n============================\n============================");

        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {
                System.out.print(x);
                System.out.print(" ");
                System.out.print(y);

                if (boardCool[x][y] == null) {
                    System.out.println(" = NULL");
                }
                else {
                    System.out.print(" = ");
                    System.out.print(boardCool[x][y].getColour());
                    System.out.print(" ");
                    System.out.println(boardCool[x][y].getName());
                }
                //RETRY

            }
        }
    }

    //1. 1st token - BEGIN_ARRAY
    //2. Went into array, 1st token - BEGIN_OBJECT
    //3. Went into object, 1st token - NAME

    @Disabled
    public void saveGame2() throws IOException {
        //saves the player names and board history to file.
        plr1 = "bob";
        plr2 = "joe";

        Setup setup = new Setup();
        setup.boardSetup();

        ArrayList<Piece[][]> boardHistory = new ArrayList<>();
        boardHistory.add(setup.getBoard());
        boardHistory.add(setup.getBoard());
        boardHistory.add(setup.getBoard());

        try {
            GsonBuilder builder = new GsonBuilder();
            //builder.setPrettyPrinting();
            Gson gson = builder.create();
            FileWriter writer = new FileWriter("m:/test2.json");
            writer.write(gson.toJson(plr1));
            writer.write("\n");
            writer.write(gson.toJson(plr2));
            writer.write("\n");
            writer.write(gson.toJson(boardHistory));
            writer.write("\n");
            writer.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found: " + e);
        }
        catch(IOException e) {
            System.out.println("Is a directory or a file that cannot be created, edited or accessed." + e);
        }
    }

    @Disabled
    void saveGame3() throws IOException
    {
        Setup setup = new Setup();
        setup.boardSetup();

        boardHistory = new ArrayList<>();
        boardHistory.add(setup.getBoard());
        boardHistory.add(setup.getBoard());
        boardHistory.add(setup.getBoard());
        boardHistory.add(setup.getBoard());
        //boardHistory.add(setup.getBoard());

        plr1 = "bob";
        plr2 = "joe";
        hasFinished = false;

        //-----------------------------------------------------------

        OutputStream outputStream = new FileOutputStream("m:/test3.json");
        Writer outputStreamWriter = new OutputStreamWriter(outputStream);

        JsonWriter writer = new JsonWriter(outputStreamWriter);
        writer.setIndent("    ");
        writer.beginObject();

        writeGameInfo(writer);
        writePastHistory(writer);

        writer.endObject();
        writer.close();
    }

    void writeGameInfo(JsonWriter writer) throws IOException
    {
        writer.name("plr1").value(plr1);
        writer.name("plr2").value(plr2);
        writer.name("hasFinished").value(hasFinished);
    }

    void writePastHistory(JsonWriter writer) throws IOException
    {
        writer.name("boardHistory");
        writer.beginArray(); //go into board history

        for (Piece[][] board : boardHistory)
        {
            writer.beginArray(); //go into board.

            for (int x = 0; x < 8; x++)
            {
                writer.beginArray(); //go into each 1D array of 2D board array.

                for (int y = 0; y < 8; y++)
                {
                    if (board[x][y] != null)
                    {
                        writer.beginObject();


                        writer.name("type").value(board[x][y].getName().getLabel());
                        writer.name("colour").value(board[x][y].getColour().getColourLabel());
                        writer.name("x").value(x);
                        writer.name("y").value(y);

                        if (board[x][y].getName() == PieceName.PAWN)
                        {
                            writer.name("isFirstTurn").value(((Pawn) board[x][y]).isFirstTurn());
                            writer.name("isEnPassantable").value(((Pawn) board[x][y]).isEnPassantable());
                            writer.name("isHasEnPassanted").value(((Pawn) board[x][y]).hasEnPassanted());
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

    @Test
    void loadUltraMax() throws IOException
    {
        Piece[][] newBoard = new Piece[9][9];

        JsonReader reader = new JsonReader(new FileReader("m:/test3.json"));

        reader.beginObject();

        loadGameInfo(reader);
        loadBoardHistory(reader);

        reader.endObject();

        Piece[][] board = boardHistory.get(0);

        System.out.println(plr1);
        System.out.println(plr2);
        System.out.println(hasFinished);
        System.out.println("============");

        for (int x = 0; x < 8; x++)
        {
            for (int y = 0; y < 8; y++)
            {

                System.out.print(x);
                System.out.print(",");
                System.out.print(y);

                if (board[x][y] == null)
                {
                    System.out.println(" = NULL");
                }
                else
                {
                    System.out.print(" = ");
                    System.out.print(board[x][y].getColour());
                    System.out.print(" ");
                    System.out.println(board[x][y].getName());
                }
            }
        }
    }

    void loadGameInfo(JsonReader reader) throws IOException
    {
        reader.nextName();
        plr1 = reader.nextString();
        reader.nextName();
        plr2 = reader.nextString();
        reader.nextName();
        hasFinished = reader.nextBoolean();
    }

    void loadBoardHistory(JsonReader reader) throws IOException
    {

        Piece[][] board;

        reader.nextName();
        reader.beginArray();


        while (reader.hasNext()) //while board history is not fully read. / For every board...
        {
            reader.beginArray(); //begin reading board.

            board = new Piece[9][9];

            while (reader.hasNext()) //For each column
            {

                reader.beginArray(); //begin reading column.

                while (reader.hasNext()) //From the start of the column to the end.
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

                    if (piece.getName() == PieceName.PAWN)
                    {
                        reader.nextName();
                        ((Pawn) piece).setFirstTurn(reader.nextBoolean());

                        reader.nextName();
                        ((Pawn) piece).setEnPassantable(reader.nextBoolean());

                        reader.nextName();
                        ((Pawn) piece).setEnPassanted(reader.nextBoolean());
                    }

                    board[piece.getXPos()][piece.getYPos()] = piece;

                    reader.endObject();
                }

                reader.endArray(); //end reading column.
            }
            reader.endArray();

            boardHistory.add(board);


        }
        reader.endArray();
    }

    Piece stringToPiece(String pieceName)
    {
        Piece piece;
        switch (pieceName) {
            case "b":
                piece = new Bishop();
                piece.setName(PieceName.BISHOP);
                break;
            case "k":
                piece = new King();
                piece.setName(PieceName.KING);
                break;
            case "kn":
                piece = new Knight();
                piece.setName(PieceName.KNIGHT);
                break;
            case "q":
                piece = new Queen();
                piece.setName(PieceName.QUEEN);
                break;
            case "r":
                piece = new Rook();
                piece.setName(PieceName.ROOK);
                break;
            case "p":
                piece = new Pawn();
                piece.setName(PieceName.PAWN);
                break;
            default:
                return null;
        }
        return piece;
    }

    PieceColour stringToPieceColour(String pieceName)
    {
        switch (pieceName) {
            case "b":
                return PieceColour.BLACK;
            case "w":
                return PieceColour.WHITE;
            default:
                return null;
        }
    }
}

class BoardAdapter extends TypeAdapter<Piece[][]> {
    @Override
    public void write(JsonWriter jsonWriter, Piece[][] pieces) throws IOException {
        //
    }

    public Piece[][] read(JsonReader reader) throws IOException {

        Piece[][] newBoard = new Piece[9][9];

        reader.beginArray();
        JsonToken token;
        String field;
        JsonElement elem;

        //For each column
        for (int x = 0; x < 8; x++)
        {
            token = reader.peek();
            System.out.println(token);
            reader.beginArray();

            //From the start of the column to the end.
            for (int y = 0; y < 8; y++)
            {

                token = reader.peek();

                System.out.print(x);
                System.out.print(",");
                System.out.print(y);

                if (token == JsonToken.NULL)
                {
                    System.out.println(" = NULL");
                    reader.nextNull();
                }
                else
                {
                    //if a piece is at x,y...

                    reader.beginObject();
                    field = reader.nextName(); //get the field name of the


                    //if it is a pawn...
                    if (Objects.equals(field, "isFirstTurn"))
                    {
                        //If it is a pawn ...
                        //Is in form {"isFirstTurn":<bool>,"isEnPassantable":<bool>,"isHasEnPassanted":<bool>,"name":<str>,"colour":<str>,"xPos":<int>,"yPos":<int>}

                        Pawn pawn = new Pawn();
                        pawn.setFirstTurn( reader.nextBoolean() );
                        reader.nextName();
                        pawn.setEnPassantable( reader.nextBoolean() );
                        reader.nextName();
                        pawn.setEnPassantable( reader.nextBoolean() );
                        reader.nextName();
                        reader.nextString();
                        pawn.setName(PieceName.PAWN);
                        reader.nextName();
                        pawn.setColour(stringToPieceColour( reader.nextString() ));
                        pawn.setxPos(x);
                        pawn.setyPos(y);
                        newBoard[x][y] = pawn;
                        System.out.println(" = PAWN");

                    }
                    else
                    {

                        Piece piece = stringToPieceName(reader.nextString());
                        reader.nextName();
                        piece.setColour(stringToPieceColour( reader.nextString() ));
                        piece.setxPos(x);
                        piece.setyPos(y);
                        newBoard[x][y] = piece;
                        System.out.print(" = ");
                        System.out.println(piece.getName());
                    }
                    reader.nextName();
                    reader.nextInt();
                    reader.nextName();
                    reader.nextInt();
                    reader.endObject();
                }
                reader.peek();
            }
            reader.endArray();
        }
        reader.endArray();
        return newBoard;
    }

    Piece stringToPieceName(String pieceName) {
        Piece piece;
        switch (pieceName) {
            case "BISHOP":
                piece = new Bishop();
                piece.setName(PieceName.BISHOP);
                break;
            case "KING":
                piece = new King();
                piece.setName(PieceName.KING);
                break;
            case "KNIGHT":
                piece = new Knight();
                piece.setName(PieceName.KNIGHT);
                break;
            case "QUEEN":
                piece = new Queen();
                piece.setName(PieceName.QUEEN);
                break;
            case "ROOK":
                piece = new Rook();
                piece.setName(PieceName.ROOK);
                break;
            case "PAWN":
                piece = new Pawn();
                piece.setName(PieceName.PAWN);
                break;
            default:
                return null;
        }
        return piece;
    }

    PieceColour stringToPieceColour(String pieceName) {
        switch (pieceName) {
            case "BLACK":
                return PieceColour.BLACK;
            case "WHITE":
                return PieceColour.WHITE;
            default:
                return null;
        }
    }
}

