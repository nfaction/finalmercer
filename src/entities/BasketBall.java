package entities;

import engine.World;
import engine.body.Body;
import engine.shapes.Circle;

public class BasketBall extends Ball {

	private Body bBall;

	public BasketBall() {
		
		super("");
		bBall = new Body("BasketBall", new Circle(20.0f), 1);
		bBall.setRestitution(1.0f);
		bBall.setDamping(.001f);
		
		setImagePath("Images/bball.jpg");
		

	}
	
	@Override
	public float getX() {
		return bBall.getPosition().getX();
		
	}

	@Override
	public float getY() {
		
		return bBall.getPosition().getY();
	}

	@Override
	public void addObj(World world, float x, float y) {
		bBall.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(bBall);  		
	}
}
