package engine.vector;

import engine.vector.Vector;

/**
 * A two dimensional vector
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 */
public strictfp class Vector2D {
	public Vector col1 = new Vector();
	public Vector col2 = new Vector();

	/**
	 * Create an empty 2dVecotor
	 */
	public Vector2D() {
	}

	/**
	 * Create a 2dVecotor with a rotation
	 * 
	 * @param angle
	 *            The angle of the rotation decribed by the 2dVecotor
	 */
	public Vector2D(float angle) {
		float c = (float) Math.cos(angle);
		float s = (float) Math.sin(angle);
		col1.x = c;
		col2.x = -s;
		col1.y = s;
		col2.y = c;
	}

	/**
	 * Create a 2dVecotor
	 * 
	 * @param col1
	 *            The first column
	 * @param col2
	 *            The second column
	 */
	public Vector2D(Vector col1, Vector col2) {
		this.col1.set(col1);
		this.col2.set(col2);
	}

	/**
	 * Transpose the 2dVecotor
	 * 
	 * @return A newly created matrix containing the transpose of this 2dVecotor
	 */
	public Vector2D transpose() {
		return new Vector2D(new Vector(col1.x, col2.x), new Vector(col1.y,
				col2.y));
	}

	/**
	 * Transpose the invert
	 * 
	 * @return A newly created 2dVecotor containing the invert of this 2dVecotor
	 */
	public Vector2D invert() {
		float a = col1.x, b = col2.x, c = col1.y, d = col2.y;
		Vector2D B = new Vector2D();

		float det = a * d - b * c;
		if (det == 0.0f) {
			throw new RuntimeException(
					"Matrix2f: invert() - determinate is zero!");
		}

		det = 1.0f / det;
		B.col1.x = det * d;
		B.col2.x = -det * b;
		B.col1.y = -det * c;
		B.col2.y = det * a;
		return B;
	}
}
