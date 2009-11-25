package engine.collide;

import engine.vector.Vector;

/**
 * A description of a single contact point between two bodies
 * 
 * @author Jeffery D. Ahern
 */
public strictfp class Contact {
	/** The position of the contact */
	public Vector position = new Vector();
	/** The normal at the contact point which, for convex bodies,
	 * points away from the first body. */
	public Vector normal = new Vector();
	/** ? */
	public float separation;
	/** The impulse accumlated in the direction of the normal */
	public float accumulatedNormalImpulse;
	/** The impulse accumlated in the direction of the tangent */
	public float accumulatedTangentImpulse;
	/** The mass applied throught the normal at this contact point */
	public float massNormal;
	/** The mass applied through the tangent at this contact point */
	public float massTangent;
	/** The correction factor penetration */
	public float bias;
	/** The pair of edges this contact is between */
	public FeaturePair feature = new FeaturePair();
	/** The restitution at this point of contact */
	public float restitution;
	/** The bias impulse accumulated */
	public float biasImpulse;
	
	/**
	 * Create a new contact point
	 */
	public Contact() {
		accumulatedNormalImpulse = 0.0f;
		accumulatedTangentImpulse = 0.0f;
	}
	
	/** 
	 * Get the position of this contact 
	 * 
	 * @return The position of this contact
	 */
	public Vector getPosition() {
		return position;
	}
	
	/**
	 * Set the contact information based on another contact
	 * 
	 * @param contact The contact information
	 */
	public void set(Contact contact) {
		position.set(contact.position);
		normal.set(contact.normal);
		separation = contact.separation;
		accumulatedNormalImpulse = contact.accumulatedNormalImpulse;
		accumulatedTangentImpulse = contact.accumulatedTangentImpulse;
		massNormal = contact.massNormal;
		massTangent = contact.massTangent;
		bias = contact.bias;
		restitution = contact.restitution;
		feature.set(contact.feature);
	}
	
	/**
	 * Get the seperation between bodies
	 * 
	 * @return The seperation between bodies
	 */
	public float getSeparation() {
		return separation;
	}
	
	/**
	 * Get the normal at the point of contact
	 * 
	 * @return The normal at the point of contact
	 */
	public Vector getNormal() {
		return normal;
	}
	
	/**
	 * Set the normal at the point of contact.
	 * 
	 * @param normal The normal at the point of contact
	 */
	public void setNormal(Vector normal) {
		this.normal.set(normal);
	}
	
	/**
	 * Set the position of the contact
	 * 
	 * @param position The position of the contact
	 */
	public void setPosition(Vector position) {
		this.position.set(position);
	}
	
	/**
	 * Get the pairing identifing the location of the contact
	 * 
	 * @return The feature painting identifing the contact
	 */
	public FeaturePair getFeature() {
		return feature;
	}
	
	/**
	 * Set the feature identifying the location of the contact
	 * 
	 * @param pair The pair identifying the location of the contact
	 */
	public void setFeature(FeaturePair pair) {
		this.feature = pair;
	}

	/**
	 * Set the separation between bodies
	 * 
	 * @param separation The separation between bodies at this contact
	 */
	public void setSeparation(float separation) {
		this.separation = separation;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return feature.hashCode();
	}
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (other.getClass() == getClass()) {
			return ((Contact) other).feature.equals(feature);
		}
		
		return false;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[Contact "+position+" n: "+normal+" sep: "+separation+"]";
	}
}
