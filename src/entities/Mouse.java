package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import enums.EType;

public class Mouse extends Entities{
	private Body mouse;
	private static BufferedImage[] staticSprites;
	
	public Mouse(){
		super(EType.mouse);
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/"), 5, 5);
		sprite = staticSprites;
		mouse = new Body("mouse", new Box(10,20), 10f);
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
	
	

	@Override
	public int gettouchingBodies() {
		return this.mouse.getTouchingCount();
	}
}
