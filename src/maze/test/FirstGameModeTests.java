package maze.test;

import maze.logic.Board;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vasco Ribeiro on 02/03/2016.
 */
public class FirstGameModeTests extends TestEnvironment {

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
