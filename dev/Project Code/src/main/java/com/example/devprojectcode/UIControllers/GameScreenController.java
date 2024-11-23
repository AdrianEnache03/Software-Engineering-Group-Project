/**
 * Controller.java 0.1 2023/03/07
 * <p>
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package com.example.devprojectcode.UIControllers;

import com.example.devprojectcode.Enums.PieceColour;
import com.example.devprojectcode.Enums.PieceName;
import com.example.devprojectcode.Game;
import com.example.devprojectcode.Pieces.Pawn;
import com.example.devprojectcode.Replay;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A Class that handles UI control
 *
 * @author George Cooper, gwc1
 * @author Finley Aubin, (fia5)
 *
 * @version 0.1 Initial development
 * @version 0.2 Added method to handle en passant
 * @version 0.3 Added methods to handle replay buttons
 *
 * @see GameScreenController
 */
public class GameScreenController extends Game {

    /*
        the current saved X and Y positions of the last clicked on piece
     */
    Node currentNodeSelected;
    ArrayList<Image> images;
    ArrayList<Node> squares;

    /**
     * Setter method for currentNodeSelected
     *
     * @param n the node to assign to current Node Selected
     */
    private void setSelectedNode(Node n) {
        currentNodeSelected = n;
    }


    /**
     * Method to handle switching scenes from main menu to board
     * If the button is pressed the user will be taken to the login screen.
     *
     * @param actionEvent The button being pressed
     */
    public void handleStartNewGame(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(getLogIn());
        stage.show();
    }

    /**
     * Method to handle when quitGame button is pressed
     * If the button is pressed the user will quit the program.
     *
     * @param actionEvent The button being pressed
     */
    public void handleQuitGame(ActionEvent actionEvent) {
        actionEvent.consume();
        Platform.exit();
        System.exit(0);
    }


    /**
     * Method to rotate the board
     * first calls rotateAllNodes to rotate the nodes
     * then rotates the board
     *
     * @param p A node element to fetch the gridPane from
     */
    public void handleBoardRotation(Parent p) {
        Parent p2 = p.getParent();
        GridPane gpW = (GridPane) p2.lookup("#whiteGp");
        GridPane gpB = (GridPane) p2.lookup("#blackGp");
        String wStyle = gpW.getStyle();
        gpW.setStyle(gpB.getStyle());
        gpB.setStyle(wStyle);
        Text tw = (Text) gpW.lookup("#playerOne");
        Text tb = (Text) gpB.lookup("#playerTwo");
        String playerName = tb.getText();
        tb.setText(tw.getText());
        tw.setText(playerName);
        rotateAllNodes(p);
        RotateTransition rt = new RotateTransition(Duration.millis(200), p);
        rt.setByAngle(180);
        rt.play();
    }


    /**
     * A method to rotate all chess pieces
     * Creates an ArrayList of all nodes, then rotates them all
     *
     * @param p the parent
     */
    public void rotateAllNodes(Parent p) {
        ArrayList<Node> nodes = new ArrayList<>(p.getChildrenUnmodifiable());
        for (Node n : nodes) {
            RotateTransition rt = new RotateTransition(Duration.millis(200), n);
            rt.setByAngle(-180);
            rt.play();
        }
    }

    /**
     * Method to create a drop shadow effect
     * Creates a Color object from r,g,b, values
     * Creates a new drop shadow object
     *
     * @param r r value
     * @param g g value
     * @param b b value
     * @return the drop shadow effect
     */
    private static DropShadow newDropShadow(int r, int g, int b) {
        Color paint = Color.rgb(r, g, b);
        DropShadow ds = new DropShadow();
        ds.setColor(paint);
        ds.setWidth(60);
        ds.setHeight(60);
        ds.setSpread(0.45);
        return ds;
    }

    /**
     *
     * A method to handle when a square is selected
     *
     * @param mouseEvent when the user clicks on the node.
     * @throws IOException if an unexpected square is selected as the input.
     * @throws InterruptedException when threads are interrupted and can not proceed past this  point.
     */
    public void handlePiece(MouseEvent mouseEvent) throws IOException, InterruptedException {
        if (currentNodeSelected == null) {
            Node n = ((Node) mouseEvent.getSource());
            int currentNodeSelectedY = setRowIndex(n);
            int currentNodeSelectedX = setColumnIndex(n);
            if (turnChecker(chessBoard[currentNodeSelectedX][currentNodeSelectedY].getColour().getColourLabel())) {
                n.setEffect(newDropShadow(255, 255, 255));
                this.setSelectedNode(n);
                System.out.println(currentNodeSelectedX + " " + currentNodeSelectedY);
                showMoves(currentNodeSelectedX, currentNodeSelectedY);
            }
        } else {
            handleRequestMove(mouseEvent);
        }
    }

