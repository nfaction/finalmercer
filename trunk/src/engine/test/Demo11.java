package engine.test;

import engine.Body;
import engine.StaticBody;
import engine.World;
import engine.shapes.Box;
import engine.shapes.Circle;
import engine.shapes.DynamicShape;
import engine.vector.Vector2f;

/**
 * A stacking demonstration with balls!
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class Demo11 extends AbstractDemo {
	/** The world in which the simulation takes place */
	private World world;
	
	/**
	 * Create the demo
	 */
	public Demo11() {
		super("Ball/Stack Demo - Hit space");
	}

	/**
	 * @see engine.test.AbstractDemo#keyHit(char)
	 */
	protected void keyHit(char c) {
		super.keyHit(c);
		
		if (c == ' ') {
			Body body2 = new Body("Mover1", new Circle(20), 300.0f);
			body2.setPosition(-50, (float) (((Math.random() * 50) + 150)));
			world.add(body2);
			body2.adjustAngularVelocity(1);
			body2.adjustVelocity(new Vector2f(200,(float) (Math.random() * 200)));
		}
	}
	
	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {
		this.world = world;
		
		Body body1 = new StaticBody("Ground1", new Box(400.0f, 20.0f));
		body1.setPosition(250.0f, 400);
		body1.setFriction(1);
		world.add(body1);
		
		for (int y=0;y<5;y++) {
			int xbase = 250 - (y * 21);
			for (int x=0;x<y+1;x++) {
				DynamicShape shape = new Box(40,40);
				if ((x == 1) && (y == 2)) {
					shape = new Circle(19);
				}
				if ((x == 1) && (y == 4)) {
					shape = new Circle(19);
				}
				if ((x == 3) && (y == 4)) {
					shape = new Circle(19);
				}
				Body body2 = new Body("Mover1", shape, 100.0f);
				body2.setPosition(xbase + (x * 42), y*45);
				world.add(body2);
			}
		}
	}
	
	/**
	 * Entry point for tetsing
	 * 
	 * @param argv The arguments to the test
	 */
	public static void main(String[] argv) {
		Demo11 demo = new Demo11();
		demo.start();
	}
}
