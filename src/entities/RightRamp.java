package entities;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;
import enums.EType;

public class RightRamp extends Entities {

	private Body rRamp;
	
	public static final int Y_LENGTH = 58;
	public static final int X_LENGTH = 100;
	
	public RightRamp() {
		super(EType.rightRamp);
		rRamp = new StaticBody("Right Ramp", new Box(225.0f, 25.0f));
		rRamp.setPosition(350.0f, 300);
		rRamp.setRestitution(1.0f);
		rRamp.setRotation(.4f);
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSpriteY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {

		return "rightramp";
	}
	
	

}
