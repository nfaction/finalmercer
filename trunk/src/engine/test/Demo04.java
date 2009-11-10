package engine.test;

import engine.StaticBody;
import engine.World;
import engine.body.Body;
import engine.joint.BasicJoint;
import engine.shapes.Box;
import engine.vector.Vector;

/**
 * Teeter Test
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class Demo04 extends AbstractDemo {

	/**
	 * Create a new demo
	 */
	public Demo04() {
		super("Demo4 - Teeter");
	}

	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {
		Body body1 = new StaticBody("Ground1", new Box(500.0f, 20.0f));
		body1.setPosition(250.0f, 400);
		world.add(body1);

		Body body2 = new Body("Teeter", new Box(250.0f, 10.0f), 5);
		body2.setFriction(0.5f);
		body2.setPosition(250.0f, 370);
		world.add(body2);

		Body body3 = new Body("light1", new Box(10.0f, 10.0f), 10);
		body3.setPosition(135, 360);
		world.add(body3);
		Body body4 = new Body("light2", new Box(10.0f, 10.0f), 10);
		body4.setPosition(150, 360);
		world.add(body4);

		Body body5 = new Body("weight", new Box(25.0f, 25.0f), 30);
		body5.setPosition(350, 50);
		world.add(body5);

		BasicJoint j = new BasicJoint(body1, body2, new Vector(250, 370));
		world.add(j);
	}

	/**
	 * Entry point for tetsing
	 * 
	 * @param argv
	 *            The arguments to the test
	 */
	public static void main(String[] argv) {
		Demo04 demo = new Demo04();
		demo.start();
	}
}
