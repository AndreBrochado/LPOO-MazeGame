package maze.logic;

import java.util.Random;
import java.util.Stack;

/**
 * Created by Andre on 08/03/2016.
 */

public class MazeBuilder implements IMazeBuilder {

    private int mazeSize;
    private Random generator;
    private GameObject[][] maze;
    private Coordinate guideCell, visitedGuideCell;
    private GameObject exit, empty, wall;
    private int visitedCellsSize;
    private boolean[][] visitedCells;
    private Stack<Coordinate> pathHistory;

    private void initializeValues(int mazeSize) {
        this.mazeSize = mazeSize;

        generator = new Random();

        maze = new GameObject[mazeSize][mazeSize];

        createExit();

        createGuideCell();

        createVisitedCellsArray();

        char[] wallReps = {'X', ' '};
        wall = new GameObject(-1, -1, wallReps, true, false);
        empty = new GameObject();

        pathHistory = new Stack<Coordinate>();
    }

    private void createGuideCell() {
        guideCell = new Coordinate();
        if (exit.getX() == 0) {
            guideCell.setX(exit.getX() + 1);
            guideCell.setY(exit.getY());
        } else if (exit.getX() == mazeSize - 1) {
            guideCell.setX(exit.getX() - 1);
            guideCell.setY(exit.getY());
        } else if (exit.getY() == 0) {
            guideCell.setY(exit.getY() + 1);
            guideCell.setX(exit.getX());
        } else if (exit.getY() == mazeSize - 1) {
            guideCell.setY(exit.getY() - 1);
            guideCell.setX(exit.getX());
        }
    }

    private void createExit() {
        char[] exitReps = {'S', ' '};
        exit = new GameObject(-1, -1, exitReps);

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

    private void mazeCellPosition(Coordinate originalCoordinate, Coordinate destinationCoordenate) {
        destinationCoordenate.setX(originalCoordinate.getX() * 2 + 1);
        destinationCoordenate.setY(originalCoordinate.getY() * 2 + 1);
    }

    private void visitedCellsPosition(Coordinate originalCoordinate, Coordinate destinationCoordenate) {
        destinationCoordenate.setX((originalCoordinate.getX() - 1) / 2);
        destinationCoordenate.setY((originalCoordinate.getY() - 1) / 2);
    }

    private boolean validGuideCellMovement(int direction, Coordinate visitedGuideCell) {
        switch (direction) {
            case 0:
                return visitedGuideCell.getX() - 1 >= 0 && !visitedCells[visitedGuideCell.getY()][visitedGuideCell.getX() - 1];
            case 1:
                return visitedGuideCell.getX() + 1 < visitedCellsSize && !visitedCells[visitedGuideCell.getY()][visitedGuideCell.getX() + 1];
            case 2:
                return visitedGuideCell.getY() - 1 >= 0 && !visitedCells[visitedGuideCell.getY() - 1][visitedGuideCell.getX()];
            case 3:
                return visitedGuideCell.getY() + 1 < visitedCellsSize && !visitedCells[visitedGuideCell.getY() + 1][visitedGuideCell.getX()];
        }
        return false;
    }

    private void moveGuideCell(int direction) {
        Coordinate newPos = new Coordinate();
        switch (direction) {
            case 0:
                newPos.setX(visitedGuideCell.getX() - 1);
                newPos.setY(visitedGuideCell.getY());
                maze[guideCell.getY()][guideCell.getX() - 1] = empty;
                break;
            case 1:
                newPos.setX(visitedGuideCell.getX() + 1);
                newPos.setY(visitedGuideCell.getY());
                maze[guideCell.getY()][guideCell.getX() + 1] = empty;
                break;
            case 2:
                newPos.setX(visitedGuideCell.getX());
                newPos.setY(visitedGuideCell.getY() - 1);
                maze[guideCell.getY() - 1][guideCell.getX()] = empty;
                break;
            case 3:
                newPos.setX(visitedGuideCell.getX());
                newPos.setY(visitedGuideCell.getY() + 1);
                maze[guideCell.getY() + 1][guideCell.getX()] = empty;
                break;
        }
        visitedCells[newPos.getY()][newPos.getX()] = true;
        visitedGuideCell = newPos;
        mazeCellPosition(visitedGuideCell, guideCell);
        pathHistory.push(visitedGuideCell);
    }

    private boolean isDeadEnd() {
        for (int i = 0; i < 4; i++) {
            if (validGuideCellMovement(i, visitedGuideCell))
                return false;
        }
        return true;
    }

    public GameObject[][] buildMaze(int size) throws IllegalArgumentException {
        if (size <= 3 || size % 2 == 0)
            throw new IllegalArgumentException();

        initializeValues(size);

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                maze[i][j] = wall;

        for (int i = 1; i < size; i += 2)
            for (int j = 1; j < size; j += 2)
                maze[i][j] = empty;

        maze[exit.getY()][exit.getX()] = exit;

        visitedGuideCell = new Coordinate();
        visitedCellsPosition(guideCell, visitedGuideCell);
        visitedCells[visitedGuideCell.getY()][visitedGuideCell.getX()] = true;

        pathHistory.push(visitedGuideCell);

        int direction = 0;

        while (!pathHistory.empty()) {
            while (isDeadEnd()) {
                pathHistory.pop();
                if (pathHistory.empty())
                    break;
                else {
                    visitedGuideCell = pathHistory.peek();
                    mazeCellPosition(visitedGuideCell, guideCell);
                }
            }
            if (pathHistory.empty())
                break;
            while (!validGuideCellMovement(direction, visitedGuideCell))
                direction = generator.nextInt(4);
            moveGuideCell(direction);
        }
        return maze;
    }

    public static void main(String[] args) {
        MazeBuilder mb = new MazeBuilder();
        GameObject[][] board = mb.buildMaze(21);
        Board b = new Board(21, 21);
        b.setBoard(board);
        b.print();
    }
}