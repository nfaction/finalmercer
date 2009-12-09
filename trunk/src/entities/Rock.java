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
					.loadImage("Images/weight.gif"), 1, 1);
		sprite = staticSprites;

		weight = new Body(new Box(50.0f, 50.0f), 10000.0f);
		weight.setCanRest(true);
	}

	@Override
	public void addObj(World world, float x, float y) {
		// TODO Auto-generated method stub

	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int gettouchingBodies() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeObj(World world) {
		// TODO Auto-generated method stub

	}

}
