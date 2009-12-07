package engine.joint;

import engine.shapes.Body;
import engine.vector.Vector;

/**
 * A joint between two bodies.
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 */
public strictfp class FixedJoint implements Joint {
	public static int NEXT_ID = 0;
	private Body body1;
	private Body body2;
	private int id;
	private BasicJoint joint1;
	private BasicJoint joint2;

	/**
	 * Create a joint holding two bodies together
	 * 
	 * @param b1
	 *            The first body attached to the joint
	 * @param b2
	 *            The second body attached to the joint
	 */
	public FixedJoint(Body b1, Body b2) {
		id = NEXT_ID++;

		set(b1, b2);
	}

	/**
	 * Set the relaxtion value on this joint.
	 * 
	 * @param relaxation
	 *            The relaxation value
	 */
	public void setRelaxation(float relaxation) {
		joint1.setRelaxation(relaxation);
		joint2.setRelaxation(relaxation);
	}

	/**
	 * Get the first body attached to this joint
	 * 
	 * @return The first body attached to this joint
	 */
	public Body getBody1() {
		return body1;
	}

	/**
	 * Get the second body attached to this joint
	 * 
	 * @return The second body attached to this joint
	 */
	public Body getBody2() {
		return body2;
	}

	/**
	 * Reconfigure this joint
	 * 
	 * @param b1
	 *            The first body attached to this joint
	 * @param b2
	 *            The second body attached to this joint
	 */
	public void set(Body b1, Body b2) {
		body1 = b1;
		body2 = b2;

		joint1 = new BasicJoint(b1, b2, new Vector(b1.getPosition()));
		joint2 = new BasicJoint(b2, b1, new Vector(b2.getPosition()));
	}

	/**
	 * Precaculate everything and apply initial impulse before the simulation
	 * step takes place
	 * 
	 * @param invDT
	 *            The amount of time the simulation is being stepped by
	 */
	public void preStep(float invDT) {
		joint1.preStep(invDT);
		joint2.preStep(invDT);
	}

	/**
	 * Apply the impulse caused by the joint to the bodies attached.
	 */
	public void applyImpulse() {
		joint1.applyImpulse();
		joint2.applyImpulse();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return id;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (other.getClass() == getClass()) {
			return ((FixedJoint) other).id == id;
		}

		return false;
	}
}
