package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Circle;
import engine.vector.Vector;
import enums.EType;

public class Balloon extends Entities {

	private Body balloon;
	private static BufferedImage[] staticSprites;
	private int count = 0;
	
	public Balloon() {
		super(EType.balloon, "Images/Balloon.gif");
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/balloonSpriteSheet.png"), 5, 5);
		sprite = staticSprites;
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
		if(!balloon.isMoveable()){
			state = 4;
			if(count < 4)
			state = 2;
			if(count <2)
				state =1;
		}
		
	}

	@Override
	public int gettouchingBodies() {
		return this.balloon.getTouchingCount();
	}

}
