package engine.shapes;


/**
 * A body that will not move
 * 
 * @author Jeffery D. AHern
 */
public strictfp class StaticBody extends Body {

	/**
	 * Create a static body
	 * 
	 * @param shape The shape representing this body
	 */
	public StaticBody(Shape shape) {
		super(shape, Body.INFINITE_MASS);
	}

	/**
	 * Create a static body
	 * 
	 * @param name The name to assign to the body
	 * @param shape The shape representing this body
	 */
	public StaticBody(String name, Shape shape) {
		super(name, shape, Body.INFINITE_MASS);
	}

	/**
	 * @see engine.shapes.Body#isRotatable()
	 */
	public boolean isRotatable() {
		return false;
	}
	
	/**
	 * @see engine.shapes.Body#isMoveable()
	 */
	public boolean isMoveable() {
		return false;
	}
	
	/**
	 * Check if this body is static
	 * 
	 * @return True if this body is static
	 */
	public boolean isStatic() {
		return true;
	}
	
	/**
	 * @see engine.shapes.Body#isResting()
	 */
	public boolean isResting() {
		return true;
	}
}
