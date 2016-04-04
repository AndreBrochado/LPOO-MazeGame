package maze.logic;

/**
 *
 * Represents the MazeGame
 *
 * @author Andre Reis
 * @author Vasco Ribeiro
 *
 * @see Board
 *
 */
public class MazeGame {

    public static final char UP = 'w', LEFT = 'a', RIGHT = 'd', DOWN = 's';
    public static final int GAME_UNDERWAY = 0,  GAME_LOST = 1, GAME_WON = 2;

    private Board gameBoard;
    private int gameMode;

    /**
     *
     * Constructor and initializes the gameBoard and gameMode specified
     *
     * @param gameBoard
     *              Board of the game
     *
     * @param gameMode
     *              Start game mode
     *
     */

    public MazeGame(Board gameBoard, int gameMode){
        this.gameBoard = gameBoard;
        this.gameMode = gameMode;
    }

    /**
     *
     * Constructor and initializes the gameBoard with a board size and number of dragons specified and initializes gameMode specified
     *
     * @param boardSize
     *              Size of Board
     *
     * @param noDragons
     *              Number of dragons
     *
     * @param gameMode
     *              Start game mode
     *
     */

    public MazeGame(int boardSize, int noDragons, int gameMode) {
        this.gameBoard = new Board(boardSize, noDragons);
        this.gameMode = gameMode;
    }

    /**
     *
     * Do a play, user move hero and dragons move and change states automatically
     *
     * @param heroMovement
     *              char that specified the heroMovement intended
     *
     * @return Board state after the play
     *
     */

    public int makePlay(char heroMovement){
        gameBoard.moveHero(heroMovement);
        if (gameMode == 2)
            gameBoard.handleAllDragonsSleep();
        if (gameMode != 0)
            gameBoard.moveAllDragons();
        gameBoard.updateBoard();
        return gameBoard.getBoardState();
    }

    /**
     *
     * Return Game Board.
     *
     * @return the board
     *
     */

    public Board getGameBoard() {
        return gameBoard;
    }

	public void setGameBoard(Board gameBoard) {
		this.gameBoard = gameBoard;
	}

	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}

    public int getGameMode() {
        return gameMode;
    }
}
