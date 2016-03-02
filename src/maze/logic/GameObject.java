package maze.logic;

/**
 * Created by Andre on 27/02/2016.
 */
public class GameObject {

    final static int VISIBLE = 0, INVISIBLE = 1, ASLEEP = 2;

    protected int x, y;
    protected int state = 0;
    protected boolean impassable = false, equipable = false;
    protected char[] representations;

    public GameObject(){
        this.x = -1;
        this.y = -1;
        char[] initializer = {' ', ' '};
        this.representations = new char[initializer.length];
        System.arraycopy(initializer, 0, this.representations, 0, initializer.length);
    }

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

    public char getRepresentation(){
        return representations[state];
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getState() {
        return state;
    }
}
