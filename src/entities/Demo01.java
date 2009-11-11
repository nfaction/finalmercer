package entities;

import engine.StaticBody;
import engine.World;
import engine.body.Body;
import engine.shapes.Box;
import engine.test.AbstractDemo;
import entities.BasketBall;
import entities.BowlingBall;
import entities.Entities;

/**
 * A simple demo with so flat blocks falling
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public strictfp class Demo01 extends AbstractDemo {
	/**
	 * Create the demo
	 */
	public Demo01() {
		super("Phys2D Demo 1");
	}

	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {
		Body body1 = new StaticBody("Ground1", new Box(400.0f, 20.0f));
		body1.setPosition(250.0f, 400);
		world.add(body1);
		body1.setRestitution(1);

		Entities ball = new BasketBall();
		ball.addObj(world, 200, 50);
		
		Entities ballBowl = new BowlingBall();
		ballBowl.addObj(world, 400, 50);
	}
	
	/**
	 * Entry point for tetsing
	 * 
	 * @param argv The arguments to the test
	 */
	public static void main(String[] argv) {
		Demo01 demo = new Demo01();
		demo.start();
	}
}
