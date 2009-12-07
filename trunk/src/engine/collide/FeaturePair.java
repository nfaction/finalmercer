package engine.collide;



/**
 * An identifier for a pair of edges between two bodies. This identifies which
 * contact points we're using in any given situation
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 */
public class FeaturePair {


	int inEdge1;
	int outEdge1;
	int inEdge2;
	int outEdge2;
	
	/** 
	 * Public constructor since something in the raw port want to access
	 * it. Should not be constructed by a user.
	 */
	public FeaturePair() {
	}
	
	/**
	 * Construct a feature pair and set edges.
	 * 
	 * @param inEdge1
	 * @param outEdge1
	 * @param inEdge2
	 * @param outEdge2
	 */
	public FeaturePair(int inEdge1, int inEdge2, int outEdge1, int outEdge2) {
		this.inEdge1 = inEdge1;
		this.inEdge2 = inEdge2;
		this.outEdge1 = outEdge1;
		this.outEdge2 = outEdge2;
	}
	
	/**
	 * Create a new feature pair
	 * 
	 * @param index The index identifing the feature pair that collided
	 */
	FeaturePair(int index) {
		inEdge1 = index;
	}
	
	/**
	 * Get this feature pair as a key value used for hashing
	 * 
	 * @return The key value
	 */
    int getKey() {
		return inEdge1 + (outEdge1 << 8) + (inEdge2 << 16) + (outEdge2 << 24);
	}
	
    /**
     * @see java.lang.Object#hashCode()
     */
	public int hashCode() {
		return getKey();
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (other instanceof FeaturePair) {
			return ((FeaturePair) other).getKey() == getKey();
		}
		
		return false;
	}
	
	/**
	 * Set the contents of this pair from another
	 * 
	 * @param other The other pair to populate this pair from
	 */
	public void set(FeaturePair other) {
		inEdge1 = other.inEdge1;
		inEdge2 = other.inEdge2;
		outEdge1 = other.outEdge1;
		outEdge2 = other.outEdge2;
	}
	
	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return "((" + inEdge1 + "," + inEdge2 + "),(" + outEdge1 + "," + outEdge2 + "))"; 
	}
}
