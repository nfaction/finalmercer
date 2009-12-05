package entities;

import engine.World;

import engine.shapes.Body;

import engine.shapes.Circle;
import enums.EType;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public strictfp class BowlingBall extends Entities {

	private Body bowlBall;
	
	public static final int Y_LENGTH = 23;
	public static final int X_LENGTH = 23;

	public BowlingBall() {

		super(EType.bowlingball);
		bowlBall = new Body("BowlingBall", new Circle(15.0f), 7257.0f);
		bowlBall.setRestitution(.5f);
		bowlBall.setDamping(.01f);
		bowlBall.setCanRest(true);

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

	@Override
	public int getSpriteX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSpriteY() {
		// TODO Auto-generated method stub
		return 0;
	}

}
