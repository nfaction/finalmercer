package engine.joint;

import engine.shapes.Body;
import engine.vector.*;

/**
 * Create a joint that keeps the distance between two bodies in a given range
 * 
 * @author Jeffery D. AHern
 */
public class SlideJoint implements Joint {
	/** The marker to indicate minimum collision */
	private static final int COLLIDE_MIN=-1;
	/** The marker to indicate no collision */
	private static final int COLLIDE_NONE=0;
	/** The marker to indicate maximum collision */
	private static final int COLLIDE_MAX=1;
	
	/**The first body in the constraint*/
	private Body body1;
	/**The second body in the constraint*/
	private Body body2;
	/** Anchor point for first body, on which impulse is going to apply*/
	private Vector anchor1;
	/** Anchor point for second body, on which impulse is going to apply*/
	private Vector anchor2;
	/** The rotation of the first body */
	protected Vector r1;
	/** The rotation of the second body */
	protected Vector r2;
	/** The minimum distance between the two bodies */
	protected float minDistance;
	/** The maximum distance between the two bodies */
	protected float maxDistance;
	/** The restitution on hitting the end of the joint */
	protected float restitutionConstant;
	
	/** The collision side of the end of the joint if any */
	private int collideSide;
	/** Normalised distance vector*/
	private Vector ndp;
	/** The cached impulse through the calculation to yield correct impulse faster */
	private float accumulateImpulse;
	/** The minimum distance between the two bodies squared */
	private float minDistance2;
	/** The maximum distance between the two bodies squared */
	private float maxDistance2;
	/** Used to calculate the relation ship between impulse and velocity change between body*/
	private float K;
	/** The restitution constant when angle bounce on either side*/
	private float restitute;
	
	/**
	 * Create a new joint
	 * 
	 * @param body1	The first body to be attached on constraint
	 * @param body2 The second body to be attached on constraint
	 * @param anchor1 The anchor point on first body
	 * @param anchor2 The anchor point on second body
	 * @param minDistance The maximum distance limit of the slide
	 * @param maxDistance The minimum distance limit of the slide
	 * @param restitution The restitution body is going to be effected when bounce off the distance limit
	 */
	public SlideJoint(Body body1,Body body2,Vector anchor1,Vector anchor2,float minDistance,float maxDistance,float restitution){
		this.restitutionConstant=restitution;
		this.minDistance2=minDistance;
		this.maxDistance2=maxDistance;
		this.minDistance=minDistance*minDistance;
		this.maxDistance=maxDistance*maxDistance;
		this.body1=body1;
		this.body2=body2;
		this.anchor1=anchor1;
		this.anchor2=anchor2;
		
	}
	
