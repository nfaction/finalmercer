package entities;

import java.awt.image.BufferedImage;

import engine.World;

import engine.shapes.*;
import enums.EType;

public class PingPongBall extends Entities{

	private Body ppBall;

	private static BufferedImage[] staticSprites;
	public PingPongBall() {
		
		super(EType.pingPongBall);
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/PingPongBall.gif"), 1, 1);
		sprite = staticSprites;

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
	public int gettouchingBodies() {
		
		return ppBall.getTouchingCount();
	}
	
	public void upDate(){
		super.upDate();
		if (gettouchingBodies() > 0 && soundCount > 20){
			mySoundPlayer.play(baseDir + "Tap.wav");
			soundCount = 0;
		}
		soundCount++;
		if(gettouchingBodies() > 0){
			soundCount = 0;
		}
	}
	
}
