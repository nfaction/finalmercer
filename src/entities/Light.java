package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;
import enums.EType;

public class Light extends Entities {

	private Body light;
	private boolean field = false;
	private static BufferedImage[] staticSprites;
	
	public Light() {
		super(EType.light);
		if(staticSprites == null ) {
			staticSprites = new BufferedImage[2];
			staticSprites[0] = utils.loadImage("Images/light.gif");
			staticSprites[1] = utils.loadImage("Images/lightON.gif");
		}
		sprite = staticSprites;

		light = new StaticBody("light", new Box(30.0f, 45.0f));
	}
	@Override
	public void addObj(World world, float x, float y) {

		light.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(light);
	}
	@Override
	public float getX() {

		return light.getPosition().getX();
	}
	@Override
	public float getY() {

		return light.getPosition().getY();
	}
	@Override
	public int gettouchingBodies() {

		return light.getTouchingCount();
	}
	@Override
	public void removeObj(World world) {

		world.remove(light);
	}
	
	public boolean inField(){
		
		return field;
	}
	
	public void setField(boolean b) {
		field = b;
	}

}
