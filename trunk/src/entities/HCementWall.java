package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;
import enums.EType;

public class HCementWall extends Entities{

	private Body hCementWall;
	private static BufferedImage[] staticSprites;
	
	public HCementWall() {
		super(EType.vCWall);
			if(staticSprites == null )
				staticSprites = utils.splitImage(utils.loadImage("Images/vCWall.gif"), 5, 5);
			sprite = staticSprites;
			hCementWall = new StaticBody("cHorzWall", new Box(125.0f, 10.0f));
	}

	@Override
	public void addObj(World world, float x, float y) {

		hCementWall.setPosition(x, y);
		world.add(hCementWall);
	}

	@Override
	public float getX() {

		return hCementWall.getPosition().getX();
	}

	@Override
	public float getY() {

		return hCementWall.getPosition().getY();
	}

	@Override
	public int gettouchingBodies() {

		return this.hCementWall.getTouchingCount();
	}

	@Override
	public void removeObj(World world) {

		world.remove(hCementWall);
	}

}
