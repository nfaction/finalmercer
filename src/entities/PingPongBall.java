package entities;

import engine.World;

import engine.shapes.*;
import enums.EType;

public class PingPongBall extends Ball{

	private Body ppBall;

	public PingPongBall(String objType) {
		
		super(EType.pingPongBall);
		ppBall = new Body("Ping-Pong Ball", new Circle(5.0f), 2.72f);
		ppBall.setRestitution(.8f);
		ppBall.setDamping(.00009f);
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

	@Override
	public void upDate() {
		// TODO Auto-generated method stub
		
	}
}
