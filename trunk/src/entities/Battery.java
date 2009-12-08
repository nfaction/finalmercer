package entities;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;
import enums.EType;

public class Battery extends Entities{

	private Body battery;
	
//////////Info for sprite sheet/////////////////////
	public static final int batteryWidth = 30;
	public static final int batteryHeight = 30;
	public static final int Y_LENGTH = 15;	// not sure what this number is doing now
	public static final int X_LENGTH = 15;
	public int bbX = 0;
	public int bbY = 0;
	////////////////////////////////////////////////////
	
	public Battery() {
		super(EType.battery);
		battery = new StaticBody("Battery", new Box(1.0f, 1.0f));
		battery.setRestitution(1.0f);
		battery.setEnabled(false);
		setImagePath("Images/batterySpriteSheet.gif");
	}

	@Override
	public void addObj(World world, float x, float y) {

		battery.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(battery);
	}

	@Override
	public int getSpriteX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSpriteY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getX() {

		return battery.getPosition().getX();
	}

	@Override
	public int getXLength() {

		return 0;
	}

	@Override
	public float getY() {

		return battery.getPosition().getY();
	}

	@Override
	public int getYLength() {
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

		world.remove(battery);
	}

	@Override
	public String toString() {

		return "battery";
	}

	@Override
	public void upDate() {

		setImageLocations();
	}

	@Override
	public int getSpriteHeight() {

		return batteryHeight;
	}

	@Override
	public int getSpriteWidth() {

		return batteryHeight;
	}

	@Override
	public void setSprite() {
		// TODO Auto-generated method stub
		
	}

}
