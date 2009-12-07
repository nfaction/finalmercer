package engine.collide;

import engine.vector.Vector;

/**
 * Implements a sweepline algorithm that facilitates collision detection between
 * two bodys
 * 
 * @author Jeffery D. Ahern
 * 
 */
public class EdgeSweep {

	/** The doubly linked list list of inserted vertices */
	class ProjectedVertex {
		public int vertex;
		public boolean isA;
		public float distance;

		public ProjectedVertex next;
		public ProjectedVertex previous;

		/**
		 * Construct a list element with all it's values set except the next and
		 * previous elements of the list.
		 * 
		 * @param vertex
		 *            Vertex number, usually the index of the vertex in a
		 *            polygon's array
		 * @param isA
		 *            True if this is a vertex belonging to polygon A, false if
		 *            B
		 * @param distance
		 *            Distance of the projection onto the sweep direction from
		 *            the origin
		 */
		public ProjectedVertex(int vertex, boolean isA, float distance) {
			this.vertex = vertex;
			this.isA = isA;
			this.distance = distance;
		}
	}

	private ProjectedVertex current;
	private Vector sweepDir;

	/**
	 * Constructs an EdgeSweep object with the given sweep direction.
	 * 
	 * @param sweepDir
	 *            The direction in which to sweep
	 */
	public EdgeSweep(Vector sweepDir) {
		this.sweepDir = new Vector(sweepDir);
	}

	/**
	 * Insert a new element into our list that is known to be somewhere before
	 * the current element. It walks backwards over the vertex list untill a
	 * vertex with a smaller distance or the start of the list is reached and
	 * inserts the element there.
	 * 
	 * @param vertex
	 *            Vertex number, usually the index of the vertex in a polygon's
	 *            array
	 * @param isA
	 *            True if this is a vertex belonging to polygon A, false if B
	 * @param distance
	 *            Distance of the projection onto the sweep direction from the
	 *            origin
	 */
	private void insertBackwards(int vertex, boolean isA, float distance) {
		ProjectedVertex svl = new ProjectedVertex(vertex, isA, distance);

		if (current == null) {
			current = svl;
			return;
		}

		while (current.distance > svl.distance) {
			if (current.previous == null) {
				// insert before current
				current.previous = svl;
				svl.next = current;
				current = svl;
				return;
			}

			current = current.previous;
		}

		// insert after current
		svl.next = current.next;
		svl.previous = current;
		current.next = svl;

		if (svl.next != null)
			svl.next.previous = svl;

		current = svl;
	}

	/**
	 * Insert a vertex into the sorted list.
	 * 
	 * @param vertex
	 *            Vertex number, usually the index of the vertex in a polygon's
	 *            array
	 * @param isA
	 *            True if this is a vertex belonging to polygon A, false if B
	 * @param distance
	 *            Distance of the projection onto the sweep direction from the
	 *            origin
	 */
	public void insert(int vertex, boolean isA, float distance) {
		if (current == null || current.distance <= distance)
			insertForwards(vertex, isA, distance);
		else
			insertBackwards(vertex, isA, distance);
	}

	/**
	 * Insert a new element into our list that is known to be somewhere after
	 * the current element. It walks forwards over the vertex list untill a
	 * vertex with a smaller distance or the end of the list is reached and
	 * inserts the element there.
	 * 
	 * @param vertex
	 *            Vertex number, usually the index of the vertex in a polygon's
	 *            array
	 * @param isA
	 *            True if this is a vertex belonging to polygon A, false if B
	 * @param distance
	 *            Distance of the projection onto the sweep direction from the
	 *            origin
	 */
	private void insertForwards(int vertex, boolean isA, float distance) {
		ProjectedVertex svl = new ProjectedVertex(vertex, isA, distance);

		if (current == null) {
			current = svl;
			return;
		}

		while (current.distance <= svl.distance) {
			if (current.next == null) {
				// insert after current
				current.next = svl;
				svl.previous = current;
				current = svl;
				return;
			}

			current = current.next;
		}

		// insert before current
		svl.next = current;
		svl.previous = current.previous;
		current.previous = svl;

		if (svl.previous != null)
			svl.previous.next = svl;

		current = svl;
	}

