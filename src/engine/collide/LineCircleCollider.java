package engine.collide;

import engine.Contact;
import engine.body.Body;
import engine.shapes.Circle;
import engine.shapes.Line;
import engine.vector.Vector;
import engine.vector.Vector;

/**
 * Collision routines betwene a circle and a line. The create method is
 * provided in case this collider becomes stateful at some point.
 * 
 * @author Kevin Glass
 */
public strictfp class LineCircleCollider implements Collider {

	/**
	 * @see engine.collide.Collider#collide(engine.Contact[], engine.body.Body, engine.body.Body)
	 */
	public int collide(Contact[] contacts, Body bodyA, Body bodyB) {
		Line line = (Line) bodyA.getShape();
		Circle circle = (Circle) bodyB.getShape();
		
		Vector[] vertsA = line.getVertices(bodyA.getPosition(), bodyA.getRotation());
		
		// compute intersection of the line A and a line parallel to 
		// the line A's normal passing through the origin of B
		Vector startA = vertsA[0];
		Vector endA = vertsA[1];
		Vector startB = bodyB.getPosition();
		Vector endB = new Vector(endA);
		endB.sub(startA);
		endB.set(endB.y, -endB.x);
//		endB.add(startB);// TODO: inline endB into equations below, this last operation will be useless..
		
		//TODO: reuse mathutil.intersect
//		float d = (endB.y - startB.getY()) * (endA.x - startA.x);
//		d -= (endB.x - startB.getX()) * (endA.y - startA.y);
//		
//		float uA = (endB.x - startB.getX()) * (startA.y - startB.getY());
//		uA -= (endB.y - startB.getY()) * (startA.x - startB.getX());
//		uA /= d;
		float d = endB.y * (endA.x - startA.x);
		d -= endB.x * (endA.y - startA.y);
		
		float uA = endB.x * (startA.y - startB.getY());
		uA -= endB.y * (startA.x - startB.getX());
		uA /= d;
		
		Vector position = null;
		
		if ( uA < 0 ) { // the intersection is somewhere before startA
			position = startA;
		} else if ( uA > 1 ) { // the intersection is somewhere after endA
			position = endA;
		} else {
			position = new Vector(
					startA.x + uA * (endA.x - startA.x),
					startA.y + uA * (endA.y - startA.y));
		}
		
		Vector normal = endB; // reuse of vector object
		normal.set(startB);
		normal.sub(position);
		float distSquared = normal.lengthSquared();
		float radiusSquared = circle.getRadius() * circle.getRadius();
		
		if ( distSquared < radiusSquared ) {
			contacts[0].setPosition(position);
			contacts[0].setFeature(new FeaturePair());
			
			normal.normalise();
			contacts[0].setNormal(normal);
			
			float separation = (float) Math.sqrt(distSquared) - circle.getRadius();
			contacts[0].setSeparation(separation);
			
			return 1;
		}
		
		return 0;
	}

}
