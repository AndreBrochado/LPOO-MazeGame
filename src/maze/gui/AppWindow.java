package maze.gui;

import maze.logic.MazeGame;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.*;
import java.awt.Font;

public class AppWindow {

    private JFrame frmMazeGame;
    private JTextField mazeSizeField;
    private JTextField noDragonsField;
    private JTextArea gameAreaField;
    private JButton playGame;
    private JPanel gameAreaPanel;
    private JLabel bottomLabel;
    private MazeGame game;
    private GamePanel panel;
    private GameWindow gameWindow;

    private void createPanel() {
    	panel = new GamePanel();
        gameAreaPanel.add(panel, BorderLayout.CENTER);
        panel.addKeyListener(new KeyListener() {
                                 @Override
                                 public void keyTyped(KeyEvent e) {

                                 }

                                 @Override
                                 public void keyPressed(KeyEvent e) {
                                     if (game.getGameBoard().getBoardState() == MazeGame.GAME_UNDERWAY) {
                                         switch (e.getKeyCode()) {
                                             case KeyEvent.VK_LEFT:
                                                 clickDirection(MazeGame.LEFT);
                                                 break;
                                             case KeyEvent.VK_RIGHT:
                                                 clickDirection(MazeGame.RIGHT);
                                                 break;
                                             case KeyEvent.VK_UP:
                                                 clickDirection(MazeGame.UP);
                                                 break;
                                             case KeyEvent.VK_DOWN:
                                                 clickDirection(MazeGame.DOWN);
                                                 break;
                                             case KeyEvent.VK_ESCAPE:
                                            	 createPanel();
                                            	 frmMazeGame.setVisible(true);
                                            	 gameWindow.close();
                                                 playGame.setEnabled(false);
                                            	 break;
                                         }
                                     }
                                 }

                                 @Override
                                 public void keyReleased(KeyEvent e) {

                                 }
                             }
        );
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                panel.requestFocus();
            }
        });
    }

    private void clickDirection(char direction) {
        int gameState = game.makePlay(direction);
        panel.repaint();
        if (gameState != MazeGame.GAME_UNDERWAY) {
            if (gameState == MazeGame.GAME_WON)
                bottomLabel.setText("You won the game! :D");
            else if (gameState == MazeGame.GAME_LOST)
                bottomLabel.setText("You lost the game... :(");
            gameWindow.close();
            frmMazeGame.setVisible(true);
            createPanel();
            playGame.setEnabled(false);
        }
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AppWindow window = new AppWindow();
                    window.frmMazeGame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public AppWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmMazeGame = new JFrame();
        frmMazeGame.setTitle("Maze Game");
        BorderLayout borderLayout = (BorderLayout) frmMazeGame.getContentPane().getLayout();
        frmMazeGame.setBounds(100, 100, 800, 500);
        frmMazeGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        frmMazeGame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        GridBagLayout gbl_mainPanel = new GridBagLayout();
        gbl_mainPanel.columnWidths = new int[]{30, 0, 0};
        gbl_mainPanel.rowHeights = new int[]{30, 30, 30};
        gbl_mainPanel.columnWeights = new double[]{1.0, 0.0, 0.0};
        gbl_mainPanel.rowWeights = new double[]{1.0, 1.0, 1.0};
        mainPanel.setLayout(gbl_mainPanel);

        JPanel variablesPanel = new JPanel();
        GridBagConstraints gbc_variablesPanel = new GridBagConstraints();
        gbc_variablesPanel.gridwidth = 2;
        gbc_variablesPanel.fill = GridBagConstraints.BOTH;
        gbc_variablesPanel.insets = new Insets(0, 0, 5, 0);
        gbc_variablesPanel.gridx = 0;
        gbc_variablesPanel.gridy = 0;
        mainPanel.add(variablesPanel, gbc_variablesPanel);
        variablesPanel.setLayout(new GridLayout(3, 0, 0, 0));

        JPanel mazeSizePanel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) mazeSizePanel.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        variablesPanel.add(mazeSizePanel);

        JLabel mazeSizeLabel = new JLabel("Maze Size:");
        mazeSizeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        mazeSizePanel.add(mazeSizeLabel);

        mazeSizeField = new JTextField();
        mazeSizeField.setLocation(new Point(50, 0));
        mazeSizeField.setHorizontalAlignment(SwingConstants.LEFT);
        mazeSizeLabel.setLabelFor(mazeSizeField);
        mazeSizeField.setText("11");
        mazeSizeField.setColumns(5);
        mazeSizePanel.add(mazeSizeField);

        JPanel noDragonsPanel = new JPanel();
        FlowLayout flowLayout_1 = (FlowLayout) noDragonsPanel.getLayout();
        flowLayout_1.setAlignment(FlowLayout.LEFT);
        variablesPanel.add(noDragonsPanel);

        JLabel noDragonsLabel = new JLabel("Number of Dragons:");
        noDragonsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        noDragonsPanel.add(noDragonsLabel);

        noDragonsField = new JTextField();
        noDragonsLabel.setLabelFor(noDragonsField);
        noDragonsField.setText("1");
        noDragonsField.setColumns(10);
        noDragonsPanel.add(noDragonsField);

        JPanel dragonTypePanel = new JPanel();
        FlowLayout flowLayout_2 = (FlowLayout) dragonTypePanel.getLayout();
        flowLayout_2.setAlignment(FlowLayout.LEFT);
        variablesPanel.add(dragonTypePanel);

        JLabel dragonTypeLabel = new JLabel("Dragon Type:");
        dragonTypeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dragonTypePanel.add(dragonTypeLabel);

        JComboBox dragonTypeBox = new JComboBox();
        dragonTypeLabel.setLabelFor(dragonTypeBox);
        dragonTypeBox.setMaximumRowCount(3);
        dragonTypeBox.setModel(new DefaultComboBoxModel(new String[]{"Static", "Random Movement", "Random Movement and Rest"}));
        dragonTypeBox.setSelectedIndex(0);
        dragonTypePanel.add(dragonTypeBox);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc_buttonsPanel = new GridBagConstraints();
        gbc_buttonsPanel.insets = new Insets(0, 10, 10, 0);
        gbc_buttonsPanel.fill = GridBagConstraints.BOTH;
        gbc_buttonsPanel.gridx = 2;
        gbc_buttonsPanel.gridy = 0;
        mainPanel.add(buttonsPanel, gbc_buttonsPanel);
        buttonsPanel.setLayout(new GridLayout(0, 1, 0, 25));

        JButton genMazeButton = new JButton("Generate Maze");
        genMazeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int boardSize, noDragons;
                try {
                    boardSize = Integer.parseInt(mazeSizeField.getText());
                    if (boardSize > 61) {
                        JOptionPane.showMessageDialog(mazeSizeField, "Invalid Size!");
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(mazeSizeField, "Invalid Format!");
                    return;
                }
                try {
                    noDragons = Integer.parseInt(noDragonsField.getText());
                    if (noDragons > boardSize / 2) {
                        JOptionPane.showMessageDialog(noDragonsField, "Can't have more than Maze Size/2 dragons!");
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(noDragonsField, "Invalid Format!");
                    return;
                }
                try {
                    game = new MazeGame(boardSize, noDragons, dragonTypeBox.getSelectedIndex());
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(mazeSizeField, "Maze Size must be odd!");
                    return;
                }

                panel.setGameBoard(game.getGameBoard());
                panel.repaint();
                
                bottomLabel.setText("You can play!");
                playGame.setEnabled(true);
                
            }
        });
        
        playGame = new JButton("Play Game");
        playGame.setEnabled(false);
        playGame.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		gameWindow = new GameWindow(panel);
        	}
        });
        buttonsPanel.add(playGame);
        buttonsPanel.add(genMazeButton);
                
                JButton createMaze = new JButton("Create Your Own Maze");
                createMaze.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent arg0) {
                		game = new MazeGame(11, 1, -1);
                		MazeCreator mc = new MazeCreator(game);
                        mc.setVisible(true);

                		panel.setGameBoard(game.getGameBoard());
                		panel.repaint();
                		bottomLabel.setText("You can play!");
                        playGame.setEnabled(true);
                	}
                });
                buttonsPanel.add(createMaze);
                
                        JButton exitButton = new JButton("Exit");
                        exitButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent arg0) {
                                System.exit(0);
                            }
                        });
                        buttonsPanel.add(exitButton);

        JPanel directionsPanel = new JPanel();
        directionsPanel.setPreferredSize(new Dimension(250, 100));
        GridBagConstraints gbc_directionsPanel = new GridBagConstraints();
        gbc_directionsPanel.gridheight = 2;
        gbc_directionsPanel.insets = new Insets(0, 10, 5, 0);
        gbc_directionsPanel.fill = GridBagConstraints.BOTH;
        gbc_directionsPanel.gridx = 2;
        gbc_directionsPanel.gridy = 1;
        mainPanel.add(directionsPanel, gbc_directionsPanel);
        directionsPanel.setLayout(new BorderLayout(0, 0));
        
        JTextArea gameInstructions = new JTextArea();
        gameInstructions.setMargin(new Insets(50, 2, 2, 2));
        gameInstructions.setEditable(false);
        gameInstructions.setFont(new Font("Courier New", Font.PLAIN, 12));
        gameInstructions.setText("Instructions:\r\nTo choose your Game Maze:\r\n-Change the editable values boxes\r\n-Click Generate Maze\r\n-Repeat until you like the maze\r\n-Click Play Game\r\n-Use your Keyboard Arrow keys \r\n-Have Fun!");
        directionsPanel.add(gameInstructions, BorderLayout.CENTER);

        gameAreaPanel = new JPanel();
        GridBagConstraints gbc_gameAreaPanel = new GridBagConstraints();
        gbc_gameAreaPanel.gridwidth = 2;
        gbc_gameAreaPanel.gridheight = 2;
        gbc_gameAreaPanel.fill = GridBagConstraints.BOTH;
        gbc_gameAreaPanel.gridx = 0;
        gbc_gameAreaPanel.gridy = 1;
        mainPanel.add(gameAreaPanel, gbc_gameAreaPanel);
        gameAreaPanel.setLayout(new BorderLayout(0, 0));

        createPanel();
        
        //panel.requestFocusInWindow();
        bottomLabel = new JLabel("You can generate your maze!");
        gameAreaPanel.add(bottomLabel, BorderLayout.SOUTH);
    }

}