    /**
     * Sets the row index for the object to not go outside the javadoc graphics window.
     * @param n of type Node which can be any object on the board/game screen.
     * @return the variable y which indicates if the row index for n exists. (Makes sure that the variable y is on the
     * game board) If so, this value is returned. Otherwise, 0 is returned.
     */
    private int setRowIndex(Node n) {
        int y;
        if (n != null) {
            if (GridPane.getRowIndex(n) != null) {
                y = GridPane.getRowIndex(n);
            } else {
                y = 0;
            }
        } else {
            y = 0;
        }
        return y;
    }

    /**
     * Sets the column index for the object to not go outside the javadoc graphics window.
     * @param n of type Node which can be any object on the board/game screen.
     * @return the variable x which indicates if the row index for n exists. (Makes sure that the variable x value is on the
     * game board) If so, this value is returned. Otherwise, 0 is returned.
     */
    private int setColumnIndex(Node n) {
        int x;
        if (n != null) {
            if (GridPane.getColumnIndex(n) != null) {
                x = GridPane.getColumnIndex(n);
            } else {
                x = 0;
            }
        } else {
            x = 0;
        }
        return x;
    }

    /**
     * method to handle a request to move one piece to another
     * assigns the location of the square to move to (x2/y2)
     * assigns the location of the current square (x1/y1)
     * if checkMove returns true calls movePiece and handleRotateBoard
     * else prints an error message
     *
     * @param mouseEvent when the square to move the piece to is clicked
     * @throws IOException if file cannot be found
     * @exception InterruptedException is thrown when a thread is interrupted while it's waiting, sleeping, or otherwise occupied
     */
    public void handleRequestMove(MouseEvent mouseEvent) throws IOException, InterruptedException {

        // get the coordinates of the selected square and the target square
        Node n = ((Node) mouseEvent.getSource());
        int y2 = setRowIndex(n);
        int x2 = setColumnIndex(n);
        int y1 = setRowIndex(currentNodeSelected);
        int x1 = setColumnIndex(currentNodeSelected);

        // check if the current player is in check
        if (isPlayerInCheck) {
            frontEndCheck(mouseEvent, x1, x2, y1, y2);
        } else {

            // check if the move is valid
            if (checkMove(x1, x2, y1, y2)) {
                resetImages();
                updateBoard(x1, x2, y1, y2);
                movePiece(mouseEvent, x1, x2, y1, y2);

                // handle pawn promotion if necessary
                if (chessBoard[x2][y2].getName().equals(PieceName.PAWN)) {
                    pawnPromotion((Pawn) chessBoard[x2][y2], y2);
                }

                // update turn tracker and board rotation
                turnTracker++;
                handleBoardRotation(currentNodeSelected.getParent());
                getReplay().addToBoardHistory(copyBoard(chessBoard));

                // check for checkmate
                if (isCheckMate()) {
                    gameOver();
                }
                showCheck(n);
            } else {
                System.out.println("illegal move");
                resetImages();
            }

            // reset the selected node
            if(currentNodeSelected!=null){
                currentNodeSelected.setEffect(null);
            }
            currentNodeSelected = null;
        }
    }

    /**
     * Checks to see what changes to the board the user is causing.
     * @param mouseEvent when the mouse has been clicked
     * @param x1 represents the starting x position
     * @param x2 represents the next x position the piece from x1 will move to.
     * @param y1 represents the starting y position
     * @param y2 represents the next y position the piece from y1 will move to.
     * @throws IOException if file cannot be found
     * @exception InterruptedException is thrown when a thread is interrupted while it's waiting, sleeping, or otherwise occupied
     */

    private void frontEndCheck(MouseEvent mouseEvent, int x1, int x2, int y1, int y2) throws IOException, InterruptedException {
        if (isOpponentCheck(x1, x2, y1, y2).equals(false)) {
            if (checkMove(x1, x2, y1, y2)) {
                isPlayerInCheck = false;
                handlePiece(mouseEvent);
                return;
            }
        }
        System.out.println("you are in check");
        resetImages();
        currentNodeSelected.setEffect(null);
        currentNodeSelected = null;
    }

    /**
     * Displays to the user after pressing the king that you can castle by highlighting that space.
     * @param n of type Node which can be any object on the board/game screen.
     * @throws FileNotFoundException
     */
    private void showCastle(Node n) throws FileNotFoundException {
        ImageView castlingSquare = (ImageView) n;
        if(hasCastled){
            Image castle = new Image(new FileInputStream("src/main/resources/com/example/devprojectcode/Pieces/take.png"));
            castlingSquare.setImage(castle);
        }
    }

