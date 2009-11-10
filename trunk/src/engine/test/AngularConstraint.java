package engine.test;

import engine.World;
import engine.body.Body;
import engine.joint.AngleJoint;
import engine.joint.DistanceJoint;
import engine.shapes.Circle;
import engine.vector.Vector;

/**
 * A test to demonstrate AngleJoint
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class AngularConstraint extends AbstractDemo {
	/** The body under test */
	private Body b1;
	
	/**
	 * Create a new test
	 */
	public AngularConstraint(){
		super("Angular Test");
	}
	
	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {
		b1= new Body(new Circle(15),1e3f);
		b1.setMoveable(false);
		b1.setPosition(250,250);
		b1.setRotDamping(10);
		b1.setRotation(0.01f);
		
		world.add(b1);
		final int N=15;
		final Body bodies[] = new Body[N];
		for(int i=0;i<N;i++){
			final Body b2 = new Body(new Circle(8),10);
			b2.setPosition(250+30*(1+i),250);
			world.add(b2);
			
			bodies[i]=b2;
		}
		
		final AngleJoint aj1 = new AngleJoint(b1,bodies[0],new Vector(),new Vector(),-(float)Math.PI/9.0f,-0.0f);
		//AngleJoint aj1 = new AngleJoint(b1,bodies[0],new Vector2f(),new Vector2f(),0.0f,-0.0f);
		final DistanceJoint dj1 = new DistanceJoint(b1,bodies[0],new Vector(),new Vector(),30);
		final AngleJoint aja2 = new AngleJoint(bodies[0],b1,new Vector(),new Vector(),-(float)Math.PI/9.0f+(float)Math.PI,(float)Math.PI);
		world.add(aja2);
		world.add(dj1);
		world.add(aj1);
		for(int i=1;i<N;i++){
			final AngleJoint aj = new AngleJoint(bodies[i-1],bodies[i],new Vector(),new Vector(),-(float)Math.PI/9.0f,-0.0f);
			world.add(aj);
			
			final AngleJoint aj2 = new AngleJoint(bodies[i],bodies[i-1],new Vector(),new Vector(),-(float)Math.PI/9.0f+(float)(Math.PI+Math.sin(i/10.0f)),(float)(Math.PI+Math.sin(i/10.0f)));
			world.add(aj2);
			
			final DistanceJoint dj = new DistanceJoint(bodies[i-1],bodies[i],new Vector(),new Vector(),30);
			world.add(dj);
		}
	}
	
	/**
	 * @see engine.test.AbstractDemo#update()
	 */
	protected void update(){
		b1.setTorque(20000);
	}

	/**
	 * Entry point for tetsing
	 * 
	 * @param argv The arguments to the test
	 */
	public static void main(String[] argv) {
		(new AngularConstraint()).start();
	}

}
