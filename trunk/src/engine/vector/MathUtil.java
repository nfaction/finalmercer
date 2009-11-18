/*
 * Phys2D - a 2D physics engine based on the work of Erin Catto. The
 * original source remains:
 * 
 * Copyright (c) 2006 Erin Catto http://www.gphysics.com
 * 
 * This source is provided under the terms of the BSD License.
 * 
 * Copyright (c) 2006, Phys2D
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the following 
 * conditions are met:
 * 
 *  * Redistributions of source code must retain the above 
 *    copyright notice, this list of conditions and the 
 *    following disclaimer.
 *  * Redistributions in binary form must reproduce the above 
 *    copyright notice, this list of conditions and the following 
 *    disclaimer in the documentation and/or other materials provided 
 *    with the distribution.
 *  * Neither the name of the Phys2D/New Dawn Software nor the names of 
 *    its contributors may be used to endorse or promote products 
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND 
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, 
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY 
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
 * OF SUCH DAMAGE.
 */
package engine.vector;

import engine.vector.Vector;

/**
 * Simple utility wrapping up a bunch of math operations so that 
 * the rest of the code doesn't have to look so cluttered.
 * 
 * @author Kevin Glass
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
	 * Multiply a matrix by a vector
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
	 * Multiple two matricies
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
	 * Create the absolute version of a matrix
	 * 
	 * @param A The matrix to make absolute
	 * @return A newly created absolute matrix
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
	 * Add two matricies
	 * 
	 * @param A The first matrix
	 * @param B The second matrix
	 * @return A newly created matrix containing the result
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
	 * TODO: move this function somewhere else?
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
	
//	public static Vector2f intersect(Vector2f startA, Vector2f endA, Vector2f startB, Vector2f endB) {				
//		float d = (endB.y - startB.y) * (endA.x - startA.x) - (endB.x - startB.x) * (endA.y - startA.y);
//		
//		if ( d == 0 ) // parallel lines
//			return null;
//		
//		float uA = (endB.x - startB.x) * (startA.y - startB.y) - (endB.y - startB.y) * (startA.x - startB.x);
//		uA /= d;
//		float uB = (endA.x - startA.x) * (startA.y - startB.y) - (endA.y - startA.y) * (startA.x - startB.x);
//		uB /= d;
//		
//		if ( uA < 0 || uA > 1 || uB < 0 || uB > 1 ) 
//			return null; // intersection point isn't between the start and endpoints
//		
//		return new Vector2f(
//				startA.x + uA * (endA.x - startA.x),
//				startA.y + uA * (endA.y - startA.y));
//	}
	
	
}