	/**
	 * Set current to the first element of the list
	 */
	private void goToStart() {
		while (current.previous != null)
			current = current.previous;
	}

	/**
	 * Get all edges whose projection onto the sweep direction overlap.
	 * 
	 * @return The numbers of the overlapping edges. The array will always have
	 *         dimension [n][2], where [i][0] is the edge of polygon A and
	 *         [i][1] of B.
	 */
	public int[][] getOverlappingEdges() {
		if (current == null)
			return new int[0][2];

		goToStart();

		CurrentEdges edgesA = new CurrentEdges();
		CurrentEdges edgesB = new CurrentEdges();
		EdgePairs collidingEdges = new EdgePairs();

		float lastDist = -Float.MAX_VALUE;

		while (current != null) {
			if (current.distance > lastDist) {
				lastDist = current.distance;
				edgesA.removeScheduled();
				edgesB.removeScheduled();
			}

			if (current.isA) {
				if (!edgesA.contains(current.vertex)) {
					edgesA.addEdge(current.vertex);

					int[] edgeListB = edgesB.getEdges();
					for (int i = 0; i < edgeListB.length; i++)
						collidingEdges.add(current.vertex, edgeListB[i]);

				} else {
					edgesA.scheduleRemoval(current.vertex);
				}
			} else {
				if (!edgesB.contains(current.vertex)) {
					edgesB.addEdge(current.vertex);

					int[] edgeListA = edgesA.getEdges();
					for (int i = 0; i < edgeListA.length; i++)
						collidingEdges.add(edgeListA[i], current.vertex);

				} else {
					edgesB.scheduleRemoval(current.vertex);
				}
			}

			current = current.next;
		}

		return collidingEdges.toList();
	}

	/**
	 * The list of edges that are touched by the sweepline at a given time.
	 */
	private class CurrentEdges {
		private LinkedEdgeList currentEdges;
	
		/**
		 * The edges that have been scheduled for removal but have not yet been
		 * removed
		 */
		private LinkedEdgeList scheduledForRemoval;

		/**
		 * Add an edge to the top of the list. We do not check wether it is
		 * already in the list, but maybe this should be done to be on the safe
		 * side.
		 * 
		 * @param e
		 *            The edge to be added
		 */
		public void addEdge(int e) {
			currentEdges = new LinkedEdgeList(e, currentEdges);
		}

		/**
		 * Schedule an edge for removal, it will be removed as soon as
		 * {@link CurrentEdges#removeScheduled()} is called.
		 * 
		 * @param e
		 *            The edge to be scheduled for removal
		 */
		public void scheduleRemoval(int e) {
			if (currentEdges == null)
				return; // this shouldn't happen, but to be sure..

			if (currentEdges.edge == e) {
				currentEdges = currentEdges.next;
			} else {
				LinkedEdgeList current = currentEdges.next;
				LinkedEdgeList last = currentEdges;

				while (current != null) {
					if (current.edge == e) {
						last.next = current.next;
						scheduledForRemoval = new LinkedEdgeList(e,
								scheduledForRemoval);
						return;
					}
					last = current;
					current = current.next;
				}
			}
		}

		/**
		 * Remove the edges that have been scheduled for removal by
		 * {@link CurrentEdges#scheduleRemoval(int)}.
		 */
		public void removeScheduled() {
			scheduledForRemoval = null;
		}

		/**
		 * Check if this edge list contains a specific edge.
		 * 
		 * @param e
		 *            The edge to look for
		 * @return True iff the edgelist contains the edge
		 */
		public boolean contains(int e) {
			LinkedEdgeList current = currentEdges;
			while (current != null) {
				if (current.edge == e)
					return true;
				current = current.next;
			}

			current = scheduledForRemoval;
			while (current != null) {
				if (current.edge == e)
					return true;
				current = current.next;
			}

			return false;
		}

