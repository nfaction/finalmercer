package entities;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;
import enums.EType;

public class RightRamp extends Entities {

	private Body rRamp;
	
//////////Info for sprite sheet/////////////////////
	public static final int rightRampWidth = 115;
	public static final int rightRampHeight = 200;
	public static final int Y_LENGTH = 58;
	public static final int X_LENGTH = 100;
	public int bbX = 0;
	public int bbY = 0;
	////////////////////////////////////////////////////
	
	public RightRamp() {
		super(EType.rightRamp);
		rRamp = new StaticBody("Right Ramp", new Box(225.0f, 25.0f));
		rRamp.setPosition(350.0f, 300);
		rRamp.setRestitution(1.0f);
		rRamp.setRotation(.4f);
		setImagePath("Image/rightRampSpriteSheet.png");
	}

	@Override
	public void addObj(World world, float x, float y) {

		rRamp.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(rRamp);
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

		world.remove(rRamp);
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

	@Override
	public String toString() {

		return "rightramp";
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

		return rightRampHeight;
	}

	@Override
	public int getSpriteWidth() {

		return rightRampWidth;
	}

	@Override
	public void setSprite() {
		// TODO Auto-generated method stub
		
	}
	
	

}
