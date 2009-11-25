package engine.collision;

import engine.BodyList;

/**
 * The description of a class which can compute the collisions of a 
 * set of bodies. This is part of decoupling the algorithm for broad phase
 * collisions against from the physics simulation world.
 * 
 * @author Kevin Glass
 */
public interface CollisionContext {
	/**
	 * Resolve and store the collisions betwen each body in the list 
	 * 
	 * @param bodies The bodies to be resolved against each other
	 * @param dt The time thats passed since last collision check
	 */
	public void resolve(BodyList bodies, float dt);
}