		/**
		 * Get the total number of edges, this includes the edges that are
		 * scheduled for removal.
		 * 
		 * @return The total number of edges
		 */
		public int getNoEdges() {
			int count = 0;
			LinkedEdgeList current = currentEdges;
			while (current != null) {
				count++;
				current = current.next;
			}

			current = scheduledForRemoval;
			while (current != null) {
				count++;
				current = current.next;
			}

			return count;
		}

		/**
		 * Get the list of edges in this list. It should not contain any
		 * duplicates, but that depends on the insertion of elements.
		 * 
		 * @return the list of edges
		 */
		public int[] getEdges() {
			int[] returnEdges = new int[getNoEdges()];

			int i = 0;
			LinkedEdgeList current = currentEdges;
			while (current != null) {
				returnEdges[i] = current.edge;
				i++;
				current = current.next;
			}
			current = scheduledForRemoval;
			while (current != null) {
				returnEdges[i] = current.edge;
				i++;
				current = current.next;
			}

			return returnEdges;
		}

		/** A singly linked list for edges */
		class LinkedEdgeList {
			public int edge;
			public LinkedEdgeList next;

			/**
			 * Construct a new list element with its attributes set to the
			 * supplied values.
			 * 
			 * @param edge
			 *            The edge number
			 * @param next
			 *            The next list element
			 */
			public LinkedEdgeList(int edge, LinkedEdgeList next) {
				this.edge = edge;
				this.next = next;
			}
		}
	}

	/** The list of collision candidates in a linked list */
	private class EdgePairs {
		private EdgePair first;
		private int size = 0;

		/**
		 * Add a pair of edges to this list
		 * 
		 * @param idA
		 *            An edge of polygon A
		 * @param idB
		 *            An edge of polygon B
		 */
		public void add(int idA, int idB) {
			first = new EdgePair(idA, idB, first);
			size++;
		}

		/**
		 * Convert this linked list into a two dimensional array
		 * 
		 * @return The numbers of the overlapping edges. The array will always
		 *         have dimension [n][2], where [i][0] is the edge of polygon A
		 *         and [i][1] of B.
		 */
		public int[][] toList() {
			int[][] list = new int[size][2];

			EdgePair current = first;
			for (int i = 0; i < size; i++) {
				list[i][0] = current.a;
				list[i][1] = current.b;

				current = current.next;
			}

			return list;
		}

		/** The singly linked list representing one pair of edges */
		class EdgePair {
			/** An edge of polygon A */
			public int a;
			/** An edge of polygon B */
			public int b;
			/** The next element in the list */
			public EdgePair next;

			/**
			 * Construct a new list element with all the attributes set to the
			 * provided values.
			 * 
			 * @param a
			 *            An edge of polygon A
			 * @param b
			 *            An edge of polygon B
			 * @param next
			 *            The next element in the list
			 */
			public EdgePair(int a, int b, EdgePair next) {
				this.a = a;
				this.b = b;
				this.next = next;
			}
		}
	}

	/**
	 * Insert a list of edges
	 * 
	 * @param isA
	 *            True iff the inserted vertices are of the first object
	 * @param verts
	 *            The list of vertices to be inserted in counter clockwise order
	 */
	public void addVerticesToSweep(boolean isA, Vector[] verts) {
		for (int i = 0, j = verts.length - 1; i < verts.length; j = i, i++) {
			float dist = sweepDir.dot(verts[i]);

			insert(i, isA, dist);
			insert(j, isA, dist);
		}
	}

	/**
	 * Get the direction of this edgesweep
	 * 
	 * @return the direction of this edgesweep
	 */
	public Vector getSweepDir() {
		return sweepDir;
	}
}
