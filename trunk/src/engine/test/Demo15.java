package engine.test;

import engine.StaticBody;
import engine.World;
import engine.body.Body;
import engine.shapes.Box;
import engine.shapes.Line;

/**
 * Lines
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber */
public class Demo15 extends AbstractDemo {
	/**
	 * Create the demo
	 */
	public Demo15() {
		super("Phys2D Demo 15 - Line vs Box");
	}

	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {
		Body land = new StaticBody("Line1", new Line(190,0));
		land.setPosition(50,400);
		world.add(land);
		Body land2 = new StaticBody("Box1", new Box(190,10));
		land2.setPosition(350,405);
		world.add(land2);
		
		Body box = new Body("Faller", new Box(50,50), 1);
		box.setPosition(150,100);
		box.setRotation(0.5f);
		world.add(box);
		Body box2 = new Body("Faller", new Box(50,50), 1);
		box2.setPosition(350,100);
		box2.setRotation(0.5f);
		world.add(box2);
	}
	
	/**
	 * Entry point for tetsing
	 * 
	 * @param argv The arguments to the test
	 */
	public static void main(String[] argv) {
		Demo15 demo = new Demo15();
		demo.start();
	}
}
