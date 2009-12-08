package entities;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Circle;
import engine.vector.Vector;
import enums.EType;

public class Balloon extends Entities {

	private Body balloon;
	public int bbX = 0;
	public int bbY = 0;
	
	public Balloon() {
		super(EType.balloon,"Images/balloonSpriteSheet.png", "Images/Balloon.gif", 25, 25, 45, 45);
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
		super.upDate();
		balloon.adjustVelocity(new Vector(0.0f, -1.0f));
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
	public int gettouchingBodies() {
		return this.balloon.getTouchingCount();
	}

	@Override
	public void setSprite() {
		// TODO setSprite() in Balloon
	}

}
