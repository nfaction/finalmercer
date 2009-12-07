package engine.collide;

import engine.vector.Vector;

/**
 * This class will, given an intersection pair (an ingoing and outgoing
 * intersection), calculate the penetration depth.
 * 
 * <p>
 * The penetration depth or separation is calculated by running a sweepline
 * between the two points of an intersection pair.
 * </p>
 * 
 * <pre>
 *        -<----
 *       |B     |
 *       |      | 
 *    out|      |in
 *  -----+--.---+-------
 * |A    |  !  /        |
 * |      \ ! /         |
 * |       \!/          |
 * |        .           |
 *  ->------------------
 * </pre>
 * 
 * <p>
 * The sweepline always runs from the ingoing to the outgoing intersection.
 * Usually the normal is perpendicular to the sweepline.
 * </p>
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 * 
 */
public class PenetrationSweep {

	private Vector normal;
	private Vector sweepDir;
	private float startDist;
	private float endDist;

	/**
	 * Constructs a Penetration Sweep object, with all its attributes set.
	 * 
	 * @param normal
	 *            The collision normal
	 * @param sweepDir
	 *            The sweep direction
	 * @param intersectionStart
	 *            The start bound of the intersection area
	 * @param intersectionEnd
	 *            The end bound of the intersection area.
	 */
	public PenetrationSweep(Vector normal, Vector sweepDir,
			Vector intersectionStart, Vector intersectionEnd) {
		super();
		this.normal = normal;
		this.sweepDir = sweepDir;
		this.startDist = intersectionStart.dot(sweepDir);
		this.endDist = intersectionEnd.dot(sweepDir);
	}

	/**
	 * Given two intersecting polygons, the intersection points and a collision
	 * normal, get the maximum penetration distance along the normal.
	 * 
	 * @param in
	 *            The ingoing intersection
	 * @param out
	 *            The outgoing intersection
	 * @param normal
	 *            The collision normal
	 * @param vertsA
	 *            The vertices of polygon A
	 * @param vertsB
	 *            The vertices of polygon B
	 * @return the maximum penetration depth along the given normal
	 */
	public static float getPenetrationDepth(Intersection in, Intersection out,
			Vector normal, Vector[] vertsA, Vector[] vertsB) {
		Vector sweepdir = new Vector(out.position);
		sweepdir.sub(in.position);

		PenetrationSweep ps = new PenetrationSweep(normal, sweepdir,
				in.position, out.position);

		ContourWalker walkerA = ps.new ContourWalker(vertsA, in.edgeA,
				out.edgeA, false);
		ContourWalker walkerB = ps.new ContourWalker(vertsB, (out.edgeB + 1)
				% vertsB.length, (in.edgeB + 1) % vertsB.length, true);

		float penetration = 0;
		float lowerBound = in.position.dot(normal);
		float upperBound = lowerBound;

		while (walkerA.hasNext() || walkerB.hasNext()) {
			if (walkerA.hasNext()
					&& (walkerA.getNextDistance() < walkerB.getNextDistance() || !walkerB
							.hasNext())) {
				walkerA.next();
				if (walkerA.getDistance() < ps.startDist
						|| walkerA.getDistance() > ps.endDist)
					continue; // we don't care for vertices outside of the
								// intersecting borders

				upperBound = walkerA.getPenetration();
				lowerBound = walkerB.getPenetration(walkerA.getDistance());
			} else {
				walkerB.next();
				if (walkerB.getDistance() < ps.startDist
						|| walkerB.getDistance() > ps.endDist)
					continue;

				upperBound = walkerA.getPenetration(walkerB.getDistance());
				lowerBound = walkerB.getPenetration();
			}

			penetration = Math.max(penetration, upperBound - lowerBound);
		}

		return penetration;
	}

	/**
	 * The contour walker walks over the edges or vertices of a polygon. The
	 * class keeps track of two values:
	 * <ul>
	 * <li>The penetration, which is the projection of the current vertex onto
	 * the collision normal</li>
	 * <li>The distance, which is the projection of the current vertex onto the
	 * sweep direction</li>
	 * </ul>
	 * 
	 */
	public class ContourWalker {

		private Vector[] verts;
		private int currentVert;
		private int firstVert;
		private int lastVert;
		private boolean isBackwards;
		private float distance;
		private float nextDistance;
		private float penetration;
		private float penetrationDelta;

