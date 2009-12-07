package engine.collide;

import engine.shapes.*;

/**
 * A collider factory to create colliders for bodies
 * 
 * @author Jeff Ahern
 * @author Matt DePortor
 * @author Keith Kowalski
 * @author Mcomber, Kevin
 * 
 */
public class ColliderFactory {

	/**
	 * Create a collider for two bodies. The decision depends on the body's
	 * shapes.
	 * 
	 * @param bodyA
	 *            First body in the collision test
	 * @param bodyB
	 *            Second body in the collision test
	 * @return A collider that can test wether the two bodies actually collide
	 * @throws ColliderUnavailableException
	 *             This exception will be thrown if no suitable collider can be
	 *             found.
	 */
	public Collider createCollider(Body bodyA, Body bodyB)
			throws ColliderUnavailableException {
		Shape shapeA = bodyA.getShape();
		Shape shapeB = bodyB.getShape();

		if (shapeA instanceof Circle) {
			return createColliderFor((Circle) shapeA, shapeB);
		} else if (shapeA instanceof Box) {
			return createColliderFor((Box) shapeA, shapeB);
		} else if (shapeA instanceof Line) {
			return createColliderFor((Line) shapeA, shapeB);
		}
		throw new ColliderUnavailableException(shapeA, shapeB);
	}

	/**
	 * Creates a collider for a Circle and a Shape.
	 * 
	 * @param shapeA
	 *            The circle to provide a collider for
	 * @param shapeB
	 *            The shape to provide a collider for
	 * @return a suitable collider
	 * @throws ColliderUnavailableException
	 *             This exception will be thrown if no suitable collider can be
	 *             found.
	 */
	public Collider createColliderFor(Circle shapeA, Shape shapeB)
			throws ColliderUnavailableException {

		if (shapeB instanceof Circle) {
			return new CircleCircleCollider();
		} else if (shapeB instanceof Box) {
			return new SwapCollider(new BoxCircleCollider());
		} else if (shapeB instanceof Line) {
			return new SwapCollider(new LineCircleCollider());
		}

		throw new ColliderUnavailableException(shapeA, shapeB);
	}

	/**
	 * Creates a collider for a Box and a Shape.
	 * 
	 * @param shapeA
	 *            The box to provide a collider for
	 * @param shapeB
	 *            The shape to provide a collider for
	 * @return a suitable collider
	 * @throws ColliderUnavailableException
	 *             This exception will be thrown if no suitable collider can be
	 *             found.
	 */
	public Collider createColliderFor(Box shapeA, Shape shapeB)
			throws ColliderUnavailableException {

		if (shapeB instanceof Circle) {
			return new BoxCircleCollider();
		} else if (shapeB instanceof Box) {
			return new BoxBoxCollider();
		} else if (shapeB instanceof Line) {
			return new SwapCollider(new LineBoxCollider());
		}

		throw new ColliderUnavailableException(shapeA, shapeB);
	}

	/**
	 * Creates a collider for a Line and a Shape.
	 * 
	 * @param shapeA
	 *            The line to provide a collider for
	 * @param shapeB
	 *            The shape to provide a collider for
	 * @return a suitable collider
	 * @throws ColliderUnavailableException
	 *             This exception will be thrown if no suitable collider can be
	 *             found.
	 */
	public Collider createColliderFor(Line shapeA, Shape shapeB)
			throws ColliderUnavailableException {

		if (shapeB instanceof Circle) {
			return new LineCircleCollider();
		} else if (shapeB instanceof Box) {
			return new LineBoxCollider();
		} else if (shapeB instanceof Line) {
			return new LineLineCollider();
		}

		throw new ColliderUnavailableException(shapeA, shapeB);
	}

}
