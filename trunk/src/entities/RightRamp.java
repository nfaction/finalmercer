package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;
import enums.EType;

public class RightRamp extends Entities {

	private Body rRamp;
	private static BufferedImage[] staticSprites;
		
	public RightRamp() {
		super(EType.rightRamp);
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/BasketBallSpriteSheet.png"), 5, 5);
		sprite = staticSprites;

		rRamp = new StaticBody("Right Ramp", new Box(225.0f, 25.0f));
		rRamp.setPosition(350.0f, 300);
		rRamp.setRestitution(1.0f);
		rRamp.setRotation(.4f);
		setImagePath("Image/rightRampSpriteSheet.png");
	}

	@Override
	public void addObj(World world, float x, float y) {

		rRamp.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(rRamp);
	}

	@Override
	public float getX() {
		return rRamp.getPosition().getX();
	}

	@Override
	public float getY() {
		return rRamp.getPosition().getY();
	}

	@Override
	public void removeObj(World world) {

		world.remove(rRamp);
	}

	@Override
	public int gettouchingBodies() {
		return rRamp.getTouchingCount();
	}
}