		/**
		 * Construct a contourwalker.
		 * 
		 * @param verts
		 *            The vertices of the polygon which's contour is being
		 *            followed
		 * @param firstVert
		 *            The index of the vertex where the contour's subsection
		 *            which we walk on starts
		 * @param lastVert
		 *            The index of the vertex where the contour's subsection
		 *            which we walk on ends
		 * @param isBackwards
		 *            True iff we're walking backwards over the contour
		 */
		public ContourWalker(Vector[] verts, int firstVert, int lastVert,
				boolean isBackwards) {
			if (firstVert < 0 || lastVert < 0)
				throw new IllegalArgumentException(
						"Vertex numbers cannot be negative.");

			if (firstVert > verts.length || lastVert > verts.length)
				throw new IllegalArgumentException(
						"The given vertex array doesn't include the first or the last vertex.");

			this.isBackwards = isBackwards;
			this.verts = verts;
			this.firstVert = firstVert;
			this.lastVert = lastVert;
			this.currentVert = isBackwards ? lastVert : firstVert;

			this.distance = verts[currentVert].dot(sweepDir);
			this.penetration = verts[currentVert].dot(normal);
			calculateNextValues();
		}

		/**
		 * Get the distance of the current vertex
		 * 
		 * @return the distance of the current vertex
		 */
		public float getDistance() {
			return distance;
		}

		/**
		 * Get the distance of the next vertex which can be a point on one of
		 * the borders or a vertex on the polygon's contour.
		 * 
		 * @return The next vertex's distance
		 */
		public float getNextDistance() {
			if (distance < startDist)
				return Math.min(nextDistance, startDist);
			if (distance < endDist)
				return Math.min(nextDistance, endDist);

			return nextDistance;
		}

		/**
		 * Get the penetration of the current vertex.
		 * 
		 * @return the penetration of the current vertex
		 */
		public float getPenetration() {
			return penetration;
		}

		/**
		 * Get the penetration of a point on the current edge at the supplied
		 * distance.
		 * 
		 * @param distance
		 *            The distance at which we want the penetration on the
		 *            current edge
		 * @return The penetration at the supplied distance
		 */
		public float getPenetration(float distance) {
			return penetration + penetrationDelta * (distance - this.distance);
		}

		/**
		 * Let this walker take a step to the next vertex, which is either the
		 * next vertex in the contour or a point on the current edge that
		 * crosses one of the sweep area borders.
		 */
		public void next() {
			if (!hasNext())
				return;

			// if the edge crosses the sweep area border, set our position on
			// the border
			if (distance < startDist && nextDistance > startDist) {
				this.penetration = getPenetration(startDist);
				this.distance = startDist;
				return;
			}

			if (distance < endDist && nextDistance > endDist) {
				this.penetration = getPenetration(endDist);
				this.distance = endDist;
				return;
			}

			if (isBackwards) {
				currentVert = (currentVert - 1 + verts.length) % verts.length;
			} else {
				currentVert = (currentVert + 1) % verts.length;
			}

			distance = verts[currentVert].dot(sweepDir);
			penetration = verts[currentVert].dot(normal);
			calculateNextValues();
		}

		/**
		 * Take a look at the next vertex and sets nextDistance and
		 * penetrationDelta.
		 */
		private void calculateNextValues() {
			int nextVert = isBackwards ? currentVert - 1 : currentVert + 1;
			nextVert = (nextVert + verts.length) % verts.length;

			nextDistance = verts[nextVert].dot(sweepDir);

			penetrationDelta = verts[nextVert].dot(normal) - penetration;
			if (nextDistance == distance) {
				// the next vertex is straight up, since we're searching
				// for the maximum anyway, it's safe to set our penetration
				// to the next vertex's penetration.
				penetration += penetrationDelta;
				penetrationDelta = 0;
			} else {
				penetrationDelta /= nextDistance - distance;
			}
		}

		/**
		 * Check if there are still vertices to walk to.
		 * 
		 * @return True iff a call to hasNext would succeed.
		 */
		public boolean hasNext() {
			if (distance < startDist && nextDistance > startDist)
				return true;

			if (distance < endDist && nextDistance > endDist)
				return true;
			int x = isBackwards ? lastVert - currentVert : currentVert
					- firstVert;
			x = (x + verts.length) % verts.length;
			x = (lastVert - firstVert + verts.length) % verts.length - x;

			return x > 0;
		}

		/**
		 * Reverse the direction of this walker.
		 */
		public void reverse() {
			isBackwards = !isBackwards;

			calculateNextValues();
		}
	}

}
