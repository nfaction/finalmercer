package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import enums.EType;

public class Bomb extends Entities {
	private Body bomb;
	private static BufferedImage[] staticSprites;
	
	public Bomb(){
		super(EType.bomb);
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/bombSpriteSheet.gif"), 2, 1);
		sprite = staticSprites;
		bomb = new Body("bomb", new Box(10,20), 10f);
	}

	@Override
	public void addObj(World world, float x, float y) {
		bomb.setPosition(x, y);
		world.add(bomb);
		
	}

	@Override
	public float getX() {
		return bomb.getPosition().getX();
	}

	@Override
	public float getY() {
		return bomb.getPosition().getY();
	}

	@Override
	public void removeObj(World world) {
		world.remove(bomb);

	}

	public void upDate() {
		super.upDate();
		if (gettouchingBodies() > 0) {
			state = 1;
		}
			super.upDate();
			if (gettouchingBodies() > 0 && soundCount > 20){
				mySoundPlayer.play(baseDir + "bomb.wav");
				soundCount = 0;
			}
			soundCount++;
			if(gettouchingBodies() > 0){
				soundCount = 0;
			
		
	}
	}
	
	@Override
	public int gettouchingBodies() {
		return this.bomb.getTouchingCount();
	}
	
}
