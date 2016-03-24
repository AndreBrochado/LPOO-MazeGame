package maze.test;

import maze.cli.MazeGame;
import maze.logic.Board;
import maze.logic.GameCharacter;
import maze.logic.GameObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Andre on 05/03/2016.
 */
public class RandomBehaviourTests {

    /*@Test(timeout = 2500)
    public void testRandomMovement() {
        MazeGame game = new MazeGame();
        Board b = game.prepareGameBoard();
        b.updateBoard(false);
        GameCharacter dragon = b.getCharacters()[1];
        dragon.setX(5);
        dragon.setY(5);

        boolean xMovement = false, yMovement = false;

        int initialX = dragon.getX(), initialY = dragon.getY();

        while (xMovement == false || yMovement == false) {
            b.moveDragon(dragon);
            if (dragon.getX() != initialX)
                xMovement = true;
            else if (dragon.getY() != initialY)
                yMovement = true;
        }

    }*/
}
