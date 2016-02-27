package maze.logic;

/**
 * Created by Andre on 27/02/2016.
 */
public class Board {

    private int height, width;
    private GameObject[][] board;
    private GameObject[] objects;
    private GameCharacter[] characters;

    public Board(int height, int width){
        this.height = height;
        this.width = width;
        this.board = new GameObject[height][width];
    }

    public Board(int height, int width, GameObject[] objects, GameCharacter[] characters){
        this(height, width);
        this.objects = new GameObject[objects.length];
        System.arraycopy(objects, 0, this.objects, 0, objects.length);
        this.characters = new GameCharacter[characters.length];
        System.arraycopy(characters, 0, this.characters, 0, characters.length);
    }
}
