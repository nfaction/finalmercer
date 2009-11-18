/*
 * Phys2D - a 2D physics engine based on the work of Erin Catto. The
 * original source remains:
 * 
 * Copyright (c) 2006 Erin Catto http://www.gphysics.com
 * 
 * This source is provided under the terms of the BSD License.
 * 
 * Copyright (c) 2006, Phys2D
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the following 
 * conditions are met:
 * 
 *  * Redistributions of source code must retain the above 
 *    copyright notice, this list of conditions and the 
 *    following disclaimer.
 *  * Redistributions in binary form must reproduce the above 
 *    copyright notice, this list of conditions and the following 
 *    disclaimer in the documentation and/or other materials provided 
 *    with the distribution.
 *  * Neither the name of the Phys2D/New Dawn Software nor the names of 
 *    its contributors may be used to endorse or promote products 
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND 
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, 
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY 
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
 * OF SUCH DAMAGE.
 */
package engine.joint;

import engine.body.Body;
import engine.vector.MathUtil;
import engine.vector.Vector2D;
import engine.vector.Vector;

/**
 * A joint that constrains the distance that two bodies can be from each other
 * 
 * @author guRuQu
 */
public class DistanceJoint implements Joint {
	/** The cached impulse through the calculation to yield correct impulse faster */
	protected float accumulatedImpulse;
	/** Anchor point for first body, on which impulse is going to apply*/
	protected Vector anchor1;
	/** Anchor point for second body, on which impulse is going to apply*/
	protected Vector anchor2;
	/** The caculated bias */
	protected float bias;
	/** The first body in this joint */
	protected Body body1;
	/** The second bodu in this joint */
	protected Body body2;
	/** The distance between the bodies */
	private float distant;
	/** Distance Vector*/
	protected Vector dp;
	/** The matrix for applying impulse */
	protected Vector2D M;
	/** The rotation of the first body */
	protected Vector r1;
	/** The rotation of the second body */
	protected Vector r2;
	/** The scalar */
	protected float sc;

	/**
	 * @param body1	The first body to be attached on constraint
	 * @param body2 The second body to be attached on constraint
	 * @param anchor1 The anchor point on first body
	 * @param anchor2 The anchor point on second body
	 * @param distant The fixed distance that is going to keep between two bodies
	 */
	public DistanceJoint(Body body1, Body body2, Vector anchor1,
			Vector anchor2, float distant) {
		this.body1 = body1;
		this.body2 = body2;
		this.anchor1 = anchor1;
		this.anchor2 = anchor2;
		this.distant = distant * distant;
	}
	
	/**
	 * @see engine.joint.Joint#applyImpulse()
	 */
	public void applyImpulse() {
		Vector dv = new Vector(body2.getVelocity());
		dv.add(MathUtil.cross(body2.getAngularVelocity(), r2));
		dv.sub(body1.getVelocity());
		dv.sub(MathUtil.cross(body1.getAngularVelocity(), r1));

		float ju = -dv.dot(dp) + bias;
		float p = ju / sc;

		Vector impulse = new Vector(dp);
		impulse.scale(p);

		if (!body1.isStatic()) {
			Vector accum1 = new Vector(impulse);
			accum1.scale(-body1.getInvMass());
			body1.adjustVelocity(accum1);
			body1.adjustAngularVelocity(-(body1.getInvI() * MathUtil.cross(r1,
					impulse)));
		}

		if (!body2.isStatic()) {
			Vector accum2 = new Vector(impulse);
			accum2.scale(body2.getInvMass());
			body2.adjustVelocity(accum2);
			body2.adjustAngularVelocity(body2.getInvI()
					* MathUtil.cross(r2, impulse));
		}

		accumulatedImpulse += p;
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
		Vector2D rot1 = new Vector2D(body1.getRotation());
		Vector2D rot2 = new Vector2D(body2.getRotation());
		r1 = MathUtil.mul(rot1, anchor1);
		r2 = MathUtil.mul(rot2, anchor2);

		Vector2D K1 = new Vector2D();
		K1.col1.x = body1.getInvMass() + body2.getInvMass();
		K1.col2.x = 0.0f;
		K1.col1.y = 0.0f;
		K1.col2.y = body1.getInvMass() + body2.getInvMass();

		Vector2D K2 = new Vector2D();
		K2.col1.x = body1.getInvI() * r1.y * r1.y;
		K2.col2.x = -body1.getInvI() * r1.x * r1.y;
		K2.col1.y = -body1.getInvI() * r1.x * r1.y;
		K2.col2.y = body1.getInvI() * r1.x * r1.x;

		Vector2D K3 = new Vector2D();
		K3.col1.x = body2.getInvI() * r2.y * r2.y;
		K3.col2.x = -body2.getInvI() * r2.x * r2.y;
		K3.col1.y = -body2.getInvI() * r2.x * r2.y;
		K3.col2.y = body2.getInvI() * r2.x * r2.x;

		Vector2D K = MathUtil.add(MathUtil.add(K1, K2), K3);

		Vector p1 = new Vector(body1.getPosition());
		p1.add(r1);
		Vector p2 = new Vector(body2.getPosition());
		p2.add(r2);
		dp = new Vector(p2);
		dp.sub(p1);

		float biasFactor = 0.3f;
		bias = biasFactor * (-dp.lengthSquared() + distant);

		dp.normalise();

		sc = MathUtil.mul(K, dp).dot(dp);

		Vector impulse = new Vector(dp);
		impulse.scale(accumulatedImpulse);

		if (!body1.isStatic()) {
			Vector accum1 = new Vector(impulse);
			accum1.scale(-body1.getInvMass());
			body1.adjustVelocity(accum1);
			body1.adjustAngularVelocity(-(body1.getInvI() * MathUtil.cross(r1,
					impulse)));
		}

		if (!body2.isStatic()) {
			Vector accum2 = new Vector(impulse);
			accum2.scale(body2.getInvMass());
			body2.adjustVelocity(accum2);
			body2.adjustAngularVelocity(body2.getInvI()
					* MathUtil.cross(r2, impulse));
		}
	}

	/**
	 * @see engine.joint.Joint#setRelaxation(float)
	 */
	public void setRelaxation(float relaxation) {
	}

}
