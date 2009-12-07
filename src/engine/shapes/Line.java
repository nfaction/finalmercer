package engine.shapes;

import engine.vector.Vector;

/**
 * Implemenation of a bunch of maths functions to do with lines. Note that lines
 * can't be used as dynamic shapes right now - also collision with the end of a
 * line is undefined.
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 */
public strictfp class Line extends AbstractShape {
	private Vector start;
	private Vector end;
	/** The vector between the two points */
	private Vector vec;
	private float lenSquared;
	private Vector loc = new Vector(0, 0);
	private Vector v = new Vector(0, 0);
	private Vector v2 = new Vector(0, 0);
	private Vector proj = new Vector(0, 0);
	private Vector closest = new Vector(0, 0);
	private Vector other = new Vector(0, 0);

	/** True if this line blocks on the outer edge */
	private boolean outerEdge = true;
	/** True if this line blocks on the inner edge */
	private boolean innerEdge = true;

	/**
	 * Create a new line based on the origin and a single point
	 * 
	 * @param x
	 *            The end point of the line
	 * @param y
	 *            The end point of the line
	 * @param inner
	 *            True if this line blocks on it's inner edge
	 * @param outer
	 *            True if this line blocks on it's outer edge
	 */
	public Line(float x, float y, boolean inner, boolean outer) {
		this(0, 0, x, y);

		setBlocksInnerEdge(inner);
		setBlocksOuterEdge(outer);
	}

	/**
	 * Create a new line based on the origin and a single point
	 * 
	 * @param x
	 *            The end point of the line
	 * @param y
	 *            The end point of the line
	 */
	public Line(float x, float y) {
		this(x, y, true, true);
	}

	/**
	 * Create a new line based on two points
	 * 
	 * @param x1
	 *            The x coordinate of the start point
	 * @param y1
	 *            The y coordinate of the start point
	 * @param x2
	 *            The x coordinate of the end point
	 * @param y2
	 *            The y coordinate of the end point
	 */
	public Line(float x1, float y1, float x2, float y2) {
		this(new Vector(x1, y1), new Vector(x2, y2));
	}

	/**
	 * Create a new line based on two points
	 * 
	 * @param start
	 *            The start point
	 * @param end
	 *            The end point
	 */
	public Line(Vector start, Vector end) {
		super();
		float radius = Math.max(start.length(), end.length());
		bounds = new AABox(0, 0, radius * 2, radius * 2);

		set(start, end);
	}

	/**
	 * Check if this line blocks the inner side (for want of a better term)
	 * 
	 * @return True if this line blocks the inner side
	 */
	public boolean blocksInnerEdge() {
		return innerEdge;
	}

	/**
	 * Indicate if this line blocks on it's inner edge
	 * 
	 * @param innerEdge
	 *            True if this line blocks on it's inner edge
	 */
	public void setBlocksInnerEdge(boolean innerEdge) {
		this.innerEdge = innerEdge;
	}

	/**
	 * Check if this line blocks the outer side (for want of a better term)
	 * 
	 * @return True if this line blocks the outer side
	 */
	public boolean blocksOuterEdge() {
		return outerEdge;
	}

	/**
	 * Indicate if this line blocks on it's outer edge
	 * 
	 * @param outerEdge
	 *            True if this line blocks on it's outer edge
	 */
	public void setBlocksOuterEdge(boolean outerEdge) {
		this.outerEdge = outerEdge;
	}

	/**
	 * Get the start point of the line
	 * 
	 * @return The start point of the line
	 */
	public Vector getStart() {
		return start;
	}

	/**
	 * Get the end point of the line
	 * 
	 * @return The end point of the line
	 */
	public Vector getEnd() {
		return end;
	}

	/**
	 * Find the length of the line
	 * 
	 * @return The the length of the line
	 */
	public float length() {
		return vec.length();
	}

	/**
	 * Find the length of the line squared (cheaper and good for comparisons)
	 * 
	 * @return The length of the line squared
	 */
	public float lengthSquared() {
		return vec.lengthSquared();
	}

	/**
	 * Configure the line
	 * 
	 * @param start
	 *            The start point of the line
	 * @param end
	 *            The end point of the line
	 */
	public void set(Vector start, Vector end) {
		this.start = start;
		this.end = end;

		vec = new Vector(end);
		vec.sub(start);

		lenSquared = vec.length();
		lenSquared *= lenSquared;
	}

	/**
	 * Get the x direction of this line
	 * 
	 * @return The x direction of this line
	 */
	public float getXDirection() {
		return end.getX() - start.getX();
	}

	/**
	 * Get the y direction of this line
	 * 
	 * @return The y direction of this line
	 */
	public float getYDirection() {
		return end.getY() - start.getY();
	}

	/**
	 * Get the x coordinate of the start point
	 * 
	 * @return The x coordinate of the start point
	 */
	public float getXStart() {
		return start.getX();
	}

	/**
	 * Get the y coordinate of the start point
	 * 
	 * @return The y coordinate of the start point
	 */
	public float getYStart() {
		return start.getY();
	}

	/**
	 * Get the x coordinate of the end point
	 * 
	 * @return The x coordinate of the end point
	 */
	public float getXEnd() {
		return end.getX();
	}

	/**
	 * Get the y coordinate of the end point
	 * 
	 * @return The y coordinate of the end point
	 */
	public float getYEnd() {
		return end.getY();
	}

	/**
	 * Get the shortest distance from a point to this line
	 * 
	 * @param point
	 *            The point from which we want the distance
	 * @return The distance from the line to the point
	 */
	public float distance(Vector point) {
		return (float) Math.sqrt(distanceSquared(point));
	}

	/**
	 * Get the shortest distance squared from a point to this line
	 * 
	 * @param point
	 *            The point from which we want the distance
	 * @return The distance squared from the line to the point
	 */
	public float distanceSquared(Vector point) {
		getClosestPoint(point, closest);
		closest.sub(point);

		float result = closest.lengthSquared();

		return result;
	}

	/**
	 * Get the closest point on the line to a given point
	 * 
	 * @param point
	 *            The point which we want to project
	 * @param result
	 *            The point on the line closest to the given point
	 */
	public void getClosestPoint(Vector point, Vector result) {
		loc.set(point);
		loc.sub(start);

		v.set(vec);
		v2.set(vec);
		v2.scale(-1);

		v.normalise();
		loc.projectOntoUnit(v, proj);
		if (proj.lengthSquared() > vec.lengthSquared()) {
			result.set(end);
			return;
		}
		proj.add(start);

		other.set(proj);
		other.sub(end);
		if (other.lengthSquared() > vec.lengthSquared()) {
			result.set(start);
			return;
		}

		result.set(proj);
		return;
	}

	/**
	 * @see engine.shapes.Shape#getSurfaceFactor()
	 */
	public float getSurfaceFactor() {
		return lengthSquared() / 2;
	}

	/**
	 * Get a line starting a x,y and ending offset from the current end point.
	 * Curious huh?
	 * 
	 * @param displacement
	 *            The displacement of the line
	 * @param rotation
	 *            The rotation of the line in radians
	 * @return The newly created line
	 */
	public Line getPositionedLine(Vector displacement, float rotation) {
		Vector[] verts = getVertices(displacement, rotation);
		Line line = new Line(verts[0], verts[1]);

		return line;
	}

	/**
	 * Return a translated and rotated line.
	 * 
	 * @param displacement
	 *            The displacement of the line
	 * @param rotation
	 *            The rotation of the line in radians
	 * @return The two endpoints of this line
	 */
	public Vector[] getVertices(Vector displacement, float rotation) {
		float cos = (float) Math.cos(rotation);
		float sin = (float) Math.sin(rotation);

		Vector[] endPoints = new Vector[2];
		endPoints[0] = new Vector(// getX1(), getY1());
				getXStart() * cos - getYStart() * sin, getYStart() * cos
						+ getXStart() * sin);
		endPoints[0].add(displacement);
		endPoints[1] = new Vector(// getX2(), getY2());
				getXEnd() * cos - getYEnd() * sin, getYEnd() * cos + getXEnd()
						* sin);
		endPoints[1].add(displacement);

		return endPoints;
	}

	/**
	 * Move this line a certain amount
	 * 
	 * @param v
	 *            The amount to move the line
	 */
	public void move(Vector v) {
		Vector temp = new Vector(start);
		temp.add(v);
		start = temp;
		temp = new Vector(end);
		temp.add(v);
		end = temp;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[Line " + start + "," + end + "]";
	}

	/**
	 * Intersect this line with another
	 * 
	 * @param other
	 *            The other line we should intersect with
	 * @return The intersection point or null if the lines are parallel
	 */
	public Vector intersect(Line other) {
		float dx1 = end.getX() - start.getX();
		float dx2 = other.end.getX() - other.start.getX();
		float dy1 = end.getY() - start.getY();
		float dy2 = other.end.getY() - other.start.getY();
		float denom = (dy2 * dx1) - (dx2 * dy1);

		if (denom == 0) {
			return null;
		}

		float ua = (dx2 * (start.getY() - other.start.getY()))
				- (dy2 * (start.getX() - other.start.getX()));
		ua /= denom;
		float ub = (dx1 * (start.getY() - other.start.getY()))
				- (dy1 * (start.getX() - other.start.getX()));
		ub /= denom;

		float u = ua;

		float ix = start.getX() + (u * (end.getX() - start.getX()));
		float iy = start.getY() + (u * (end.getY() - start.getY()));

		return new Vector(ix, iy);
	}

}
