package engine.collide;

import engine.shapes.Body;
import engine.vector.MathUtil;
import engine.vector.Vector;

/**
 * A collider wrapper that swaps the collision result of the collider.
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 * 
 */
public class SwapCollider implements Collider {

	private Collider collider;

	/**
	 * Create a collider that swaps the result
	 * 
	 * @param collider
	 *            The collider of which to swap the result
	 */
	public SwapCollider(Collider collider) {
		this.collider = collider;
	}

	/**
	 * @see engine.collide.Collider#collide(engine.collide.Contact[],
	 *      engine.shapes.Body, engine.shapes.Body)
	 */
	public int collide(Contact[] contacts, Body bodyA, Body bodyB) {
		int count = collider.collide(contacts, bodyB, bodyA);

		// reverse the collision results by inverting normals
		for (int i = 0; i < count; i++) {
			Vector vec = MathUtil.scale(contacts[i].getNormal(), -1);
			contacts[i].setNormal(vec);
		}

		return count;
	}

}
