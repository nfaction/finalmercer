package engine.test;

import engine.BasicJoint;
import engine.Body;
import engine.StaticBody;
import engine.World;
import engine.shapes.Box;
import engine.vector.Vector2f;

/**
 * Rope bridge demo
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class Demo05 extends AbstractDemo {
	/** The joint to break */
	private BasicJoint joint;
	/** The world in which the demo takes place */
	private World world;
	
	/**
	 * Create a new demo
	 */
	public Demo05() {
		super("Bridge Demo - Hit Space");
	}
	
	/**
	 * @see engine.test.AbstractDemo#keyHit(char)
	 */
	protected void keyHit(char c) {
		if (c == ' ') {
			System.out.println("Removing joint");
			world.remove(joint);
		}
	}
	
	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {
		this.world = world;
		float relax = 0.8f;
		
		Body body1 = new StaticBody("Ground1", new Box(500.0f, 20.0f));
		body1.setPosition(250.0f, 400);
		world.add(body1);
		
		Body body2 = new Body("First", new Box(40.0f, 10.0f), 500);
		body2.setFriction(0.2f);
		body2.setPosition(80.0f, 300);
		world.add(body2);
		
		BasicJoint j = new BasicJoint(body1,body2,new Vector2f(40,300));
		j.setRelaxation(relax);
		world.add(j);
		
		int i;
		for (i=1;i<8;i++) {
			Body body3 = new Body("Teeter",new Box(40.0f, 10.0f), 500);
			body3.setFriction(0.2f);
			body3.setPosition(80.0f+(i*45), 300);
			world.add(body3);
			
			BasicJoint j2 = new BasicJoint(body2,body3,new Vector2f(65+(i*45),300));
			j2.setRelaxation(relax);
			world.add(j2);
			if (i == 4) {
				joint = j2;
			}
			
			body2 = body3;
		}

		BasicJoint j3 = new BasicJoint(body1,body2,new Vector2f(80+(i*45),300));
		j3.setRelaxation(relax);
		world.add(j3);
	}

	/**
	 * Entry point for tetsing
	 * 
	 * @param argv The arguments to the test
	 */
	public static void main(String[] argv) {
		Demo05 demo = new Demo05();
		demo.start();
	}
}
