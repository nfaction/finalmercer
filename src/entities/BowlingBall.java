package entities;

import engine.World;

import engine.shapes.Body;

import engine.shapes.Circle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public strictfp class BowlingBall extends Ball {

	private Body bowlBall;
	private BufferedImage bowlingBallimgs;

	public BowlingBall() {

		super("");
		bowlBall = new Body("BowlingBall", new Circle(15.0f), 16.0f);
		bowlBall.setRestitution(.5f);
		bowlBall.setDamping(.01f);
		bowlBall.setCanRest(true);

		try {
			bowlingBallimgs = ImageIO.read(new File(
					"Images/bowlingBallSpriteSheet.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BowlingBall(float x, float y) {
		super("");
	}

	/**
	 * This method will get the subimage which represents the state of the
	 * bowlingball.
	 * 
	 * @param i
	 *            , the ball for which we want an image
	 * @return The image for the ball i
	 */
	public BufferedImage getBBImage(char dir, int st) {

		// Need to select which row / col to use based on direction and state of
		// the bowling ball
		int row = 0;// getRow(i.getDirection());
		int col = 0;// getCol(i.getState());

		// The size of 1 bowling ball is 46 x 46
		// The call to getSubimage has the following parameters: startX, startY,
		// width, height
		BufferedImage thisBowlBall = bowlingBallimgs.getSubimage(46 * col,
				46 * row, 46, 46);

		return thisBowlBall;
	}

	/**
	 * This method will return the row that the bowling ball will be on in the
	 * sprite sheet
	 * 
	 * @param c
	 *            , direction of bowling ball c
	 * @return row of bowling ball's image
	 */
	public int getDirection(char c) {

		switch (c) {
		case 'L':
			return 0;
		case 'R':
			return 1;
		}

		return -1;
	}

	/**
	 * This method will return the column that the card will be on in the sprite
	 * sheet
	 * 
	 * @param r
	 *            , Rank of card c
	 * @return column of card c's image
	 */
	public int getState(int i) {

		switch (i) {
		case 0: // state of 0 means ball is NOT rolling
			return 0;
		case 1: // state of 1 means ball IS rolling

			return 1;
		}

		return -1;
	}

	@Override
	public void addObj(World world, float x, float y) {
		bowlBall.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(bowlBall);

	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeObj(World world) {
		// TODO Auto-generated method stub

	}

	@Override
	public void upDate() {
		// TODO Auto-generated method stub
		
	}

}
