package engine.collide;

import engine.shapes.Shape;

/**
 * An exception that is thrown by the ColliderFactory when no suitable collider
 * has been found.
 * 
 * @author Jeffery D. Ahern
 * 
 */
@SuppressWarnings("serial")
public class ColliderUnavailableException extends Exception {

	/**
	 * Constructs an exception in case there is no collider for a specific
	 * combination of two shapes.
	 * 
	 * @param shapeA
	 *            First shape
	 * @param shapeB
	 *            Second shape
	 */
	public ColliderUnavailableException(Shape shapeA, Shape shapeB) {
		super("No collider available for shapes of type "
				+ shapeA.getClass().getName() + " and "
				+ shapeB.getClass().getName());
	}

	/** A constructor that can be used by subclasses. */
	protected ColliderUnavailableException() {
	}

}
