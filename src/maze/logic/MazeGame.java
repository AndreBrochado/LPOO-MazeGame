package maze.logic;

/**
 * Created by Andre on 02/04/2016.
 */
public class MazeGame {

    public static final char UP = 'w', LEFT = 'a', RIGHT = 'd', DOWN = 's';
    public static final int GAME_UNDERWAY = 0,  GAME_LOST = 1, GAME_WON = 2;

    private Board gameBoard;
    private int gameMode;

    public MazeGame(Board gameBoard, int gameMode){
        this.gameBoard = gameBoard;
        this.gameMode = gameMode;
    }

    public MazeGame(int boardSize, int noDragons, int gameMode) {
        this.gameBoard = new Board(boardSize, noDragons);
        this.gameMode = gameMode;
    }

    public int makePlay(char heroMovement){
        gameBoard.moveHero(heroMovement);
        if (gameMode == 2)
            gameBoard.handleAllDragonsSleep();
        if (gameMode != 0)
            gameBoard.moveAllDragons();
        gameBoard.updateBoard();
        return gameBoard.getBoardState();
    }

    public Board getGameBoard() {
        return gameBoard;
    }
}
