package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;
import enums.EType;

public class HRubberWall extends Entities{

	private Body hRubberWall;
	private static BufferedImage[] staticSprites;
	
	public HRubberWall() {
		super(EType.vCWall);
			if(staticSprites == null )
				staticSprites = utils.splitImage(utils.loadImage("Images/hRWall.gif"), 5, 5);
			sprite = staticSprites;
			hRubberWall = new StaticBody("rVertWall", new Box(125.0f, 10.0f));
			hRubberWall.setRestitution(3.0f);
	}

	@Override
	public void addObj(World world, float x, float y) {

		hRubberWall.setPosition(x, y);
		world.add(hRubberWall);
	}

	@Override
	public float getX() {

		return hRubberWall.getPosition().getX();
	}

	@Override
	public float getY() {

		return hRubberWall.getPosition().getY();
	}

	@Override
	public int gettouchingBodies() {

		return this.hRubberWall.getTouchingCount();
	}

	@Override
	public void removeObj(World world) {

		world.remove(hRubberWall);
	}

}