    /**
     * Shows if you are in Check
     * @param n of type Node which can be any object on the board/game screen.
     */
    private void showCheck(Node n) {
        ImageView k;
        if(isPlayerInCheck) {
            if (this.turnChecker("b")) {
                k = (ImageView) n.getParent().lookup("#bK");
            } else {
            k = (ImageView) n.getParent().lookup("#wK");
        }
            DropShadow ds = newDropShadow(225, 0, 0);
            k.setEffect(ds);
        } else {
            if (this.turnChecker("b")) {
                k = (ImageView) n.getParent().lookup("#wK");
            } else {
                k = (ImageView) n.getParent().lookup("#bK");
            }
            k.setEffect(null);
        }
    }

    /**
    *
     * Method to show all the possible moves for a selected piece on the chessboard
     * Gets all the children nodes from the parent of the currentNodeSelected
     * Loops through each node and gets their coordinates
     * If the player is in check, only squares that will take them out of check will be highlighted
     * Otherwise, all legal moves for the selected piece will be highlighted
     * Adds the highlighted squares to the squares ArrayList
     * Calls the addToImages method to set the image of each highlighted square to the selected image
     * @param x1 initial x position of the selected piece
     * @param y1 initial y position of the selected piece
     * @throws IOException if an I/O error occurs
     */
    public void showMoves(int x1, int y1) throws IOException {
        GridPane gp = (GridPane) currentNodeSelected.getParent();
        ArrayList<Node> nodes = new ArrayList<>(gp.getChildren());
        squares = new ArrayList<>();
        for (Node node : nodes) {
            int y2;
            int x2;
            if (GridPane.getRowIndex(node) == null) {
                y2 = 0;
            } else {
                y2 = GridPane.getRowIndex(node);
            }
            if (GridPane.getColumnIndex(node) == null) {
                x2 = 0;
            } else {
                x2 = GridPane.getColumnIndex(node);
            }
            //showCastle(node);
            if (isPlayerInCheck) {
                if (isOpponentCheck(x1, x2, y1, y2).equals(false)) {
                    if (checkMove(x1, x2, y1, y2)) {
                        squares.add(node);
                    }
                }
            } else {
                if (checkMove(x1, x2, y1, y2)) {
                    squares.add(node);
                }
            }
        }
        addToImages();
    }

    /**
     *
     * Adds the images of all the squares on the chessboard to an array list
     * If the square is not focus traversable, applies a drop shadow effect to the image
     * If the square is focus traversable, adds the image to the images array list and sets the image to the 'selected' image
     * @throws FileNotFoundException if the image file is not found
     * Handles the UI of the program, giving a highlight to squares with pieces and changing empty nodes to the referenced image
     */
    public void addToImages() throws FileNotFoundException {
        images = new ArrayList<>(64);
        Image selected = new Image(new FileInputStream("src/main/resources/com/example/devprojectcode/Pieces/selected.png"));
        DropShadow ds = newDropShadow(225, 225, 0);
        for (Node square : squares) {
            ImageView imageView = (ImageView) square;
            if (imageView.isFocusTraversable()) {
                imageView.setEffect(ds);
            } else {
                images.add(imageView.getImage());
                imageView.setImage(selected);
            }
        }
    }

    /**
     * Resets the images of all the squares on the chessboard
     * -If the square is not focus traversable, sets the image to the default image
     * -If the square is focus traversable, removes any visual effects applied to the image
     * -This is to distinguish between an empty square or not
     */
    public void resetImages() {
        if (squares != null) {
            for (Node square : squares) {
                ImageView imageView = (ImageView) square;
                if (imageView.isFocusTraversable()) {
                    imageView.setEffect(null);
                } else {
                    imageView.setImage(images.get(images.size() - 1));
                }
            }
        }
    }
    /**
     * Handles the special case of an en passant move in chess.
     * @param x2 The x-coordinate of the destination square.
     * @param y2 The y-coordinate of the destination square.
     * @param n The node representing the source square.
     * @param gp The GridPane containing the chess board.
     */

