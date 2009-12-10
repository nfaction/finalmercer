package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import enums.EType;

public class Poop extends Entities{
	private Body poop;
	private static BufferedImage[] staticSprites;
	
	public Poop(){
		super(EType.poop);
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/"), 5, 5);
		sprite = staticSprites;
		poop = new Body("poop", new Box(10,20), 10f);
	}

	@Override
	public void addObj(World world, float x, float y) {
		poop.setPosition(x, y);
		world.add(poop);
		
	}

	@Override
	public float getX() {
		return poop.getPosition().getX();
	}

	@Override
	public float getY() {
		return poop.getPosition().getY();
	}

	@Override
	public void removeObj(World world) {
		world.remove(poop);

	}

	@Override
	public int gettouchingBodies() {
		return this.poop.getTouchingCount();
	}
}
