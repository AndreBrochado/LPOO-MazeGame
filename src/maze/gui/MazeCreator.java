package maze.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import maze.logic.Board;
import maze.logic.Coordinate;
import maze.logic.GameCharacter;
import maze.logic.GameObject;
import maze.logic.MazeGame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MazeCreator extends JDialog implements ActionListener {

    private JTextField mazeSizeField;
    private GamePanel mazePanel;
    private JComboBox dragonTypeBox;
    private JButton tryMazeButton;
    private MazeGame game;
    private Board newBoard;
    private Coordinate highlightedCell;
    private GameObject empty = new GameObject(), wall = new GameObject(-1, -1, new char[]{'X', ' '}, true, false);
    private GameObject sword = new GameObject(-1, -1, new char[]{'E', ' '}, false, true), exit = new GameObject(-1, -1, new char[]{'S', ' '});
    private GameCharacter hero = new GameCharacter(-1, -1, new char[]{'H', ' ', 'A'}), dragon = new GameCharacter(-1, -1, new char[]{'D', ' ', 'F', 'd'});
    private GameObject[] comboBoxOrder = new GameObject[]{empty, wall, sword, exit, hero, dragon};
    private int numDragons = 0;

    private GameObject[][] badWalls = {
            {wall, wall, wall},
            {wall, wall, wall},
            {wall, wall, wall}};
    private GameObject[][] badSpaces = {
            {empty, empty},
            {empty, empty}};
    private GameObject[][] badDiagonalDown = {
            {wall, empty},
            {empty, wall}};
    private GameObject[][] badDiagonalUp = {
            {empty, wall},
            {wall, empty}};

    // Auxiliary class
    public static class Point {
        private int x, y;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Point(int y, int x) {
            this.x = x;
            this.y = y;
        }

        public boolean adjacentTo(Point p) {
            return Math.abs(p.x - this.x) + Math.abs(p.y - this.y) == 1;
        }
    }

    private Point findPos(GameObject[][] maze, GameObject c) {
        for (int x = 0; x < maze.length; x++)
            for (int y = 0; y < maze.length; y++)
                if (maze[y][x].equals(c))
                    return new Point(y, x);
        return null;
    }

    private void visit(GameObject[][] m, int i, int j, boolean[][] visited) {
        if (i < 0 || i >= m.length || j < 0 || j >= m.length)
            return;
        if (m[i][j].equals(wall) || visited[i][j])
            return;
        visited[i][j] = true;
        visit(m, i - 1, j, visited);
        visit(m, i + 1, j, visited);
        visit(m, i, j - 1, visited);
        visit(m, i, j + 1, visited);
    }

    private boolean checkExitReachable(GameObject[][] maze) {
        Point p = findPos(maze, new GameObject(0, 0, new char[]{'S', ' '}));
        boolean[][] visited = new boolean[maze.length][maze.length];

        visit(maze, p.getY(), p.getX(), visited);

        for (int i = 0; i < maze.length; i++)
            for (int j = 0; j < maze.length; j++)
                if (!maze[i][j].equals(wall) && !visited[i][j])
                    return false;

        return true;
    }

    private boolean hasSquare(GameObject[][] maze, GameObject[][] square) {
        for (int i = 0; i < maze.length - square.length; i++)
            for (int j = 0; j < maze.length - square.length; j++) {
                boolean match = true;
                for (int y = 0; y < square.length; y++)
                    for (int x = 0; x < square.length; x++) {
                        if (!maze[i + y][j + x].equals(square[y][x]))
                            match = false;
                    }
                if (match)
                    return true;
            }
        return false;
    }

    private boolean checkBoundaries(GameObject[][] m) {
        int countExit = 0;
        int n = m.length;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (i == 0 || j == 0 || i == n - 1 || j == n - 1)
                    if (m[i][j].equals(new GameObject(0, 0, new char[]{'S', ' '})))
                        if ((i == 0 || i == n - 1) && (j == 0 || j == n - 1))
                            return false;
                        else
                            countExit++;
                    else if (!m[i][j].equals(wall))
                        return false;
        return countExit == 1;
    }

    private boolean isValidMaze() {
        GameObject[][] m = newBoard.getBoard();

        if (!checkBoundaries(m))
            return false;
        if (hasSquare(m, badWalls) || hasSquare(m, badSpaces) || hasSquare(m, badDiagonalDown) || hasSquare(m, badDiagonalUp))
            return false;
        if (!checkExitReachable(m))
            return false;
        if (findPos(m, exit) == null || findPos(m, hero) == null || findPos(m, dragon) == null || findPos(m, sword) == null)
            return false;
        if (findPos(m, hero).adjacentTo(findPos(m, dragon)))
            return false;
        return true;
    }

    public void fillBoardWithWalls() {
        for (int i = 0; i < newBoard.getSize(); i++) {
            for (int j = 0; j < newBoard.getSize(); j++) {
                newBoard.getBoard()[j][i] = wall;
            }
        }

    }

    public void calculateHighlightedCell(int mouseX, int mouseY) {
        if (highlightedCell == null)
            highlightedCell = new Coordinate();
        double panelSize = mazePanel.getThisSize();
        double cellSize = panelSize / newBoard.getSize();
        highlightedCell.setX((int) (mouseX / cellSize));
        highlightedCell.setY((int) (mouseY / cellSize));
    }

    /**
     * Create the application.
     */
    public MazeCreator(MazeGame game) {
        super();
        this.game = game;
        this.setModal(true);
        initialize(game);
    }

    /**
     * Initialize the contents of the frame.
     */
    public void initialize(MazeGame game) {
        newBoard = game.getGameBoard();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(0, 0, screenSize.width, screenSize.height * 3/4);
        this.setTitle("Play Maze Game");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.getContentPane().add(mainPanel, BorderLayout.NORTH);
        GridBagLayout gbl_mainPanel = new GridBagLayout();
        gbl_mainPanel.columnWidths = new int[]{390, 132};
        gbl_mainPanel.rowHeights = new int[]{562, 0};
        gbl_mainPanel.columnWeights = new double[]{1.0, 1.0};
        gbl_mainPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        mainPanel.setLayout(gbl_mainPanel);

        JPanel boardPanelArea = new JPanel();
        GridBagConstraints gbc_boardPanelArea = new GridBagConstraints();
        gbc_boardPanelArea.insets = new Insets(0, 0, 0, 5);
        gbc_boardPanelArea.fill = GridBagConstraints.BOTH;
        gbc_boardPanelArea.gridx = 0;
        gbc_boardPanelArea.gridy = 0;
        mainPanel.add(boardPanelArea, gbc_boardPanelArea);
        boardPanelArea.setLayout(new BorderLayout(0, 0));

        mazePanel = new GamePanel();
        boardPanelArea.add(mazePanel);

        JPanel controlArea = new JPanel();
        GridBagConstraints gbc_controlArea = new GridBagConstraints();
        gbc_controlArea.fill = GridBagConstraints.BOTH;
        gbc_controlArea.gridx = 1;
        gbc_controlArea.gridy = 0;
        mainPanel.add(controlArea, gbc_controlArea);
        controlArea.setLayout(new GridLayout(4, 0, 0, 0));

        JPanel mazeSizePanel = new JPanel();
        controlArea.add(mazeSizePanel);
        mazeSizePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel mazeSizeLabel = new JLabel("Maze Size: ");
        mazeSizePanel.add(mazeSizeLabel);

        mazeSizeField = new JTextField();
        mazeSizeField.setText("11");
        mazeSizePanel.add(mazeSizeField);
        mazeSizeField.setColumns(10);

        JButton startCreating = new JButton("Start Creating!");
        startCreating.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int mazeSize;
                try {
                    mazeSize = Integer.parseInt(mazeSizeField.getText());

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(mazeSizeField, "Invalid Format!");
                    return;
                }

                try {
                    newBoard = new Board(mazeSize);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(mazeSizeField, "Maze Size must be odd!");
                    return;
                }
                fillBoardWithWalls();
                tryMazeButton.setEnabled(true);
                mazePanel.setGameBoard(newBoard);
                mazePanel.repaint();
                mazePanel.setVisible(true);
                mazePanel.requestFocus();
            }
        });
        mazeSizePanel.add(startCreating);

        JPanel pickObjectPanel = new JPanel();
        controlArea.add(pickObjectPanel);

        JLabel placeObjectLabel = new JLabel("Object to place:");
        pickObjectPanel.add(placeObjectLabel);

        JComboBox objectsListBox = new JComboBox();
        objectsListBox.setModel(new DefaultComboBoxModel(new String[]{"Empty", "Wall", "Sword", "Exit", "Hero", "Dragon"}));
        objectsListBox.setSelectedIndex(0);
        pickObjectPanel.add(objectsListBox);

        JPanel panel = new JPanel();
        controlArea.add(panel);

        JLabel dragonTypeLabel = new JLabel("Dragon Type: ");
        panel.add(dragonTypeLabel);

        dragonTypeBox = new JComboBox();
        dragonTypeBox.setModel(new DefaultComboBoxModel(new String[]{"Static", "Random Movement", "Random Movement and Rest"}));
        dragonTypeBox.setSelectedIndex(0);
        panel.add(dragonTypeBox);

        JPanel finishMazePanel = new JPanel();
        controlArea.add(finishMazePanel);

        tryMazeButton = new JButton("Try Maze!");
        tryMazeButton.setEnabled(false);
        tryMazeButton.addActionListener(this);
        finishMazePanel.add(tryMazeButton);


        this.mazePanel.addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mousePressed(MouseEvent e) {
                                                super.mousePressed(e);
                                                if (highlightedCell == null) {
                                                    calculateHighlightedCell(e.getX(), e.getY());
                                                    return;
                                                }
                                                Coordinate oldCell = new Coordinate(highlightedCell.getX(), highlightedCell.getY());
                                                calculateHighlightedCell(e.getX(), e.getY());

                                                if (highlightedCell.equals(oldCell)) {
                                                    GameObject selectedObj = comboBoxOrder[objectsListBox.getSelectedIndex()];
                                                    if (selectedObj.equals(dragon) || selectedObj.equals(wall) || selectedObj.equals(empty)) {
                                                        if (selectedObj.equals(dragon)) numDragons++;
                                                        newBoard.getBoard()[highlightedCell.getY()][highlightedCell.getX()] = comboBoxOrder[objectsListBox.getSelectedIndex()];
                                                    } else {
                                                        if (selectedObj.getX() != -1 && selectedObj.getY() != -1)
                                                            newBoard.getBoard()[selectedObj.getY()][selectedObj.getX()] = empty;
                                                        newBoard.getBoard()[highlightedCell.getY()][highlightedCell.getX()] = comboBoxOrder[objectsListBox.getSelectedIndex()];
                                                        comboBoxOrder[objectsListBox.getSelectedIndex()].setX(highlightedCell.getX());
                                                        comboBoxOrder[objectsListBox.getSelectedIndex()].setY(highlightedCell.getY());
                                                    }
                                                }

                                                mazePanel.highlightCell(highlightedCell.getX(), highlightedCell.getY());
                                            }
                                        });
                this.mazePanel.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (!isValidMaze()) {
            JOptionPane.showMessageDialog(mazePanel, "Invalid maze! Maze restrictions are: \n a) the maze boundaries must have exactly one exit and everything else walls\n b) the exit cannot be a corner\n c) there must exist a path between any blank cell and the maze exit\n d) there cannot exist 2x2 (or greater) squares with blanks only\n e) there cannot exit 2x2 (or greater) squares with blanks in one diagonal and walls in the other\n f) there cannot exist 3x3 (or greater) squares with walls only\n g) Hero, Sword, Dragons and Exit must be placed on different positions, with Hero and Dragon on not-adjacent positions");
            return;
        } else {
            GameCharacter[] characters = new GameCharacter[numDragons + 1];
            characters[0] = hero;
            int k = 1;
            for (int i = 1; i < newBoard.getSize(); i++) {
                for (int j = 1; j < newBoard.getSize(); j++) {
                    if (newBoard.getBoard()[i][j].equals(dragon)) {
                        characters[k] = new GameCharacter(j, i, new char[]{'D', ' ', 'F', 'd'});
                        k++;
                    }
                }
            }
            newBoard.setCharacters(characters);
            newBoard.setObjects(new GameObject[]{empty, wall, sword, exit});
            this.game.setGameBoard(newBoard);
            game.setGameMode(dragonTypeBox.getSelectedIndex());
            this.dispose();
        }

    }
}

