package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import enums.EType;

public class StickMan extends Entities{
	
	private Body man;
	private static BufferedImage[] staticSprites;
	
	public StickMan(){
		super(EType.stickMan);
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/stickManSpriteSheet"), 5, 5);
		sprite = staticSprites;
		man = new Body("man", new Box(10,20), 10f);
	}

	@Override
	public void addObj(World world, float x, float y) {
		man.setPosition(x, y);
		world.add(man);
		
	}

	@Override
	public float getX() {
		return man.getPosition().getX();
	}

	@Override
	public float getY() {
		return man.getPosition().getY();
	}

	@Override
	public void removeObj(World world) {
		world.remove(man);

	}

	@Override
	public int gettouchingBodies() {
		return this.man.getTouchingCount();
	}

}
