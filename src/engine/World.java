package engine;

import java.util.ArrayList;

import engine.collide.Contact;
import engine.collision.CollisionEvent;
import engine.collision.CollisionSpace;
import engine.joint.Joint;
import engine.shapes.Body;
import engine.vector.Vector;

/**
 * The physics model in which the bodies exist.
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 */
public strictfp class World extends CollisionSpace {
	/** The joints contained in the world */
	private JointList joints = new JointList();
	/** The direction and force of gravity */
	private Vector gravity = new Vector(0, 0);
	/** The number of iteration to run each step */
	private int iterations;
	/** The damping in effect in the system */
	private float damping = 1;
	/** True if resting body detection is turned on */
	private boolean restingBodyDetection = false;
	/**
	 * The velocity a body hitting a resting body has to have to consider moving
	 * it
	 */
	private float hitTolerance;
	/** The amount a body has to rotate for it to be considered non-resting */
	private float rotationTolerance;
	/** The amoutn a body has to move for it to be considered non-resting */
	private float positionTolerance;
	private static World world;

	public static World createWorld(Vector gravity, int iterations,
			Strategy strategy) {
		world = new World(gravity, iterations, strategy);
		return world;
	}

	/**
	 * Create a new physics model World
	 * 
	 * @param gravity
	 *            The direction and force of gravity
	 * @param iterations
	 *            The number of iterations to run each step. More iteration is
	 *            more accurate but slower
	 * @param strategy
	 *            The strategy used to determine which bodies to check detailed
	 *            collision on
	 */
	public World(Vector gravity, int iterations, Strategy strategy) {
		super(strategy);

		this.gravity = gravity;
		this.iterations = iterations;

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
	public void enableRestingBodyDetection(float hitTolerance,
			float rotationTolerance, float positionTolerance) {
		this.hitTolerance = hitTolerance;
		this.rotationTolerance = rotationTolerance;
		this.positionTolerance = positionTolerance;
		restingBodyDetection = true;
	}

	/**
	 * Disable resting body detection on the world
	 */
	public void disableRestingBodyDetection() {
		restingBodyDetection = false;
	}

	/**
	 * Reset all dynamic bodies to indicate they are no longer resting. Useful
	 * when manually changing the state of the world and then expecting normal
	 * results
	 */
	public void clearRestingState() {
		for (int i = 0; i < bodies.size(); i++) {
			bodies.get(i).setIsResting(false);
		}
	}

	/**
	 * Set the amount of energy retained during collisions across the system.
	 * This might be used to simulate sound, heat type losses
	 * 
	 * @param damping
	 *            The amount of energy retain across the system (1 = no loss, 0
	 *            = total loss)
	 */
	public void setDamping(float damping) {
		this.damping = damping;
	}

	/**
	 * Set the gravity applied in the world
	 * 
	 * @param x
	 *            The x component of the gravity factor
	 * @param y
	 *            The y component of the gravity factor
	 */
	public void setGravity(float x, float y) {
		gravity.x = x;
		gravity.y = y;
	}

	/**
	 * Retrieve a immutable list of joints in the simulation
	 * 
	 * @return The list of joints
	 */
	public JointList getJoints() {
		return joints;
	}

	/**
	 * Retrieve a immutable list of arbiters in the simulation
	 * 
	 * @return The list of arbiters
	 */
	public ArbiterList getArbiters() {
		return arbiters;
	}

	/**
	 * Clear any arbiters in place for the given body
	 * 
	 * @param b
	 *            The body whose arbiters should be removed
	 */
	public void clearArbiters(Body b) {
		for (int i = 0; i < arbiters.size(); i++) {
			if (arbiters.get(i).concerns(b)) {
				arbiters.remove(arbiters.get(i));
				i--;
			}
		}
	}

	/**
	 * Add a joint to the simulation
	 * 
	 * @param joint
	 *            The joint to be added
	 */
	public void add(Joint joint) {
		joints.add(joint);
	}

	/**
	 * Remove a joint from the simulation
	 * 
	 * @param joint
	 *            The joint to be removed
	 */
	public void remove(Joint joint) {
		joints.remove(joint);
	}

	/**
	 * Remove all the elements from this world
	 */
	public void clear() {
		super.clear();

		joints.clear();
	}

	/**
	 * Step the simulation. Currently anything other than 1/60f as a step leads
	 * to unpredictable results - hence the default step fixes us to this step
	 */
	public void step() {
		step(1 / 60.0f);
	}

	/**
	 * Get the list of bodies that should be considered active at this time.
	 * Sub-classes can override to incur spatial culling
	 * 
	 * @return The list of bodies that should be considered active at this point
	 *         in time
	 */
	protected BodyList getActiveBodies() {
		return bodies;
	}

	/**
	 * Get the list of joints that should be considered active at this time.
	 * Sub-classes can override to incur spatial culling
	 * 
	 * @return The list of joints that should be considered active at this point
	 *         in time
	 */
	protected JointList getActiveJoints() {
		return joints;
	}

	/**
	 * Step the simulation. Currently anything other than 1/60f as a step leads
	 * to unpredictable results - hence the default step fixes us to this step
	 * 
	 * @param dt
	 *            The amount of time to step
	 */
	public void step(float dt) {
		BodyList bodies = getActiveBodies();
		JointList joints = getActiveJoints();

		float invDT = dt > 0.0f ? 1.0f / dt : 0.0f;

		if (restingBodyDetection) {
			for (int i = 0; i < bodies.size(); ++i) {
				Body b = bodies.get(i);
				b.startFrame();
			}
			for (int i = 0; i < joints.size(); ++i) {
				Joint j = joints.get(i);
				j.getBody1().setIsResting(false);
				j.getBody2().setIsResting(false);
			}
		}

		broadPhase(dt);

		for (int i = 0; i < bodies.size(); ++i) {
			Body b = bodies.get(i);

			if (b.getInvMass() == 0.0f) {
				continue;
			}
			if (b.isResting() && restingBodyDetection) {
				continue;
			}

			Vector temp = new Vector(b.getForce());
			temp.scale(b.getInvMass());
			if (b.getGravityEffected()) {
				temp.add(gravity);
			}
			temp.scale(dt);

			b.adjustVelocity(temp);

			Vector damping = new Vector(b.getVelocity());
			damping.scale(-b.getDamping() * b.getInvMass());
			b.adjustVelocity(damping);

			b.adjustAngularVelocity(dt * b.getInvI() * b.getTorque());
			b.adjustAngularVelocity(-b.getAngularVelocity() * b.getInvI()
					* b.getRotDamping());
		}

		for (int i = 0; i < arbiters.size(); i++) {
			Arbiter arb = arbiters.get(i);
			if (!restingBodyDetection || !arb.hasRestingPair()) {
				arb.preStep(invDT, dt, damping);
			}
		}

		for (int i = 0; i < joints.size(); ++i) {
			Joint j = joints.get(i);
			j.preStep(invDT);
		}

		for (int i = 0; i < iterations; ++i) {
			for (int k = 0; k < arbiters.size(); k++) {
				Arbiter arb = arbiters.get(k);
				if (!restingBodyDetection || !arb.hasRestingPair()) {
					arb.applyImpulse();
				} else {
					arb.getBody1().collided(arb.getBody2());
					arb.getBody2().collided(arb.getBody1());
				}
			}

			for (int k = 0; k < joints.size(); ++k) {
				Joint j = joints.get(k);
				j.applyImpulse();
			}
		}

		for (int i = 0; i < bodies.size(); ++i) {
			Body b = bodies.get(i);

			if (b.getInvMass() == 0.0f) {
				continue;
			}
			if (restingBodyDetection) {
				if (b.isResting()) {
					continue;
				}
			}

			b.adjustPosition(b.getVelocity(), dt);
			b.adjustPosition(b.getBiasedVelocity(), dt);

			b.adjustRotation(dt * b.getAngularVelocity());
			b.adjustRotation(dt * b.getBiasedAngularVelocity());

			b.resetBias();
			b.setForce(0, 0);
			b.setTorque(0);
		}

		if (restingBodyDetection) {
			for (int i = 0; i < bodies.size(); ++i) {
				Body b = bodies.get(i);
				b.endFrame();
			}
		}

		cleanUpArbiters();
	}

	/**
	 * Clean up the arbiters for departied bodies
	 */
	private void cleanUpArbiters() {
		for (int i = 0; i < arbiters.size(); i++) {
			Arbiter arbiter = arbiters.get(i);

			if (!arbiter.getBody1().added() || !arbiter.getBody2().added()) {
				arbiters.remove(arbiter);
				i--;
			}
		}
	}

	/**
	 * The broad collision phase
	 * 
	 * @param dt
	 *            The amount of time passed since last collision phase
	 */
	void broadPhase(float dt) {
		collide(dt);
	}

	/**
	 * Get a list of collisions at the current time for a given body
	 * 
	 * @param body
	 *            The body to check for
	 * @return The list of collision events describing the current contacts
	 */
	public CollisionEvent[] getContacts(Body body) {
		ArrayList<CollisionEvent> collisions = new ArrayList<CollisionEvent>();

		for (int i = 0; i < arbiters.size(); i++) {
			Arbiter arb = arbiters.get(i);

			if (arb.concerns(body)) {
				for (int j = 0; j < arb.getNumContacts(); j++) {
					Contact contact = arb.getContact(j);
					CollisionEvent event = new CollisionEvent(0,
							arb.getBody1(), arb.getBody2(), contact
									.getPosition(), contact.getNormal(),
							contact.getSeparation());

					collisions.add(event);
				}
			}
		}

		return collisions.toArray(new CollisionEvent[0]);
	}

	/**
	 * Get the total energy in the system
	 * 
	 * @return The total energy in all the bodies in the system
	 */
	public float getTotalEnergy() {
		float total = 0;

		for (int i = 0; i < bodies.size(); i++) {
			total += bodies.get(i).getEnergy();
		}

		return total;
	}

	/**
	 * @see engine.collision.CollisionSpace#add(engine.shapes.Body)
	 */
	public void add(Body body) {
		body.configureRestingBodyDetection(hitTolerance, rotationTolerance,
				positionTolerance);
		super.add(body);
	}

	/**
	 * Remove a body from the simulation
	 * 
	 * @param body
	 *            The body to be removed
	 */
	public void remove(Body body) {
		clearArbiters(body);
		super.remove(body);
	}
}
