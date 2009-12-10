package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import enums.EType;

public class Poop extends Entities{
	private Body poop;
	private static BufferedImage[] staticSprites;
	
	public Poop(){
		super(EType.poop);
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/poopSpriteSheet.gif"), 2, 1);
		sprite = staticSprites;
		poop = new Body("poop", new Box(5,5), 10f);
	}

	@Override
	public void addObj(World world, float x, float y) {
		poop.setPosition(x, y);
		world.add(poop);
		
	}

	@Override
	public float getX() {
		return poop.getPosition().getX();
	}

	@Override
	public float getY() {
		return poop.getPosition().getY();
	}

	@Override
	public void removeObj(World world) {
		world.remove(poop);

	}

	@Override
	public int gettouchingBodies() {
		return this.poop.getTouchingCount();
	}
	
	public void upDate(){
		super.upDate();
		if(gettouchingBodies()>1){
			state = 1;
		}
		if (gettouchingBodies() > 0 && soundCount > 20){
			mySoundPlayer.play(baseDir + "tada.wav");
			soundCount = 0;
		}
		soundCount++;
		if(gettouchingBodies() > 0){
			soundCount = 0;
		}
	}
}
