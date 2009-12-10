package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;
import enums.EType;

public class Switch extends Entities {

	private Body s;
	private static BufferedImage[] staticSprites;

	public Switch() {
		super(EType.Switch);
		if (staticSprites == null)
			staticSprites = utils.splitImage(utils
					.loadImage("Images/switchSpriteSheet.gif"), 1, 2);
		sprite = staticSprites;

		s = new StaticBody(toString(), new Box(10f, 20f));
	}

	@Override
	public void addObj(World world, float x, float y) {
		s.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(s);

	}

	public void upDate() {
		super.upDate();
		if (gettouchingBodies() > 0) {
			state = 1;
		}
			if (gettouchingBodies() > 0 && soundCount > 20){
				mySoundPlayer.play(baseDir + "SW1.wav");
				soundCount = 0;
			}
			soundCount++;
			if(gettouchingBodies() > 0){
				soundCount = 0;
			}
		

	}



	@Override
	public float getX() {
		return s.getPosition().getX();
	}

	@Override
	public float getY() {
		return s.getPosition().getY();
	}

	@Override
	public int gettouchingBodies() {
		return s.getTouchingCount();
	}

	@Override
	public void removeObj(World world) {
		world.remove(s);
	}
}
