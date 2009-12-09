package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;
import enums.EType;

public class StraightRamp extends Entities {

	private Body sRamp;

	private static BufferedImage[] staticSprites;
	
	public StraightRamp() {
		super(EType.straightRamp);
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/StraightRamp.gif"), 1, 1);
		sprite = staticSprites;

	
		sRamp = new StaticBody("Straight Ramp", new Box(225.0f, 25.0f));
		sRamp.setPosition(350.0f, 300);
		sRamp.setRestitution(1.0f);
	}

	@Override
	public void addObj(World world, float x, float y) {

		sRamp.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(sRamp);
	}

	@Override
	public float getX() {
		
		return sRamp.getPosition().getX();
	}

	@Override
	public float getY() {
		
		return sRamp.getPosition().getY();
	}

	@Override
	public void removeObj(World world) {

		world.remove(sRamp);
	}

	@Override
	public int gettouchingBodies() {
		return sRamp.getTouchingCount();
	}
}
