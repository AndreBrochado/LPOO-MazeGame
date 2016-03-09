package maze.logic;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Andre on 27/02/2016.
 */
public class Board {

    private int height, width;
    private GameObject[][] board;
    private GameObject[] objects;
    private GameCharacter[] characters;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.board = new GameObject[height][width];
    }

    public Board(int height, int width, GameObject[] objects, GameCharacter[] characters) {
        this(height, width);
        this.objects = new GameObject[objects.length];
        System.arraycopy(objects, 0, this.objects, 0, objects.length);
        this.characters = new GameCharacter[characters.length];
        System.arraycopy(characters, 0, this.characters, 0, characters.length);
    }

    /*public void addObject(GameObject object){
        this.objects = Arrays.copyOf(this.objects, this.objects.length+1);
        this.objects[this.objects.length-1] = object;
    }*/

    public void updateBoard(boolean hasWalls) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((i == 0 || j == 0 || i == height - 1 || j == width - 1) && hasWalls)
                    board[i][j] = objects[1];
                else if (((j == 7 && i > 1 && i < 8) || (j == 5 && i > 1 && i < 8 && i != 5) || ((j == 2 || j == 3) && i > 1 && i != 5)) && hasWalls)
                    board[i][j] = objects[1];
                else
                    board[i][j] = objects[0];
            }
        }
        for (int i = 2; i < objects.length; i++)
            board[objects[i].getY()][objects[i].getX()] = objects[i];
        for (GameCharacter character : characters) {
            if (character.state != GameCharacter.INVISIBLE)
                board[character.getY()][character.getX()] = character;
        }
    }

    public void updateBoard() {
        this.updateBoard(true);
    }

    public void print() {
        for (GameObject[] line : board) {
            for (GameObject obj : line) {
                System.out.print(obj.getRepresentation());
                System.out.print(' ');
            }
            System.out.print('\n');
        }
    }

    private void checkCombat() {
        GameCharacter hero = characters[0];
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 || dy != 0) {
                    try {
                        if (board[hero.getY() + dy][hero.getX() + dx].representations[0] == 'D') {
                            GameObject dragon = board[hero.getY() + dy][hero.getX() + dx];
                            if (hero.state == GameCharacter.ARMED)
                                dragon.state = GameCharacter.DEAD;
                            else if (dragon.getRepresentation() == GameCharacter.ASLEEP)
                                dragon.state = GameCharacter.DEAD;
                            else
                                hero.state = GameCharacter.DEAD;
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                }
            }
        }
    }

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


