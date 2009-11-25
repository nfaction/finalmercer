package engine.strategies;

import engine.BodyList;
import engine.collision.CollisionContext;

/**
 * A description of any strategy for determining which bodies should
 * be compared against each other for collision - some times referred to
 * as the broad phase. For example the default implementation simply
 * compares every body against every other. Another implementation might
 * spatially partition the bodies into areas and only resolve collisions
 * between those in the same area.
 * 
 * @author Jeffery D> AHern
 */
public interface BroadCollisionStrategy {

	/**
	 * Perform the broad phase strategy. The implementation of this method
	 * is expected to determine a set of lists of bodies to collided against
	 * each other and then pass these lists back through the context for
	 * collision detection and response.
	 * 
	 * @param context The context that can actually perform the collision
	 * checking.
	 * @param bodies The complete list of bodies to be computed
	 * @param dt The amount of time passed since last collision
	 */
	public void collideBodies(CollisionContext context, BodyList bodies, float dt);
}
