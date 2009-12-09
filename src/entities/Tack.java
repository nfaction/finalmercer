package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;
import enums.EType;

public class Tack extends Entities {

	private Body tack;
	private static BufferedImage[] staticSprites;
	
	public Tack() {
		super(EType.tack);
		if(staticSprites == null )
			staticSprites = utils.splitImage(utils.loadImage("Images/tack.gif"), 1, 1);
		sprite = staticSprites;
		tack = new StaticBody("tack", new Box(2.0f, 15.0f));
		tack.setRestitution(0.0f);
		tack.setGravityEffected(false);
	}
	@Override
	public void addObj(World world, float x, float y) {

		tack.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(tack);
	}
	@Override
	public float getX() {

		return tack.getPosition().getX();
	}
	@Override
	public float getY() {

		return tack.getPosition().getY();
	}
	@Override
	public int gettouchingBodies() {

		return tack.getTouchingCount();
	}
	@Override
	public void removeObj(World world) {

		world.remove(tack);
	}
}
