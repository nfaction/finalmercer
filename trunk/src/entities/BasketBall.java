package entities;

import engine.World;
import engine.shapes.*;
import enums.EType;

public class BasketBall extends Entities {

	private Body bBall;

	//////////Info for sprite sheet/////////////////////
	public static final int basketBallWidth = 45;
	public static final int basketBallHeight = 45;
	public int bbX = 0;
	public int bbY = 0;
	////////////////////////////////////////////////////
	
	public BasketBall() {
		
		super(EType.basketball);
		bBall = new Body("BasketBall", new Circle(20.0f), 2.0f);
		bBall.setRestitution(1.0f);
		bBall.setDamping(.001f);
		bBall.setCanRest(true);
		
		setSpritePath("Images/BasketBallSpriteSheet.png");
		setImagePath("Images/basketball.gif");
		Y_LENGTH = 24;
		X_LENGTH = 24;
	}

	public int getSpriteWidth() {
		return basketBallWidth;
	}

	public int getSpriteHeight() {

		return basketBallHeight;
	}

	public int getSpriteX() {
		return bbX;
	}

	public int getSpriteY() {
		return bbY;
	}

	public void setSprite() {

		float rotation = bBall.getRotation();
		rotation = rotation % (2f*(new Float(Math.PI)));

		if (rotation >= 0f && rotation < 1.046f)
			state = 0;
		else if (rotation >= 1.046f && rotation < 2.093)
			state = 1;
		else if (rotation >= 2.093 && rotation < 3.14)
			state = 2;
		else if (rotation >= 3.144 && rotation < 4.190)
			state = 3;
		else if (rotation >= 4.190 && rotation < 5.236f)
			state = 2;
		else if (rotation >= 5.236f && rotation < 6.3f)
			state = 3;
		else if (rotation <= 0f && rotation > -1.046f)
			state = 3;
		else if (rotation <= -1.046f && rotation > -2.093)
			state = 2;
		else if (rotation <= -2.093 && rotation > -3.14)
			state = 1;
		else if (rotation <= -3.144 && rotation > -4.190)
			state = 0;
		else if (rotation <= -4.190 && rotation > -5.236f)
			state = 3;
		else if (rotation <= -5.236f && rotation > -6.3f)
			state = 2;

		System.out.println(rotation);

		bbX = ((state * 50));
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

	public String toString() {
		return "basketball";
	}

	@Override
	public int gettouchingBodies() {
		return bBall.getTouchingCount();
	}
}
