package entities;

import engine.World;
import engine.shapes.Body;

public class Balloon extends Entities{
	
	private Body balloon;

	public Balloon(String objType) {
		super(objType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addObj(World world, float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeObj(World world) {
		world.remove(balloon);
		
	}

}
