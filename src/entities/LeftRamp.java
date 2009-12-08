package entities;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;
import enums.EType;

public class LeftRamp extends Entities {
	
	private Body lRamp;
	
//////////Info for sprite sheet/////////////////////
	public static final int leftRampWidth = 200;
	public static final int leftRampHeight = 115;
	public static final int Y_LENGTH = 58;
	public static final int X_LENGTH = 100;
	public int bbX = 0;
	public int bbY = 0;
	////////////////////////////////////////////////////
	
	public LeftRamp() {
		super(EType.leftRamp);
		lRamp = new StaticBody("Left Ramp", new Box(225.0f, 25.0f));
		lRamp.setRestitution(1.0f);
		lRamp.setRotation(-.4f);
	}

	@Override
	public void addObj(World world, float x, float y) {

		lRamp.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(lRamp);
	}

	@Override
	public float getX() {
		
		return lRamp.getPosition().getX();
	}

	@Override
	public float getY() {
		
		return lRamp.getPosition().getY();
	}

	@Override
	public void removeObj(World world) {

		world.remove(lRamp);
	}

	@Override
	public void upDate() {

		setImageLocations();
	}

	@Override
	public int getSpriteX() {

		return bbX;
	}

	@Override
	public int getSpriteY() {

		return bbY;
	}

	public String toString() {
		return "leftramp";
	}

	@Override
	public int getXLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getYLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int gettouchingBodies() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSpriteHeight() {

		return leftRampHeight;
	}

	@Override
	public int getSpriteWidth() {

		return leftRampWidth;
	}

	@Override
	public void setSprite() {
		// TODO Auto-generated method stub
		
	}
}