    private void handleEnPassantMove(int x2, int y2, Node n, GridPane gp) {
        // Get the pawn that is making the en passant move
        Pawn p = (Pawn) chessBoard[x2][y2];

        //Initialises variables
        ImageView remove = null;
        int col;
        if (p.getColour() == PieceColour.BLACK) {
            col = 1;
        } else {
            col = -1;
        }
        if (p.hasEnPassanted()) {
            // Calculates the position of the captured pawn
            double nodeY = GridPane.getRowIndex(n) - col;
            double nodeX = GridPane.getColumnIndex(n);
            // Find the captured pawn in the GridPane
            for (Node node : gp.getChildren()) {
                if (GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) != null) {
                    if (GridPane.getColumnIndex(node) == nodeX && GridPane.getRowIndex(node) == nodeY) {
                        remove = (ImageView) node;
                        break;
                    }
                }
            }
            // Removes the captured pawn from the GridPane and replace it with a blank square
            gp.getChildren().removeAll(remove);
            ImageView imageView = (ImageView) gp.getScene().lookup("#blank");
            assert remove != null;
            remove.setImage(imageView.getImage());
            remove.setOnMouseClicked(imageView.getOnMouseClicked());
            // Move the current pawn to the destination square
            gp.add(remove, x2, y2 - col);
            ((Pawn) chessBoard[x2][y2]).setEnPassanted(false);
        }
    }


    /**
     * Method to move pieces once the move has been deemed legal
     * - Removes the two nodes (currentNodeSelected and n) from the GridPane.
     * - Adds currentNodeSelected to new position (x2, y2) in the GridPane.
     * - Adds a blank node to the original position (x1, y1) in the GridPane.
     *
     * @param mouseEvent the MouseEvent triggered by the user's click
     * @param x1 the initial x position of currentNodeSelected
     * @param x2 the initial x position of n
     * @param y1 the initial y position of currentNodeSelected
     * @param y2 the initial y position of n
     */
    @FXML
    public void movePiece(MouseEvent mouseEvent, int x1, int x2, int y1, int y2) {
        // Get the ImageView that triggered the MouseEvent and its parent GridPane
        ImageView n = ((ImageView) mouseEvent.getSource());
        GridPane gp = (GridPane) n.getParent();
        // If the piece being moved is a pawn and has executed an en passant move, handle it
        if (chessBoard[x2][y2].getName() == PieceName.PAWN) {
            handleEnPassantMove(x2, y2, n, gp);
        }
        if(hasCastled){
            handleCastling(x2,y2,gp);
        }
        // Remove the two nodes (currentNodeSelected and n) from the GridPane
        gp.getChildren().removeAll(n, currentNodeSelected);
        // Add currentNodeSelected to new position (x2, y2) in the GridPane
        gp.add(currentNodeSelected, x2, y2);
        // Replace the ImageView of the removed node (n) with a blank node in its original position (x1, y1)
        ImageView imageView = (ImageView) gp.getScene().lookup("#blank");
        n.setImage(imageView.getImage());
        n.setOnMouseClicked(imageView.getOnMouseClicked());
        n.setFocusTraversable(false);
        gp.add(n, x1, y1);
        System.out.println("Yay it worked");
    }

    /**
     * Method to handle the front end for castling
     * Moves the rook piece.
     * If the castling is a left side castle, the rook to the left is moved.
     * Else, the rook to the right is moved
     *
     * @param x1 King x position
     * @param y1 King y position
     * @param gp Board gridPane
     */
    private void handleCastling(int x1, int y1, GridPane gp) {
        Node rook;
        int dir = -1;

        //Determine which rook to move based on current players turn and which side they are castling too
        if(turnChecker("b")) {
            if (leftSide) {
                dir = 1;
                rook = gp.lookup("#BLRook");
            } else {
                rook = gp.lookup("#BRRook");
            }
        } else {
            if (leftSide) {
                dir = 1;
                rook = gp.lookup("#WLRook");
            } else {
                rook = gp.lookup("#WRRook");
            }
        }

        //Removes rook from current position and adds it next to the king
        gp.getChildren().remove(rook);
        gp.add(rook, x1+dir, y1);

        //Sets hasCastled flag to false since a castling move has just been made
        hasCastled = false;
    }

    /**
     *
     * Handles the event when the "Load Game" button is clicked
     * Calls the Replay class to load an old game
     * @param actionEvent The event triggered by the "Load Game" button
     * @throws IOException if there is an error loading the game
     */
    public void handleLoadOldGameButton(ActionEvent actionEvent) throws IOException {
        replay = new Replay();
        if (replay.readLine()) {
            if (replay.loadGame()) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(getBoard());
                stage.getScene().lookup("#forward").setVisible(true);
                stage.getScene().lookup("#backward").setVisible(true);
                stage.show();
            }
        }
    }


    /**
     * Method to handle when forward button is pressed to increment the turn by one in a replay.
     *
     * @param actionEvent when the forward button is pressed
     * @throws FileNotFoundException if next turn cannot be found
     */
    public void handleForwardButton(ActionEvent actionEvent) throws FileNotFoundException {
        replay.navigate('r');
        actionEvent.consume();
    }

    /**
     * Method to handle when backward button is pressed to increment the turn by one in a replay.
     *
     * @param actionEvent when the backwards button is pressed
     * @throws FileNotFoundException if the next turn cannot be found
     */
    public void handleBackwardButton(ActionEvent actionEvent) throws FileNotFoundException {
        replay.navigate('l');
        actionEvent.consume();
    }
}
