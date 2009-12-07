package engine.collide;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

import engine.vector.Vector;

/**
 * A very important class for collision detection between polygons
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 * 
 */
public class IntersectionGatherer {

	public static float MIN_PAIR_DIST = 0.5f;
	public static int MAX_INTERSECTIONS = 50;
	private SortableIntersection[] intersections;
	private int noIntersections = 0;
	private Vector[] vertsA;
	private Vector[] vertsB;

	/**
	 * Construct an IntersectionGatherer for a specific pair of polygons.
	 * 
	 * @param vertsA
	 *            The 'first' polygon involved in this collision check
	 * @param vertsB
	 *            The 'second' polygon involved in this collision check
	 */
	public IntersectionGatherer(Vector[] vertsA, Vector[] vertsB) {
		this.intersections = new SortableIntersection[MAX_INTERSECTIONS];
		this.noIntersections = 0;
		this.vertsA = vertsA;
		this.vertsB = vertsB;
	}

	/**
	 * Intersect two edges of the two polygons and stores the intersecting lines
	 * along with their position.
	 * 
	 * @param a
	 *            The edge of polygon A to check for intersection with B
	 * @param b
	 *            The edge of polygon B to check for intersection with A
	 */
	public void intersect(int a, int b) {
		if (noIntersections >= MAX_INTERSECTIONS)
			return;

		Vector startA = vertsA[a];
		Vector endA = vertsA[(a + 1) % vertsA.length];
		Vector startB = vertsB[b];
		Vector endB = vertsB[(b + 1) % vertsB.length];
		float d = (endB.y - startB.y) * (endA.x - startA.x)
				- (endB.x - startB.x) * (endA.y - startA.y);

		if (d == 0) // parallel lines
			return;

		float uA = (endB.x - startB.x) * (startA.y - startB.y)
				- (endB.y - startB.y) * (startA.x - startB.x);
		uA /= d;
		float uB = (endA.x - startA.x) * (startA.y - startB.y)
				- (endA.y - startA.y) * (startA.x - startB.x);
		uB /= d;

		if (uA < 0 || uA > 1 || uB < 0 || uB > 1)
			return; // intersection point isn't between the start and endpoints

		Vector position = new Vector(startA.x + uA * (endA.x - startA.x),
				startA.y + uA * (endA.y - startA.y));

		Vector dist = new Vector(position);
		dist.sub(startA);
		float distFromVertA = dist.lengthSquared();
		dist = new Vector(position);
		dist.sub(startB);
		float distFromVertB = dist.lengthSquared();

		// z axis of 3d cross product
		float sA = (startA.x - startB.x) * (endB.y - startB.y)
				- (endB.x - startB.x) * (startA.y - startB.y);

		if (sA > 0) {
			intersections[noIntersections] = new SortableIntersection(a, b,
					position, true, distFromVertA, distFromVertB);
		} else {
			intersections[noIntersections] = new SortableIntersection(a, b,
					position, false, distFromVertA, distFromVertB);
		}

		noIntersections++;
	}

	/**
	 * Get the list of intersections, sorted by the order defined by
	 * {@link IntersectionComparator}.
	 * 
	 * @return A sorted list of intersections
	 */
	public Intersection[] getIntersections() {
		Intersection[] out = new Intersection[noIntersections];

		for (int i = 0; i < noIntersections; i++)
			out[i] = intersections[i];

		Arrays.sort(out, new IntersectionComparator());

		return out;
	}

	/**
	 * Get the pairs of ingoing and outgoing intersections encountered when
	 * tracing the contour of polygon A.
	 * 
	 * @return An array with the intersection pairs which has the dimensions
	 *         [n][2] or [n][1]
	 */
	public Intersection[][] getIntersectionPairs() {
		if (noIntersections < 2)
			return new Intersection[0][2];

		// sort the array for a trace
		Arrays.sort(intersections, 0, noIntersections,
				new IntersectionComparator());

		// sort a pointer table which uses the indices in the intersections
		// array
		Integer[] pointers = new Integer[noIntersections];
		for (int i = 0; i < noIntersections; i++)
			pointers[i] = new Integer(i);
		Arrays.sort(pointers, new PointerTableComparator());

		int referenceVertB = getReferencePointer(pointers);
		filterIntersections(referenceVertB, pointers);

		// make sure we're starting with an ingoing edge
		int first = intersections[0].isIngoing ? 0 : 1;

		// now copy our results to a new array
		LinkedList<Intersection[]> outIntersections = new LinkedList<Intersection[]>();
		for (int i = first; i < noIntersections + first;) {
			SortableIntersection in = intersections[i % noIntersections];
			SortableIntersection out = intersections[(i + 1) % noIntersections];

			if (in == null) {
				i += 1;
				continue;
			}

			if (out != null && in.isIngoing && !out.isIngoing) {
				// pairs that are too close to eachother will
				// often cause problems, so don't create them
				if (!in.position.equalsDelta(out.position, MIN_PAIR_DIST)) {
					Intersection[] pair = { in, out };
					outIntersections.add(pair);
					i += 2;
					continue;
				}
			}

			Intersection[] inArr = { in };
			outIntersections.add(inArr);
			i += 1;
		}

		return outIntersections.toArray(new Intersection[outIntersections
				.size()][]);
	}

