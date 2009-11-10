package engine.test;

import engine.StaticBody;
import engine.World;
import engine.body.Body;
import engine.shapes.Box;
import engine.shapes.Circle;
import engine.shapes.Line;
import engine.vector.Vector2f;

/**
 * Edge test
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class Demo20 extends AbstractDemo {
	/** The body we're pushing off the edge */
	private Body body2;
	
	/**
	 * Create the demo
	 */
	public Demo20() {
		super("Phys2D Edge test");
	}

	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {
		Body body1 = new StaticBody("Ground1", new Line(-300.0f, 0, false, true));
		body1.setPosition(400, 300);
		world.add(body1);
		Body body3 = new StaticBody("Ground2", new Line(0,50, false, true));
		body3.setPosition(100, 301);
		world.add(body3);
		
		body2 = new Body("Mover1", new Box(40,40), 10.0f);
		body2.setPosition(150.0f, 280.0f);
		world.add(body2);
		body2 = new Body("Mover1", new Box(40,40), 10.0f);
		body2.setPosition(190.0f, 280.0f);
		world.add(body2);
		body2 = new Body("Mover1", new Box(40,40), 10.0f);
		body2.setPosition(230.0f, 280.0f);
		world.add(body2);
		body2 = new Body("Mover1", new Box(40,40), 10.0f);
		body2.setPosition(280.0f, 280.0f);
		world.add(body2);
		body2 = new Body("Mover1", new Circle(20), 10.0f);
		body2.setPosition(380.0f, 280.0f);
		world.add(body2);
	}
	
	/**
	 * @see engine.test.AbstractDemo#update()
	 */
	protected void update() {
		body2.addForce(new Vector2f(-100,0));
	}
	
	/**
	 * Entry point for tetsing
	 * 
	 * @param argv The arguments to the test
	 */
	public static void main(String[] argv) {
		Demo20 demo = new Demo20();
		demo.start();
	}
}
