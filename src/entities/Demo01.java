package entities;

import engine.Body;
import engine.ElasticJoint;
import engine.FixedJoint;
import engine.Joint;
import engine.StaticBody;
import engine.World;
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
public class Demo01 extends Test {
	Body body2;
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
		//body1.setRestitution(1.0f);
		world.add(body1);
		
		body2 = new Body("FW", new Circle(20.0f), 1.0f);
		body2.setPosition(250, 100.0f);
		Vector2f f = new Vector2f(0,-100);
		world.add(body2);
		
		Body body4 = new Body("RW", new Circle(25.0f), 10.0f);
		body4.setPosition(150, 100.0f);
		//body4.setRestitution(1.0f);
		world.add(body4);
		
		
		Body body3 = new Body("Body", new Box(100,10), 1);
		body3.setPosition(200, 100);
		world.add(body3);
		
		body3.addExcludedBody(body4);
		body3.addExcludedBody(body2);
		
		Joint j = new FixedJoint(body2,body3);
	
//		Joint j = new ElasticJoint(body3,body4);
		world.add(j);
		Joint s = new FixedJoint(body3,body4);
		s.setRelaxation(1);
		world.add(s);
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
		Demo01 demo = new Demo01();
		demo.start();
	}
}
