package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import enums.EType;

public class RubberBall extends Entities{
	private Body rubberBall;
	private static BufferedImage[] staticSprites;
	
	public RubberBall(){
		super(EType.rubberBall);
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/rubberBall.gif"), 5, 5);
		sprite = staticSprites;
		rubberBall = new Body("rubberBall", new Box(10,20), 10f);
	}

	@Override
	public void addObj(World world, float x, float y) {
		rubberBall.setPosition(x, y);
		world.add(rubberBall);
		
	}

	@Override
	public float getX() {
		return rubberBall.getPosition().getX();
	}

	@Override
	public float getY() {
		return rubberBall.getPosition().getY();
	}

	@Override
	public void removeObj(World world) {
		world.remove(rubberBall);

	}

	@Override
	public int gettouchingBodies() {
		return this.rubberBall.getTouchingCount();
	}
}
