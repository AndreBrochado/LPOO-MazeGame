package maze.logic;

import maze.cli.GameLauncher;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Andre on 27/02/2016.
 */
public class Board {

    private int size;
    private GameObject[][] board;
    private GameObject[] objects;
    private GameCharacter[] characters;

    //test mode constructor
    public Board(GameObject[][] board) {
        this.board = board;
    }

    public Board(int size){
        this(size, 1);
    }

    public Board(int size, int noDragons) {
        this.size = size;

        MazeBuilder builder = new MazeBuilder();
        this.board = builder.buildMaze(size, noDragons);
        this.objects = builder.getGameObjects();
        this.characters = builder.getGameCharacters();
    }

    //update the board with the position of all GameObjects
    public void updateBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!Arrays.asList(this.objects).contains(board[j][i]))
                    board[j][i] = getEmpty();
            }
        }
        for(GameObject o : objects){
            if(o.getX() != -1 && o.getY() != -1)
                if(o.getState() != GameObject.INVISIBLE)
                    board[o.getY()][o.getX()] = o;
        }
        for (GameCharacter c : characters) {
            if (c.state != GameCharacter.DEAD)
                board[c.getY()][c.getX()] = c;
        }
    }

    //print on the screen the board
    public String toString() {
        String s = "";
        for (GameObject[] line : board) {
            for (GameObject obj : line) {
                s = s + obj.getRepresentation() + ' ';
            }
            s = s + '\n';
        }
        return s;
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
        if (board[character.getY() + deltay][character.getX() + deltax].impassable == false && !(board[character.getY() + deltay][character.getX() + deltax].equals(getExit()) && !allDragonsDead())) {
            if (character.state == GameCharacter.ARMED && character.representations[0] == 'D') {
                character.state = GameCharacter.VISIBLE;
                getSword().state = GameObject.VISIBLE;
            }
            if (board[character.getY() + deltay][character.getX() + deltax].equipable == true && board[character.getY() + deltay][character.getX() + deltax].state == GameObject.VISIBLE) {
                character.state = GameCharacter.ARMED;
                board[character.getY() + deltay][character.getX() + deltax].state = GameObject.INVISIBLE;
            }
            character.move(deltax, deltay);
        }
        checkCombat();
    }

    /**
     * Moves the user 1 position to the specified direction by the user.
     * Moves the character only if the movement is valid and takes care of any actions caused by the movement
     *
     * @param userInput
     *              Direction char that user inputed
     *
     * @see {@link private void moveActions(GameCharacter character, int deltax, int deltay)}
     */
    public void moveHero(char userInput) {
        int deltax = 0, deltay = 0;
        switch (userInput) {
            case MazeGame.UP:
                deltay = -1;
                break;
            case MazeGame.DOWN:
                deltay = 1;
                break;
            case MazeGame.LEFT:
                deltax = -1;
                break;
            case MazeGame.RIGHT:
                deltax = 1;
                break;
            default:
                return;
        }
        moveActions(getHero(), deltax, deltay);
    }

    //this funciont move the dragon on a random direction
    public void moveDragon(GameCharacter dragon) {
        if (dragon.state == GameCharacter.VISIBLE || dragon.state == GameCharacter.ARMED) {
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

    private boolean allDragonsDead() {
        for (int i = 1; i < characters.length; i++) {
            if (characters[i].state != GameCharacter.DEAD)
                return false;
        }
        return true;
    }

    public int getBoardState() {
        if (getHero().state == GameCharacter.DEAD)
            return MazeGame.GAME_LOST;

        if (allDragonsDead() && getHero().getPosition().equals(getExit().getPosition()))
            return MazeGame.GAME_WON;

        return MazeGame.GAME_UNDERWAY;
    }

    public void setBoard(GameObject[][] board) {
        this.board = board;
    }

    public GameObject getEmpty() {
        return this.objects[0];
    }

    public GameObject getWall() {
        return this.objects[1];
    }

    public GameObject getSword() {
        return this.objects[2];
    }

    private GameObject getExit() {
        return this.objects[3];
    }

    public GameCharacter getHero() {
        return this.characters[0];
    }

    public void setObjects(GameObject[] objects) {
        this.objects = objects;
    }

    public void setCharacters(GameCharacter[] characters) {
        this.characters = characters;
    }
}


