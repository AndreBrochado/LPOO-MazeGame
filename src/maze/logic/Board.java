package maze.logic;

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

    //base constructor
    public Board(int size) {
        this.size = size;

        MazeBuilder builder = new MazeBuilder();
        this.board = builder.buildMaze(size);
        this.objects = builder.getGameObjects();
        this.characters = builder.getGameCharacters();
    }

    //update the board with the position of all GameObjects
    public void updateBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(!Arrays.asList(this.objects).contains(board[j][i]))
                    board[j][i] = getEmpty();
            }
        }
        for (GameCharacter c : characters){
            if(c.state != GameCharacter.DEAD)
                board[c.getY()][c.getX()] = c;
        }
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
        if (board[character.getY() + deltay][character.getX() + deltax].impassable == false && !(board[character.getY()+deltay][character.getX()+deltax].equals(getExit()) && !allDragonsDead())) {
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

    private boolean allDragonsDead(){
        for (int i = 1; i < characters.length; i++) {
            if (characters[i].state != GameCharacter.DEAD)
                return false;
        }
        return true;
    }

    public int getBoardState() {
        if (getHero().state == GameCharacter.DEAD)
            return 1;

        GameObject exit = getExit();
        //TODO:replace getx and gety by position.equals
        if (allDragonsDead() && getHero().getX() == exit.getX() && getHero().getY() == exit.getY())
            return 2;

        return 0;
    }

    public GameCharacter[] getCharacters() {
        return characters;
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

    public GameCharacter getHero() {
        return this.characters[0];
    }
}


