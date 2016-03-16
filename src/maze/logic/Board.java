package maze.logic;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Andre on 27/02/2016.
 */
public class Board {

    public static final GameObject empty = new GameObject();
    public static final GameObject wall = new GameObject(-1, -1, new char[]{'X', ' '}, true, false);
    public static final GameObject sword = new GameObject(-1, -1, new char[]{'E', ' '}, false, true);
    public static final GameObject exit = new GameObject(-1, -1, new char[]{'S', ' '});
    public static final GameObject hero = new GameCharacter(-1, -1, new char[]{'H', ' ', 'A'});
    public static final GameObject dragon = new GameCharacter(-1, -1, new char[]{'D', ' ', 'F', 'd'});

    private int size;
    private GameObject[][] board;
    private GameObject[] objects;
    private GameCharacter[] characters;

    //base constructor
    public Board(int size) {
        this.size = size;
        MazeBuilder builder = new MazeBuilder();
        this.board = builder.buildMaze(size);
        this.objects = new GameObject[2];
        //objects[0] = empty;
        //objects[1] = wall;
        this.characters = new GameCharacter[0];
    }

    //complex constructor
    public Board(int size, int numDragons) {
        this(size);
        this.objects = new GameObject[objects.length];
        System.arraycopy(objects, 0, this.objects, 0, objects.length);
        this.characters = new GameCharacter[characters.length];
        System.arraycopy(characters, 0, this.characters, 0, characters.length);
    }

    /*public void addObject(GameObject object){
        this.objects = Arrays.copyOf(this.objects, this.objects.length+1);
        this.objects[this.objects.length-1] = object;
    }*/

    //update the board with the position of all GameObjects
    public void updateBoard(boolean hasWalls) {
        /*for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (
            }
        }
        for (int i = 2; i < objects.length; i++)
            board[objects[i].getY()][objects[i].getX()] = objects[i];
        for (GameCharacter character : characters) {
            if (character.state != GameCharacter.INVISIBLE)
                board[character.getY()][character.getX()] = character;
        }*/
    }

    public void updateBoard() {
        this.updateBoard(true);
    }

    //print on the screen the board
    public void print() {
        for (GameObject[] line : board) {
            for (GameObject obj : line) {
                System.out.print(obj.getRepresentation());
                System.out.print(' ');
            }
            System.out.print('\n');
        }
    }

    //check if Hero is next to any Dragon and take account of combat consequences
    private void checkCombat() {
        GameCharacter hero = characters[0];
        for (int i = 1; i < characters.length; i++) {
            //checks if a dragon is adjacent to hero
            if (Math.abs(hero.getX() - characters[i].getX()) <= 1 && Math.abs(hero.getY() - characters[i].getY()) <= 1) {
                if (hero.state == GameCharacter.ARMED)
                    characters[i].state = GameCharacter.DEAD;
                else if (characters[i].state != GameCharacter.ASLEEP)
                    hero.state = GameCharacter.DEAD;
            }
        }
    }

    //move a GameCharacter if possible on horizontal (-1 or 1 in deltax) or in vertical (-1 or 1 in deltay)
    private void moveActions(GameCharacter character, int deltax, int deltay) {
        if (board[character.getY() + deltay][character.getX() + deltax].impassable == false) {
            if (character.state == GameCharacter.ARMED && character.representations[0] == 'D') {
                character.state = GameCharacter.VISIBLE;
                board[character.getY() + deltay][character.getX() + deltax].state = GameObject.VISIBLE;
            }
            if (board[character.getY() + deltay][character.getX() + deltax].equipable == true && board[character.getY() + deltay][character.getX() + deltax].state == GameObject.VISIBLE) {
                character.state = GameCharacter.ARMED;
                board[character.getY() + deltay][character.getX() + deltax].state = GameObject.INVISIBLE;
            }
            character.move(deltax, deltay);
        }
        checkCombat();
    }

    //receive the userInput and moveHero in the corret direction depending on the input
    public void moveHero(char userInput) {
        int deltax = 0, deltay = 0;
        switch (userInput) {
            case 'W':
                deltay = -1;
                break;
            case 'S':
                deltay = 1;
                break;
            case 'A':
                deltax = -1;
                break;
            case 'D':
                deltax = 1;
                break;
            default:
                return;
        }
        moveActions(characters[0], deltax, deltay);
    }

    //this funciont move the dragon on a random direction
    public void moveDragon(GameCharacter dragon) {
        if (dragon.state == GameCharacter.VISIBLE) {
            Random random = new Random();
            int deltax = 0, deltay = 0;
            int movement = random.nextInt(5);
            switch (movement) {
                case 0:
                    return;
                case 1:
                    deltax = -1;
                    break;
                case 2:
                    deltax = 1;
                    break;
                case 3:
                    deltay = -1;
                    break;
                case 4:
                    deltay = 1;
                    break;
            }
            moveActions(dragon, deltax, deltay);
        }
    }

    public void moveAllDragons() {
        for (int i = 1; i < characters.length; i++)
            moveDragon(characters[i]);
    }

    //change the state of the dragon in a random way, dragon falls asleep in a probability of 1/5, and dragon wake up in a probability of 1/3
    private void dragonSleepHandler(GameCharacter dragon) {
        Random random = new Random();
        int action = random.nextInt(5);
        if (dragon.state == GameCharacter.VISIBLE) {
            if (action == 0)
                dragon.state = GameCharacter.ASLEEP;
        } else if (dragon.state == GameCharacter.ASLEEP) {
            action = random.nextInt(3);
            if (action == 0)
                dragon.state = GameCharacter.VISIBLE;
        }
    }

    public void handleAllDragonsSleep() {
        for (int i = 1; i < characters.length; i++)
            dragonSleepHandler(characters[i]);
    }

    public GameObject[][] getBoard() {
        return board;
    }

    private GameObject getExit() {
        for (GameObject obj : objects)
            if (obj.representations[0] == 'S') {
                return obj;
            }
        return null;
    }

    public int getBoardState() {
        if (characters[0].state == GameCharacter.DEAD)
            return 1;

        boolean dragonsDead = true;
        for (int i = 1; i < characters.length; i++) {
            if (characters[i].state != GameCharacter.DEAD)
                dragonsDead = false;
        }

        GameObject exit = getExit();
        if (dragonsDead && characters[0].getX() == exit.getX() && characters[0].getY() == exit.getY())
            return 2;

        return 0;
    }

    public GameCharacter[] getCharacters() {
        return characters;
    }

    public void setBoard(GameObject[][] board) {
        this.board = board;
    }
}


