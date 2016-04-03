package maze.logic;

/**
 * Created by Andre on 27/02/2016.
 */

public class GameCharacter extends GameObject {

    public final static int DEAD = 1, ARMED = 2, ASLEEP = 3;
    private char lastMovement = MazeGame.UP;

    public GameCharacter(int x, int y, char[] representations) {
        super(x, y, representations, false, false);
    }

    public void move(int deltax, int deltay) {
        this.position.incrementX(deltax);
        this.position.incrementY(deltay);
    }

    public char getLastMovement() {
        return lastMovement;
    }

    public void setLastMovement(char lastMovement) {
        this.lastMovement = lastMovement;
    }
}
