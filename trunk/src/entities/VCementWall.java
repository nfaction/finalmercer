package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.Circle;
import engine.shapes.StaticBody;
import enums.EType;

public class VCementWall extends Entities{

	private Body vCementWall;
	private static BufferedImage[] staticSprites;
	
	public VCementWall() {
		super(EType.vCWall);
			if(staticSprites == null )
				staticSprites = utils.splitImage(utils.loadImage("Images/vCWall.gif"), 5, 5);
			sprite = staticSprites;
			vCementWall = new StaticBody("cVertWall", new Box(10.0f, 125.0f));
		
	}

	@Override
	public void addObj(World world, float x, float y) {

		vCementWall.setPosition(x, y);
		world.add(vCementWall);
	}

	@Override
	public float getX() {

		return vCementWall.getPosition().getX();
	}

	@Override
	public float getY() {

		return vCementWall.getPosition().getY();
	}

	@Override
	public int gettouchingBodies() {

		return this.vCementWall.getTouchingCount();
	}

	@Override
	public void removeObj(World world) {

		world.remove(vCementWall);
	}

}
