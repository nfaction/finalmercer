package engine.collide;

import engine.Contact;
import engine.shapes.Body;
import engine.shapes.Circle;
import engine.vector.MathUtil;
import engine.vector.Vector;

/**
 * A collider for circle 2 circle collisions
 * 
 * @author Jeffery D. Ahern
 */
public strictfp class CircleCircleCollider implements Collider {	
	/**
	 * @see engine.collide.Collider#collide(engine.Contact[], engine.shapes.Body, engine.shapes.Body)
	 */
	public int collide(Contact[] contacts, Body bodyA, Body bodyB) {
		float x1 = bodyA.getPosition().getX();
		float y1 = bodyA.getPosition().getY();
		float x2 = bodyB.getPosition().getX();
		float y2 = bodyB.getPosition().getY();
		
		boolean touches = bodyA.getShape().getBounds().touches(x1,y1,bodyB.getShape().getBounds(),x2,y2);
		if (!touches) {
			return 0;
		}
		
		Circle circleA = (Circle) bodyA.getShape();
		Circle circleB = (Circle) bodyB.getShape();
		
		touches = circleA.touches(x1,y1,circleB,x2,y2);
		if (!touches) {
			return 0;
		}
		
		Vector normal = MathUtil.sub(bodyB.getPosition(),bodyA.getPosition());
		float sep = (circleA.getRadius() + circleB.getRadius()) - normal.length();

		normal.normalise();
		Vector pt = MathUtil.scale(normal, circleA.getRadius());
		pt.add(bodyA.getPosition());

		contacts[0].setSeparation(-sep);
		contacts[0].setPosition(pt);
		contacts[0].setNormal(normal);
		
		FeaturePair fp = new FeaturePair();
		contacts[0].setFeature(fp);

		return 1;
	}
}
