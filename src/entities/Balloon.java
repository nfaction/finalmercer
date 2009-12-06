package entities;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Circle;
import engine.vector.Vector;
import enums.EType;

public class Balloon extends Entities {

	private Body balloon;

	public static final int Y_LENGTH = 25;
	public static final int X_LENGTH = 25;
	
	public Balloon() {
		super(EType.balloon);
		balloon = new Body("Balloon", new Circle(15.0f), .5f);
		balloon.setPosition(200.0f, 300.0f);
		balloon.setGravityEffected(false);	
	}

	@Override
	public void addObj(World world, float x, float y) {
		
		balloon.setPosition(x, y);
		world.add(balloon);
	}

	@Override
	public float getX() {
		
		return balloon.getPosition().getX();
	}

	@Override
	public float getY() {

		return balloon.getPosition().getY();
	}

	@Override
	public void removeObj(World world) {
		world.remove(balloon);

	}

	@Override
	public void upDate() {
		balloon.adjustVelocity(new Vector(0.0f, -1.0f));
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

	@Override
	public String toString() {

		return "balloon";
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
