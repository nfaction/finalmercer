package engine.test;

import engine.Body;
import engine.StaticBody;
import engine.World;
import engine.shapes.Box;
import engine.shapes.Circle;

/**
 * A simple demo with balls falling
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class Demo08 extends AbstractDemo {
	/**
	 * Create the demo
	 */
	public Demo08() {
		super("Phys2D Demo 8 - Balls");
	}

	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {
		Body body1 = new StaticBody("Ground1", new Box(400.0f, 20.0f));
		body1.setPosition(250.0f, 400);
		world.add(body1);
		Body body1a = new StaticBody("Ground2", new Box(200.0f, 20.0f));
		body1a.setPosition(350.0f, 280);
		body1a.setRotation(-0.7f);
		world.add(body1a);
		Body body1b = new StaticBody("Ground1", new Box(20.0f, 400.0f));
		body1b.setPosition(20.0f, 200);
		world.add(body1b);
		
		Body body2 = new Body("Mover1", new Circle(25), 100.0f);
		body2.setPosition(300.0f, 4.0f);
		world.add(body2);
		//Body body3 = new Body("Mover2", new Box(25,25), 10.0f);
		Body body3 = new Body("Mover2", new Circle(25), 10.0f);
		body3.setRotation(0.3f);
		body3.setPosition(380.0f, 100.0f);
		world.add(body3);
	}
	
	/**
	 * Entry point for tetsing
	 * 
	 * @param argv The arguments to the test
	 */
	public static void main(String[] argv) {
		Demo08 demo = new Demo08();
		demo.start();
	}
}
