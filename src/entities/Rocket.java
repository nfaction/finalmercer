package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.vector.Vector;
import enums.EType;

public class Rocket extends Entities {

	private Body rocket;
	private static BufferedImage[] staticSprites;
	
	public Rocket() {
		super(EType.rocket);
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/rocket.gif"), 5, 5);
		sprite = staticSprites;
		rocket = new Body("rocket", new Box(30.0f, 65.0f), .5f);
		rocket.setGravityEffected(false);
	}
	
	@Override
	public void addObj(World world, float x, float y) {

		rocket.setPosition(x, y);
		world.add(rocket);
	}

	@Override
	public float getX() {
		return rocket.getPosition().getX();
	}

	@Override
	public float getY() {
		return rocket.getPosition().getY();
	}

	@Override
	public void removeObj(World world) {
		world.remove(rocket);

	}

	@Override
	public void upDate() {
		super.upDate();
		
		if(rocket.isMoveable()){
		rocket.adjustVelocity(new Vector(0.0f, -1.0f));
		} else {
			rocket.resetBias();
			rocket.adjustVelocity(new Vector(0.0f, 1000.0f));
			rocket.adjustVelocity(new Vector(0.0f, 1000.0f));
			rocket.adjustVelocity(new Vector(0.0f, 1000.0f));
			rocket.adjustVelocity(new Vector(0.0f, 1000.0f));
			rocket.adjustVelocity(new Vector(0.0f, 1000.0f));
			rocket.adjustVelocity(new Vector(0.0f, 1000.0f));
		}
		
	}

	@Override
	public int gettouchingBodies() {
		return this.rocket.getTouchingCount();
	}

}
