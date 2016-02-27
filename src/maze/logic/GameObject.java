package maze.logic;

/**
 * Created by Andre on 27/02/2016.
 */
public class GameObject {

    final int VISIBLE = 0, INVISIBLE = 1;

    private int x, y;
    private int state = 0;
    private boolean impassable = false, equipable = false;
    private char[] representations;

    public GameObject(int x, int y, char[] representations){
        this.x = x;
        this.y = y;
        this.representations = new char[representations.length];
        System.arraycopy(representations, 0, this.representations, 0, representations.length);
    }

    public GameObject(int x, int y, char[] representations, boolean impassable, boolean equipable){
        this(x, y, representations);
        this.impassable = impassable;
        this.equipable = equipable;
    }
}
