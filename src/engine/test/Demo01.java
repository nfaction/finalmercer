package engine.test;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.StaticBody;

/**
 * A simple demo with so flat blocks falling
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class Demo01 extends AbstractDemo {
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
		Body body3 = new StaticBody("Ground2", new Box(200.0f, 20.0f));
		body3.setPosition(360.0f, 380);
		world.add(body3);
		Body body5 = new StaticBody("Ground3", new Box(20.0f, 100.0f));
		body5.setPosition(200.0f, 300);
		world.add(body5);
		Body body6 = new StaticBody("Ground3", new Box(20.0f, 100.0f));
		body6.setPosition(400.0f, 300);
		world.add(body6);
		
		Body body2 = new Body("Mover1", new Box(50.0f, 50.0f), 100.0f);
		body2.setPosition(250.0f, 4.0f);
		world.add(body2);
		Body body4 = new Body("Mover2", new Box(50.0f, 50.0f), 100.0f);
		body4.setPosition(230.0f, -60.0f);
		world.add(body4);
		Body body8 = new Body("Mover3", new Box(50.0f, 50.0f), 100.0f);
		body8.setPosition(280.0f, -120.0f);
		world.add(body8);
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
