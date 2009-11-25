package entities;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Circle;
import engine.vector.Vector;

public class Balloon extends Entities {

	private Body balloon;

	public Balloon(String objType) {
		super(objType);
		balloon = new Body("Balloon", new Circle(15.0f), .5f);
		balloon.setPosition(200.0f, 300.0f);
		balloon.setGravityEffected(false);
		//balloon.setForce(0.0f, -1000.0f);
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

}
