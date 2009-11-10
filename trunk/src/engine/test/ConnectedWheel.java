package engine.test;

import engine.Body;
import engine.DistanceJoint;
import engine.FixedAngleJoint;
import engine.StaticBody;
import engine.World;
import engine.shapes.Circle;
import engine.vector.Vector2f;

/**
 * A test to show the FixedAngleJoint at work
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class ConnectedWheel extends AbstractDemo {
	/** The wheel being turned */
	private Body wheel;
	
	/**
	 * Create a new test
	 */
	public ConnectedWheel(){
		super("Connected Wheel");
	}
	
	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {
		Body leftAxis = new StaticBody(new Circle(10));
		leftAxis.setPosition(100,250);
		leftAxis.setRestitution(1);
		world.add(leftAxis);
		
		wheel = new Body(new Circle(70),400);
		wheel.setPosition(400, 250);
		wheel.setMoveable(false);
		wheel.setDamping(5f);
		world.add(wheel);
		
		Body socket = new Body(new Circle(10),50);
		socket.setPosition(300, 250);
		socket.setRestitution(1);
		world.add(socket);
		
		FixedAngleJoint angle = new FixedAngleJoint(leftAxis,socket,new Vector2f(),new Vector2f(),0);
		world.add(angle);
		
		DistanceJoint dist = new DistanceJoint(socket,wheel,new Vector2f(),new Vector2f(65,0),165);
		world.add(dist);
	}
	
	/**
	 * @see engine.test.AbstractDemo#update()
	 */
	protected void update(){
		wheel.setTorque(20000);
	}

	/**
	 * Entry point for tetsing
	 * 
	 * @param argv The arguments to the test
	 */
	public static void main(String[] argv) {
		(new ConnectedWheel()).start();
	}

}