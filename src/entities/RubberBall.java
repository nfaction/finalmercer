package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Circle;
import enums.EType;

public class RubberBall extends Entities{
	private Body rubberBall;
	private static BufferedImage[] staticSprites;
	
	public RubberBall(){
		super(EType.rubberBall);
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/rubberBall.gif"), 1, 1);
		sprite = staticSprites;
		rubberBall = new Body("rubberBall", new Circle(10.0f), 1.0f);
		rubberBall.setRestitution(1.0f);
		rubberBall.setDamping(.001f);	
		rubberBall.setCanRest(true);
	}

	@Override
	public void addObj(World world, float x, float y) {
		rubberBall.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(rubberBall);
	}

	@Override
	public float getX() {
		return rubberBall.getPosition().getX();
	}

	@Override
	public float getY() {
		return rubberBall.getPosition().getY();
	}

	@Override
	public void removeObj(World world) {
		world.remove(rubberBall);

	}

	@Override
	public int gettouchingBodies() {
		return this.rubberBall.getTouchingCount();
	}
	
	public void upDate(){
		super.upDate();
		if (gettouchingBodies() > 0 && soundCount > 20){
			mySoundPlayer.play(baseDir + "Boingey.wav");
			soundCount = 0;
		}
		soundCount++;
		if(gettouchingBodies() > 0){
			soundCount = 0;
		}
	}
}
