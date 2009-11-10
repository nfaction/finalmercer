package engine.test;

import engine.StaticBody;
import engine.World;
import engine.body.Body;
import engine.joint.BasicJoint;
import engine.shapes.ConvexPolygon;
import engine.shapes.Polygon;
import engine.vector.Vector;

/**
 * A demo showing gears inteacting in a most un-believeable way
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class GearDemo extends AbstractDemo {
	/** A wheel dropped into the scene */
	private Body wheel;
	
	/**
	 * Create a new gears demo instance
	 */
	public GearDemo() {
		super("Gears Demo");
	}

	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {
		Vector[] groundVerts = {new Vector(-200, -10), new Vector(200,-10), new Vector(200,10), new Vector(-200,10)};
		ConvexPolygon groundBox = new ConvexPolygon(groundVerts);
		Body ground = new StaticBody("ground", groundBox);
		ground.setPosition(250, 50);
		world.add(ground);
		
		{
			int noVerts = 40;
			Vector[] circleVerts = new Vector[noVerts];
			float[] radius = {50,42,42,50};
			for( int i = 0; i < noVerts; i++ ) {
				float angle = (float) (i* 2 * Math.PI/noVerts);
				circleVerts[i] = new Vector(
						(float) (Math.cos(angle) * radius[i%radius.length]), 
						(float) (Math.sin(angle) * radius[i%radius.length]));
			}
			Polygon circlePolygon = new Polygon(circleVerts);
			Body circle = new Body("circle", circlePolygon, 2);
			circle.setPosition(250, 150);
			world.add(circle);
			
			BasicJoint joint = new BasicJoint(ground, circle, new Vector(circle.getPosition()));
			world.add(joint);
		}
		{
			int outerCircleVerts = 30;
			int noVerts = 120;
			Vector[] circleVerts = new Vector[outerCircleVerts+1 + noVerts+1];
			for( int i = 0; i <= outerCircleVerts; i++ ) {
				float angle = (float) (i* 2 * Math.PI/outerCircleVerts);
				circleVerts[i] = new Vector(
						(float) (Math.cos(angle) * 150), 
						(float) (Math.sin(angle) * 150));
			}
			float[] radius = {140, 133, 133, 140};
			for( int i = 0; i <= noVerts; i++ ) {
				float angle = (float) (i* 2 * Math.PI/noVerts);
				circleVerts[outerCircleVerts+1 + noVerts-i] = new Vector(
						(float) (Math.cos(angle) * radius[i%radius.length]), 
						(float) (Math.sin(angle) * radius[i%radius.length]));
			}
			Polygon circlePolygon = new Polygon(circleVerts);
			Body circle = new Body("circle", circlePolygon, 30);
			circle.setPosition(250, 220);
			world.add(circle);
		}
		
		{
			int noVerts = 20;
			Vector[] circleVerts = new Vector[noVerts];
			float[] radius = {30,20,20,30};
			for( int i = 0; i < noVerts; i++ ) {
				float angle = (float) (i* 2 * Math.PI/noVerts);
				circleVerts[i] = new Vector(
						(float) (Math.cos(angle) * radius[i%radius.length]), 
						(float) (Math.sin(angle) * radius[i%radius.length]));
			}
			Polygon circlePolygon = new Polygon(circleVerts);
			Body circle = new Body("circle", circlePolygon, 2);
			circle.setPosition(250, 300);
			world.add(circle);
			
			Vector[] nonConvexPoly = {new Vector(-20,-10), new Vector(20,-10), new Vector(10,0), new Vector(20,10), new Vector(-20,10), new Vector(-10,0)};
			Polygon poly = new Polygon(nonConvexPoly);
			Body nonConvexBody = new Body("poly", poly, 25);
			nonConvexBody.setPosition(250, 400);
			world.add(nonConvexBody);
			
			BasicJoint joint = new BasicJoint(circle,nonConvexBody, new Vector(circle.getPosition()));
			world.add(joint);
			
			wheel = circle;
			circle.setRotDamping(10);
		}

	}

	/**
	 * @see engine.test.AbstractDemo#update()
	 */
	protected void update() {
		wheel.setTorque(20000);
	}
	
	/**
	 * Entry point to run this test stand alone
	 * 
	 * @param argv The arguments passed into the demo
	 */
	public static void main(String[] argv) {
		GearDemo demo = new GearDemo();
		demo.start();
	}

}
