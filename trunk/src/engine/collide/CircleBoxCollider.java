package engine.collide;

import engine.Contact;
import engine.body.Body;
import engine.shapes.Circle;
import engine.vector.MathUtil;
import engine.vector.Vector;

/**
 * A collider for circles hitting boxes, Circle = BodyA, Box = BodyB
 * 
 * The create() method is used as a factory incase this class should
 * ever become stateful.
 * 
 * @author Jeffery D. Ahern
 */
public strictfp class CircleBoxCollider extends BoxCircleCollider {
	private static CircleBoxCollider single = new CircleBoxCollider();
	
	public static CircleBoxCollider createCircleBoxCollider() {
		return single;
	}

	private CircleBoxCollider() {
	}
	
	/**
	 * @see engine.collide.Collider#collide(engine.Contact[], engine.body.Body, engine.body.Body)
	 */
	public int collide(Contact[] contacts, Body circleBody, Body boxBody) {
		int count = super.collide(contacts, boxBody, circleBody);
		
		for (int i=0;i<count;i++) {
			Vector vec = MathUtil.scale(contacts[i].getNormal(),-1);
			contacts[i].setNormal(vec);
			
			Vector pt = MathUtil.sub(contacts[i].getPosition(), circleBody.getPosition());
			pt.normalise();
			pt.scale(((Circle) circleBody.getShape()).getRadius());
			pt.add(circleBody.getPosition());
			contacts[i].setPosition(pt);
		}
		
		return count;
	}
}
