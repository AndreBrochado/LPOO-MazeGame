package maze.logic;

/**
 *
 * Represents the GameCharacter such as Dragon and Hero.
 * It's a derived class of GameObject
 *
 * @author Andre Reis
 * @author Vasco Ribeiro
 *
 */

public class GameCharacter extends GameObject {

    public final static int DEAD = 1, ARMED = 2, ASLEEP = 3;
    private char lastMovement = MazeGame.UP;

    /**
     *
     * Constructs and initializes the position specified, the representations specified and states as false by default.
     *
     * @param x
     *          Original X coord.
     *
     * @param y
     *          Original Y coord.
     *
     * @param representations
     *          Chars representations of the Character.
     *
     */

    public GameCharacter(int x, int y, char[] representations) {
        super(x, y, representations, false, false);
    }

    /**
     *
     * Move the Character in a specified direction.
     *
     * @param deltax
     *          Integer to increment
     *
     * @param deltay
     *          Integer to increment
     *
     */

    public void move(int deltax, int deltay) {
        this.position.incrementX(deltax);
        this.position.incrementY(deltay);
    }

    /**
     * Returns the Char that represents the last movement
     * @return last movement
     */
    public char getLastMovement() {
        return lastMovement;
    }

    /**
     * Sets the last movement char to a given direction
     * @param lastMovement
     *                      last movement direction
     */
    public void setLastMovement(char lastMovement) {
        this.lastMovement = lastMovement;
    }
}
