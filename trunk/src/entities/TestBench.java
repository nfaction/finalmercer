package entities;

import engine.World;
import engine.body.Body;
import engine.body.StaticBody;
import engine.shapes.Box;
import engine.shapes.Circle;
import engine.test.AbstractDemo;
import engine.vector.Vector;

/**
 * A simple demo with so flat blocks falling
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class TestBench extends AbstractDemo {
	/**
	 * Create the demo
	 */
	public TestBench() {
		super("Test Bench");
	}

	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {
		Body ground = new StaticBody("Ground1", new Box(400.0f, 20.0f));
		ground.setPosition(250.0f, 400);
		ground.setRestitution(1.0f);
		world.add(ground);

		Body blockade = new StaticBody("blockade", new Box(300.0f, 20.0f));
		blockade.setPosition(100.0f, 100.0f);
		blockade.setRotation(-.4f);
		blockade.setRestitution(3.0f);
		world.add(blockade);
		
		//Ping-Pong Ball
		/*Body ppBall = new Body("Ping-Pong Ball", new Circle(5.0f), .006f);
		ppBall.setRestitution(.8f);
		ppBall.setDamping(.000007f);
		ppBall.setPosition(200.0f, 200);
		world.add(ppBall);*/
		
		//Balloon
		Body balloon = new Body("Balloon", new Circle(15.0f), .5f);
		balloon.setPosition(200.0f, 300.0f);
		balloon.setGravityEffected(false);
		balloon.setForce(0.0f, -1000.0f);		
		balloon.setRestitution(.1f);

//		balloon.adjustAngularVelocity(-10000.0f);
		balloon.adjustVelocity(new Vector(0.0f, -1000.0f));
		balloon.addForce(new Vector(0.0f, -1000.0f));
		
		
		world.add(balloon);
		
	}

	/**
	 * Entry point for tetsing
	 * 
	 * @param argv
	 *            The arguments to the test
	 */
	public static void main(String[] argv) {
		TestBench demo = new TestBench();
		demo.start();
	}
}
