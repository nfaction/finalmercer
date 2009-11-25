package engine.shapes;

/**
 * A simple Circle within the simulation, defined by its radius and the
 * position of the body to which it belongs
 * 
 * @author Jeffery D. Ahern
 */
public strictfp class Circle extends AbstractShape {
	/** The radius of the circle */
	private float radius;
	
	/**
	 * Create a new circle based on its radius
	 * 
	 * @param radius The radius of the circle
	 */
	public Circle(float radius) {
		super(new AABox(radius*2, radius*2));
		
		this.radius = radius;
	}

	/**
	 * Get the radius of the circle
	 * 
	 * @return The radius of the circle
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * @see engine.shapes.Shape#getSurfaceFactor()
	 */
	public float getSurfaceFactor() {
		float circ = (float) (2 * Math.PI * radius);
		circ /= 2;
		
		return circ * circ;
	}
	
	/**
	 * Check if this circle touches another
	 * 
	 * @param x The x position of this circle
	 * @param y The y position of this circle
	 * @param other The other circle
	 * @param ox The other circle's x position
	 * @param oy The other circle's y position
	 * @return True if they touch
	 */
	public boolean touches(float x, float y, Circle other, float ox, float oy) {
		float totalRad2 = getRadius() + other.getRadius();
		
		if (Math.abs(ox - x) > totalRad2) {
			return false;
		}
		if (Math.abs(oy - y) > totalRad2) {
			return false;
		}
		
		totalRad2 *= totalRad2;
		
		float dx = Math.abs(ox - x);
		float dy = Math.abs(oy - y);
		
		return totalRad2 >= ((dx*dx) + (dy*dy));
	}
}
