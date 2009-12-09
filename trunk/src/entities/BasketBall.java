package entities;

import java.awt.Color;
import java.awt.image.BufferedImage;
import engine.World;
import engine.shapes.*;
import enums.EType;

public class BasketBall extends Entities {

	private Body bBall;
	private static BufferedImage[] staticSprites;

	public BasketBall() {
		super(EType.basketball, "Images/basketball.gif");
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/BasketBallSpriteSheet.png"), 5, 5);
		sprite = staticSprites;
		bBall = new Body("BasketBall", new Circle(20.0f), 2.0f);
		bBall.setRestitution(1.0f);
		bBall.setDamping(.001f);
		bBall.setCanRest(true);
	}

	@Override
	public float getX() {
		return bBall.getPosition().getX();
	}

	@Override
	public float getY() {
		return bBall.getPosition().getY();
	}

	@Override
	public void addObj(World world, float x, float y) {
		bBall.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(bBall);
	}

	@Override
	public void removeObj(World world) {
		world.remove(bBall);
	}

	@Override
	public int gettouchingBodies() {
		return bBall.getTouchingCount();
	}

	public BufferedImage getSpriteImage() {
		float rotation = bBall.getRotation();
		rotation = rotation % (2f * (new Float(Math.PI)));

		BufferedImage temp = sprite[0];
		temp = utils.rotate(temp, rotation);
		temp = utils.makeColorTransparent(temp, Color.black);
		return temp;

	}
}
