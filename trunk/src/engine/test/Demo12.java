package engine.test;

import engine.StaticBody;
import engine.World;
import engine.body.Body;
import engine.joint.BasicJoint;
import engine.shapes.Box;
import engine.shapes.Circle;
import engine.vector.Vector2f;

/**
 * A simple demo with so flat blocks falling
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class Demo12 extends AbstractDemo {
	/**
	 * Create the demo
	 */
	public Demo12() {
		super("Phys2D Demo 12");
	}

	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {
		Body body1 = new StaticBody("Ground1", new Box(400.0f, 20.0f));
		body1.setPosition(250.0f, 400);
		world.add(body1);
		Body body3 = new StaticBody("Ground2", new Box(200.0f, 20.0f));
		body3.setPosition(250.0f, 100);
		world.add(body3);
		
		Body swing = new Body("Swing", new Circle(10), 50);
		swing.setPosition(160.0f, 300);
		world.add(swing);
		Body swing2 = new Body("Swing", new Circle(10), 50);
		swing2.setPosition(340.0f, 300);
		world.add(swing2);
		Body swing3 = new Body("Swing", new Box(250.0f, 10.0f), 50);
		swing3.setPosition(250.0f, 285);
		swing3.setFriction(4.0f);
		world.add(swing3);
		
		Body box = new Body("Resting", new Box(30,30), 1);
		box.setPosition(250.0f, 200);
		box.setRotation(0.15f);
		world.add(box);
		
		BasicJoint j1 = new BasicJoint(body3, swing, new Vector2f(160,110));
		world.add(j1);
		BasicJoint j2 = new BasicJoint(body3, swing2, new Vector2f(340,110));
		world.add(j2);
		BasicJoint j3 = new BasicJoint(swing, swing3, new Vector2f(160,300));
		world.add(j3);
		BasicJoint j4 = new BasicJoint(swing2, swing3, new Vector2f(340,300));
		world.add(j4);
		
		swing.adjustVelocity(new Vector2f(-100,0));
	}
	
	/**
	 * Entry point for tetsing
	 * 
	 * @param argv The arguments to the test
	 */
	public static void main(String[] argv) {
		Demo12 demo = new Demo12();
		demo.start();
	}
}
