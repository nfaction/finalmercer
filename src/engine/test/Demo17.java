package engine.test;

import engine.raw.Body;
import engine.raw.StaticBody;
import engine.raw.World;
import engine.raw.shapes.Box;
import engine.raw.shapes.Circle;

/**
 * ExclusionTest
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class Demo17 extends AbstractDemo {
	/**
	 * Create the demo
	 */
	public Demo17() {
		super("Phys2D Exclusion Test");
	}

	/**
	 * @see engine.test.AbstractDemo#init(engine.raw.World)
	 */
	protected void init(World world) {
		Body[] bodies = new Body[5];
		for (int x=0;x<4;x++) {
			bodies[x] = new Body("Ball"+x, new Circle(20.0f), 1);
			bodies[x].setPosition(220.0f+(x*20), 200+(x*40f));
			bodies[x].setRestitution(1.0f);
			world.add(bodies[x]);
		}
		for (int x=0;x<4;x++) {
			for (int i=0;i<4;i++) {
				bodies[x].addExcludedBody(bodies[i]);
			}
		}

		System.out.println(bodies[3].getExcludedList());
		
		Body body5 = new StaticBody("Ground1", new Box(20.0f, 500.0f));
		body5.setPosition(20.0f, 250);
		body5.setRestitution(1.0f);
		world.add(body5);
		Body body6 = new StaticBody("Ground2", new Box(20.0f, 500.0f));
		body6.setPosition(480.0f, 250);
		body6.setRestitution(1.0f);
		world.add(body6);
		Body body1 = new StaticBody("Ground3", new Box(500.0f, 20.0f));
		body1.setPosition(250.0f, 480);
		body1.setRestitution(1.0f);
		world.add(body1);
	}
	
	/**
	 * Entry point for tetsing
	 * 
	 * @param argv The arguments to the test
	 */
	public static void main(String[] argv) {
		Demo17 demo = new Demo17();
		demo.start();
	}
}
