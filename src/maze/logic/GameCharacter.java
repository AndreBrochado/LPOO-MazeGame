package maze.logic;

/**
 * Created by Andre on 27/02/2016.
 */

public class GameCharacter extends GameObject {

    final static int DEAD = 1, ARMED = 2, ASLEEP = 3;

    public GameCharacter(int x, int y, char[] representations) {
        super(x, y, representations, false, false);
    }

    public void move(int deltax, int deltay) {
        this.position.incrementX(deltax);
        this.position.incrementY(deltay);
    }

}
