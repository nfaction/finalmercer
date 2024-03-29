package engine;

import engine.collide.Collide;
import engine.collide.Contact;
import engine.shapes.Body;
import engine.shapes.StaticBody;
import engine.vector.MathUtil;
import engine.vector.Vector;

/**
 * A arbiter resolving contacts between a pair of bodies
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 */
public strictfp class Arbiter {
	public static final int MAX_POINTS = 10;

	private Contact[] contacts = new Contact[MAX_POINTS];
	private int numContacts;
	private Body body1;
	private Body body2;
	private float friction;

	/**
	 * Create a new arbiter
	 * 
	 * @param b1
	 *            The first body in contact
	 * @param b2
	 *            The second body in contact
	 */
	public Arbiter(Body b1, Body b2) {
		for (int i = 0; i < MAX_POINTS; i++) {
			contacts[i] = new Contact();
		}

		if (!(b2 instanceof StaticBody) && b1.hashCode() < b2.hashCode()) {
			body1 = b1;
			body2 = b2;
		} else {
			body1 = b2;
			body2 = b1;
		}
	}

	/**
	 * Check if this arbiter has two bodies that are resting
	 * 
	 * @return True if the arbiter has two bodies that are "at rest"
	 */
	public boolean hasRestingPair() {
		return body1.isResting() && body2.isResting();
	}

	/**
	 * Perform the collision analysis between the two bodies arbitrated
	 * 
	 * @param dt
	 *            The amount of time passed since last collision check
	 */
	public void collide(float dt) {
		numContacts = Collide.collide(contacts, body1, body2, dt);
	}

	/**
	 * Get one of the two contacts handled being handled by this arbiter
	 * 
	 * @param index
	 *            The index of the contact to retrieve
	 * @return The contact or null if no contact has been detected
	 */
	public Contact getContact(int index) {
		return contacts[index];
	}

	/**
	 * Initialise state for this arbiter
	 * 
	 */
	public void init() {
		if (numContacts > 0) {
			friction = (float) Math.sqrt(body1.getFriction()
					* body2.getFriction());
		}
	}

	/**
	 * Retrieve the contacts being resolved by this arbiter
	 * 
	 * @return The contacts being resolved by this arbiter
	 */
	public Contact[] getContacts() {
		return contacts;
	}

	/**
	 * The number of contacts being resolved by this arbiter
	 * 
	 * @return The number of contacts being resolve by this arbiter
	 */
	public int getNumContacts() {
		return numContacts;
	}

	/**
	 * Get the first of the two bodies handled by this arbiter
	 * 
	 * @return The first of the two bodies handled by this arbiter
	 */
	public Body getBody1() {
		return body1;
	}

	/**
	 * Get the second of the two bodies handled by this arbiter
	 * 
	 * @return The second of the two bodies handled by this arbiter
	 */
	public Body getBody2() {
		return body2;
	}

	/**
	 * Update this arbiter from a second set of data determined as the
	 * simulation continues
	 * 
	 * @param newContacts
	 *            The new contacts that have been found
	 * @param numNewContacts
	 *            The number of new contacts discovered
	 */
	public void update(Contact[] newContacts, int numNewContacts) {
		Contact[] mergedContacts = new Contact[MAX_POINTS];
		for (int i = 0; i < mergedContacts.length; i++) {
			mergedContacts[i] = new Contact();
		}

		for (int i = 0; i < numNewContacts; ++i) {
			Contact cNew = newContacts[i];
			int k = -1;
			for (int j = 0; j < numContacts; ++j) {
				Contact cOld = contacts[j];
				if (cNew.feature.equals(cOld.feature)) {
					k = j;
					break;
				}
			}

			if (k > -1) {
				Contact c = mergedContacts[i];
				Contact cOld = contacts[k];
				c.set(cNew);
				c.accumulatedNormalImpulse = cOld.accumulatedNormalImpulse;
				c.accumulatedTangentImpulse = cOld.accumulatedTangentImpulse;
			} else {
				mergedContacts[i].set(newContacts[i]);
			}
		}

		for (int i = 0; i < numNewContacts; ++i) {
			contacts[i].set(mergedContacts[i]);
		}

		numContacts = numNewContacts;
	}

	/**
	 * Check if this arbiter affects the specified body
	 * 
	 * @param body
	 *            The body to check for
	 * @return True if this arbiter effects the body
	 */
	public boolean concerns(Body body) {
		boolean result = (body1 == body) || (body2 == body);

		return result;
	}

	/**
	 * Apply the friction impulse from each contact.
	 * 
	 * @param dt
	 *            The amount of time to step the simulation by
	 * @param invDT
	 *            The inverted time
	 * @param damping
	 *            The percentage of energy to retain through out collision. (1 =
	 *            no loss, 0 = total loss)
	 */
	void preStep(float invDT, float dt, float damping) {
		float allowedPenetration = 0.01f;
		float biasFactor = 0.8f;

		for (int i = 0; i < numContacts; ++i) {
			Contact c = contacts[i];
			c.normal.normalise();

			Vector r1 = new Vector(c.position);
			r1.sub(body1.getPosition());
			Vector r2 = new Vector(c.position);
			r2.sub(body2.getPosition());

			// Precompute normal mass, tangent mass, and bias.
			float rn1 = r1.dot(c.normal);
			float rn2 = r2.dot(c.normal);
			float kNormal = body1.getInvMass() + body2.getInvMass();
			kNormal += body1.getInvI() * (r1.dot(r1) - rn1 * rn1)
					+ body2.getInvI() * (r2.dot(r2) - rn2 * rn2);
			c.massNormal = damping / kNormal;

			Vector tangent = MathUtil.cross(c.normal, 1.0f);
			float rt1 = r1.dot(tangent);
			float rt2 = r2.dot(tangent);
			float kTangent = body1.getInvMass() + body2.getInvMass();
			kTangent += body1.getInvI() * (r1.dot(r1) - rt1 * rt1)
					+ body2.getInvI() * (r2.dot(r2) - rt2 * rt2);
			c.massTangent = damping / kTangent;

			// Compute restitution
			// Relative velocity at contact
			Vector relativeVelocity = new Vector(body2.getVelocity());
			relativeVelocity
					.add(MathUtil.cross(r2, body2.getAngularVelocity()));
			relativeVelocity.sub(body1.getVelocity());
			relativeVelocity
					.sub(MathUtil.cross(r1, body1.getAngularVelocity()));

			float combinedRestitution = (body1.getRestitution() * body2
					.getRestitution());
			float relVel = c.normal.dot(relativeVelocity);
			c.restitution = combinedRestitution * -relVel;
			c.restitution = Math.max(c.restitution, 0);

			float penVel = -c.separation / dt;
			if (c.restitution >= penVel) {
				c.bias = 0;
			} else {
				c.bias = -biasFactor * invDT
						* Math.min(0.0f, c.separation + allowedPenetration);
			}

			// apply damping
			c.accumulatedNormalImpulse *= damping;

			// Apply normal + friction impulse
			Vector impulse = MathUtil.scale(c.normal,
					c.accumulatedNormalImpulse);
			impulse.add(MathUtil.scale(tangent, c.accumulatedTangentImpulse));

			body1.adjustVelocity(MathUtil.scale(impulse, -body1.getInvMass()));
			body1.adjustAngularVelocity(-body1.getInvI()
					* MathUtil.cross(r1, impulse));

			body2.adjustVelocity(MathUtil.scale(impulse, body2.getInvMass()));
			body2.adjustAngularVelocity(body2.getInvI()
					* MathUtil.cross(r2, impulse));

			// rest bias
			c.biasImpulse = 0;
		}
	}

	/**
	 * Apply the impulse accumlated at the contact points maintained by this
	 * arbiter.
	 */
	void applyImpulse() {
		Body b1 = body1;
		Body b2 = body2;

		for (int i = 0; i < numContacts; ++i) {
			Contact c = contacts[i];

			Vector r1 = new Vector(c.position);
			r1.sub(b1.getPosition());
			Vector r2 = new Vector(c.position);
			r2.sub(b2.getPosition());

			// Relative velocity at contact
			Vector relativeVelocity = new Vector(b2.getVelocity());
			relativeVelocity.add(MathUtil.cross(b2.getAngularVelocity(), r2));
			relativeVelocity.sub(b1.getVelocity());
			relativeVelocity.sub(MathUtil.cross(b1.getAngularVelocity(), r1));

			// Compute normal impulse with bias.
			float vn = relativeVelocity.dot(c.normal);

			// bias caculations are now handled seperately hence we only
			// handle the real impulse caculations here
			// float normalImpulse = c.massNormal * ((c.restitution - vn) +
			// c.bias);
			float normalImpulse = c.massNormal * (c.restitution - vn);

			// Clamp the accumulated impulse
			float oldNormalImpulse = c.accumulatedNormalImpulse;
			c.accumulatedNormalImpulse = Math.max(oldNormalImpulse
					+ normalImpulse, 0.0f);
			normalImpulse = c.accumulatedNormalImpulse - oldNormalImpulse;

			// Apply contact impulse
			Vector impulse = MathUtil.scale(c.normal, normalImpulse);

			b1.adjustVelocity(MathUtil.scale(impulse, -b1.getInvMass()));
			b1.adjustAngularVelocity(-(b1.getInvI() * MathUtil.cross(r1,
					impulse)));

			b2.adjustVelocity(MathUtil.scale(impulse, b2.getInvMass()));
			b2
					.adjustAngularVelocity(b2.getInvI()
							* MathUtil.cross(r2, impulse));

			// Compute bias impulse
			relativeVelocity.set(b2.getBiasedVelocity());
			relativeVelocity.add(MathUtil.cross(b2.getBiasedAngularVelocity(),
					r2));
			relativeVelocity.sub(b1.getBiasedVelocity());
			relativeVelocity.sub(MathUtil.cross(b1.getBiasedAngularVelocity(),
					r1));
			float vnb = relativeVelocity.dot(c.normal);

			float biasImpulse = c.massNormal * (-vnb + c.bias);
			float oldBiasImpulse = c.biasImpulse;
			c.biasImpulse = Math.max(oldBiasImpulse + biasImpulse, 0.0f);
			biasImpulse = c.biasImpulse - oldBiasImpulse;

			Vector Pb = MathUtil.scale(c.normal, biasImpulse);

			b1.adjustBiasedVelocity(MathUtil.scale(Pb, -b1.getInvMass()));
			b1.adjustBiasedAngularVelocity(-(b1.getInvI() * MathUtil.cross(r1,
					Pb)));

			b2.adjustBiasedVelocity(MathUtil.scale(Pb, b2.getInvMass()));
			b2.adjustBiasedAngularVelocity((b2.getInvI() * MathUtil.cross(r2,
					Pb)));

			//
			// Compute friction (tangent) impulse
			//
			float maxTangentImpulse = friction * c.accumulatedNormalImpulse;

			// Relative velocity at contact
			relativeVelocity.set(b2.getVelocity());
			relativeVelocity.add(MathUtil.cross(b2.getAngularVelocity(), r2));
			relativeVelocity.sub(b1.getVelocity());
			relativeVelocity.sub(MathUtil.cross(b1.getAngularVelocity(), r1));

			Vector tangent = MathUtil.cross(c.normal, 1.0f);
			float vt = relativeVelocity.dot(tangent);
			float tangentImpulse = c.massTangent * (-vt);

			// Clamp friction
			float oldTangentImpulse = c.accumulatedTangentImpulse;
			c.accumulatedTangentImpulse = MathUtil.clamp(oldTangentImpulse
					+ tangentImpulse, -maxTangentImpulse, maxTangentImpulse);
			tangentImpulse = c.accumulatedTangentImpulse - oldTangentImpulse;

			// Apply contact impulse
			impulse = MathUtil.scale(tangent, tangentImpulse);

			b1.adjustVelocity(MathUtil.scale(impulse, -b1.getInvMass()));
			b1.adjustAngularVelocity(-b1.getInvI()
					* MathUtil.cross(r1, impulse));

			b2.adjustVelocity(MathUtil.scale(impulse, b2.getInvMass()));
			b2
					.adjustAngularVelocity(b2.getInvI()
							* MathUtil.cross(r2, impulse));
		}
	}

	/**
	 * Get the energy contained within 2 bodies
	 * 
	 * @param body1
	 *            The first body
	 * @param body2
	 *            The second body
	 * @return The energy contained
	 */
	protected float getEnergy(Body body1, Body body2) {
		Vector combinedVel = MathUtil.scale(body1.getVelocity(), body1
				.getMass());
		combinedVel.add(MathUtil.scale(body2.getVelocity(), body2.getMass()));

		float combinedInertia = body1.getI() * body1.getAngularVelocity();
		combinedInertia += body2.getI() * body2.getAngularVelocity();

		float vel1Energy = body1.getMass()
				* body1.getVelocity().dot(body1.getVelocity());
		float vel2Energy = body2.getMass()
				* body2.getVelocity().dot(body2.getVelocity());
		float ang1Energy = body1.getI()
				* (body1.getAngularVelocity() * body1.getAngularVelocity());
		float ang2Energy = body2.getI()
				* (body2.getAngularVelocity() * body2.getAngularVelocity());
		float energy = vel1Energy + vel2Energy + ang1Energy + ang2Energy;

		return energy;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return body1.hashCode() + body2.hashCode();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (other.getClass().equals(getClass())) {
			Arbiter o = (Arbiter) other;

			return (o.body1.equals(body1) && o.body2.equals(body2));
		}

		return false;
	}
}
