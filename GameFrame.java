import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.Rectangle2D;

public class GameFrame extends JFrame implements ActionListener {

	private GamePanel gamePanel;
	AudioClip playSound = null;

	public GameFrame () {
		setSize (615, 310);
		setTitle ("Bat and Ball Game");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gamePanel = new GamePanel ();			// create the Game Panel
		add (gamePanel, "Center");

		gamePanel.setFocusable (true);
		gamePanel.requestFocus();

		JPanel buttonPanel = new JPanel ();

		JButton startB = new JButton ("Start Game");
		startB.addActionListener (this);
		buttonPanel.add (startB);

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

		if (command.equals ("Start Game")) {
			gamePanel.requestFocus();
			gamePanel.startGame();
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