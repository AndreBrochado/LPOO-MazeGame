package maze.logic;

import maze.cli.GameLauncher;

import java.util.Arrays;
import java.util.Random;


/**
 *
 * Represents a Game.
 *
 * @author Andre Reis
 * @author Vasco Ribeiro
 *
 * @see GameObject
 * @see GameCharacter
 * @see Coordinate
 * @see GameObject
 * @see MazeBuilder
 *
 */

public class Board {

    private int size;
    private GameObject[][] board;
    private GameObject[] objects;
    private GameCharacter[] characters;

    /**
     *
     * Class constructor specifying the board;
     *
     * @param board
     *              2D array of GameObjects
     *
     */
    public Board(GameObject[][] board) {
        this.board = board;
    }

    /**
     *
     * Class constructor specifying the size of the board;
     *
     * @param size
     *              Size of the board
     *
     */

    public Board(int size){
        this(size, 1);
    }

    /**
     *
     * Class constructor specifying the board size and the number of Dragons;
     * Generate a new Maze with a specify size and number of Dragons;
     *
     * @param size
     *              Size of the board
     * @param noDragons
     *              Number of Dragons
     *
     */

    public Board(int size, int noDragons) {
        this.size = size;

        MazeBuilder builder = new MazeBuilder();
        this.board = builder.buildMaze(size, noDragons);
        this.objects = builder.getGameObjects();
        this.characters = builder.getGameCharacters();
    }

    /**
     *
     * Updates the status array 2d board, after they had changed the positions of its content
     *
     */


    public void updateBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!Arrays.asList(this.objects).contains(board[j][i]))
                    board[j][i] = getEmpty();
            }
        }
        for (GameCharacter c : characters) {
            if (c.state != GameCharacter.DEAD)
                board[c.getY()][c.getX()] = c;
        }
    }

    /**
     *
     *Converts the board to a String, using the representation of the Gameobjects;
     *
     * @return the String
     *
     */

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

    /**
     *
     *Check if Hero is next to any Dragon and take account of combat consequences;
     *
     */
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

    /**
     *
     * Moves the character only if the movement is valid and update his states.
     *
     * @param character
     *              Game Character that we are going to move
     * @param deltax
     *              Movement horizontal, -1 for left or 1 for right
     * @param deltay
     *              Movement vertical, -1 for top or 1 for low
     *
     */

    private void moveActions(GameCharacter character, int deltax, int deltay) {
        if (board[character.getY() + deltay][character.getX() + deltax].impassable == false && !(board[character.getY() + deltay][character.getX() + deltax].equals(getExit()) && !allDragonsDead())) {
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

    /**
     *
     * Moves the user 1 position to the specified direction by the user.
     * Moves the character only if the movement is valid and takes care of any actions caused by the movement.
     *
     * @param userInput
     *              Direction char that user inputed
     *
     * @see {@link private void moveActions(GameCharacter character, int deltax, int deltay)}
     *
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

    /**
     *
     * Moves the Dragon in a random way
     *
     */
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

    /**
     *
     * Moves all dragons in a random way
     *
     */

    public void moveAllDragons() {
        for (int i = 1; i < characters.length; i++)
            moveDragon(characters[i]);
    }

    /**
     *
     * Change the state of the dragon in a random way.
     * Dragon falls asleep in a probability of 1/5, and dragon wake up in a probability of 1/3.
     *
     *  @param dragon
     *              Dragon that we are going to update
     *
     */

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

    /**
     *
     * Change the state of all dragons in a random way.
     *
     * @see {@link private void dragonSleepHandler(GameCharacter dragon)}
     *
     */

    public void handleAllDragonsSleep() {
        for (int i = 1; i < characters.length; i++)
            dragonSleepHandler(characters[i]);
    }

    /**
     *
     * Return the board.
     *
     * @return the board
     *
     */

    public GameObject[][] getBoard() {
        return board;
    }

    /**
     *
     * Check the states of all dragons.
     *
     * @return true if all dragons are dead, otherwise returns false
     *
     */

    private boolean allDragonsDead() {
        for (int i = 1; i < characters.length; i++) {
            if (characters[i].state != GameCharacter.DEAD)
                return false;
        }
        return true;
    }

    /**
     *
     * Return the board state.
     *
     * @return the int for the state of the Board
     *
     */

    public int getBoardState() {
        if (getHero().state == GameCharacter.DEAD)
            return MazeGame.GAME_LOST;

        if (allDragonsDead() && getHero().getPosition().equals(getExit().getPosition()))
            return MazeGame.GAME_WON;

        return MazeGame.GAME_UNDERWAY;
    }

    /**
     *
     * Set a new Board.
     *
     *  @param board
     *              The new board
     *
     */

    public void setBoard(GameObject[][] board) {
        this.board = board;
    }

    /**
     *
     * Return the empty spaces of the board.
     *
     * @return empty spaces
     *
     */

    public GameObject getEmpty() {
        return this.objects[0];
    }

    /**
     *
     * Return the walls of the board.
     *
     * @return walls
     *
     */

    public GameObject getWall() {
        return this.objects[1];
    }

    /**
     *
     * Return the exit of the board.
     *
     * @return exit
     *
     */

    private GameObject getExit() {
        return this.objects[3];
    }

    /**
     *
     * Return the hero of the board.
     *
     * @return hero
     *
     */

    public GameCharacter getHero() {
        return this.characters[0];
    }

    /**
     *
     * Set all objects to the board objects.
     *
     */

    public void setObjects(GameObject[] objects) {
        this.objects = objects;
    }

    /**
     *
     * Set all Characters to the board characters.
     *
     */

    public void setCharacters(GameCharacter[] characters) {
        this.characters = characters;
    }
}


