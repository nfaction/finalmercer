package engine.raw.forcesource;

import engine.raw.Body;
import engine.vector.Vector2f;

/**
 * A source to apply wind to all bodies in a given direction
 * 
 * @author kevin
 */
public class WindSource implements ForceSource {
	/** The force to be applied */
	private Vector2f force = new Vector2f();
	
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
	 * @see engine.raw.forcesource.ForceSource#apply(engine.raw.Body, float)
	 */
	public void apply(Body body, float delta) {
		body.addForce(force);
	}

}
