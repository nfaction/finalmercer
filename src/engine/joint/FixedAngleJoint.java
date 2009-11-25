package engine.joint;

import engine.shapes.Body;
import engine.vector.MathUtil;
import engine.vector.Vector2D;
import engine.vector.Vector;

/**
 * A joint that will maintain a fixed angle between two bodies
 * 
 * @author Jeffery D. Ahern
 */
public class FixedAngleJoint implements Joint {
	private float rotateA;
	private Body body1;
	private Body body2;
	private Vector anchor1;
	private Vector anchor2;
	/** The cached impulse through the calculation to yield correct impulse faster */
	private float accumulateImpulse;
	/** The squared distance of two body*/
	private float dlength2;
	/** Used to calculate the relation ship between impulse and velocity change between body*/
	private float K;
	/** Normalised distance vector*/
	private Vector ndp;
	/** Distance Vector*/
	private Vector dp;
	/** The normal vector of the impulse direction*/
	private Vector n;
	/** R = r1 + d */
	private Vector R;

	/**
	 * @param body1	The first body to be attached on constraint
	 * @param body2 The second body to be attached on constraint
	 * @param anchor1 The anchor point on first body
	 * @param anchor2 The anchor point on second body
	 * @param rotateA The fixed angle on body2 from body1
	 */
	public FixedAngleJoint(Body body1, Body body2, Vector anchor1,
			Vector anchor2, float rotateA) {
		this.body1 = body1;
		this.body2 = body2;
		this.rotateA = rotateA;
		this.anchor1 = anchor1;
		this.anchor2 = anchor2;
	}

	/**
	 * @see engine.joint.Joint#applyImpulse()
	 */
	public void applyImpulse() {
		Vector2D rot1 = new Vector2D(body1.getRotation());
		Vector2D rot2 = new Vector2D(body2.getRotation());
		Vector r1 = MathUtil.mul(rot1, anchor1);
		Vector r2 = MathUtil.mul(rot2, anchor2);

		Vector relativeVelocity = new Vector(body2.getVelocity());
		relativeVelocity.add(MathUtil.cross(r2, body2.getAngularVelocity()));
		relativeVelocity.sub(body1.getVelocity());
		relativeVelocity.sub(MathUtil.cross(r1, body1.getAngularVelocity()));

		float rv = MathUtil.cross(dp, relativeVelocity) / dlength2
				- body1.getAngularVelocity();
		rv = 0 - rv;

		float p = rv / K;
		float oldImpulse = accumulateImpulse;
		float newImpulse;

		newImpulse = accumulateImpulse + p;
		p = newImpulse - oldImpulse;

		accumulateImpulse = newImpulse;

		Vector impulse = new Vector(n);
		impulse.scale(p);
		if (!body1.isStatic()) {
			Vector accum1 = new Vector(impulse);
			accum1.scale(body1.getInvMass());
			body1.adjustVelocity(accum1);
			body1.adjustAngularVelocity((body1.getInvI() * MathUtil.cross(R,
					impulse)));
		}
		if (!body2.isStatic()) {
			Vector accum2 = new Vector(impulse);
			accum2.scale(-body2.getInvMass());
			body2.adjustVelocity(accum2);
			body2.adjustAngularVelocity(-(body2.getInvI() * MathUtil.cross(r2,
					impulse)));
		}
	}

	/**
	 * @see engine.joint.Joint#getBody1()
	 */
	public Body getBody1() {
		return body1;
	}

	/**
	 * @see engine.joint.Joint#getBody2()
	 */
	public Body getBody2() {
		return body2;
	}
	
	/**
	 * @see engine.joint.Joint#preStep(float)
	 */
	public void preStep(float invDT) {
		float biasFactor = 0.005f;
		float biasImpulse = 0.0f;
		float RA = body1.getRotation() + rotateA;

		Vector VA = new Vector((float) Math.cos(RA), (float) Math.sin(RA));

		Vector2D rot1 = new Vector2D(body1.getRotation());
		Vector2D rot2 = new Vector2D(body2.getRotation());
		Vector r1 = MathUtil.mul(rot1, anchor1);
		Vector r2 = MathUtil.mul(rot2, anchor2);

		Vector p1 = new Vector(body1.getPosition());
		p1.add(r1);
		Vector p2 = new Vector(body2.getPosition());
		p2.add(r2);
		dp = new Vector(p2);
		dp.sub(p1);
		dlength2 = dp.lengthSquared();
		ndp = new Vector(dp);
		ndp.normalise();

		R = new Vector(r1);
		R.add(dp);
		Vector relativeVelocity = new Vector(body2.getVelocity());
		relativeVelocity.add(MathUtil.cross(r2, body2.getAngularVelocity()));
		relativeVelocity.sub(body1.getVelocity());
		relativeVelocity.sub(MathUtil.cross(r1, body1.getAngularVelocity()));

		n = new Vector(-ndp.y, ndp.x);
		Vector v1 = new Vector(n);
		v1.scale(-body2.getInvMass() - body1.getInvMass());

		Vector v2 = MathUtil.cross(MathUtil.cross(r2, n), r2);
		v2.scale(-body2.getInvI());

		Vector v3 = MathUtil.cross(MathUtil.cross(R, n), r1);
		v3.scale(-body1.getInvI());

		Vector K1 = new Vector(v1);
		K1.add(v2);
		K1.add(v3);

		K = MathUtil.cross(dp, K1) / dlength2 - MathUtil.cross(R, n)
				* body1.getInvI();



			biasImpulse = biasFactor * MathUtil.cross(ndp, VA) * invDT;

		Vector impulse = new Vector(n);
		impulse.scale(accumulateImpulse+biasImpulse);
		if (!body1.isStatic()) {
			Vector accum1 = new Vector(impulse);
			accum1.scale(body1.getInvMass());
			body1.adjustVelocity(accum1);
			body1.adjustAngularVelocity((body1.getInvI() * MathUtil.cross(R,
					impulse)));
		}
		if (!body2.isStatic()) {
			Vector accum2 = new Vector(impulse);
			accum2.scale(-body2.getInvMass());
			body2.adjustVelocity(accum2);
			body2.adjustAngularVelocity(-(body2.getInvI() * MathUtil.cross(r2,
					impulse)));
		}
	}

	/**
	 * @see engine.joint.Joint#setRelaxation(float)
	 */
	public void setRelaxation(float relaxation) {
	}

	/**
	 * Get the angle maintained between the two bodies
	 * 
	 * @return The angle maintained between the two bodies
	 */
	public float getRotateA() {
		return rotateA;
	}


	/**
	 * Get the anchor of the joint on the first body
	 * 
	 * @return The anchor of the joint on the first body
	 */
	public Vector getAnchor1() {
		return anchor1;
	}

	/**
	 * Get the anchor of the joint on the second body
	 * 
	 * @return The anchor of the joint on the second body
	 */
	public Vector getAnchor2() {
		return anchor2;
	}

}
