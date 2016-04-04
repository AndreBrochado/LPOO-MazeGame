package maze.test;

import maze.logic.Board;
import maze.logic.GameCharacter;
import maze.logic.GameObject;

public class TestEnvironment {

    protected GameObject empty = new GameObject();
    protected GameObject wall = new GameObject(-1, -1, new char[]{'X', ' '}, true, false);
    protected GameObject exit = new GameObject(3, 4, new char[]{'S', ' '});
    protected GameObject sword = new GameObject(3, 2, new char[]{'E', ' '}, false, true);
    protected GameCharacter hero = new GameCharacter(3, 3, new char[]{'H', ' ', 'A'});
    protected GameCharacter dragon = new GameCharacter(1, 2, new char[]{'D', ' ', 'F', 'd'});

    protected GameObject[][] testMaze = {
            {wall, wall, wall, wall, wall},
            {wall, empty, empty, empty, wall},
            {wall, dragon, empty, sword, wall},
            {wall, empty, empty, hero, wall},
            {wall, wall, wall, exit, wall}
    };

    protected Board prepareTestBoard() {
        Board b = new Board(testMaze);
        b.setObjects(new GameObject[]{empty, wall, sword, exit});
        b.setCharacters(new GameCharacter[]{hero, dragon});
        return b;
    }
}
