package engine.test;

import engine.StaticBody;
import engine.World;
import engine.body.Body;
import engine.joint.DistanceJoint;
import engine.joint.SpringJoint;
import engine.joint.SpringyAngleJoint;
import engine.shapes.Circle;
import engine.vector.Vector;

/**
 * A test to demonstrate the SpringAngleJoint which can be used to produce free
 * standing springs.
 * 
 * @author Jeffery D. Ahern
 * @author Keith Kowalski
 * @author Matt DePorter
 * @author Kevin Mcomber
 */
public class SpringyTest extends AbstractDemo {
	/**
	 * Create a new test
	 */
	public SpringyTest(){
		super("Springy Test");
	}
	
	/**
	 * @see engine.test.AbstractDemo#init(engine.World)
	 */
	protected void init(World world) {
		Body knot = new StaticBody(new Circle(10));
		knot.setPosition(100,400);
		knot.setRotation(0.5f);
		world.add(knot);
		int N=8;
		Body balls[] = new Body[N];
		for(int i=0;i<N;i++){
			Body ball = new Body(new Circle(5),10);
			ball.setPosition(100,400-(i+1)*50);
			ball.setDamping(0.01f);
			world.add(ball);
			balls[i]=ball;
		}
		
		for(int i=0;i<N;i++){
			if(i==0){
				SpringyAngleJoint saj1 = new SpringyAngleJoint(knot,balls[i],new Vector(),new Vector(),1e6f,-(float)Math.PI/2.0f-0.15f);
				SpringyAngleJoint saj2 = new SpringyAngleJoint(balls[i],knot,new Vector(),new Vector(),1e6f,(float)Math.PI/2.0f);
				//DistantConstraint daj = new DistantConstraint(knot,balls[i],new Vector2f(),new Vector2f(),50);
				SpringJoint daj = new SpringJoint(knot,balls[i],new Vector(100,400),new Vector(balls[i].getPosition()));
				daj.setCompressedSpringConst(100);
				daj.setStretchedSpringConst(100);
				daj.setSpringSize(30);
				world.add(daj);
				world.add(saj1);
				world.add(saj2);
			}else{
				SpringyAngleJoint saj1 = new SpringyAngleJoint(balls[i-1],balls[i],new Vector(),new Vector(),1e6f,-(float)Math.PI/2.0f);
				SpringyAngleJoint saj2 = new SpringyAngleJoint(balls[i],balls[i-1],new Vector(),new Vector(),1e6f,(float)Math.PI/2.0f);
				//DistantConstraint daj = new DistantConstraint(balls[i-1],balls[i],new Vector2f(),new Vector2f(),50);
				SpringJoint daj = new SpringJoint(balls[i-1],balls[i],new Vector(balls[i-1].getPosition()),new Vector(balls[i].getPosition()));
				daj.setCompressedSpringConst(100);
				daj.setStretchedSpringConst(100);
				daj.setSpringSize(50);
				world.add(daj);
				world.add(saj1);
				world.add(saj2);
			}
		}
		Body balls1[] = new Body[N];
		for(int i=0;i<N;i++){
			Body ball = new Body(new Circle(5),10);
			ball.setPosition(100,400-(i+1)*50);
			ball.setDamping(0.01f);
			world.add(ball);
			balls1[i]=ball;
		}
		for(int i=0;i<N;i++){
			if(i==0){
				SpringyAngleJoint saj1 = new SpringyAngleJoint(knot,balls1[i],new Vector(),new Vector(),1e6f,-(float)Math.PI/2.0f+0.45f);
				SpringyAngleJoint saj2 = new SpringyAngleJoint(balls1[i],knot,new Vector(),new Vector(),1e6f,(float)Math.PI/2.0f);
				DistanceJoint daj = new DistanceJoint(knot,balls1[i],new Vector(),new Vector(),50);

				world.add(daj);
				world.add(saj1);
				world.add(saj2);
			}else{
				SpringyAngleJoint saj1 = new SpringyAngleJoint(balls1[i-1],balls1[i],new Vector(),new Vector(),1e6f,-(float)Math.PI/2.0f);
				SpringyAngleJoint saj2 = new SpringyAngleJoint(balls1[i],balls1[i-1],new Vector(),new Vector(),1e6f,(float)Math.PI/2.0f);
				DistanceJoint daj = new DistanceJoint(balls1[i-1],balls1[i],new Vector(),new Vector(),50);

				world.add(daj);
				world.add(saj1);
				world.add(saj2);
			}
		}
	}

	/**
	 * The entry point to the test
	 * 
	 * @param args The arguments to the test
	 */
	public static void main(String[] args) {
		(new SpringyTest()).start();
	}

}
