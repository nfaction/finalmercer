package entities;

import engine.World;
import engine.shapes.*;

public class BasketBall extends Ball {

	private Body bBall;

	public BasketBall(String BasketBall) {

		super(BasketBall);
		bBall = new Body(BasketBall, new Circle(20.0f), 2.0f);
		bBall.setRestitution(1.0f);
		bBall.setDamping(.001f);
		bBall.setCanRest(true);
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

	@Override
	public void removeObj(World world) {
		world.remove(bBall);

	}

	@Override
	public void upDate() {
		// TODO Auto-generated method stub
		
	}

}
