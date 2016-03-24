package maze.test;

import maze.cli.MazeGame;
import maze.logic.Board;
import maze.logic.GameCharacter;
import maze.logic.GameObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vasco Ribeiro on 02/03/2016.
 */
public class FirstGameModeTests {

    //GameObject empty, wall, sword, exit;
    //GameCharacter hero, dragon;

   /* public JUnit4(){

        empty = new GameObject();

        char[] wallReps = {'X', ' '};
        wall = new GameObject(-1, -1, wallReps, true, false);

        char[] swordReps = {'E', ' '};
        sword = new GameObject(0, 0, swordReps, false, true);

        char[] heroReps = {'H', ' ', 'A'};
        hero = new GameCharacter(0, 0, heroReps);

        char[] dragonReps = {'D', ' ', 'F', 'd'};
        dragon = new GameCharacter(0, 0, dragonReps);

        char[] exitReps = {'S', ' '};
        exit = new GameObject(0, 0, exitReps);
    }*/
    GameObject empty = new GameObject();
    GameObject wall = new GameObject(-1,-1, new char[]{'X', ' '}, true, false);
    GameObject exit = new GameObject(3, 4, new char[] {'S', ' '});
    GameObject sword = new GameObject(3, 2, new char[]{'E', ' '}, false, true);
    GameCharacter hero = new GameCharacter(3, 3, new char[]{'H', ' ', 'A'});
    GameCharacter dragon = new GameCharacter(1, 2, new char[]{'D', ' ', 'F', 'd'});

    GameObject[][] testMaze = {
            {wall, wall, wall, wall, wall},
            {wall, empty, empty, empty, wall},
            {wall, dragon, empty, sword, wall},
            {wall, empty, empty, hero, wall},
            {wall, wall, wall, exit, wall}
    };

    private Board prepareTestBoard(){
        Board b = new Board(testMaze);
        b.setObjects(new GameObject[]{empty, wall, sword, exit});
        b.setCharacters(new GameCharacter[]{hero, dragon});
        return b;
    }

    @Test
    public void testMoveHeroToFreeCell() {
        Board b = prepareTestBoard();

        assertEquals(3, hero.getX());
        assertEquals(3, hero.getY());

        b.moveHero('A');
        assertEquals(2, hero.getX());
        assertEquals(3, hero.getY());
    }

    @Test
    public void testMoveHeroToWall() {
        Board b = prepareTestBoard();

        assertEquals(3, hero.getX());
        assertEquals(3, hero.getY());

        b.moveHero('D');
        assertEquals(3, hero.getX());
        assertEquals(3, hero.getY());
    }

    @Test
    public void testHeroEquipSword() {
        Board b = prepareTestBoard();

        b.moveHero('W');

        assertEquals(2, hero.getState()); //state 2 equals to hero armed
    }


    @Test
    public void testHeroDeath() {
        Board b = prepareTestBoard();

        b.moveHero('A');
        assertEquals(1, hero.getState()); //state 1 equals to hero dead
    }


    @Test
    public void testArmedHeroKillDragon() {
        Board b = prepareTestBoard();

        hero.setState(2); //Hero get armed

        b.moveHero('A');
        assertEquals(1, dragon.getState()); //state 1 equals to dragon dead
    }

    @Test
    public void testHeroWin() {
        Board b = prepareTestBoard();

        dragon.setState(1); //dragon is dead

        b.moveHero('S'); //hero moves to exit

        assertEquals(2, b.getBoardState()); //state 2 equals to win
    }

    @Test
    public void testHeroTryWinWithoutKilling() {
        Board b = prepareTestBoard();

        b.moveHero('S');

        //TODO add assert equals hero same position (didnt move to exit)
        assertEquals(0, b.getBoardState()); //state 0 equals to normal state board
    }


    @Test
    public void testHeroTryWinWithSword() {
        Board b = prepareTestBoard();

        hero.setState(2); //hero armed

        b.moveHero('S');

        //TODO add assert equals hero same position (didnt move to exit)
        assertEquals(0, b.getBoardState()); //state 0 equals to normal state board
    }
}
