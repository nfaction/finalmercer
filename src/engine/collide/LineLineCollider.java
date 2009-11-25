package engine.collide;

import engine.Contact;
import engine.shapes.Body;
import engine.vector.Vector;

/**
 * Collides two lines with oneanother.
 * 
 * @author Jeffery D. AHern
 *
 */
public class LineLineCollider implements Collider {

	/**
	 * @see engine.collide.Collider#collide(engine.Contact[], engine.shapes.Body, engine.shapes.Body)
	 */
	public int collide(Contact[] contacts, Body bodyA, Body bodyB) {
		return 0;
	}
	
	/**
	 * Gets the closest point to a given point on the indefinately extended line.
	 * 
	 * @param startA Starting point of the line
	 * @param endA End point of the line
	 * @param point The point to get a closes point on the line for
	 * @return the closest point on the line or null if the lines are parallel
	 */
	public static Vector getClosestPoint(Vector startA, Vector endA, Vector point) {
		Vector startB = point;
		Vector endB = new Vector(endA);
		endB.sub(startA);
		endB.set(endB.y, -endB.x);

		float d = endB.y * (endA.x - startA.x);
		d -= endB.x * (endA.y - startA.y);
		
		if ( d == 0 )
			return null;
		
		float uA = endB.x * (startA.y - startB.getY());
		uA -= endB.y * (startA.x - startB.getX());
		uA /= d;
		
		return new Vector(
			startA.x + uA * (endA.x - startA.x),
			startA.y + uA * (endA.y - startA.y));
	}

}
