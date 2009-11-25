package entities;

import engine.World;

import engine.shapes.*;

public class PingPongBall extends Ball{

	private Body ppBall;

	public PingPongBall(String objType) {
		
		super("");
		ppBall = new Body("Ping-Pong Ball", new Circle(5.0f), .006f);
		ppBall.setRestitution(1.7f);
		ppBall.setDamping(.00002f);
		ppBall.setCanRest(true);
		ppBall.configureRestingBodyDetection(1f, 1f, 1f);
	}

	@Override
	public void addObj(World world, float x, float y) {

		ppBall.setPosition(x, y);
		this.setImageLocations(x, y);
		world.add(ppBall); 
	}

	@Override
	public float getX() {
		
		return ppBall.getPosition().getX();
	}

	@Override
	public float getY() {
		return ppBall.getPosition().getY();
	}

	@Override
	public void removeObj(World world) {
		world.remove(ppBall);
		
	}
}
