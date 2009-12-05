package entities;

import engine.World;
import engine.shapes.*;
import enums.EType;

public class BasketBall extends Entities {

	private Body bBall;

	public static final int Y_LENGTH = 24;
	public static final int X_LENGTH = 24;

	// ///////Info for sprite sheet////////////////////
	public static final int basketBallWidth = 45;
	public static final int basketBallHeight = 45;
	public static int bbX = 0;
	public static int bbY = 0;
	public int state = 0;
	///////////////////////////////////////////////////

	public BasketBall() {
		super(EType.basketball);
		bBall = new Body("BasketBall", new Circle(20.0f), 2.0f);
		bBall.setRestitution(1.0f);
		bBall.setDamping(.001f);
		bBall.setCanRest(true);
	}
	
	public int getXLength(){
		return X_LENGTH;
	}
	
	public int getYLength(){
		return Y_LENGTH;
	}

	/**
	 * @return width of basketball
	 */
	public int getSpriteWidth() {

		return basketBallWidth;
	}

	/**
	 * @return height of basketball
	 */
	public int getSpriteHeight() {

		return basketBallHeight;
	}

	/**
	 * @return starting X point for sprite state
	 */
	public int getSpriteX() {

		return bbX;
	}

	/**
	 * @return starting Y point for sprite state
	 */
	public int getSpriteY() {

		return bbY;
	}

	public void setSprite() {

		switch (this.getState()) {

		case 1:
			bbX = 45;
			bbY = 45;
			break;
		case 2:
			bbX = 90;
			bbY = 90;
			break;
		case 3:
			bbX = 135;
			bbY = 135;
			break;
		case 4:
			bbX = 180;
			bbY = 180;
			break;
		case 5:
			bbX = 225;
			bbY = 225;
			break;
		case 6:
			bbX = 270;
			bbY = 270;
			break;
		}
	}

	@Override
	public float getX() {
		return bBall.getPosition().getX();
	}

	@Override
	public float getY() {
		return bBall.getPosition().getY();
	}

	@Override
	public void addObj(World world, float x, float y) {

		bBall.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(bBall);

	}

	@Override
	public void removeObj(World world) {
		world.remove(bBall);
	}

	@Override
	public void upDate() {
		
		setImageLocations();

	}

	public String toString() {
		return "basketball";
		// should we use bBall.getName() instead for consistency ?????
	}
}
