package engine.strategies;

import engine.BodyList;
import engine.CollisionContext;

/**
 * Brute force collision. Compare every body against every other (slow)
 * 
 * @author Jeffery D. Ahern
 */
public class BruteCollisionStrategy implements BroadCollisionStrategy {

	/**
	 * @see engine.strategies.BroadCollisionStrategy#collideBodies(engine.CollisionContext, engine.BodyList, float)
	 */
	public void collideBodies(CollisionContext context, BodyList bodies, float dt) {
		context.resolve(bodies, dt);
	}

}
