package engine.test;

import engine.World;
import engine.joint.RopeJoint;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.Circle;
import engine.vector.MathUtil;
import engine.vector.Vector2D;
import engine.vector.Vector;

/**
 * A test to demonstrate the distance constraint
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class JointTest extends AbstractDemo {
	/**
	 * Create a new test
	 */
	public JointTest() {
		super("Joint test");
	}

	/**
	 * Chaing a pair of bodies together
	 * 
	 * @param body1 The first body
	 * @param body2 The second body
	 * @param anchor1 The anchor on the first body
	 * @param anchor2 The anchor on the second body
	 * @param chains The number of chains between 
	 * @param world The world in which the chain should be created
	 * @param initRot The initial rotation
	 */
	private void chain(Body body1, Body body2, Vector anchor1,
			Vector anchor2, int chains, World world, float initRot) {

		final int N = chains;

		final Vector p1 = new Vector(body1.getPosition());
		p1.add(MathUtil.mul(new Vector2D(body1.getRotation()), anchor1));

		Vector p2 = null;
		if (body2 != null) {
			p2 = new Vector(body2.getPosition());
			p2.add(MathUtil.mul(new Vector2D(body2.getRotation()), anchor2));
		} else {
			p2 = new Vector(p1);
			p2.add(new Vector(chains * 20, 0));
		}

		final Vector direction = new Vector(p2);
		direction.sub(p1);
		direction.normalise();
		direction.scale(20);

		final Body bodies[] = new Body[N];
		final Vector pos = new Vector(p1);
		for (int i = 0; i < N; i++) {
			final Body body = new Body(new Box(15, 4), 5);
			body.setDamping(0.05f);
			body.setRotation(initRot);
			for (int j = 0; j < i; j++) {
				body.addExcludedBody(bodies[j]);
			}
			pos.add(direction);
			body.setPosition(pos.x,pos.y);
			bodies[i] = body;
			world.add(body);
		}

		for (int i = 1; i < N; i++) {
			final RopeJoint dj = new RopeJoint(bodies[i - 1],
					bodies[i], new Vector(7, 0), new Vector(-7, 0), 6);
			world.add(dj);
		}
		{
			final RopeJoint dj = new RopeJoint(body1, bodies[0],
					anchor1, new Vector(-7, 0), 10);
			world.add(dj);
		}
		if (body2 != null) {
			final RopeJoint dj = new RopeJoint(body2, bodies[N - 1],
					anchor2, new Vector(7, 0), 10);
			world.add(dj);
		}
	}

	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {
		// world.setGravity(0, 0);
		final Body b1 = new Body(new Circle(5), Body.INFINITE_MASS);
		b1.setPosition(100, 50);
		world.add(b1);

		final Body b2 = new Body(new Circle(5), Body.INFINITE_MASS);
		b2.setPosition(300, 250);
		world.add(b2);

		final Body b3 = new Body(new Circle(5), Body.INFINITE_MASS);
		b3.setPosition(450, 400);
		world.add(b3);

		final Body b4 = new Body(new Circle(5), Body.INFINITE_MASS);
		b4.setPosition(150, 380);
		world.add(b4);

		final Body b5 = new Body(new Circle(10), 130);
		b5.setDamping(0.1f);
		b5.setPosition(0, 350);
		world.add(b5);
		chain(b1, b2, new Vector(), new Vector(0, -5), 14, world, 0.3f);
		chain(b5, b3, new Vector(10, 0), new Vector(-5, 0), 20, world, 0f);


		final Body b6 = new Body(new Circle(5), Body.INFINITE_MASS);
		b6.setPosition(440, 50);
		world.add(b6);

		final Body b7 = new Body(new Circle(5), Body.INFINITE_MASS);
		b7.setPosition(440, 340);
		world.add(b7);

		chain(b6, b7, new Vector(), new Vector(), 15, world,
				(float) Math.PI / 2f);
		
		for (int i = 0; i < 30; i++) {
			final Body b = new Body(new Circle(10), 10);
			b.setPosition(110, -i * 30-50);
			world.add(b);
		}
		//world.setGravity(0, 100);
	}

	/**
	 * Entry point to the test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		(new JointTest()).start();
	}

}
