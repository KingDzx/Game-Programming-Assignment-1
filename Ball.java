import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.util.Random;
import java.applet.Applet;
import java.applet.AudioClip;

public class Ball {

	private static final int XSIZE = 10;
	private static final int YSIZE = 10;
	private int DY = 5;
	private int DX = 5;

	private JPanel panel;
	private Bat bat;
	private Random random;
	private int x;
	private int y;
	private int score;
	private String Scoreboard;
	private boolean isAlive;

	AudioClip hitBatSound;
	AudioClip fallOffSound;

	Graphics2D g2;
	private Color backgroundColor;
	private Dimension dimension;

	public Ball (JPanel p, Bat b, int stage) {
		panel = p;
		bat = b;
		Graphics g = panel.getGraphics ();
		g2 = (Graphics2D) g;
		dimension = panel.getSize();
		backgroundColor = panel.getBackground ();

		random = new Random();
		score = 0;
		Scoreboard = "";
		isAlive = true;

		x = random.nextInt(dimension.width/2) + dimension.width/4;// randomly generate x location between 1/4 and 3/4 of the screen

		if (stage == 5){
			y = 75;
		}else if (stage == 7){
			y = 110;
		}else{
			y = 155;
		}

		loadClips();
	}


	private void setScore(String s){
		Scoreboard = s;
	}

	public void draw (Graphics2D g2) {
		g2.setColor (Color.BLACK);
		g2.fill (new Ellipse2D.Double (x, y, XSIZE, YSIZE));

		g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g2.setColor(Color.RED);
		g2.drawString(Scoreboard, 0, 20);
	}

	public void erase (Graphics2D g2) {
		g2.setColor (backgroundColor);
		g2.fill (new Ellipse2D.Double (x, y, XSIZE, YSIZE));
	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
	}

	public boolean batHitsBall () {

		Rectangle2D.Double rectBall = getBoundingRectangle();
		Rectangle2D.Double rectBat = bat.getBoundingRectangle();
		
		if (rectBall.intersects(rectBat))
			return true;
		else
			return false;
	}


	public boolean hitBottom () {

		if (y + YSIZE > dimension.height)
			return true;
		else
			return false;
	}

	public boolean hitTop () {

		if (y < 0)
			return true;
		else
			return false;
	}

	public void move (boolean brickHit) {

		if (!panel.isVisible ()) return;
	
		// erase();					// no need to erase with background image

		y = y + DY;
		x = x + DX;

		setScore("Score " + new Integer(score).toString());

		if (batHitsBall() || hitTop() || brickHit) {
			playClip (1);			// play clip if bat hits ball
			if (brickHit){
				//DX *= -1;
				y = y - (DY * 2);
				score += 1;
			}
			DY *= -1;


		}else if (hitBottom()){					// play clip if ball falls out at bottom
			try {					// take a rest if bat hits ball or
				playClip (2);
				isAlive = false;
				setScore("GAME OVER!!!");
				Thread.sleep (1000);		//   ball falls out of play.
				//setPosition ();
			}
			catch (InterruptedException e) {}
		}

		if (x + XSIZE >= dimension.width || x < 0){
			DX *= -1;
		}

	}

	public void loadClips() {

		try {

			hitBatSound = Applet.newAudioClip (
					getClass().getResource("hitBat.au"));

			fallOffSound = Applet.newAudioClip (
					getClass().getResource("fallOff.au"));

		}
		catch (Exception e) {
			System.out.println ("Error loading sound file: " + e);
		}

	}

	public void playClip (int index) {

		if (index == 1 && hitBatSound != null)
			hitBatSound.play();
		else
		if (index == 2 && fallOffSound != null)
			fallOffSound.play();

	}
	public boolean checkAlive(){
		return isAlive;
	}
}