	/**
	 * @see engine.joint.Joint#applyImpulse()
	 */
	public void applyImpulse() {
		if(collideSide==COLLIDE_NONE)
			return;
		
		Vector dv = new Vector(body2.getVelocity());
		dv.add(MathUtil.cross(body2.getAngularVelocity(),r2));
		dv.sub(body1.getVelocity());
		dv.sub(MathUtil.cross(body1.getAngularVelocity(),r1));
		float reln = dv.dot(ndp);
		reln = restitute-reln;
		float P = reln/K;
		float newImpulse;
		if(collideSide==COLLIDE_MIN){
			newImpulse = accumulateImpulse+P<0.0f?accumulateImpulse+P:0.0f;
		}else{
			newImpulse = accumulateImpulse+P>0.0f?accumulateImpulse+P:0.0f;
		}
		P = newImpulse-accumulateImpulse;
		accumulateImpulse = newImpulse;
		
		Vector impulse = new Vector(ndp);
		impulse.scale(P);
		
		if (!body1.isStatic()) {
			Vector accum1 = new Vector(impulse);
			accum1.scale(body1.getInvMass());
			body1.adjustVelocity(accum1);
			body1.adjustAngularVelocity((body1.getInvI() * MathUtil.cross(r1, impulse)));
		}
		if (!body2.isStatic()) {
			Vector accum2 = new Vector(impulse);
			accum2.scale(-body2.getInvMass());
			body2.adjustVelocity(accum2);
			body2.adjustAngularVelocity(-(body2.getInvI() * MathUtil.cross(r2, impulse)));
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
		float biasFactor=0.01f;
		float biasImpulse=0.0f;
		Vector2D rot1 = new Vector2D(body1.getRotation());
		Vector2D rot2 = new Vector2D(body2.getRotation());

		 r1 = MathUtil.mul(rot1,anchor1);
		 r2 = MathUtil.mul(rot2,anchor2);
		
		Vector p1 = new Vector(body1.getPosition());
		p1.add(r1);
		Vector p2 = new Vector(body2.getPosition());
		p2.add(r2);
		Vector dp = new Vector(p2);
		dp.sub(p1);
		
		Vector dv = new Vector(body2.getVelocity());
		dv.add(MathUtil.cross(body2.getAngularVelocity(),r2));
		dv.sub(body1.getVelocity());
		dv.sub(MathUtil.cross(body1.getAngularVelocity(),r1));
	    
		ndp = new Vector(dp);
		ndp.normalise();
		
		restitute = -restitutionConstant*dv.dot(ndp);
		
		Vector v1 = new Vector(ndp);
		v1.scale(-body2.getInvMass() - body1.getInvMass());

		Vector v2 = MathUtil.cross(MathUtil.cross(r2, ndp), r2);
		v2.scale(-body2.getInvI());
		
		Vector v3 = MathUtil.cross(MathUtil.cross(r1, ndp),r1);
		v3.scale(-body1.getInvI());
		
		Vector K1 = new Vector(v1);
		K1.add(v2);
		K1.add(v3);
		
		K = K1.dot(ndp);
		float length=dp.lengthSquared();
		if(length<minDistance){
			if(collideSide!=COLLIDE_MIN)
				accumulateImpulse=0;
			collideSide=COLLIDE_MIN;			
			biasImpulse=-biasFactor*(length-minDistance);
			if(restitute<0)
				restitute=0;
		}else if(length>maxDistance){
			if(collideSide!=COLLIDE_MAX)
				accumulateImpulse=0;
			collideSide=COLLIDE_MAX;
			biasImpulse=-biasFactor*(length-maxDistance);
			if(restitute>0)
				restitute=0;
		}else{
			collideSide=COLLIDE_NONE;
			accumulateImpulse=0;
		}
		restitute+=biasImpulse;
		Vector impulse = new Vector(ndp);
		impulse.scale(accumulateImpulse);
		
		if (!body1.isStatic()) {
			Vector accum1 = new Vector(impulse);
			accum1.scale(body1.getInvMass());
			body1.adjustVelocity(accum1);
			body1.adjustAngularVelocity((body1.getInvI() * MathUtil.cross(r1, impulse)));
		}
		if (!body2.isStatic()) {
			Vector accum2 = new Vector(impulse);
			accum2.scale(-body2.getInvMass());
			body2.adjustVelocity(accum2);
			body2.adjustAngularVelocity(-(body2.getInvI() * MathUtil.cross(r2, impulse)));
		}
	}

	/**
	 * Get the minimum distance between two bodies
	 * 
	 * @return The minimum distance between two bodies
	 */
	public float getMinDistance() {
		return minDistance2;
	}
	
	/**
	 * Set the minimum allowed distance between the two bodies
	 * 
	 * @param minDistance The minimum distance allowed between the two bodies
	 */
	public void setMinDistance(float minDistance) {
		this.minDistance = minDistance;
	}

	/**
	 * Get the maximum distance between two bodies
	 * 
	 * @return The minimum distance between two bodies
	 */
	public float getMaxDistance() {
		return maxDistance2;
	}

	/**
	 * Set the maximum allowed distance between the two bodies
	 * 
	 * @param maxDistance The maximum distance allowed between the two bodies
	 */
	public void setMaxDistance(float maxDistance) {
		this.maxDistance = maxDistance;
	}
	
	/**
	 * Get the restitution constant
	 * 
	 * @return The restitution constant
	 */
	public float getRestitutionConstant() {
		return restitutionConstant;
	}

	/**
	 * Set the restitution constant
	 * 
	 * @param restitutionConstant The restitution constant
	 */
	public void setRestitutionConstant(float restitutionConstant) {
		this.restitutionConstant = restitutionConstant;
	}
	
	/**
	 * @see engine.joint.Joint#setRelaxation(float)
	 */
	public void setRelaxation(float relaxation) {
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
