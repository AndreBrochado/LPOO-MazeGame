package maze.logic;


public interface IMazeBuilder {
    public GameObject[][] buildMaze(int size) throws IllegalArgumentException;
}