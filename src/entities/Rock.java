package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import enums.EType;

public class Rock extends Entities {

	private Body rock;
	private static BufferedImage[] staticSprites;
	
	public Rock() {
		super(EType.rock);
		if (staticSprites == null)
			staticSprites = utils.splitImage(utils
					.loadImage("Images/rock.gif"), 1, 1);
		sprite = staticSprites;

		rock = new Body(new Box(80.0f, 50.0f), 1000.0f);
		rock.setCanRest(true);
	}

	@Override
	public void addObj(World world, float x, float y) {

		rock.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(rock);
	}

	@Override
	public float getX() {

		return rock.getPosition().getX();
	}

	@Override
	public float getY() {

		return rock.getPosition().getY();
	}

	@Override
	public int gettouchingBodies() {

		return this.rock.getTouchingCount();
	}

	@Override
	public void removeObj(World world) {

		world.remove(rock);
	}
}
