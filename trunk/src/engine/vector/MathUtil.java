package engine.vector;

import engine.vector.Vector;

/**
 * Simple utility wrapping up a bunch of math operations so that 
 * the rest of the code doesn't have to look so cluttered.
 * 
 * @author Jeffery D. AHern
 */
public final strictfp class MathUtil {
	/** Prevent construction */
	private MathUtil() {}
	
	/**
	 * Scale a vector by a given value
	 * 
	 * @param a The vector to be scaled
	 * @param scale The amount to scale the vector by
	 * @return A newly created vector - a scaled version of the new vector
	 */
	public static Vector scale(Vector a, float scale) {
		Vector temp = new Vector(a);
		temp.scale(scale);
		
		return temp;
	}
	
	/**
	 * Subtract one vector from another
	 * 
	 * @param a The vector to be subtracted from
	 * @param b The vector to subtract
	 * @return A newly created containing the result
	 */
	public static Vector sub(Vector a,Vector b) {
		Vector temp = new Vector(a);
		temp.sub(b);
		
		return temp;
	}
	
	/**
	 * Check the sign of a value 
	 * 
	 * @param x The value to check
	 * @return -1.0f if negative, 1.0 if positive
	 */
	public static float sign(float x)
	{
		return x < 0.0f ? -1.0f : 1.0f;
	}
	
	/**
	 * Multiply a 2dVecotor  by a vector
	 * 
	 * @param A The matrix to be multiplied
	 * @param v The vector to multiple by
	 * @return A newly created vector containing the resultant vector
	 */
	public static Vector mul(Vector2D A, Vector v)
	{
		return new Vector(A.col1.x * v.getX() + A.col2.x * v.getY(), A.col1.y * v.getX() + A.col2.y * v.getY());
	}
	
	/**
	 * Multiple two 2dVecotor
	 * 
	 * @param A The first matrix
	 * @param B The second matrix
	 * @return A newly created matrix containing the result
	 */
	public static Vector2D mul(Vector2D A, Vector2D B) 
	{
		return new Vector2D(mul(A,B.col1), mul(A,B.col2));
	}
	
	/**
	 * Create the absolute version of a 2dVecotor
	 * 
	 * @param A The 2dVecotor to make absolute
	 * @return A newly created absolute 2dVecotor
	 */
	public static Vector2D abs(Vector2D A) {
		return new Vector2D(abs(A.col1), abs(A.col2));
	}

	/**
	 * Make a vector absolute
	 * 
	 * @param a The vector to make absolute
	 * @return A newly created result vector
	 */
	public static Vector abs(Vector a)
	{
		return new Vector(Math.abs(a.x), Math.abs(a.y));
	}

	/**
	 * Add two 2dVecotor
	 * 
	 * @param A The first 2dVecotor
	 * @param B The second 2dVecotor
	 * @return A newly created 2dVecotor containing the result
	 */
	public static Vector2D add(Vector2D A, Vector2D B)
	{
		Vector temp1 = new Vector(A.col1);
		temp1.add(B.col1);
		Vector temp2 = new Vector(A.col2);
		temp2.add(B.col2);
		
		return new Vector2D(temp1,temp2);
	}
	
	/**
	 * Find the cross product of two vectors
	 * 
	 * @param a The first vector
	 * @param b The second vector
	 * @return The cross product of the two vectors
	 */
	public static float cross(Vector a, Vector b)
	{
		return a.x * b.y - a.y * b.x;
	}

	/**
	 * Find the cross product of a vector and a float
	 * 
	 * @param s The scalar float
	 * @param a The vector to fidn the cross of
	 * @return A newly created resultant vector
	 */
	public static Vector cross(float s, Vector a)
	{
		return new Vector(-s * a.y, s * a.x);
	}

	/**
	 * Find the cross product of a vector and a float
	 * 
	 * @param s The scalar float
	 * @param a The vector to fidn the cross of
	 * @return A newly created resultant vector
	 */
	public static Vector cross(Vector a, float s)
	{
		return new Vector(s * a.y, -s * a.x);
	}
	
	/**
	 * Clamp a value 
	 * 
	 * @param a The original value
	 * @param low The lower bound
	 * @param high The upper bound
	 * @return The clamped value
	 */
	public static float clamp(float a, float low, float high)
	{
		return Math.max(low, Math.min(a, high));
	}
	

	/**
	 * Get the normal of a line x y (or edge). 
	 * When standing on x facing y, the normal will point
	 * to the left.
	 *
	 * @param x startingpoint of the line
	 * @param y endpoint of the line
	 * @return a (normalised) normal
	 */
	public static Vector getNormal(Vector x, Vector y) {
		Vector normal = new Vector(y);
		normal.sub(x);
		
		normal = new Vector(normal.y, -normal.x);
		normal.normalise();
		
		return normal;
	}
	
}
