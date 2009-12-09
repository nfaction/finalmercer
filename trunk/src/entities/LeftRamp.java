package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;
import enums.EType;

public class LeftRamp extends Entities {
	
	private Body lRamp;
	private static BufferedImage[] staticSprites;
	
	public LeftRamp() {
		super(EType.leftRamp);
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/LeftRampSpriteSheet.png"), 5, 5);
		sprite = staticSprites;
		lRamp = new StaticBody("Left Ramp", new Box(225.0f, 25.0f));
		lRamp.setRestitution(1.0f);
		lRamp.setRotation(-.4f);
	}

	@Override
	public void addObj(World world, float x, float y) {
		lRamp.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(lRamp);
	}

	@Override
	public float getX() {
		
		return lRamp.getPosition().getX();
	}

	@Override
	public float getY() {
		
		return lRamp.getPosition().getY();
	}

	@Override
	public void removeObj(World world) {

		world.remove(lRamp);
	}

	@Override
	public int gettouchingBodies() {
		return this.lRamp.getTouchingCount();
	}

}
