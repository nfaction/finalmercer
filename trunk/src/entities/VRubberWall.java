package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;
import enums.EType;

public class VRubberWall extends Entities{

	private Body vRubberWall;
	private static BufferedImage[] staticSprites;
	
	public VRubberWall() {
		super(EType.vCWall);
			if(staticSprites == null )
				staticSprites = utils.splitImage(utils.loadImage("Images/vRWall.gif"), 5, 5);
			sprite = staticSprites;
			vRubberWall = new StaticBody("rVertWall", new Box(10.0f, 125.0f));
			vRubberWall.setRestitution(3.0f);
	}

	@Override
	public void addObj(World world, float x, float y) {

		vRubberWall.setPosition(x, y);
		world.add(vRubberWall);
	}

	@Override
	public float getX() {

		return vRubberWall.getPosition().getX();
	}

	@Override
	public float getY() {

		return vRubberWall.getPosition().getY();
	}

	@Override
	public int gettouchingBodies() {

		return this.vRubberWall.getTouchingCount();
	}

	@Override
	public void removeObj(World world) {

		world.remove(vRubberWall);
	}

}
