/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rob
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/** 
 * Program to play the Towers of Hanoi.
 * @author Barbara Lerner
 * @version Apr 27, 2010
 *
 */
public class Hanoi extends JPanel implements ActionListener, ChangeListener {
	// window size
	private static final int WINDOW_SIZE = 400;		
	
	// Height where bottom disk should be drawn
	private static final int BASELINE = 150;
	
	// The button to start the animation
	private JButton startButton;

	// number of disks currently in use
	private int numDisks = 5; 

	// The object that displays and animates the game
	private HanoiPanel game = new HanoiPanel(numDisks, WINDOW_SIZE, BASELINE);

	// The field where the user can change the number of disks
	private JTextField numField = new JTextField("" + numDisks, 10);

	// The slider the user can use to control the speed of the animation
	private JSlider speed;

	private Thread gameThread;
	
	/**
	 * Draw the initial screen
	 */
	public Hanoi() {
		setLayout (new BorderLayout());
		
		JPanel topPanel = new JPanel();
		Box topBox = new Box(BoxLayout.Y_AXIS);

		JPanel speedPanel = new JPanel();
		speedPanel.add(new JLabel("speed:", JLabel.RIGHT));
		speed = new JSlider(JSlider.HORIZONTAL, 10, 100, 10);
		speed.addChangeListener(this);
		speedPanel.add(speed);
		topBox.add(speedPanel);
		
		JPanel diskPanel = new JPanel();
		diskPanel.add(new JLabel("# disks:", JLabel.RIGHT));
		diskPanel.add(numField);
		topBox.add(diskPanel);
		topPanel.add(topBox);

		JPanel bottomPanel = new JPanel();
		startButton = new JButton("Start");
		bottomPanel.add(startButton);
		startButton.addActionListener(this);

		add(topPanel, BorderLayout.NORTH);
		add (game, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	/**
	 * Called when the user adjusts the speed slider. Changes the speed of the
	 * animation.
	 */
	public void stateChanged(ChangeEvent evt) {
		if (game != null) {
			game.setSpeed(speed.getValue());
		}
	}

	/**
	 * Called when the user clicks the start button.
	 * Begins a new animation.
	 */
	public void actionPerformed(ActionEvent evt) {
		// Empty the pegs.
		if (game != null) {
			game.clear();
		}
		
		if (gameThread != null) {
			gameThread.interrupt();
		}

		// Find out how many disks to use.
		String stringValue = numField.getText();
		try {
			numDisks = Integer.parseInt(stringValue);
		} catch (NumberFormatException e) {
			// Leave it unchanged if the user does not type an integer
		}

		// Create a new game
		game.setDisks(numDisks);
		game.setSpeed(speed.getValue());
		
		gameThread = new Thread(game);
		gameThread.start();
	}

	/**
	 * Runs the Towers of Hanoi program
	 * @param args not used
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setTitle("Towers of Hanoi");
		f.setSize(new Dimension(WINDOW_SIZE, WINDOW_SIZE));

		Hanoi hanoi = new Hanoi();
		f.getContentPane().add(hanoi, BorderLayout.CENTER);

		// Display the window.
		f.setVisible(true);

	}

    void addWindowListener(WindowAdapter windowAdapter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
