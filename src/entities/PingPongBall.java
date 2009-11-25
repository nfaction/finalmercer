package entities;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Circle;

public class PingPongBall extends Ball{

	private Body ppBall;

	public PingPongBall(String objType) {
		
		super("");
		ppBall = new Body("Ping-Pong Ball", new Circle(5.0f), .006f);
		ppBall.setRestitution(.000007f);
		ppBall.setDamping(.00002f);
		ppBall.setPosition(200.0f, 200);
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
		// TODO Auto-generated method stub
		
	}
}
