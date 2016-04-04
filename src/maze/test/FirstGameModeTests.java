package maze.test;

import maze.logic.Board;

import maze.logic.GameCharacter;
import maze.logic.MazeGame;
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

        b.moveHero(MazeGame.LEFT);
        assertEquals(2, hero.getX());
        assertEquals(3, hero.getY());
    }

    @Test
    public void testMoveHeroToWall() {
        Board b = prepareTestBoard();

        assertEquals(3, hero.getX());
        assertEquals(3, hero.getY());

        b.moveHero(MazeGame.RIGHT);
        assertEquals(3, hero.getX());
        assertEquals(3, hero.getY());
    }

    @Test
    public void testHeroEquipSword() {
        Board b = prepareTestBoard();

        b.moveHero(MazeGame.UP);

        assertEquals(GameCharacter.ARMED, hero.getState());
    }


    @Test
    public void testHeroDeath() {
        Board b = prepareTestBoard();

        b.moveHero(MazeGame.LEFT);
        assertEquals(GameCharacter.DEAD, hero.getState());
    }


    @Test
    public void testArmedHeroKillDragon() {
        Board b = prepareTestBoard();

        hero.setState(GameCharacter.ARMED);

        b.moveHero(MazeGame.LEFT);
        assertEquals(GameCharacter.DEAD, dragon.getState());
    }

    @Test
    public void testHeroWin() {
        Board b = prepareTestBoard();

        dragon.setState(GameCharacter.DEAD); //dragon is dead

        b.moveHero(MazeGame.DOWN); //hero moves to exit

        assertEquals(MazeGame.GAME_WON, b.getBoardState());
    }

    @Test
    public void testHeroTryWinWithoutKilling() {
        Board b = prepareTestBoard();

        b.moveHero(MazeGame.DOWN);

        //TODO add assert equals hero same position (didnt move to exit)
        assertEquals(0, b.getBoardState()); //state 0 equals to normal state board
    }


    @Test
    public void testHeroTryWinWithSword() {
        Board b = prepareTestBoard();

        hero.setState(GameCharacter.ARMED); //hero armed

        b.moveHero(MazeGame.DOWN);

        //TODO add assert equals hero same position (didnt move to exit)
        assertEquals(0, b.getBoardState()); //state 0 equals to normal state board
    }
}
