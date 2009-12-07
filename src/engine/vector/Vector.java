package engine.vector;

/**
 * A vector, or way to store points.
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 */
public strictfp class Vector {
	/** The x component of this vector */
	public float x;
	/** The y component of this vector */
	public float y;

	/**
	 * Create an empty vector
	 */
	public Vector() {
	}

	/**
	 * @see engine.vector.Vector#getX()
	 */
	public float getX() {
		return x;
	}

	/**
	 * @see engine.vector.Vector#getY()
	 */
	public float getY() {
		return y;
	}

	/**
	 * Create a new vector based on another
	 * 
	 * @param other
	 *            The other vector to copy into this one
	 */
	public Vector(Vector other) {
		this(other.getX(), other.getY());
	}

	/**
	 * Create a new vector
	 * 
	 * @param x
	 *            The x component to assign
	 * @param y
	 *            The y component to assign
	 */
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Set the value of this vector
	 * 
	 * @param other
	 *            The values to set into the vector
	 */
	public void set(Vector other) {
		set(other.getX(), other.getY());
	}

	/**
	 * @see engine.vector.Vector#dot(engine.vector.Vector)
	 */
	public float dot(Vector other) {
		return (x * other.getX()) + (y * other.getY());
	}

	/**
	 * Set the values in this vector
	 * 
	 * @param x
	 *            The x component to set
	 * @param y
	 *            The y component to set
	 */
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Negate this vector
	 * 
	 * @return A copy of this vector negated
	 */
	public Vector negate() {
		return new Vector(-x, -y);
	}

	/**
	 * Add a vector to this vector
	 * 
	 * @param v
	 *            The vector to add
	 */
	public void add(Vector v) {
		x += v.getX();
		y += v.getY();
	}

	/**
	 * Subtract a vector from this vector
	 * 
	 * @param v
	 *            The vector subtract
	 */
	public void sub(Vector v) {
		x -= v.getX();
		y -= v.getY();
	}

	/**
	 * Scale this vector by a value
	 * 
	 * @param a
	 *            The value to scale this vector by
	 */
	public void scale(float a) {
		x *= a;
		y *= a;
	}

	/**
	 * Normalise the vector
	 * 
	 */
	public void normalise() {
		float l = length();

		if (l == 0)
			return;

		x /= l;
		y /= l;
	}

	/**
	 * The length of the vector squared
	 * 
	 * @return The length of the vector squared
	 */
	public float lengthSquared() {
		return (x * x) + (y * y);
	}

	/**
	 * @see engine.vector.Vector#length()
	 */
	public float length() {
		return (float) Math.sqrt(lengthSquared());
	}

	/**
	 * Project this vector onto another
	 * 
	 * @param b
	 *            The vector to project onto
	 * @param result
	 *            The projected vector
	 */
	public void projectOntoUnit(Vector b, Vector result) {
		float dp = b.dot(this);

		result.x = dp * b.getX();
		result.y = dp * b.getY();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[Vec " + x + "," + y + " (" + length() + ")]";
	}

	/**
	 * Get the distance from this point to another
	 * 
	 * @param other
	 *            The other point we're measuring to
	 * @return The distance to the other point
	 */
	public float distance(Vector other) {
		return (float) Math.sqrt(distanceSquared(other));
	}

	/**
	 * Get the distance squared from this point to another
	 * 
	 * @param other
	 *            The other point we're measuring to
	 * @return The distance to the other point
	 */
	public float distanceSquared(Vector other) {
		float dx = other.getX() - getX();
		float dy = other.getY() - getY();

		return (dx * dx) + (dy * dy);
	}

	/**
	 * Compare two vectors allowing for a (small) error as indicated by the
	 * delta. Note that the delta is used for the vector's components
	 * separately, i.e. any other vector that is contained in the square box
	 * with sides 2*delta and this vector at the center is considered equal.
	 * 
	 * @param other
	 *            The other vector to compare this one to
	 * @param delta
	 *            The allowed error
	 * @return True iff this vector is equal to other, with a tolerance defined
	 *         by delta
	 */
	public boolean equalsDelta(Vector other, float delta) {
		return (other.getX() - delta < x && other.getX() + delta > x
				&& other.getY() - delta < y && other.getY() + delta > y);

	}
}
