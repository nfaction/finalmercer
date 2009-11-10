package engine.joint;

import engine.body.Body;
import engine.vector.MathUtil;
import engine.vector.Matrix2f;
import engine.vector.Vector;

/**
 * A joint that constrains the angle two bodies can be at in relation to each other.
 * 
 * @author guRuQu
 */
public class AngleJoint implements Joint {
	/**The higher angle bound in the angle constraint*/
	private float rotateA;
	/**The lower angle bound in the angle constraint*/
	private float rotateB;
	/**The first body in the constraint*/
	private Body body1;
	/**The second body in the constraint*/
	private Body body2;
	/** Anchor point for first body, on which impulse is going to apply*/
	private Vector anchor1;
	/** Anchor point for second body, on which impulse is going to apply*/
	private Vector anchor2;
	/** The cached impulse through the calculation to yield correct impulse faster */
	private float accumulateImpulse;
	/** The target angular velocity after bounce on either side*/
	private float restituteAngular;
	/** Indication on which side the constraint is violated*/
	private int bounceSide;
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
	/** for bounceSide to indicate bounce on lower side*/
	private final int BOUNCE_LOWER = -1;
	/** for bounceSide to indicate bounce on no side*/
	private final int BOUNCE_NONE = 0;
	/** for bounceSide to indicate bounce on higher side*/
	private final int BOUNCE_HIGHER = 1;
	/** The restitution constant when angle bounce on either side*/
	float restitution;
	/** R = r1 + d */
	private Vector R;

	/**
	 * Create a new angle joint 
	 * 
	 * @param body1	The first body that is attached on the constraint
	 * @param body2 The second body that is attached on the constraint
	 * @param anchor1 The anchor point on first body
	 * @param anchor2 The anchor point on second body
	 * @param rotateA The higher angle bound for constraint
	 * @param rotateB The lower angle bound for constraint
	 */
	public AngleJoint(Body body1, Body body2, Vector anchor1,
			Vector anchor2, float rotateA, float rotateB) {
		this(body1, body2, anchor1, anchor2, rotateA, rotateB, 0);
	}

	/**
	 * Create a new angle joint 
	 * 
	 * @param body1	The first body that is attached on the constraint
	 * @param body2 The second body that is attached on the constraint
	 * @param anchor1 The anchor point on first body
	 * @param anchor2 The anchor point on second body
	 * @param rotateA The higher angle bound for constraint
	 * @param rotateB The lower angle bound for constraint
	 * @param restitution The restitution when body bounce on either side
	 */
	public AngleJoint(Body body1, Body body2, Vector anchor1,
			Vector anchor2, float rotateA, float rotateB, float restitution) {
		this.body1 = body1;
		this.body2 = body2;
		this.rotateA = rotateA;
		this.rotateB = rotateB;
		this.anchor1 = anchor1;
		this.anchor2 = anchor2;
		this.restitution = restitution;
	}

	/**
	 * @see engine.joint.Joint#applyImpulse()
	 */
	public void applyImpulse() {
		if (bounceSide == BOUNCE_NONE)
			return;
		Matrix2f rot1 = new Matrix2f(body1.getRotation());
		Matrix2f rot2 = new Matrix2f(body2.getRotation());
		Vector r1 = MathUtil.mul(rot1, anchor1);
		Vector r2 = MathUtil.mul(rot2, anchor2);

		Vector relativeVelocity = new Vector(body2.getVelocity());
		relativeVelocity.add(MathUtil.cross(r2, body2.getAngularVelocity()));
		relativeVelocity.sub(body1.getVelocity());
		relativeVelocity.sub(MathUtil.cross(r1, body1.getAngularVelocity()));

		float rv = MathUtil.cross(dp, relativeVelocity) / dlength2
				- body1.getAngularVelocity();
		rv = restituteAngular - rv;

		float p = rv / K;
		float oldImpulse = accumulateImpulse;
		float newImpulse;

		if (bounceSide == BOUNCE_HIGHER) {
			newImpulse = accumulateImpulse + p > 0.0f ? accumulateImpulse + p
					: 0.0f;
			p = newImpulse - oldImpulse;
		} else {
			newImpulse = accumulateImpulse + p < 0.0f ? accumulateImpulse + p
					: 0.0f;
			p = newImpulse - oldImpulse;
		}
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
		float RB = body1.getRotation() + rotateB;

		Vector VA = new Vector((float) Math.cos(RA), (float) Math.sin(RA));
		Vector VB = new Vector((float) Math.cos(RB), (float) Math.sin(RB));

		Matrix2f rot1 = new Matrix2f(body1.getRotation());
		Matrix2f rot2 = new Matrix2f(body2.getRotation());
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
		// System.out.println(accumulateImpulse);
		Vector relativeVelocity = new Vector(body2.getVelocity());
		relativeVelocity.add(MathUtil.cross(r2, body2.getAngularVelocity()));
		relativeVelocity.sub(body1.getVelocity());
		relativeVelocity.sub(MathUtil.cross(r1, body1.getAngularVelocity()));

		// relativeVelocity.add(MathUtil.cross(dp,body1.getAngularVelocity()));
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

		restituteAngular = -restitution
				* (MathUtil.cross(dp, relativeVelocity) / dlength2 - body1
						.getAngularVelocity());

		if (MathUtil.cross(ndp, VA) > 0) {
			// collide on A side
			if (bounceSide != BOUNCE_LOWER)
				accumulateImpulse = 0;
			biasImpulse = biasFactor * MathUtil.cross(ndp, VA) * invDT;
			bounceSide = BOUNCE_LOWER;
			if (restituteAngular < 0) {
				restituteAngular = 0;
			}
		} else if (MathUtil.cross(VB, ndp) > 0) {
			// collide on B side
			if (bounceSide != BOUNCE_HIGHER)
				accumulateImpulse = 0;
			biasImpulse = -biasFactor * MathUtil.cross(VB, ndp) * invDT;
			bounceSide = BOUNCE_HIGHER;
			if (restituteAngular > 0) {
				restituteAngular = 0;
			}
		} else {
			accumulateImpulse = 0;
			biasImpulse = 0.0f;
			bounceSide = BOUNCE_NONE;
		}
		restituteAngular += biasImpulse;

		Vector impulse = new Vector(n);
		impulse.scale(accumulateImpulse);
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
	 * Get the upper angle bound
	 * 
	 * @return The upper angle bound
	 */
	public float getRotateA() {
		return rotateA;
	}

	/** 
	 * Get the lower angle bound
	 * 
	 * @return The lower angle bound
	 */
	public float getRotateB() {
		return rotateB;
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
