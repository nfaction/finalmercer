package engine.collision;

import engine.BodyList;

/**
 * The description of a class which can compute the collisions of a set of
 * bodies.
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 */
public interface CollisionContext {
	/**
	 * Resolve and store the collisions betwen each body in the list
	 * 
	 * @param bodies
	 *            The bodies to be resolved against each other
	 * @param dt
	 *            The time thats passed since last collision check
	 */
	public void resolve(BodyList bodies, float dt);
}
