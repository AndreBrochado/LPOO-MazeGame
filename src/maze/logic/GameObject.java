package maze.logic;

/**
 * Created by Andre on 27/02/2016.
 */
public class GameObject {

    final static int VISIBLE = 0, INVISIBLE = 1, ASLEEP = 2;

    protected Coordinate position;
    protected int state = 0;
    protected boolean impassable = false, equipable = false;
    protected char[] representations;

    public GameObject() {
        this.position = new Coordinate();
        char[] initializer = {' ', ' '};
        this.representations = new char[initializer.length];
        System.arraycopy(initializer, 0, this.representations, 0, initializer.length);
    }

    public GameObject(int x, int y, char[] representations) {
        this.position = new Coordinate(x, y);
        this.representations = new char[representations.length];
        System.arraycopy(representations, 0, this.representations, 0, representations.length);
    }

    public GameObject(int x, int y, char[] representations, boolean impassable, boolean equipable) {
        this(x, y, representations);
        this.impassable = impassable;
        this.equipable = equipable;
    }

    public char getRepresentation() {
        return representations[state];
    }

    public int getY() {
        return position.getY();
    }

    public int getX() {
        return position.getX();
    }

    public void setX(int x) {
        this.position.setX(x);
    }

    public void setY(int y) {
        this.position.setY(y);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
