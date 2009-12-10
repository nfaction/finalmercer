package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.vector.Vector;
import enums.EType;

public class Mouse extends Entities{
	private Body mouse;
	private static BufferedImage[] staticSprites;
	
	public Mouse(){
		super(EType.mouse);
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/mouseSpriteSheet.gif"), 2, 1);
		sprite = staticSprites;
		mouse = new Body("mouse", new Box(75,20), 10f);
		mouse.adjustVelocity(new Vector(10.0f, 0f));
	}

	@Override
	public void addObj(World world, float x, float y) {
		mouse.setPosition(x, y);
		world.add(mouse);
		
	}

	@Override
	public float getX() {
		return mouse.getPosition().getX();
	}

	@Override
	public float getY() {
		return mouse.getPosition().getY();
	}

	@Override
	public void removeObj(World world) {
		world.remove(mouse);

	}
	
	public void upDate(){
		super.upDate();
		mouse.adjustVelocity(new Vector(1.0f, 0f));
	}
	

	@Override
	public int gettouchingBodies() {
		return this.mouse.getTouchingCount();
	}
}
