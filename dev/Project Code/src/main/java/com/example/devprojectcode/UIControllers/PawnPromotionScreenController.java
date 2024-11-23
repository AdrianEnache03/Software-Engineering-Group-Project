/**
 * Game.java 1.0 2023/02/07
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 * @author Fin Aubin (fia5)
 * @version 1.0 initial development
 * @version 1.1 Methods created
 *
 */

package com.example.devprojectcode.UIControllers;

import com.example.devprojectcode.Enums.PieceColour;
import com.example.devprojectcode.Enums.PieceName;
import com.example.devprojectcode.Game;
import com.example.devprojectcode.Pieces.Pawn;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Transform pawn into Queen if prompted to do so
 * @return the Queen piece
 * @throws FileNotFoundException incase of image changing error
 */
public class PawnPromotionScreenController extends Game {
    public PieceName handleQueen(MouseEvent mouseEvent) throws FileNotFoundException {
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                int y2;
                if (y == 1) {
                    y2 = 7;
                } else {
                    y2 = 0;
                }
                if (chessBoard[x][y2] != null) {
                    if (chessBoard[x][y2].getName().equals(PieceName.PAWN)) {
                        Pawn.changePiece(PieceName.QUEEN, x, y2, chessBoard[x][y2].getColour());
                        Node node = getNode(x, y2);
                        Image selected;
                        if (chessBoard[x][y2].getColour().equals(PieceColour.BLACK)) {
                            selected = new Image(new FileInputStream("src/main/resources/com/example/devprojectcode/Pieces/Black_Queen.png"));
                        } else {
                            selected = new Image(new FileInputStream("src/main/resources/com/example/devprojectcode/Pieces/White_Queen.png"));
                        }
                        ImageView imageView = (ImageView) node;
                        imageView.setImage(selected);
                        System.out.println("Queen");
                        System.out.println(x + " " + y + "" + chessBoard[x][y2].getColour());
                    }
                }
            }
        }
        return PieceName.QUEEN;
    }

    /**
     * Transform pawn into knight if prompted to do so
     * @param mouseEvent checks for when the mouse is clicked
     * @return the knight piece
     * @throws FileNotFoundException incase of image changing error
     */
    public PieceName handleKnight(MouseEvent mouseEvent) throws FileNotFoundException {
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                int y2;
                if (y == 1) {
                    y2 = 7;
                } else {
                    y2 = 0;
                }
                if (chessBoard[x][y2] != null) {
                    if (chessBoard[x][y2].getName().equals(PieceName.PAWN)) {
                        Pawn.changePiece(PieceName.KNIGHT, x, y2, chessBoard[x][y2].getColour());
                        Node node = getNode(x, y2);
                        Image selected;
                        if (chessBoard[x][y2].getColour().equals(PieceColour.BLACK)) {
                            selected = new Image(new FileInputStream("src/main/resources/com/example/devprojectcode/Pieces/Black_Knight.png"));
                        } else {
                            selected = new Image(new FileInputStream("src/main/resources/com/example/devprojectcode/Pieces/White_Knight.png"));
                        }
                        ImageView imageView = (ImageView) node;
                        imageView.setImage(selected);
                        System.out.println("Knight");
                        System.out.println(x + " " + y + "" + chessBoard[x][y2].getColour());
                    }
                }
            }
        }
        return PieceName.KNIGHT;
    }
    /**
     *Transform pawn into bishop if prompted to do so
     * @param mouseEvent checks for when the mouse is clicked
     * @return the bishop piece
     * @throws FileNotFoundException incase of image changing error
     */
    public PieceName handleBishop(MouseEvent mouseEvent) throws FileNotFoundException {
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                int y2;
                if (y == 1) {
                    y2 = 7;
                } else {
                    y2 = 0;
                }
                if (chessBoard[x][y2] != null) {
                    if (chessBoard[x][y2].getName().equals(PieceName.PAWN)) {
                        Pawn.changePiece(PieceName.BISHOP, x, y2, chessBoard[x][y2].getColour());
                        Node node = getNode(x, y2);
                        Image selected;
                        if (chessBoard[x][y2].getColour().equals(PieceColour.BLACK)) {
                            selected = new Image(new FileInputStream("src/main/resources/com/example/devprojectcode/Pieces/Black_Bishop.png"));
                        } else {
                            selected = new Image(new FileInputStream("src/main/resources/com/example/devprojectcode/Pieces/White_Bishop.png"));
                        }
                        ImageView imageView = (ImageView) node;
                        imageView.setImage(selected);
                        System.out.println("Bishop");
                        System.out.println(x + " " + y + "" + chessBoard[x][y2].getColour());
                    }
                }
            }
        }
        return PieceName.BISHOP;
    }

    /**
     * Transform pawn into rook if prompted to do so
     * @param mouseEvent checks for when the mouse is clicked
     * @return the rook piece
     * @throws FileNotFoundException in case of image changing error
     */
    public PieceName handleRook(MouseEvent mouseEvent) throws FileNotFoundException {
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 8; x++) {
                int y2;
                if (y == 1) {
                    y2 = 7;
                } else {
                    y2 = 0;
                }
                if (chessBoard[x][y2] != null) {
                    if (chessBoard[x][y2].getName().equals(PieceName.PAWN)) {
                        Pawn.changePiece(PieceName.ROOK, x, y2, chessBoard[x][y2].getColour());
                        Node node = getNode(x, y2);
                        Image selected;
                        if (chessBoard[x][y2].getColour().equals(PieceColour.BLACK)) {
                            selected = new Image(new FileInputStream("src/main/resources/com/example/devprojectcode/Pieces/Black_Rook.png"));
                        } else {
                            selected = new Image(new FileInputStream("src/main/resources/com/example/devprojectcode/Pieces/White_Rook.png"));
                        }
                        ImageView imageView = (ImageView) node;
                        imageView.setImage(selected);
                        System.out.println("Rook");
                        System.out.println(x + " " + y2 + "" + chessBoard[x][y2].getColour());
                    }
                }
            }
        }return PieceName.ROOK;
    }

    /**
     * Tells another class what the value is from a private variable node
     * @param x is the x position in the javaFX window
     * @param y is the y position in the javaFX window
     * @return the value of a specific node
     */
    public static Node getNode(int x, int y) {
        GridPane gridPane = (GridPane) getBoard().lookup("#Grid");
        Node node=null;
        ObservableList<Node> nodes = gridPane.getChildren();
        for (Node node1 : nodes) {
                if (node1 != null && GridPane.getColumnIndex(node1)!=null){
                    if (GridPane.getRowIndex(node1) == y && GridPane.getColumnIndex(node1) == x) {
                        node=node1;
                        break;
                    }
                }
            }
        return node;

    }
}
