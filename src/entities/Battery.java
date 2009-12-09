package entities;

import java.awt.image.BufferedImage;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;
import enums.EType;

public class Battery extends Entities {

	private Body battery;
	private static BufferedImage[] staticSprites;

	public Battery() {
		super(EType.battery);
		if (staticSprites == null) {
			staticSprites = utils.splitImage(utils
					.loadImage("Images/Battery.gif"), 1, 1);
			staticSprites = new BufferedImage[2];
			staticSprites[0] = utils.loadImage("Images/battery.gif");
			staticSprites[1] = utils.loadTranslucentImage(utils.loadImage("Images/batteryON.gif"), .5f);
		}
		
		sprite = staticSprites;
		
		battery = new StaticBody("Battery", new Box(1.0f, 1.0f));
		battery.setRestitution(1.0f);
		battery.setEnabled(false);

	}

	@Override
	public void addObj(World world, float x, float y) {

		battery.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(battery);
	}

	@Override
	public float getX() {

		return battery.getPosition().getX();
	}

	@Override
	public float getY() {

		return battery.getPosition().getY();
	}

	@Override
	public int gettouchingBodies() {
		return this.battery.getTouchingCount();
	}

	@Override
	public void removeObj(World world) {

		world.remove(battery);
	}

}
