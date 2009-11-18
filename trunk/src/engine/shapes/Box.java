package engine.shapes;

import engine.vector.MathUtil;
import engine.vector.Vector2D;
import engine.vector.Vector;

/**
 * A simple box in the engine - defined by a width and height
 * 
 * @author Kevin Glass
 */
public strictfp class Box extends AbstractShape {
	/** The size of the box */
	private Vector size = new Vector();
	
	/**
	 * Create a box in the simulation 
	 * 
	 * @param width The width of a box
	 * @param height The hieght of the box
	 */
	public Box(float width, float height) {
		super();
		
		size.set(width,height);
		bounds = new AABox(size.length(), size.length());
	}

	/**
	 * Get the size of this box
	 * 
	 * @return The size of this box
	 */
	public Vector getSize() {
		return size;
	}

	/**
	 * @see engine.shapes.Shape#getSurfaceFactor()
	 */
	public float getSurfaceFactor() {
		float x = size.getX();
		float y = size.getY();
		
		return (x * x + y * y);
	}

	/**
	 * Get the current positon of a set of points
	 * 
	 * @param pos The centre of the box
	 * @param rotation The rotation of the box
	 * @return The points building up a box at this position and rotation
	 */
	public Vector[] getPoints(Vector pos, float rotation) {
		Vector2D R = new Vector2D(rotation);
		Vector[] pts = new Vector[4];
		Vector h = MathUtil.scale(getSize(),0.5f);

		pts[0] = MathUtil.mul(R, new Vector(-h.getX(), -h.getY()));
		pts[0].add(pos);
		pts[1] = MathUtil.mul(R, new Vector(h.getX(), -h.getY()));
		pts[1].add(pos);
		pts[2] = MathUtil.mul(R, new Vector( h.getX(),  h.getY()));
		pts[2].add(pos);
		pts[3] = MathUtil.mul(R, new Vector(-h.getX(),  h.getY()));
		pts[3].add(pos);

		return pts;
	}
}
