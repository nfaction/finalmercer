package engine.shapes;

import java.util.ArrayList;

import engine.BodyList;
import engine.vector.Vector;

/**
 * A body in the physics model
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 */
public strictfp class Body {
	/** The next ID to be assigned */
	private static int NEXT_ID = 0;
	/** The maximum value indicating that body won't move */
	public static final float INFINITE_MASS = Float.MAX_VALUE;
	/** The current position of this body */
	private Vector position = new Vector();
	/** The position of this body in the last frame */
	private Vector lastPosition = new Vector();
	/** The current rotation of this body in radians */
	private float rotation;
	/** The velocity of this body */
	private Vector velocity = new Vector();
	/** The angular velocity of this body */
	private float angularVelocity;
	/** The last velocity of this body (before last update) */
	private Vector lastVelocity = new Vector();
	/** The last angular velocity of this body (before last update) */
	private float lastAngularVelocity;
	/** The velocity of this body */
	private Vector biasedVelocity = new Vector();
	/** The angular velocity of this body */
	private float biasedAngularVelocity;
	/** The force being applied to this body - i.e. driving velocity */
	private Vector force = new Vector();
	/** The angular force being applied this body */
	private float torque;
	/** The shape representing this body */
	private Shape shape;
	/** The friction on the surface of this body */
	private float surfaceFriction;
	/** The damping caused by friction of the 'air' on this body. */
	private float damping;
	/** The rotational damping. */
	private float rotDamping;
	/** The mass of this body */
	private float mass;
	/** The inverse mass of this body */
	private float invMass;
	/** The density of this body */
	private float I;
	/** The inverse of this density */
	private float invI;
	/** The name assigned to this body */
	private String name;
	/** The id assigned ot this body */
	private int id;
	/** The restitution of this body */
	private float restitution = 0f;
	/** The list of bodies excluded from colliding with this body */
	private BodyList excluded = new BodyList();
	/** True if this body is effected by gravity */
	private boolean gravity = true;
	/** A hook for the library's user's data */
	private Object userData = null;
	/** The old position */
	private Vector oldPosition;
	/** The new position */
	private Vector newPosition;
	/** True if we've been hit by another this frame */
	private boolean hitByAnother;
	/** True if we're considered static at the moment */
	private boolean isResting;
	/** The original mass of this object */
	private float originalMass;
	/** The number of hits this frame */
	private int hitCount = 0;
	/** True if resting body detection is turned on */
	private boolean restingBodyDetection = false;
	/** The velocity a body hitting a resting body has to consider moving it */
	private float hitTolerance;
	/** The amount a body has to rotate for it to be considered non-resting */
	private float rotationTolerance;
	/** The amoutn a body has to move for it to be considered non-resting */
	private float positionTolerance;
	/** The list of bodies this body touches */
	private BodyList touching = new BodyList();
	/** True if this body is touching a static */
	private boolean touchingStatic = false;
	/** Number of bodies we're touching */
	private int touchingCount;
	/** True if this body is capable of coming to a resting state */
	private boolean canRest = true;
	/** True if this body can rotate */
	private boolean rotatable = true;
	/** True if this body can move */
	private boolean moveable = true;
	/** True if this body is enabled */
	private boolean enabled = true;
	/** True if this body has been added to the simulation */
	private boolean added = false;
	/** The maximum velocity the the body can travel at on each axis */
	private Vector maxVelocity;

	/**
	 * Check if this body is disabled
	 * 
	 * @return True if this body is disabled
	 */
	public boolean disabled() {
		return !enabled;
	}

	public int getTouchingCount() {
		return this.touchingCount;
	}

	/**
	 * Indicates whether this body should be active, collide, move/rotate
	 * 
	 * @param enabled
	 *            True if this body should be active
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Create a new un-named body
	 * 
	 * @param shape
	 *            The shape describing this body
	 * @param m
	 *            The mass of the body
	 */
	public Body(Shape shape, float m) {
		this("UnnamedBody", shape, m);
	}

	/**
	 * Create a named body
	 * 
	 * @param name
	 *            The name to assign to the body
	 * @param shape
	 *            The shape describing this body
	 * @param m
	 *            The mass of the body
	 */
	public Body(String name, Shape shape, float m) {
		this.name = name;

		id = NEXT_ID++;
		position.set(0.0f, 0.0f);
		lastPosition.set(0.0f, 0.0f);
		rotation = 0.0f;
		velocity.set(0.0f, 0.0f);
		angularVelocity = 0.0f;
		force.set(0.0f, 0.0f);
		torque = 0.0f;
		surfaceFriction = 0.2f;

		mass = INFINITE_MASS;
		invMass = 0.0f;
		I = INFINITE_MASS;
		invI = 0.0f;

		originalMass = m;
		set(shape, m);
	}

	/**
	 * Get the ID of this body
	 * 
	 * @return The unique ID of this body
	 */
	public int getID() {
		return id;
	}

	/**
	 * Attach an object to this Body.
	 * 
	 * @param o
	 *            The (new) userdata.
	 */
	public void setUserData(Object o) {
		userData = o;
	}

	/**
	 * Retrieved the attached object which initially will be null.
	 * 
	 * @return The attached userdata.
	 */
	public Object getUserData() {
		return userData;
	}

	/**
	 * Check if this body can rotate.
	 * 
	 * @return True if this body can rotate
	 */
	public boolean isRotatable() {
		if (disabled()) {
			return false;
		}

		return rotatable;
	}

	/**
	 * Check if this body can move
	 * 
	 * @return True if this body can move
	 */
	public boolean isMoveable() {
		if (disabled()) {
			return false;
		}

		return moveable;
	}

	/**
	 * Indicate whether this body should be able to rotate.
	 * 
	 * The default value is true
	 * 
	 * @param rotatable
	 *            True if this body is rotatable
	 */
	public void setRotatable(boolean rotatable) {
		this.rotatable = rotatable;
	}

	/**
	 * Indicate whether this body should be able to move. The default value is
	 * true
	 * 
	 * @param moveable
	 *            True if this body is rotatable
	 */
	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

	/**
	 * Enable resting body detection.
	 * 
	 * @param hitTolerance
	 *            The velocity a body hitting a resting body has to have to
	 *            consider moving it
	 * @param rotationTolerance
	 *            The amount a body has to rotate for it to be considered
	 *            non-resting
	 * @param positionTolerance
	 *            The amoutn a body has to move for it to be considered
	 *            non-resting
	 */
	public void configureRestingBodyDetection(float hitTolerance,
			float rotationTolerance, float positionTolerance) {
		this.hitTolerance = hitTolerance;
		this.rotationTolerance = rotationTolerance;
		this.positionTolerance = positionTolerance;
		restingBodyDetection = true;
	}

	/**
	 * Indicate whether this body can come to a resting state. The default is
	 * true.
	 * 
	 * @param canRest
	 *            True if the body can come to a resting state
	 */
	public void setCanRest(boolean canRest) {
		this.canRest = canRest;
	}

	/**
	 * Check if this body can come to a resting state
	 * 
	 * @return True if the body can come to a resting state
	 */
	public boolean canRest() {
		return canRest;
	}

	/**
	 * Notification that we've started an update frame/iteration
	 */
	public void startFrame() {
		if (!canRest()) {
			return;
		}

		oldPosition = new Vector(getPosition());
		hitByAnother = false;
		hitCount = 0;
		touching.clear();
	}

	/**
	 * Notification that this body collided with another
	 * 
	 * @param other
	 *            The other body that this body collided with
	 */
	public void collided(Body other) {
		if (!restingBodyDetection) {
			return;
		}

		if (!touching.contains(other)) {
			touching.add(other);
		}

		if (isResting()) {
			if ((!other.isResting())) {
				if (other.getVelocity().lengthSquared() > hitTolerance) {
					hitByAnother = true;
					setMass(originalMass);
				}
			}
		}
		hitCount++;
	}

	/**
	 * Notification that we've ended an update frame/iteration
	 */
	public void endFrame() {
		if (!canRest()) {
			return;
		}

		if ((hitCount == 0) || (touchingCount != touching.size())) {
			isResting = false;
			setMass(originalMass);
			touchingStatic = false;
			touchingCount = touching.size();
		} else {
			newPosition = new Vector(getPosition());
			if (!hitByAnother) {
				if (true
						&& (newPosition.distanceSquared(oldPosition) <= positionTolerance)
						&& (velocity.lengthSquared() <= 0.001f)
						&& (biasedVelocity.lengthSquared() <= 0.001f)
						&& (Math.abs(angularVelocity) <= rotationTolerance)) {
					if (!touchingStatic) {
						touchingStatic = isTouchingStatic(new ArrayList<Body>());
					}
					if (touchingStatic) {
						isResting = true;
						setMass(INFINITE_MASS);
						velocity.set(0.0f, 0.0f);
						biasedVelocity.set(0, 0);
						angularVelocity = 0.0f;
						biasedAngularVelocity = 0;
						force.set(0.0f, 0.0f);
						torque = 0.0f;
					}
				}
			} else {
				isResting = false;
				setMass(originalMass);
			}

			if ((newPosition.distanceSquared(oldPosition) > positionTolerance)
					&& (Math.abs(angularVelocity) > rotationTolerance)) {
				touchingStatic = false;
			}
		}

	}

	/**
	 * Check if this body is touching a static body directly or indirectly
	 * 
	 * @param path
	 *            The path of bodies we've used to get here
	 * @return True if we're touching a static body
	 */
	public boolean isTouchingStatic(ArrayList<Body> path) {
		boolean result = false;

		path.add(this);
		for (int i = 0; i < touching.size(); i++) {
			Body body = touching.get(i);
			if (path.contains(body)) {
				continue;
			}
			if (body.isStatic()) {
				result = true;
				break;
			}

			if (body.isTouchingStatic(path)) {
				result = true;
				break;
			}
		}

		return result;
	}

	/**
	 * Get the list of bodies that this body is touching. Only works if resting
	 * body detection is turned on
	 * 
	 * @return The list of bodies this body touches
	 */
	public BodyList getTouching() {
		return new BodyList(touching);
	}

	/**
	 * Get the list of bodies that this body is connected to. Only works if
	 * resting body detection is turned on
	 * 
	 * @return The list of bodies this body touches
	 */
	public BodyList getConnected() {
		return getConnected(false);
	}

	/**
	 * Get the list of bodies that this body is connected to. Only works if
	 * resting body detection is turned on
	 * 
	 * @param stopAtStatic
	 *            True if we should stop traversing and looking for elements one
	 *            you find a static one
	 * @return The list of bodies this body touches
	 */
	public BodyList getConnected(boolean stopAtStatic) {
		BodyList connected = new BodyList();
		getConnected(connected, new ArrayList<Body>(), stopAtStatic);

		return connected;
	}

	/**
	 * Get the bodies connected to this one
	 * 
	 * @param stopAtStatic
	 *            True if we should stop traversing and looking for elements one
	 *            you find a static one
	 * @param list
	 *            The list we're building up
	 * @param path
	 *            The list of elements we passed to get here
	 */
	private void getConnected(BodyList list, ArrayList<Body> path,
			boolean stopAtStatic) {
		path.add(this);
		for (int i = 0; i < touching.size(); i++) {
			Body body = touching.get(i);
			if (path.contains(body)) {
				continue;
			}
			if (body.isStatic() && stopAtStatic) {
				continue;
			}

			list.add(body);
			body.getConnected(list, path, stopAtStatic);
		}
	}

	/**
	 * Check if this body is static
	 * 
	 * @return True if this body is static
	 */
	public boolean isStatic() {
		return false;
	}

	/**
	 * Check if this body has been detected as resting and hence is considered
	 * static.
	 * 
	 * @return True if the body is considered static since it's resting
	 */
	public boolean isResting() {
		return isResting;
	}

	/**
	 * Force this body into resting or not resting mode.
	 * 
	 * @param isResting
	 *            True if this body should be seen as resting
	 */
	public void setIsResting(boolean isResting) {
		if (this.isResting && !isResting) {
			setMass(originalMass);
		}
		this.touchingStatic = false;
		this.isResting = isResting;
	}

	/**
	 * Indicate whether this body should be effected by gravity
	 * 
	 * @param gravity
	 *            True if this body should be effected by gravity
	 */
	public void setGravityEffected(boolean gravity) {
		this.gravity = gravity;
	}

	/**
	 * Check if this body is effected by gravity
	 * 
	 * @return True if this body is effected by gravity
	 */
	public boolean getGravityEffected() {
		return (gravity) || (I == INFINITE_MASS);
	}

	/**
	 * Add a body that this body is not allowed to collide with, i.e. the body
	 * specified will collide with this body
	 * 
	 * @param other
	 *            The body to exclude from collisions with this body
	 */
	public void addExcludedBody(Body other) {
		if (other.equals(this)) {
			return;
		}
		if (!excluded.contains(other)) {
			excluded.add(other);
			other.addExcludedBody(this);
		}
	}

	/**
	 * Remove a body from the excluded list of this body. i.e. the body
	 * specified will be allowed to collide with this body again
	 * 
	 * @param other
	 *            The body to remove from the exclusion list
	 */
	public void removeExcludedBody(Body other) {
		if (other.equals(this)) {
			return;
		}
		if (excluded.contains(other)) {
			excluded.remove(other);
			other.removeExcludedBody(this);
		}
	}

	/**
	 * Get the list of bodies that can not collide with this body
	 * 
	 * @return The list of bodies that can not collide with this body
	 */
	public BodyList getExcludedList() {
		return excluded;
	}

	/**
	 * Get the mass of this body
	 * 
	 * @return The mass of this body
	 */
	public float getMass() {
		return mass;
	}

	/**
	 * Get the inertia of this body
	 * 
	 * @return The inertia of this body
	 */
	public float getI() {
		return I;
	}

	/**
	 * Set the "restitution" of the body. Hard bodies transfer momentum well. A
	 * value of 1.0 would be a pool ball. The default is 1f
	 * 
	 * @param rest
	 *            The restitution of this body
	 */
	public void setRestitution(float rest) {
		this.restitution = rest;
	}

	/**
	 * Get the "restitution" of the body. Hard bodies transfer momentum well. A
	 * value of 1.0 would be a pool ball. The default is 0f
	 * 
	 * @return The "restitution" of the body
	 */
	public float getRestitution() {
		return restitution;
	}

	/**
	 * Reconfigure the body
	 * 
	 * @param shape
	 *            The shape describing this body
	 * @param m
	 *            The mass of the body
	 */
	public void set(Shape shape, float m) {
		position.set(0.0f, 0.0f);
		lastPosition.set(0.0f, 0.0f);
		rotation = 0.0f;
		velocity.set(0.0f, 0.0f);
		angularVelocity = 0.0f;
		force.set(0.0f, 0.0f);
		torque = 0.0f;
		surfaceFriction = 0.2f;

		this.shape = shape;
		setMass(m);
	}

	/**
	 * Reset the shape of this body
	 * 
	 * @param shape
	 *            The new shape of this body
	 */
	public void setShape(Shape shape) {
		this.shape = shape;
		// setMass(mass);
	}

	/**
	 * Set the mass of the body
	 * 
	 * @param m
	 *            The new mass of the body
	 */
	private void setMass(float m) {
		mass = m;

		if (mass < INFINITE_MASS) {
			invMass = 1.0f / mass;
			// I = mass * (size.x * size.x + size.y * size.y) / 12.0f;

			I = (mass * shape.getSurfaceFactor()) / 12.0f;
			invI = 1.0f / I;
		} else {
			invMass = 0.0f;
			I = INFINITE_MASS;
			invI = 0.0f;
		}
	}

	/**
	 * Set the friction on the surface of this body
	 * 
	 * @param friction
	 *            The friction on the surface of this body
	 */
	public void setFriction(float friction) {
		this.surfaceFriction = friction;
	}

	/**
	 * Set the rotation in radians of this body
	 * 
	 * @param rotation
	 *            The new rotation of the body
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	/**
	 * Set the friction of the 'air' on this body that slows down the object.
	 * The friction force F will be F = -v * damping / m
	 * 
	 * @param damping
	 *            The friction damping factor which represents the body's
	 *            airodynamic properties.
	 */
	public void setDamping(float damping) {
		this.damping = damping;
	}

	/**
	 * Get the friction of the 'air' on this body that slows down the object.
	 * 
	 * @return The friction damping factor which represents the body's
	 *         airodynamic properties.
	 */
	public float getDamping() {
		return this.damping;
	}

	/**
	 * Set the rotational damping, similar to normal damping. The torque F for
	 * this damping would be: F = -av * damping / m where av is the angular
	 * velocity.
	 * 
	 * @see #setDamping(float)
	 * 
	 * @param damping
	 *            The rotational damping which represents the body's airodynamic
	 *            properties when rotating.
	 */
	public void setRotDamping(float damping) {
		this.rotDamping = damping;
	}

	/**
	 * Get the rotational damping, similar to normal damping.
	 * 
	 * @return damping The rotational damping which represents the body's
	 *         airodynamic properties when rotating.
	 */
	public float getRotDamping() {
		return this.rotDamping;
	}

	/**
	 * Get the shape representing this body
	 * 
	 * @return The shape representing this body
	 */
	public Shape getShape() {
		return shape;
	}

	/**
	 * Set the position of this body, this will also set the previous position
	 * to the same value.
	 * 
	 * @param x
	 *            The x position of this body
	 * @param y
	 *            The y position of this body
	 */
	public void setPosition(float x, float y) {
		position.set(x, y);
		lastPosition.set(x, y);
	}

	/**
	 * Set the position of this body after it has moved this means the last
	 * position will contain the position before this function was called.
	 * 
	 * @param x
	 *            The x position of this body
	 * @param y
	 *            The y position of this body
	 */
	public void move(float x, float y) {
		lastPosition.set(position);
		position.set(x, y);
	}

	/**
	 * Get the position of this body
	 * 
	 * @return The position of this body
	 */
	public Vector getPosition() {
		return position;
	}

	/**
	 * Get the position of this body before it was moved
	 * 
	 * @return The last position of this body
	 */
	public Vector getLastPosition() {
		return lastPosition;
	}

	/**
	 * Get the change in position in the last update
	 * 
	 * @return The change in position in the last update
	 */
	public Vector getPositionDelta() {
		Vector vec = new Vector(getPosition());
		vec.sub(getLastPosition());

		return vec;
	}

	/**
	 * Get the velocity before the last update.
	 * 
	 * @return The last velocity
	 */
	public Vector getLastVelocity() {
		return lastVelocity;
	}

	/**
	 * Get the change in velocity in the last update
	 * 
	 * @return The change in velocity in the last update
	 */
	public Vector getVelocityDelta() {
		Vector vec = new Vector(getVelocity());
		vec.sub(getLastVelocity());

		return vec;
	}

	/**
	 * Get the angular velocity before the last update. This is useful during
	 * collisions to determine the change in angular velocity on impact
	 * 
	 * @return The last velocity
	 */
	public float getLastAngularVelocity() {
		return lastAngularVelocity;
	}

	/**
	 * Get the change in angular velocity in the last update
	 * 
	 * @return The change in angular velocity in the last update
	 */
	public float getAngularVelocityDelta() {
		return getAngularVelocity() - getLastAngularVelocity();
	}

	/**
	 * Get the rotation in radians of this body
	 * 
	 * @return The rotation of this body
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * Get the inverse density of this body
	 * 
	 * @return The inverse denisity of this body
	 */
	public float getInvI() {
		return invI;
	}

	/**
	 * Adjust the position of this body.
	 * 
	 * @param delta
	 *            The amount to change the position by
	 * @param scale
	 *            The amount to scale the delta by
	 */
	public void adjustPosition(Vector delta, float scale) {
		lastPosition.set(position);
		position.x += delta.getX() * scale;
		position.y += delta.getY() * scale;
	}

	/**
	 * Adjust the position of this body .
	 * 
	 * @param delta
	 *            The amount to change the position by
	 */
	public void adjustPosition(Vector delta) {
		lastPosition.set(position);
		position.add(delta);
	}

	/**
	 * Adjust the rotation of this body
	 * 
	 * @param delta
	 *            The amount to change the rotation by
	 */
	public void adjustRotation(float delta) {
		rotation += delta;
	}

	/**
	 * Set the force being applied to this body
	 * 
	 * @param x
	 *            The x component of the force
	 * @param y
	 *            The y component of the force
	 */
	public void setForce(float x, float y) {
		force.set(x, y);
	}

	/**
	 * Set the torque being applied to this body
	 * 
	 * @param t
	 *            The torque being applied to this body
	 */
	public void setTorque(float t) {
		torque = t;
	}

	/**
	 * Get the velocity of this body
	 * 
	 * @return The velocity of this body
	 */
	public Vector getVelocity() {
		return velocity;
	}

	/**
	 * Get the angular velocity of this body
	 * 
	 * @return The angular velocity of this body
	 */
	public float getAngularVelocity() {
		return angularVelocity;
	}

	/**
	 * Adjust the velocity of this body
	 * 
	 * @param delta
	 *            The amount to change the velocity by
	 */
	public void adjustVelocity(Vector delta) {
		if (!isMoveable()) {
			return;
		}
		lastVelocity.set(velocity);
		velocity.add(delta);

		validateVelocity();
	}

	/**
	 * Adjust the angular velocity of this body
	 * 
	 * @param delta
	 *            The amount to change the velocity by
	 */
	public void adjustAngularVelocity(float delta) {
		if (!isRotatable()) {
			return;
		}
		lastAngularVelocity = angularVelocity;
		angularVelocity += delta;
	}

	/**
	 * Get the friction on the surface of this body
	 * 
	 * @return The friction of this surface of this body
	 */
	public float getFriction() {
		return surfaceFriction;
	}

	/**
	 * Get the force applied to this body
	 * 
	 * @return The force applied to this body
	 */
	public Vector getForce() {
		return force;
	}

	/**
	 * Get the torque applied to this body
	 * 
	 * @return The torque applied to this body
	 */
	public float getTorque() {
		return torque;
	}

	/**
	 * Add a force to this body
	 * 
	 * @param f
	 *            The force to be applied
	 */
	public void addForce(Vector f) {
		force.add(f);
	}

	/**
	 * Get the inverse mass of this body
	 * 
	 * @return The inverse mass of this body
	 */
	public float getInvMass() {
		return invMass;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[Body '" + name + "' id: " + id + " pos: " + position
				+ " vel: " + velocity + " (" + angularVelocity + ")]";
	}

	public String getName() {
		return name;
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
			return ((Body) other).id == id;
		}

		return false;
	}

	/**
	 * Get the bias velocity of this body
	 * 
	 * @return The bias velocity of this body
	 */
	public Vector getBiasedVelocity() {
		return biasedVelocity;
	}

	/**
	 * Get the bias angular velocity of this body
	 * 
	 * @return The bias angular velocity of this body
	 */
	public float getBiasedAngularVelocity() {
		return biasedAngularVelocity;
	}

	/**
	 * Adjust the bias velocity of this body
	 * 
	 * @param delta
	 *            The amount to change the velocity by
	 */
	public void adjustBiasedVelocity(Vector delta) {
		if (!isMoveable()) {
			return;
		}
		biasedVelocity.add(delta);
	}

	/**
	 * Adjust the bias angular velocity of this body
	 * 
	 * @param delta
	 *            The amount to change the velocity by
	 */
	public void adjustBiasedAngularVelocity(float delta) {
		if (!isRotatable()) {
			return;
		}
		biasedAngularVelocity += delta;
	}

	/**
	 * Reset the bias velocity (done every time step)
	 */
	public void resetBias() {
		biasedVelocity.set(0, 0);
		biasedAngularVelocity = 0;
	}

	/**
	 * Get the energy contained in this body
	 * 
	 * @return The energy contained in this body
	 */
	public float getEnergy() {
		float velEnergy = getMass() * getVelocity().dot(getVelocity());
		float angEnergy = getI()
				* (getAngularVelocity() * getAngularVelocity());

		return velEnergy + angEnergy;
	}

	/**
	 * Check the added to simulation flag
	 * 
	 * @return The added to simulation flag
	 */
	public boolean added() {
		return added;
	}

	/**
	 * Set the added to simulation flag
	 * 
	 * @param added
	 *            The added to simulation flag
	 */
	public void setAdded(boolean added) {
		this.added = added;
	}

	/**
	 * Set the maximum velocity this body can reach. Note that these values
	 * arn't signed since they're magnitudes.
	 * 
	 * @param maxX
	 *            The maximum velocity on the X axis
	 * @param maxY
	 *            The maximum veloicty on the Y axis
	 */
	public void setMaxVelocity(float maxX, float maxY) {
		maxVelocity = new Vector(maxX, maxY);
	}

	/**
	 * Validate the velocity value thats just been applied. Correct it if it
	 * breaks any rules. The primary rule is maximum velocity setting
	 */
	protected void validateVelocity() {
		if (maxVelocity == null) {
			return;
		}

		if (Math.abs(velocity.x) > maxVelocity.x) {
			if (velocity.x > 0) {
				velocity.x = maxVelocity.x;
			} else {
				velocity.x = -maxVelocity.x;
			}
		}

		if (Math.abs(velocity.y) > maxVelocity.y) {
			if (velocity.y > 0) {
				velocity.y = maxVelocity.y;
			} else {
				velocity.y = -maxVelocity.y;
			}
		}
	}
}
