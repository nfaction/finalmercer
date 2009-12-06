package entities;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;
import enums.EType;

public class StraightRamp extends Entities {

	private Body sRamp;
	
	public static final int Y_LENGTH = 58;
	public static final int X_LENGTH = 100;
	
	public StraightRamp() {
		super(EType.straightRamp);
		sRamp = new StaticBody("Straight Ramp", new Box(225.0f, 25.0f));
		sRamp.setPosition(350.0f, 300);
		sRamp.setRestitution(1.0f);
	}

	@Override
	public void addObj(World world, float x, float y) {

		sRamp.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(sRamp);
	}

	@Override
	public float getX() {
		
		return sRamp.getPosition().getX();
	}

	@Override
	public float getY() {
		
		return sRamp.getPosition().getY();
	}

	@Override
	public void removeObj(World world) {

		world.remove(sRamp);
	}

	@Override
	public void upDate() {

		setImageLocations();
	}

	@Override
	public int getSpriteX(int count) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSpriteY() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String toString() {
		return "straightramp";
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
}
