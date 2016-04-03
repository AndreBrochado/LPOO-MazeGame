package maze.gui;

import maze.logic.Board;
import maze.logic.GameCharacter;
import maze.logic.GameObject;
import maze.logic.MazeGame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private Board gameBoard;
    private BufferedImage background;
    private HashMap<String, BufferedImage> loadedImages;

    /**
     * Create the panel.
     */
    public GamePanel() {
        try {
            background = ImageIO.read(new File("images/Background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadedImages = new HashMap<>();
    }

    public void setGameBoard(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getHeight(), getHeight(), null);

        if (gameBoard != null) {
            int compSize = getHeight() / gameBoard.getBoard().length;
            String imgPath;
            for (int i = 0; i < gameBoard.getBoard().length; i++) {
                for (int j = 0; j < gameBoard.getBoard().length; j++) {
                    GameObject objToDraw = gameBoard.getBoard()[j][i];
                    if (objToDraw.getRepresentation() != ' ') {
                        if (objToDraw instanceof GameCharacter && objToDraw.getRepresentation() != 'd')
                            imgPath = "images/" + objToDraw.getRepresentation() + ((GameCharacter) objToDraw).getLastMovement() + ".png";
                        else
                            imgPath = "images/" + objToDraw.getRepresentation() + ".png";
                        if (!loadedImages.containsKey(imgPath)) {
                            try {
                                BufferedImage newImage = ImageIO.read(new File(imgPath));
                                loadedImages.put(imgPath, newImage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        g.drawImage(loadedImages.get(imgPath), i * compSize, j * compSize, compSize, compSize, null);
                    }
                }
            }
        }
    }
}
