package maze.logic;

/**
 * Created by Andre on 08/03/2016.
 */

public interface IMazeBuilder {
    public GameObject[][] buildMaze(int size) throws IllegalArgumentException;
}