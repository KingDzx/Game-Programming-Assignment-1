import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;

public class GameFrame extends JFrame implements ActionListener {

	private GamePanel gamePanel;
	AudioClip playSound = null;

	public GameFrame () {
		setSize (615, 300);
		setTitle ("Bat and Ball Game");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gamePanel = new GamePanel ();			// create the Game Panel
		add (gamePanel, "Center");

		gamePanel.setFocusable (true);
		gamePanel.requestFocus();

		JPanel buttonPanel = new JPanel ();

		JButton startEasy = new JButton ("Start Easy Game");
		startEasy.addActionListener (this);
		buttonPanel.add (startEasy);

		JButton startMedium = new JButton ("Start Medium Game");
		startMedium.addActionListener (this);
		buttonPanel.add (startMedium);

		JButton startHard = new JButton ("Start Hard Game");
		startHard.addActionListener (this);
		buttonPanel.add (startHard);

		JButton stopB = new JButton ("Stop Game");
		stopB.addActionListener (this);
		buttonPanel.add (stopB);

		JButton closeB = new JButton ("Close");
		closeB.addActionListener (this);
		buttonPanel.add (closeB);


		add (buttonPanel, "South");

		loadClips ();
	}

	public void loadClips() {

		try {
			playSound = Applet.newAudioClip (
					getClass().getResource("clack.au"));
		}
		catch (Exception e) {
			System.out.println ("Error loading sound file: " + e);
		}

	}

	public void playClip (int index) {

		if (index == 1 && playSound != null)
			playSound.play();

	}

	// implementation of ActionListener interface for GameFrame

	public void actionPerformed (ActionEvent e) {
		String command = e.getActionCommand ();

		if (command.equals ("Start Easy Game")) {
			gamePanel.requestFocus();
			gamePanel.startGame(5);
		}
		else
		if (command.equals ("Start Medium Game")) {
			gamePanel.requestFocus();
			gamePanel.startGame(7);
		}
		else
		if (command.equals ("Start Hard Game")) {
			gamePanel.requestFocus();
			gamePanel.startGame(10);
		}
		else
		if (command.equals ("Stop Game")) {
			gamePanel.endGame();
		}
		else
		if (command.equals ("Close")) {
			setVisible (false);
			System.exit (0);
		}
	}

}		