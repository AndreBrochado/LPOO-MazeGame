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

    /*public JUnit4(){

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

    @Test
    public void testMoveHeroToFreeCell() {
        MazeGame game = new MazeGame();
        Board b = game.prepareGameBoard();
        b.updateBoard();
        GameCharacter hero = b.getCharacters()[0];

        assertEquals(1, hero.getX());
        assertEquals(1, hero.getY());

        b.moveHero('D');
        assertEquals(2, hero.getX());
        assertEquals(1, hero.getY());
    }

    @Test
    public void testMoveHeroToWall() {
        MazeGame game = new MazeGame();
        Board b = game.prepareGameBoard();
        b.updateBoard();

        GameCharacter hero = b.getCharacters()[0];

        assertEquals(1, hero.getX());
        assertEquals(1, hero.getY());

        b.moveHero('A');
        assertEquals(1, hero.getX());
        assertEquals(1, hero.getY());
    }

    @Test
    public void testHeroEquipSword() {
        MazeGame game = new MazeGame();
        Board b = game.prepareGameBoard();
        GameCharacter hero = b.getCharacters()[0];
        hero.setY(7);
        b.updateBoard();

        b.moveHero('S');
        assertEquals(2, hero.getState());
    }

    @Test
    public void testHeroDeath() {
        MazeGame game = new MazeGame();
        Board b = game.prepareGameBoard();
        GameCharacter hero = b.getCharacters()[0];
        b.updateBoard();

        b.moveHero('S');
        assertEquals(1, hero.getState());
    }

    @Test
    public void testArmedHeroKillDragon() {
        MazeGame game = new MazeGame();
        Board b = game.prepareGameBoard();
        GameCharacter hero = b.getCharacters()[0];
        GameCharacter dragon = b.getCharacters()[1];
        hero.setState(2);
        b.updateBoard();

        b.moveHero('S');
        assertEquals(1, dragon.getState());
    }

    @Test
    public void testHeroWin() {
        MazeGame game = new MazeGame();
        Board b = game.prepareGameBoard();
        GameCharacter hero = b.getCharacters()[0];
        GameCharacter dragon = b.getCharacters()[1];
        dragon.setState(1);
        hero.setX(8);
        hero.setY(5);

        b.updateBoard();

        b.moveHero('D');
        assertEquals(2, b.getBoardState());
    }

    @Test
    public void testHeroTryWinWithoutSword() {
        MazeGame game = new MazeGame();
        Board b = game.prepareGameBoard();
        GameCharacter hero = b.getCharacters()[0];
        hero.setX(8);
        hero.setY(5);
        b.updateBoard();
        b.moveHero('D');
        assertEquals(0, b.getBoardState());
    }

    @Test
    public void testHeroTryWinWithSword() {
        MazeGame game = new MazeGame();
        Board b = game.prepareGameBoard();
        GameCharacter hero = b.getCharacters()[0];
        hero.setX(8);
        hero.setY(5);
        hero.setState(2);
        b.updateBoard();
        b.moveHero('D');
        assertEquals(0, b.getBoardState());
    }
}
