package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import enums.EType;

public class Weight extends Entities{

	private Body weight;
	private static BufferedImage[] staticSprites;

	public Weight() {
		
		super(EType.weight);
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/weight.gif"), 1, 1);
		sprite = staticSprites;
		
		weight = new Body(new Box(50.0f, 50.0f), 10000.0f);
		weight.setCanRest(true);
	}

	@Override
	public void addObj(World world, float x, float y) {

		weight.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(weight);
	}

	@Override
	public float getX() {

		return weight.getPosition().getX();
	}

	@Override
	public float getY() {

		return weight.getPosition().getY();
	}

	@Override
	public int gettouchingBodies() {

		return this.weight.getTouchingCount();
	}

	@Override
	public void removeObj(World world) {
		
		world.remove(weight);
	}

}
