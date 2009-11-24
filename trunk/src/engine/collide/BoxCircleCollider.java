package engine.collide;

import engine.Contact;
import engine.body.Body;
import engine.shapes.Box;
import engine.shapes.Circle;
import engine.shapes.Line;
import engine.vector.MathUtil;
import engine.vector.Vector;

/**
 * A collider for boxes hitting circles. Box = bodyA, Circle = bodyB
 * 
 * @author Jeffery D. Ahern
 */
public strictfp class BoxCircleCollider implements Collider {	
	/**
	 * @see engine.collide.Collider#collide(engine.Contact[], engine.body.Body, engine.body.Body)
	 */
	public int collide(Contact[] contacts, Body boxBody, Body circleBody) {
		float x1 = boxBody.getPosition().getX();
		float y1 = boxBody.getPosition().getY();
		float x2 = circleBody.getPosition().getX();
		float y2 = circleBody.getPosition().getY();
		
		boolean touches = boxBody.getShape().getBounds().touches(x1,y1,circleBody.getShape().getBounds(),x2,y2);
		if (!touches) {
			return 0;
		}
		
		Box box = (Box) boxBody.getShape();
		Circle circle = (Circle) circleBody.getShape();
		
		Vector[] pts = box.getPoints(boxBody.getPosition(), boxBody.getRotation());
		Line[] lines = new Line[4];
		lines[0] = new Line(pts[0],pts[1]);
		lines[1] = new Line(pts[1],pts[2]);
		lines[2] = new Line(pts[2],pts[3]);
		lines[3] = new Line(pts[3],pts[0]);
		
		float r2 = circle.getRadius() * circle.getRadius();
		int closest = -1;
		float closestDistance = Float.MAX_VALUE;
		
		for (int i=0;i<4;i++) {
			float dis = lines[i].distanceSquared(circleBody.getPosition());
			if (dis < r2) {
				if (closestDistance > dis) {
					closestDistance = dis;
					closest = i;
				}
			}
		}
		
		if (closest > -1) {
			float dis = (float) Math.sqrt(closestDistance);
			contacts[0].setSeparation(dis - circle.getRadius());
			
			// this should really be where the edge and the line
			// between the two elements cross?
			Vector contactPoint = new Vector();
			lines[closest].getClosestPoint(circleBody.getPosition(), contactPoint);
			
			Vector normal = MathUtil.sub(circleBody.getPosition(), contactPoint);
			normal.normalise();
			contacts[0].setNormal(normal);
			contacts[0].setPosition(contactPoint);
			contacts[0].setFeature(new FeaturePair());
			
			return 1;
		}
		
		return 0;
	}
	
}
