package engine.shapes;

/**
 * A simple shape describing the area covered by a body
 * 
 * @author Jeffery D. Ahern
 */
public interface Shape {

	/**
	 * Get the box bounds of the shape
	 * 
	 * @return The bounds of the shape
	 */
	public AABox getBounds();
	
	/**
	 * Some factor based on the edges length of the shape
	 * 
	 * @return The factor result - from the original demo
	 */
	public float getSurfaceFactor();
}
