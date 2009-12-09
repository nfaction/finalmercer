package entities;

import java.awt.Color;
import java.awt.image.BufferedImage;

import engine.World;

import engine.shapes.Body;

import engine.shapes.Circle;
import enums.EType;

public strictfp class BowlingBall extends Entities {

	private Body bowlBall;
	private static BufferedImage[] staticSprites;

	public BowlingBall() {
		super(EType.bowlingball, "Images/bowlingBallSpriteSheet.png");
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/bowlingBallSpriteSheet.png"), 5, 5);
		sprite = staticSprites;
		
		bowlBall = new Body("BowlingBall", new Circle((15.0f) + 2.8f), 15.0f);
		bowlBall.setRestitution(.5f);
		bowlBall.setDamping(.01f);
		bowlBall.setCanRest(true);
	}

	@Override
	public void addObj(World world, float x, float y) {
		bowlBall.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(bowlBall);

	}

	@Override
	public float getX() {
		return bowlBall.getPosition().getX();
	}

	@Override
	public float getY() {
		return bowlBall.getPosition().getY();
	}

	@Override
	public void removeObj(World world) {
		world.remove(bowlBall);

	}

	@Override
	public int gettouchingBodies() {
		return this.bowlBall.getTouchingCount();
	}
	
	public BufferedImage getSpriteImage() {
		float rotation = bowlBall.getRotation();
		rotation = rotation % (2f * (new Float(Math.PI)));

		BufferedImage temp = sprite[0];
		temp = utils.rotate(temp, rotation);
		temp = utils.makeColorTransparent(temp, Color.black);
		return temp;

	}
}
