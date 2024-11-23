/**
 * @(#) Replay.java 1.0 2023/02/07
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 *
 */

/**
 * A class that handles Functional Requirements 10 and 11
 * (Replaying the game and storing the game data)
 *
 * @author Kieran Foy, kif11
 * @version 1.0 Initial development
 * @see Main
 */

public class Replay {

    /**
     * Method to save the data for the game:
     * (Piece positions, which pieces have been removed etc)
     * (FR9: Quitting the Game, FR11: Storing and Restoring the Game State)
     * Called after every player turn, and when the users decide to quit
     */
    public void saveGame() {
        // clear gameStats.txt
        // for every row of squares
            // for every column of squares
                // if square is occupied
                    // continue
                // write to game stats in format: "type" "color" (xPos,yPos), e.g.
                // ROOK BLACK (2,5)
    }

    /**
     * Method to load the data for the game:
     * (Piece positions, which pieces have been removed etc)
     * (FR10: Replay Game)
     * Called when the player chooses to continue the last game at the start-up menu
     */
    public void loadGame() {
        // load game statistics from a .txt file
    }
}
