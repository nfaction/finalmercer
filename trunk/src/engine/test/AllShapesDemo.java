package engine.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import engine.World;
import engine.shapes.*;


/**
 * A test to show odd shapes interacting
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class AllShapesDemo extends AbstractDemo  {
	/** The world in which the simulation takes place */
	private World world;
	
	/**
	 * Create a new demo instance
	 */
	public AllShapesDemo() {
		super("All Shapes Demo");
	}
	
	/** A local random number generator */
	private Random random = new Random();
	
	/**
	 * @see engine.test.AbstractDemo#keyHit(char)
	 */
	protected void keyHit(char c) {
		super.keyHit(c);
		
		Body newBody = null;
		
		if ( c == 'c' ) {
			newBody = new Body(new Circle(15), 2);
		} else if ( c == 'l' ) {
			newBody = new Body(new Line(0,-50,0,50), 1f);
		} else if ( c == 'b' ) {
			newBody = new Body(new Box(20,30), 3);
		} else {
			return;
		}
		
		newBody.setPosition(250, 150);
		newBody.setRotation((float) (random.nextFloat() * 2 * Math.PI));
		world.add(newBody);
		
	}
	
	/**
	 * @see engine.test.AbstractDemo#renderGUI(java.awt.Graphics2D)
	 */
	protected void renderGUI(Graphics2D g) {
		g.setColor(Color.black);
		g.drawString("S - Drop a star",385,70);
		g.drawString("T - Drop a triangle",385,90);
		g.drawString("W - Drop a wheel",385,110);
		g.drawString("L - Drop a line",385, 130);
		g.drawString("B - Drop a box",385, 150);

		super.renderGUI(g);
	}
	
	/**
	 * Entry point for tetsing
	 * 
	 * @param argv The arguments to the test
	 */
	public static void main(String[] argv) {
		AllShapesDemo demo = new AllShapesDemo();
		demo.start();
	}

	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {
		this.world = world;
		Line line1 = new Line(300, 0);
		Body ground1 = new StaticBody("line", line1);
		ground1.setPosition(100, 400);
		world.add(ground1);
		
		Line line2 = new Line(-100, -100);
		Body ground2 = new StaticBody("line", line2);
		ground2.setPosition(140, 420);
		world.add(ground2);
		
		Line line3 = new Line(100, -100);
		Body ground3 = new StaticBody("line", line3);
		ground3.setPosition(360, 420);
		world.add(ground3);
	}

}
