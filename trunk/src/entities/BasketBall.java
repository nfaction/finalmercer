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

		int dir = rollDirection();
		int curState = 0;
		
		if(curState == 6 && dir == 1)
			curState = 1;
		else if(curState == 0 && dir == -1)
			curState = 6;
		else if(dir == 1)
			curState++;
		else if(dir == -1)
			curState--;
		

		bbX = curState * 45;
		bbY = curState * 45;
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
