package maze.logic;

import java.util.Arrays;

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
        updateBoard();
    }

    public void addObject(GameObject object){
        this.objects = Arrays.copyOf(this.objects, this.objects.length+1);
        this.objects[this.objects.length-1] = object;
    }

    public void updateBoard(){
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if(i == 0 || j == 0 || i == height - 1 || j == width - 1)
                    board[i][j] = objects[1];
                else if((j == 7 && i > 1 && i < 8) || (j == 5 && i > 1 && i < 8 && i != 5) || ((j == 2 || j == 3) && i > 1 && i != 5))
                    board[i][j] = objects[1];
                else
                    board[i][j] = objects[0];
            }
        }
        for(int i = 2; i < objects.length; i++)
            board[objects[i].y][objects[i].x] = objects[i];
        for (GameCharacter character : characters) board[character.y][character.x] = character;
    }

    public void printBoard(){
        for(GameObject[] line:board){
            for(GameObject obj:line) {
                System.out.print(obj.getRepresentation());
                System.out.print(' ');
            }
            System.out.print('\n');
        }
    }

    private void checkCombat(){
        GameCharacter hero = characters[0];
        for(int dx = -1; dx <= 1; dx++){
            for(int dy = -1; dy <= 1; dy++){
                if(dx != 0 || dy != 0){
                    if(board[hero.y+dy][hero.x+dx].representations[0] == 'D'){
                        if(hero.state == GameCharacter.ARMED)
                            board[hero.y+dy][hero.x+dx].state = GameCharacter.DEAD;
                        else
                            hero.state = GameCharacter.DEAD;
                    }
                }
            }
        }
    }

    private void moveActions(GameCharacter character, int deltax, int deltay){
        if(board[character.y+deltay][character.x+deltax].impassable == false){
            if(character.state == GameCharacter.ARMED && character.representations[0] == 'D') {
                character.state = GameCharacter.VISIBLE;
                board[character.y+deltay][character.x+deltax].state = GameObject.VISIBLE;
            }
            if(board[character.y+deltay][character.x+deltax].equipable == true && board[character.y+deltay][character.x+deltax].state == GameObject.VISIBLE) {
                character.state = GameCharacter.ARMED;
                board[character.y+deltay][character.x+deltax].state = GameObject.INVISIBLE;
            }
            character.move(deltax, deltay);
        }
        checkCombat();
    }

    public void moveHero(char userInput){
        int deltax=0, deltay=0;
        switch(userInput){
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

    private GameObject getExit(){
        for(GameObject obj:objects)
            if(obj.representations[0] == 'S') {
                return obj;
            }
        return null;
    }

    public int getBoardState(){
        if(characters[0].state == GameCharacter.DEAD)
            return 1;

        boolean dragonsDead=true;
        for(int i = 1; i < characters.length; i++){
            if(characters[1].state != GameCharacter.DEAD)
                dragonsDead = false;
        }

        GameObject exit = getExit();
        if(dragonsDead && characters[0].x == exit.x && characters[0].y == exit.y)
            return 2;

        return 0;
    }

    //FOR TESTING PURPOSES
    public static void main(String[] args){
        GameObject empty = new GameObject();
        char[] wallReps = {'X', ' '};
        GameObject wall = new GameObject(-1, -1, wallReps, true, false);
        char[] swordReps = {'E', ' '};
        GameObject sword = new GameObject(1, 8, swordReps, false, true);
        char[] heroReps = {'H', ' ', 'A'};
        GameCharacter hero = new GameCharacter(1, 1, heroReps);
        GameObject[] objects = {empty, wall, sword};
        GameCharacter[] characters = {hero};
        Board b = new Board(10, 10, objects, characters);
        b.printBoard();
    }

}
