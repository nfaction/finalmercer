package engine.collide;

import engine.vector.Vector;

/**
 * Class representing a single intersection.
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 */
public class Intersection {
	public int edgeA;
	public int edgeB;
	public Vector position;
	public boolean isIngoing;

	/**
	 * Construct an intersection with all its attributes set.
	 * 
	 * @param edgeA
	 *            The edge of polygon A that intersects
	 * @param edgeB
	 *            The edge of polygon B that intersects
	 * @param position
	 *            The position of the intersection in world (absolute)
	 *            coordinates
	 * @param isIngoing
	 *            True iff this is an intersection where polygon A enters B
	 */
	public Intersection(int edgeA, int edgeB, Vector position, boolean isIngoing) {
		super();
		this.edgeA = edgeA;
		this.edgeB = edgeB;
		this.position = position;
		this.isIngoing = isIngoing;
	}
}
