/**
 * @(#) Piece.java 1.0 2023/02/07
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 *
 */

/**
 * A class that defines all the attributes used by the types of pieces.
 * Pawn, Rook, Knight, Bishop, Queen and King inherit from this class.
 *
 * @author Kieran Foy, kif11
 * @version 1.0 Initial development
 *
 */

import java.util.ArrayList;

public class Piece {
    String type;
    String color;
    int xPos, yPos;
    int score;
    ArrayList<String> allMoves;

    public Piece(String color, int xPos, int yPos, int score) {
        this.color = color;
        this.xPos = xPos;
        this.yPos = yPos;
        this.score = score;
    }



}
