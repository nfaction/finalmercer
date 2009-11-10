package engine.test;

import engine.Body;
import engine.StaticBody;
import engine.World;
import engine.shapes.Box;

/**
 * Demo with angled blocks falling
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class Demo02 extends AbstractDemo {

	/**
	 * Create a new demo
	 */
	public Demo02() {
		super("Phys2D Demo 2");
	}

	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {
		Body body1 = new StaticBody("Ground1", new Box(600.0f, 20.0f));
		body1.setPosition(250.0f, 400);
		world.add(body1);
		Body body3 = new StaticBody("Ground2", new Box(200.0f, 20.0f));
		body3.setPosition(360.0f, 340);
		body3.setRotation(0.4f);
		world.add(body3);
		Body body9 = new StaticBody("Ground3", new Box(200.0f, 20.0f));
		body9.setPosition(140.0f, 340);
		body9.setRotation(-0.4f);
		world.add(body9);
		Body bodya = new StaticBody("Wall1", new Box(20.0f, 400.0f));
		bodya.setPosition(20.0f, 190);
		world.add(bodya);
		Body bodyb = new StaticBody("Wall2", new Box(20.0f, 400.0f));
		bodyb.setPosition(480.0f, 190);
		world.add(bodyb);
		
		Body body2 = new Body("Mover1", new Box(50.0f, 50.0f), 100.0f);
		body2.setPosition(250.0f, 4.0f);
		body2.setRotation(0.2f);
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
		Demo02 demo = new Demo02();
		demo.start();
	}
}
