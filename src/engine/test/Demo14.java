package engine.test;

import engine.Body;
import engine.World;
import engine.shapes.Circle;
import engine.vector.Vector2f;

/**
 * Back and forth
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class Demo14 extends AbstractDemo {
	/** The first circle in the simulation */
	private Body circle1;
	/** The second circle in the simulation */
	private Body circle2;
	
	/**
	 * Create the demo
	 */
	public Demo14() {
		super("Phys2D Demo 14");
	}

	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {
		world.setGravity(0,0);
		
		circle1 = new Body("Circle 1", new Circle(20.0f), 1);
		circle1.setPosition(150,100);
		circle1.setRestitution(1.0f);
		circle1.setFriction(0.0f);
		circle1.adjustVelocity(new Vector2f(100,0));
		world.add(circle1);
		circle2 = new Body("Circle 2", new Circle(20.0f), 1);
		circle2.setPosition(250,100);
		circle2.setRestitution(1.0f);
		circle2.setFriction(0.0f);
		world.add(circle2);
	}
	
	/**
	 * Entry point for tetsing
	 * 
	 * @param argv The arguments to the test
	 */
	public static void main(String[] argv) {
		Demo14 demo = new Demo14();
		demo.start();
	}
}
