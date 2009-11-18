package engine.joint;

import engine.body.Body;
import engine.vector.MathUtil;
import engine.vector.Vector2D;
import engine.vector.Vector;

/**
 * A joint between two bodies. The joint affects the impulses applied to 
 * each body each step constraining the movement.
 * 
 * @author Jeffery D. Ahern
 */
public strictfp class BasicJoint implements Joint {
	/** The next ID to be used */
	private static int NEXT_ID = 0;
	
	/** The first body attached to the joint */
	private Body body1;
	/** The second body attached to the joint */
	private Body body2;

	/** The matrix describing the connection between two bodies */
	private Vector2D M = new Vector2D();
	/** The local anchor for the first body */
	private Vector localAnchor1 = new Vector();
	/** The local anchor for the second body */
	private Vector localAnchor2 = new Vector();
	/** The rotation of the anchor of the first body */
	private Vector r1 = new Vector();
	/** The rotation of the anchor of the second body */
	private Vector r2 = new Vector();
	/** ? */
	private Vector bias = new Vector();
	/** The impulse to be applied throught the joint */
	private Vector accumulatedImpulse = new Vector();
	/** How much slip there is in the joint */
	private float relaxation;

	/** The ID of this joint */
	private int id;
	
	/**
	 * Create a joint holding two bodies together
	 * 
	 * @param b1 The first body attached to the joint
	 * @param b2 The second body attached to the joint
	 * @param anchor The anchor point which movement/rotation will occur 
	 * arround.
	 */
	public BasicJoint(Body b1, Body b2, Vector anchor) {
		id = NEXT_ID++;
		accumulatedImpulse.set(0.0f, 0.0f);
		relaxation = 1.0f;
		
		set(b1,b2,anchor);
	}

	/**
	 * Set the relaxtion value on this joint. This value determines
	 * how loose the joint will be
	 * 
	 * @param relaxation The relaxation value
	 */
	public void setRelaxation(float relaxation) {
		this.relaxation = relaxation;
	}
	
	/**
	 * Retrieve the anchor for the first body attached
	 * 
	 * @return The anchor for the first body
	 */
	public Vector getLocalAnchor1() {
		return localAnchor1;
	}

	/**
	 * Retrieve the anchor for the second body attached
	 * 
	 * @return The anchor for the second body
	 */
	public Vector getLocalAnchor2() {
		return localAnchor2;
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
	 * @param b1 The first body attached to this joint
	 * @param b2 The second body attached to this joint
	 * @param anchor The static anchor point between the joints
	 */
	public void set(Body b1, Body b2, Vector anchor) {
		body1 = b1;
		body2 = b2;

		Vector2D rot1 = new Vector2D(body1.getRotation());
		Vector2D rot2 = new Vector2D(body2.getRotation());
		Vector2D rot1T = rot1.transpose();
		Vector2D rot2T = rot2.transpose();

		Vector a1 = new Vector(anchor);
		a1.sub(body1.getPosition());
		localAnchor1 = MathUtil.mul(rot1T,a1);
		Vector a2 = new Vector(anchor);
		a2.sub(body2.getPosition());
		localAnchor2 = MathUtil.mul(rot2T,a2);

		accumulatedImpulse.set(0.0f, 0.0f);
		relaxation = 1.0f;
	}

	/**
	 * Precaculate everything and apply initial impulse before the
	 * simulation step takes place
	 * 
	 * @param invDT The amount of time the simulation is being stepped by
	 */
	public void preStep(float invDT) {
		// Pre-compute anchors, mass matrix, and bias.
		Vector2D rot1 = new Vector2D(body1.getRotation());
		Vector2D rot2 = new Vector2D(body2.getRotation());

		r1 = MathUtil.mul(rot1,localAnchor1);
		r2 = MathUtil.mul(rot2,localAnchor2);

		// deltaV = deltaV0 + K * impulse
		// invM = [(1/m1 + 1/m2) * eye(2) - skew(r1) * invI1 * skew(r1) - skew(r2) * invI2 * skew(r2)]
		//      = [1/m1+1/m2     0    ] + invI1 * [r1.y*r1.y -r1.x*r1.y] + invI2 * [r1.y*r1.y -r1.x*r1.y]
		//        [    0     1/m1+1/m2]           [-r1.x*r1.y r1.x*r1.x]           [-r1.x*r1.y r1.x*r1.x]
		Vector2D K1 = new Vector2D();
		K1.col1.x = body1.getInvMass() + body2.getInvMass();	K1.col2.x = 0.0f;
		K1.col1.y = 0.0f;								K1.col2.y = body1.getInvMass() + body2.getInvMass();

		Vector2D K2 = new Vector2D();
		K2.col1.x =  body1.getInvI() * r1.y * r1.y;		K2.col2.x = -body1.getInvI() * r1.x * r1.y;
		K2.col1.y = -body1.getInvI() * r1.x * r1.y;		K2.col2.y =  body1.getInvI() * r1.x * r1.x;

		Vector2D K3 = new Vector2D();
		K3.col1.x =  body2.getInvI() * r2.y * r2.y;		K3.col2.x = -body2.getInvI() * r2.x * r2.y;
		K3.col1.y = -body2.getInvI() * r2.x * r2.y;		K3.col2.y =  body2.getInvI() * r2.x * r2.x;

		Vector2D K = MathUtil.add(MathUtil.add(K1,K2),K3);
		M = K.invert();

		Vector p1 = new Vector(body1.getPosition());
		p1.add(r1);
		Vector p2 = new Vector(body2.getPosition());
		p2.add(r2);
		Vector dp = new Vector(p2);
		dp.sub(p1);
		
		bias = new Vector(dp);
		bias.scale(-0.1f);
		bias.scale(invDT);

		// Apply accumulated impulse.
		accumulatedImpulse.scale(relaxation);
		
		if (!body1.isStatic()) {
			Vector accum1 = new Vector(accumulatedImpulse);
			accum1.scale(-body1.getInvMass());
			body1.adjustVelocity(accum1);
			body1.adjustAngularVelocity(-(body1.getInvI() * MathUtil.cross(r1, accumulatedImpulse)));
		}

		if (!body2.isStatic()) {
			Vector accum2 = new Vector(accumulatedImpulse);
			accum2.scale(body2.getInvMass());
			body2.adjustVelocity(accum2);
			body2.adjustAngularVelocity(body2.getInvI() * MathUtil.cross(r2, accumulatedImpulse));
		}
	}
	
	/**
	 * Apply the impulse caused by the joint to the bodies attached.
	 */
	public void applyImpulse() {
		Vector dv = new Vector(body2.getVelocity());
		dv.add(MathUtil.cross(body2.getAngularVelocity(),r2));
		dv.sub(body1.getVelocity());
		dv.sub(MathUtil.cross(body1.getAngularVelocity(),r1));
	    dv.scale(-1);
	    dv.add(bias); // TODO: is this baumgarte stabilization?
	    
	    if (dv.lengthSquared() == 0) {
	    	return;
	    }
	    
		Vector impulse = MathUtil.mul(M, dv);

		if (!body1.isStatic()) {
			Vector delta1 = new Vector(impulse);
			delta1.scale(-body1.getInvMass());
			body1.adjustVelocity(delta1);
			body1.adjustAngularVelocity(-body1.getInvI() * MathUtil.cross(r1,impulse));
		}

		if (!body2.isStatic()) {
			Vector delta2 = new Vector(impulse);
			delta2.scale(body2.getInvMass());
			body2.adjustVelocity(delta2);
			body2.adjustAngularVelocity(body2.getInvI() * MathUtil.cross(r2,impulse));
		}
		
		accumulatedImpulse.add(impulse);
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
			return ((BasicJoint) other).id == id;
		}
		
		return false;
	}
}
