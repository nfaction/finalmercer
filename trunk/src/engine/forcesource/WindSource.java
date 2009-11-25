package engine.forcesource;

import engine.shapes.Body;
import engine.vector.Vector;

/**
 * A source to apply wind to all bodies in a given direction
 * 
 * @author Jeffery D. AHern
 */
public class WindSource implements ForceSource {
	/** The force to be applied */
	private Vector force = new Vector();
	
	/** 
	 * Create a new source
	 * 
	 * @param x The x component of the direction
	 * @param y The y component of the direction
	 * @param power The power of the window
	 */
	public WindSource(float x, float y, float power) {
		force.x = x * power;
		force.y = y * power;
	}
	
	/**
	 * @see engine.forcesource.ForceSource#apply(engine.shapes.Body, float)
	 */
	public void apply(Body body, float delta) {
		body.addForce(force);
	}

}
