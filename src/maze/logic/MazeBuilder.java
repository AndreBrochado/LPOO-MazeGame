package maze.logic;

import java.util.Random;
import java.util.Stack;

/**
 * Created by Andre on 08/03/2016.
 */

public class MazeBuilder implements IMazeBuilder {

    private final int noObjects = 4;

    private int mazeSize;
    private Random generator;
    private GameObject[][] maze;

    private Coordinate guideCell;
    private int visitedCellsSize;
    private boolean[][] visitedCells;
    private Stack<Coordinate> pathHistory;

    //board preparation variables
    private GameCharacter hero;
    private GameCharacter[] dragons;
    private GameObject empty, wall, sword, exit;

    public MazeBuilder() {
        this.generator = new Random();
        this.empty = new GameObject();
        this.wall = new GameObject(-1, -1, new char[]{'X', ' '}, true, false);
        this.sword = new GameObject(-1, -1, new char[]{'E', ' '}, false, true);
        this.exit = new GameObject(-1, -1, new char[]{'S', ' '});
        this.hero = new GameCharacter(-1, -1, new char[]{'H', ' ', 'A'});
    }

    private void initializeValues(int mazeSize, int numDragons) {
        this.mazeSize = mazeSize;

        maze = new GameObject[mazeSize][mazeSize];

        this.dragons = new GameCharacter[numDragons];
        for (int i = 0; i < numDragons; i++) dragons[i] = new GameCharacter(-1, -1, new char[]{'D', ' ', 'F', 'd'});

        createExit();

        createGuideCell();

        createVisitedCellsArray();

        pathHistory = new Stack<>();
    }

    private void createGuideCell() {
        int guideCellX = exit.getX(), guideCellY = exit.getY();
        if (exit.getX() == 0) {
            guideCellX += 1;
        } else if (exit.getX() == mazeSize - 1) {
            guideCellX -= 1;
        } else if (exit.getY() == 0) {
            guideCellY += 1;
        } else if (exit.getY() == mazeSize - 1) {
            guideCellY -= 1;
        }
        //convert to visited cell coordinate "pattern"
        guideCell = new Coordinate((guideCellX - 1) / 2, (guideCellY - 1) / 2);
    }

    private void createExit() {
        int cornerDistance = 0;
        while (cornerDistance % 2 == 0)
            cornerDistance = generator.nextInt(mazeSize - 2) + 1;
        int exitBorder = generator.nextInt(4);

        switch (exitBorder) {
            case 0:
            case 1:
                exit.setX(exitBorder * (mazeSize - 1));
                exit.setY(cornerDistance);
                break;
            case 2:
            case 3:
                exit.setX(cornerDistance);
                exit.setY((exitBorder - 2) * (mazeSize - 1));
                break;
        }
    }

    private void createVisitedCellsArray() {
        visitedCellsSize = (mazeSize - 1) / 2;
        visitedCells = new boolean[visitedCellsSize][visitedCellsSize];
        for (int i = 0; i < visitedCellsSize; i++)
            for (int j = 0; j < visitedCellsSize; j++)
                visitedCells[i][j] = false;
    }

    private boolean validGuideCellMovement(int direction) {
        switch (direction) {
            case 0: //left
                return guideCell.getX() - 1 >= 0 && !visitedCells[guideCell.getY()][guideCell.getX() - 1];
            case 1: //right
                return guideCell.getX() + 1 < visitedCellsSize && !visitedCells[guideCell.getY()][guideCell.getX() + 1];
            case 2: //down
                return guideCell.getY() - 1 >= 0 && !visitedCells[guideCell.getY() - 1][guideCell.getX()];
            case 3: //up
                return guideCell.getY() + 1 < visitedCellsSize && !visitedCells[guideCell.getY() + 1][guideCell.getX()];
        }
        return false;
    }

