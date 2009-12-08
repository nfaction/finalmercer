package entities;

import engine.World;

import engine.shapes.*;
import enums.EType;

public class PingPongBall extends Entities{

	private Body ppBall;
	
	//////////Info for sprite sheet/////////////////////
	public static final int pingPongWidth = 16;
	public static final int pingPongHeight = 16;
	public static final int Y_LENGTH = 8;
	public static final int X_LENGTH = 8;
	public int bbX = 0;
	public int bbY = 0;
	////////////////////////////////////////////////////
	
	public PingPongBall() {
		
		super(EType.pingPongBall);
		ppBall = new Body("Ping-Pong Ball", new Circle(7.0f), 2.50f);
		ppBall.setRestitution(.8f);
		ppBall.setDamping(.00009f);
		ppBall.setCanRest(true);
		ppBall.configureRestingBodyDetection(1f, 1f, 1f);
		setImagePath("Images/pingPongBallSpriteSheet.png");
	}

	@Override
	public void addObj(World world, float x, float y) {

		ppBall.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(ppBall); 
	}

	@Override
	public float getX() {
		
		return ppBall.getPosition().getX();
	}

	@Override
	public float getY() {
		return ppBall.getPosition().getY();
	}

	@Override
	public void removeObj(World world) {
		world.remove(ppBall);
		
	}

	@Override
	public void upDate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSpriteX(){

		return bbX;
	}

	@Override
	public int getSpriteY() {

		return bbY;
	}

	@Override
	public String toString() {

		return "pingpongball";
	}

	@Override
	public int getXLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getYLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int gettouchingBodies() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSpriteHeight() {

		return pingPongHeight;
	}

	@Override
	public int getSpriteWidth() {

		return pingPongWidth;
	}

	@Override
	public void setSprite() {
		// TODO Auto-generated method stub
		
	}
}
