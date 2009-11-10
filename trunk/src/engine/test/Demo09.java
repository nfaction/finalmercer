package engine.test;

import engine.StaticBody;
import engine.World;
import engine.body.Body;
import engine.joint.BasicJoint;
import engine.shapes.Box;
import engine.shapes.Circle;
import engine.shapes.DynamicShape;
import engine.vector.Vector2f;

/**
 * Pendulum test with balls
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class Demo09 extends AbstractDemo {
	/** The controllable body */
	private Body b2;
	
	/**
	 * Create a new demo
	 */
	public Demo09() {
		super("Phys2D D9 - Hit space");
	}

	/**
	 * @see engine.test.AbstractDemo#keyHit(char)
	 */
	public void keyHit(char c) {
		super.keyHit(c);
		
		if (c == ' ') {
			b2.addForce(new Vector2f(-100000,0));
		}
	}
	
	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {
		Body b1 = new StaticBody("Anchor", new Box(500.0f, 20.0f));
		b1.setFriction(0.2f);
		b1.setPosition(250.0f, 100);
		b1.setRotation(0);
		world.add(b1);

		for (int i=0;i<9;i++) {
			float size = 6;
			if (i == 0) {
				size = 10;
			}
			DynamicShape shape = new Circle(size);
			Body body = new Body(shape, i == 0 ? 100.0f : 10.0f);
			body.setFriction(0.4f);
			body.setPosition(170.0f + (i*20), 171.0f);
			body.setRotation(0.0f);
			world.add(body);
	
			if (i == 0) {
				b2 = body;
			}
			
			BasicJoint j = new BasicJoint(b1, body, new Vector2f(170.0f + (i*20), 111.0f));
			world.add(j);
		}
	}

	/**
	 * Entry point for tetsing
	 * 
	 * @param argv The arguments to the test
	 */
	public static void main(String[] argv) {
		Demo09 demo = new Demo09();
		demo.start();
	}
}
