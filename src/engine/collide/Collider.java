package engine.collide;

import engine.shapes.Body;

/**
 * Interface for collisions
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 */
public interface Collider {

	/**
	 * Determine is any collisions have occured between the two bodies
	 * specified.
	 * 
	 * @param contacts
	 *            The contacts array to populate with results
	 * @param bodyA
	 *            The first body to check against
	 * @param bodyB
	 *            The second body to check against
	 * @return The number of contacts that have been determined and hence
	 *         populated in the array.
	 */
	public int collide(Contact[] contacts, Body bodyA, Body bodyB);
}
