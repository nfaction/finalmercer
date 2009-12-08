package entities;

import engine.World;

import engine.shapes.Body;

import engine.shapes.Circle;
import enums.EType;

public strictfp class BowlingBall extends Entities {

	private Body bowlBall;
	public int bbX = 0;
	public int bbY = 0;

	public BowlingBall() {
		super(EType.bowlingball, "Images/bowlingBallSpriteSheet.png",
				"Images/bowling ball.gif", 18, 18, 35, 35);
		bowlBall = new Body("BowlingBall", new Circle(15.0f), 7257.0f);
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
	public int getSpriteX() {
		return bbX;
	}

	@Override
	public int getSpriteY() {
		return bbY;
	}

	@Override
	public int gettouchingBodies() {
		return this.bowlBall.getTouchingCount();
	}

	@Override
	public void setSprite() {
		// TODO setSprite() for BowlingBall
	}

}
