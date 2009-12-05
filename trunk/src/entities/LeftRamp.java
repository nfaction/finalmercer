package entities;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;
import enums.EType;

public class LeftRamp extends Entities {

	private Body ground;
	
	public static final int Y_LENGTH = 58;
	public static final int X_LENGTH = 100;
	
	public LeftRamp() {
		super(EType.leftRamp);
		ground = new StaticBody("Ground1", new Box(225.0f, 25.0f));
		ground.setPosition(350.0f, 300);
		ground.setRestitution(1.0f);
		ground.setRotation(-.4f);
	}

	@Override
	public void addObj(World world, float x, float y) {

		ground.setPosition(x, y);
		world.add(ground);
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
