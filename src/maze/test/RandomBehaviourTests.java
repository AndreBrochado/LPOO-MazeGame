package maze.test;

import maze.logic.Board;

import org.junit.Test;


public class RandomBehaviourTests extends TestEnvironment {

    @Test(timeout = 2500)
    public void testRandomMovement() {
        Board b = prepareTestBoard();

        boolean xMovement = false, yMovement = false;

        int initialX = dragon.getX(), initialY = dragon.getY();

        while (xMovement == false || yMovement == false) {
            b.moveDragon(dragon);
            if (dragon.getX() != initialX)
                xMovement = true;
            else if (dragon.getY() != initialY)
                yMovement = true;
        }

    }

    @Test(timeout = 1000)
    public void testFallsAsleep() {
        Board b = prepareTestBoard();

        boolean fellAsleep = false;

        while (fellAsleep == false) {
            b.handleAllDragonsSleep();
            if (dragon.getState() == 3)
                fellAsleep = true;
        }
    }

    @Test(timeout = 1000)
    public void testAwakesFromSleep() {
        Board b = prepareTestBoard();

        boolean wokeUp = false;
        dragon.setState(3);

        while (wokeUp == false) {
            b.handleAllDragonsSleep();
            if (dragon.getState() != 3)
                wokeUp = true;
        }
    }

}
