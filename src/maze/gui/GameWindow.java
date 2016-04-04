package maze.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class GameWindow {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public GameWindow(GamePanel panel) {
		initialize(panel);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(GamePanel panel) {
		frame = new JFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(screenSize.width/2-screenSize.height/2, 0, screenSize.height-22, screenSize.height);
		frame.setTitle("Play Maze Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		panel.requestFocus();
	}

	
	public void close(){
		frame.setVisible(false);
	}

}
