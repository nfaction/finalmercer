package engine.test;

import engine.World;
import engine.joint.FixedJoint;
import engine.joint.Joint;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.Circle;
import engine.shapes.StaticBody;

/**
 * Rejoing test
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class Demo19 extends AbstractDemo {
	/**
	 * Create the demo
	 */
	public Demo19() {
		super("Phys2D rejoints");
	}

	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	public void init(World world) {
		Body body1 = new StaticBody("Ground1", new Box(400.0f, 20.0f));
		body1.setPosition(250.0f, 300);
		world.add(body1);
		body1 = new StaticBody("Ground1", new Box(20.0f, 400.0f));
		body1.setPosition(40.0f, 150);
		world.add(body1);
		body1 = new StaticBody("Ground1", new Box(20.0f, 400.0f));
		body1.setPosition(460.0f, 150);
		world.add(body1);
		
		Body body2 = new Body("Mover1", new Circle(15), 10.0f);
		body2.setPosition(200.0f, 30.0f);
		world.add(body2);
		Body body5 = new Body("Mover1", new Circle(15), 10.0f);
		body5.setPosition(-20.0f, 80.0f);
		world.add(body5);
		Body body3 = new Body("Mover2", new Circle(15), 10.0f);
		body3.setPosition(300.0f, 50.0f);
		world.add(body3);
		Body body4 = new Body("Mover3", new Circle(15), 10.0f);
		body4.setPosition(250.0f, 70.0f);
		world.add(body4);
		
		Joint j = new FixedJoint(body2,body3);
		world.add(j);
		j = new FixedJoint(body3,body4);
		world.add(j);
		j = new FixedJoint(body2,body5);
		world.add(j);
	}
	
	/**
	 * Entry point for tetsing
	 * 
	 * @param argv The arguments to the test
	 */
	public static void main(String[] argv) {
		Demo19 demo = new Demo19();
		demo.start();
	}
}
