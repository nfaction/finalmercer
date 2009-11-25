package engine.collide;

import engine.shapes.Body;

/**
 * A static utility for resolve the collision between shapes
 * 
 * @author Jeffery D. AHern
 */
public strictfp class Collide {

	/** The factory that provides us with colliders */
	private static ColliderFactory collFactory = new ColliderFactory();

	/**
	 * Perform the collision between two bodies
	 * 
	 * @param contacts The points of contact that should be populated
	 * @param bodyA The first body
	 * @param bodyB The second body
	 * @param dt The amount of time that's passed since we last checked collision
	 * @return The number of points at which the two bodies contact
	 */
	public static int collide(Contact[] contacts, Body bodyA, Body bodyB, float dt)
	{
		Collider collider;
		try {
			collider = collFactory.createCollider(bodyA, bodyB);
		} catch (ColliderUnavailableException e) {
			System.out.println(e.getMessage()
					+ "\n Ignoring any possible collision between the bodies in question");
			return 0;
		}
		
		return collider.collide(contacts, bodyA, bodyB);
	}
}
