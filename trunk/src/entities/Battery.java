package entities;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;
import enums.EType;

public class Battery extends Entities{

	private Body battery;
	
	public static final int Y_LENGTH = 1;
	public static final int X_LENGTH = 1;
	
	public Battery() {
		super(EType.battery);
		battery = new StaticBody("Battery", new Box(1.0f, 1.0f));
		battery.setRestitution(1.0f);
	}

	@Override
	public void addObj(World world, float x, float y) {

		battery.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(battery);
	}

	@Override
	public int getSpriteX(int count) {
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

}
