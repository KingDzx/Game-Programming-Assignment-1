import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements 
					Runnable, 
					KeyListener {

	private Bat bat = null;
	private Ball ball = null;
	private ArrayList<Brick> bricks = null;

	AudioClip playSound = null;

	private Thread gameThread;
	boolean isRunning;

	private Image bgImage;
	private BufferedImage image;
	public GamePanel () {

		setBackground(Color.BLACK);
		addKeyListener(this);			// respond to key events
		setFocusable(true);
    		requestFocus();    			// the GamePanel now has focus, so receives key events

		loadClips ();

		gameThread = null;
		isRunning = false;

		bgImage = loadImage ("background.jpg");
		image = new BufferedImage (700, 500, BufferedImage.TYPE_INT_RGB);
	}

	// implementation of Runnable interface

	public void run () {
		try {
			isRunning = true;
			while (isRunning) {
				gameUpdate();
				gameRender();
				Thread.sleep (100);	// increase value of sleep time to slow down ball
			}
		}
		catch(InterruptedException e) {}
	}

	// implementation of KeyListener interface

	public void keyPressed (KeyEvent e) {

		if (bat == null)
			return;

		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_LEFT) {
			bat.moveLeft();
		}
		else
		if (keyCode == KeyEvent.VK_RIGHT) {
			bat.moveRight();
		}
	}

	public void keyReleased (KeyEvent e) {

	}

	public void keyTyped (KeyEvent e) {

	}

	public void loadClips() {

		try {
			playSound = Applet.newAudioClip (
					getClass().getResource("Background.wav"));

		}
		catch (Exception e) {
			System.out.println ("Error loading sound file: " + e);
		}

	}
	/*
	public void playClip (int index) {

		if (index == 1 && playSound != null)
			playSound.play();

	}
	*/
	public Image loadImage (String fileName) {
		return new ImageIcon(fileName).getImage();
	}

	public void gameUpdate () {
		for (Brick b :bricks){
			if (b.checkStatus() && b.ballHitsBrick()) {
				ball.move(true);
				return;
			}
		}
		ball.move(false);
	}

	public void gameRender () {				// draw the game objects
		Graphics2D imageContext = (Graphics2D) image.getGraphics();
		imageContext.drawImage(bgImage, 0, 0, null);	// draw the background image
		ball.draw(imageContext);								// draw the ball
		bat.draw(imageContext);									// draw the bat
		for (Brick b : bricks){
			if (b.checkStatus())
				b.draw(imageContext);
		}

		Graphics2D g2 = (Graphics2D) getGraphics();				// get the graphics context for the panel
		g2.drawImage(image, 0, 0,null);				// draw the image on the panel
		g2.dispose();
	}	

	public void startGame() {				// initialise and start the game thread 

		if (gameThread == null) {
			isRunning = true;
			bat = new Bat (this);
			ball = new Ball (this, bat);

			bricks = new ArrayList<>();
			int x = (this.getWidth()/2) + 1;
			int y = 0;

			for (int i=1; i<6; i++){
				x -= 25;
				int xMove = 0;
				for (int j=0; j<i; j++){
					Brick b = new Brick(this, x + xMove, y, ball);
					bricks.add(b);
					xMove += 50;
				}
				y += 15;
			}

			playSound.loop();
			gameThread = new Thread(this);
			gameThread.start();
		}
	}

	public void endGame() {					// end the game thread

		if (isRunning) {
			isRunning = false;
			playSound.stop();
		}
	}
}