    private void moveGuideCell(int direction) {
        Coordinate newPos = new Coordinate();
        switch (direction) {
            case 0: //left
                newPos.setX(guideCell.getX() - 1);
                newPos.setY(guideCell.getY());
                maze[guideCell.getY() * 2 + 1][guideCell.getX() * 2] = this.empty;
                break;
            case 1: //right
                newPos.setX(guideCell.getX() + 1);
                newPos.setY(guideCell.getY());
                maze[guideCell.getY() * 2 + 1][guideCell.getX() * 2 + 2] = this.empty;
                break;
            case 2: //down
                newPos.setX(guideCell.getX());
                newPos.setY(guideCell.getY() - 1);
                maze[guideCell.getY() * 2][guideCell.getX() * 2 + 1] = this.empty;
                break;
            case 3: //up
                newPos.setX(guideCell.getX());
                newPos.setY(guideCell.getY() + 1);
                maze[guideCell.getY() * 2 + 2][guideCell.getX() * 2 + 1] = this.empty;
                break;
        }
        visitedCells[newPos.getY()][newPos.getX()] = true;
        guideCell = newPos;
        pathHistory.push(guideCell);
    }

    private boolean isDeadEnd() {
        for (int i = 0; i < 4; i++) {
            if (validGuideCellMovement(i))
                return false;
        }
        return true;
    }

    public void addObject(GameObject object) {
        Coordinate pos = new Coordinate(0, 0);

        while (!maze[pos.getY()][pos.getX()].equals(this.empty)) {
            pos.setX(generator.nextInt(mazeSize - 1) + 1);
            pos.setY(generator.nextInt(mazeSize - 1) + 1);
        }

        maze[pos.getY()][pos.getX()] = object;
        object.setX(pos.getX());
        object.setY(pos.getY());
    }

    public boolean isAdjacentToHero(Coordinate pos) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                try {
                    if (maze[pos.getY() + dy][pos.getX() + dx].equals(this.hero))
                        return true;
                } catch (ArrayIndexOutOfBoundsException ignore) {
                }
            }
        }
        return false;
    }

    public void addDragon(GameObject dragon) {
        Coordinate pos = new Coordinate(0, 0);

        while (!maze[pos.getY()][pos.getX()].equals(this.empty) || isAdjacentToHero(pos)) {
            pos.setX(generator.nextInt(mazeSize - 1) + 1);
            pos.setY(generator.nextInt(mazeSize - 1) + 1);
        }

        maze[pos.getY()][pos.getX()] = dragon;
        dragon.setX(pos.getX());
        dragon.setY(pos.getY());
    }

    public GameObject[][] buildMaze(int size) throws IllegalArgumentException {
        return buildMaze(size, 1);
    }

    public GameObject[][] buildMaze(int size, int numDragons) throws IllegalArgumentException {
        if (size <= 3 || size % 2 == 0)
            throw new IllegalArgumentException();

        initializeValues(size, numDragons);

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                maze[i][j] = this.wall;

        for (int i = 1; i < size; i += 2)
            for (int j = 1; j < size; j += 2)
                maze[i][j] = this.empty;

        maze[exit.getY()][exit.getX()] = exit;

        visitedCells[guideCell.getY()][guideCell.getX()] = true;

        pathHistory.push(guideCell);

        int direction;

        while (!pathHistory.empty()) {
            while (isDeadEnd()) {
                pathHistory.pop();
                if (pathHistory.empty())
                    break;
                else {
                    guideCell = pathHistory.peek();
                    //mazeCellPosition(visitedGuideCell, guideCell);
                }
            }
            if (pathHistory.empty())
                break;
            do {
                direction = generator.nextInt(4);
            } while (!validGuideCellMovement(direction));
            moveGuideCell(direction);
        }

        addObject(this.hero);
        addObject(this.sword);
        for (int i = 0; i < numDragons; i++)
            addDragon(dragons[i]);
        return maze;
    }

    public GameObject[] getGameObjects() {
        GameObject[] objects = new GameObject[noObjects];
        objects[0] = empty;
        objects[1] = wall;
        objects[2] = sword;
        objects[3] = exit;
        return objects;
    }

    public GameCharacter[] getGameCharacters() {
        GameCharacter[] characters = new GameCharacter[dragons.length + 1];
        characters[0] = hero;
        System.arraycopy(dragons, 0, characters, 1, dragons.length);
        return characters;
    }
}