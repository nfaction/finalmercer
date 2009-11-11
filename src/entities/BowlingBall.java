package entities;

import engine.World;
import engine.body.Body;
import engine.shapes.Circle;

public strictfp class BowlingBall extends Ball{

	private Body bowlBall;
	
	public BowlingBall() {
		
		super("");
		bowlBall = new Body("BowlingBall", new Circle(15.0f), 16.0f);
		bowlBall.setRestitution(.5f);
		bowlBall.setDamping(.01f);
	}

	@Override
	public void addObj(World world, float x, float y) {
		bowlBall.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(bowlBall);  
		
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

}
