package entities;

import engine.World;
import engine.shapes.Body;
import engine.shapes.Box;
import engine.shapes.Circle;
import engine.shapes.StaticBody;
import engine.test.AbstractDemo;

/**
 * A simple demo with so flat blocks falling
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class TestBench extends AbstractDemo {
	/**
	 * Create the demo
	 */
	public TestBench() {
		super("Test Bench");
	}

	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {

//		Body ground1 = new StaticBody("Ground1", new Box(500, 1.0f));
//		ground1.setPosition(250.0f, 500);
//		ground1.setRestitution(1.0f);
//		world.add(ground1);

//		Body ground = new StaticBody("Ground1", new Box(225.0f, 5.0f));
//		ground.setPosition(350.0f, 300);
//		ground.setRestitution(1.0f);
	//	ground.setRotation(.4f);
//		world.add(ground);

		// HorizontalRubberWall
//		Body hRubberWall = new StaticBody("rHorzWall", new Box(125.0f, 10.0f));
//		hRubberWall.setPosition(150.0f, 250.0f);
//		hRubberWall.setRestitution(3.0f);
//		world.add(hRubberWall);
		
		// VerticalRubberWall
//		Body vRubberWall = new StaticBody("rVertWall", new Box(10.0f, 125.0f));
//		vRubberWall.setPosition(150.0f, 250.0f);
//		vRubberWall.setRestitution(3.0f);
//		world.add(vRubberWall);
		
		// HorizontalCementWall
//		Body hCementWall = new StaticBody("cHorzWall", new Box(125.0f, 10.0f));
//		hCementWall.setPosition(200.0f, 250.0f);
//		hCementWall.setRestitution(1.0f);
//		world.add(hCementWall);
//		
//		Body vCementWall = new StaticBody("cVertWall", new Box(10.0f, 125.0f));
//		vCementWall.setPosition(251.0f, 199.0f);
//		vCementWall.setRestitution(1.0f);
//		world.add(vCementWall);
		
		//rocket
//		Body rocket = new Body(new Box(3.0f, 50.0f), 10.0f);
//		rocket.setPosition(300.0f, 400.0f);
//		rocket.setGravityEffected(false);
//		world.add(rocket);
		
		//bouncy ball
//		Body bouncy = new Body("bouncyBall", new Circle(10.0f), 1.0f);
//		bouncy.setPosition(370.0f, 200);
//		bouncy.setRestitution(1.0f);
//		bouncy.setDamping(0.00009f);
//		bouncy.setCanRest(true);
//		world.add(bouncy);
		
		//weight
//		Body weight = new Body(new Box(50.0f, 50.0f),10000.0f);
//		weight.setPosition(370.0f, 100);
//		weight.setCanRest(true);
//		world.add(weight);
		
		// light
//		Body lamp = new StaticBody("lamp", new Box(30.0f, 45.0f));
//		lamp.setPosition(370.0f, 200.0f);
//		world.add(lamp);
		
		//pin
//		Body pin = new StaticBody("Pin", new Box(2.5f, 15.0f));
//		pin.setPosition(370.0f, 200.0f);
//		world.add(pin);
		
		// Blockade
//		Body blockade = new StaticBody("blockade", new Box(300.0f, 20.0f));
//		blockade.setPosition(100.0f, 100.0f);
//		blockade.setRotation(-.4f);
//		blockade.setRestitution(3.0f);
//		world.add(blockade);

		// Bowling Ball
		 Body bowlingBall = new Body("Bowling Ball", new Circle(15.0f),
		 16.0f);
		 bowlingBall.setPosition(100.0f, 200.0f);
		 bowlingBall.setRestitution(.5f);
		 bowlingBall.setDamping(.01f);
		 world.add(bowlingBall);

		// Ping-Pong Ball
		/*
		 * Body ppBall = new Body("Ping-Pong Ball", new Circle(5.0f), .006f);
		 * ppBall.setRestitution(.8f); ppBall.setDamping(.000007f);
		 * ppBall.setPosition(200.0f, 200); world.add(ppBall);
		 */

		// Balloon
//		Body balloon = new Body("Balloon", new Circle(15.0f), .5f);
//		balloon.setPosition(200.0f, 300.0f);
//		balloon.setGravityEffected(false);
//		balloon.setForce(0.0f, -1000.0f);
	//	balloon.setRestitution(.1f);
//		world.add(balloon);

	}

	/**
	 * Entry point for tetsing
	 * 
	 * @param argv
	 *            The arguments to the test
	 */
	public static void main(String[] argv) {
		TestBench demo = new TestBench();
		demo.start();
	}
}