	/**
	 * Gets an outgoing intersection that seems appropriate to start the
	 * intersection filter with.
	 * 
	 * @param pointers
	 *            An array of pointers which are the indices of the
	 *            intersections array.
	 * @return The reference pointer which is an outgoing intersection.
	 */
	private int getReferencePointer(Integer[] pointers) {
		int first = intersections[pointers[0].intValue()].isIngoing ? 0 : 1;
		int maxInOutDist = 0;
		int maxInIndex = first + 1 % noIntersections;// intersections[pointers[first].intValue()].edgeB;
		int lastInEdgeB = -1;
		for (int i = first; i < noIntersections + first; i++) {
			int k = pointers[i % noIntersections].intValue();
			SortableIntersection intersection = intersections[k];

			if (intersection.isIngoing) {
				lastInEdgeB = intersection.edgeB;
			} else if (lastInEdgeB >= 0) {
				int inOutDist = (intersection.edgeB - lastInEdgeB + vertsB.length)
						% vertsB.length;

				if (inOutDist > maxInOutDist) {
					maxInOutDist = inOutDist;
					maxInIndex = i % noIntersections;
				}
				lastInEdgeB = -1;
			}
		}

		return maxInIndex;
	}

	/**
	 * This function filters out intersection pairs to remedy the situation
	 * where polygons totally penetrate eachother.
	 * 
	 * @param referencePointer
	 *            The vertex of B that lies outside of A
	 * @param pointers
	 *            An array of pointers which are the indices of the
	 *            intersections array.
	 */
	private void filterIntersections(int referencePointer, Integer[] pointers) {
		// make sure the reference vertex is real
		if (referencePointer >= noIntersections && referencePointer < 0)
			throw new RuntimeException(
					"The reference vertex cannot be correct since B does not have that many vertices.");

		// now throw out the total penetrating intersections
		int topOut = -2; // -2 + 1 will never give an edge number
		for (int i = referencePointer; i < noIntersections + referencePointer; i++) {
			int j = i % noIntersections;
			int k = pointers[j].intValue();
			SortableIntersection intersection = intersections[k];

			if (intersection.isIngoing) {
				if ((topOut - 1 + noIntersections) % noIntersections == k) { // the
					// closing
					// 'in'
					// intersection
					topOut = -2; // reset our top 'out' intersection
				} else {
					intersections[k] = null; // remove the 'in'
				}
			} else {
				if (topOut < 0) {
					topOut = k; // we encountered our new 'out' intersection
				} else {
					intersections[k] = null; // remove the out
				}
			}
		}

		// now get rid of the nullified intersections
		int noRemoved = 0;
		for (int i = 0; i < noIntersections; i++) {
			if (intersections[i] == null) {
				noRemoved++;
			} else {
				intersections[i - noRemoved] = intersections[i];
			}
		}
		noIntersections -= noRemoved;
	}

	/**
	 * Class representing a single intersection.
	 */
	class SortableIntersection extends Intersection {
		public float distFromVertA;
		public float distFromVertB;

		/**
		 * Construct an Intersection object, immediately setting all the
		 * attributes.
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
		 * @param distFromVertA
		 *            The squared distance from the vertice that starts edgeA
		 * @param distFromVertB
		 *            The squared distance from the vertice that starts edgeB
		 */
		public SortableIntersection(int edgeA, int edgeB, Vector position,
				boolean isIngoing, float distFromVertA, float distFromVertB) {
			super(edgeA, edgeB, position, isIngoing);
			this.distFromVertA = distFromVertA;
			this.distFromVertB = distFromVertB;
		}
	}

	/**
	 * Comparator used to sort intersections by their distance from A's first
	 * vertex.
	 */
	class IntersectionComparator implements Comparator<Object> {

		/**
		 * Compares two intersections. Note that this function will/should never
		 * return 0 because no two intersections can have the same distance from
		 * vertex 0. However, due to the finite precision of floating points
		 * this situation does occur. In those cases we try to put ingoing edges
		 * first.
		 * 
		 * @see Comparator#compare(Object, Object)
		 */
		public int compare(Object first, Object second) {
			SortableIntersection one = (SortableIntersection) first;
			SortableIntersection other = (SortableIntersection) second;

			if (one.edgeA < other.edgeA) {
				return -1;
			} else if (one.edgeA == other.edgeA) {
				if (one.distFromVertA < other.distFromVertA)
					return -1;
				else if (one.distFromVertA == other.distFromVertA
						&& one.isIngoing)
					return -1;
			}

			return 1;
		}
	}

	/**
	 * Comparator used to sort intersections by their distance from B's first
	 * vertex. This sorts an array of pointers which are the indices of the
	 * intersections array. So the actual intersections are retrieved via an
	 * indirection.
	 */
	class PointerTableComparator implements Comparator<Object> {

		/**
		 * Compares two intersections.
		 * 
		 * @see Comparator#compare(Object, Object)
		 */
		public int compare(Object first, Object second) {
			SortableIntersection one = intersections[((Integer) first)
					.intValue()];
			SortableIntersection other = intersections[((Integer) second)
					.intValue()];

			if (one.edgeB < other.edgeB) {
				return -1;
			} else if (one.edgeB == other.edgeB) {
				if (one.distFromVertB < other.distFromVertB)
					return -1;
				else if (one.distFromVertB == other.distFromVertB
						&& !one.isIngoing)
					return -1;
			}

			return 1;
		}
	}

}
