package maze.logic;

/**
 * Created by Andre on 27/02/2016.
 */
public class GameCharacter extends GameObject {

    final int ARMED = 2;

    public GameCharacter(int x, int y, char[] representations){
        super(x, y, representations, true, false);
    }

    public void move(int deltax, int deltay){
        this.x += deltax;
        this.y += deltay;
    }

}
