package engine.shapes;

/**
 * Super class of generic shapes in the engine
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 */
public strictfp abstract class AbstractShape implements Shape {
	protected AABox bounds;

	/**
	 * Construct a new shape as subclas swhich will specified it's own bounds
	 */
	protected AbstractShape() {
	}

	/**
	 * Create a shape
	 * 
	 * @param bounds
	 *            The bounds of the shape
	 */
	public AbstractShape(AABox bounds) {
		this.bounds = bounds;
	}

	/**
	 * @see engine.shapes.Shape#getBounds()
	 */
	public AABox getBounds() {
		return bounds;
	}
}
