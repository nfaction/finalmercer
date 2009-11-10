package entities;

import engine.World;



public abstract class Ball extends Entities {

	public Ball(String objType) {
		super(objType);
	}
	
	public abstract void addObj(World world);

	
}
